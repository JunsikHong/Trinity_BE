package com.engineer.Trinity_BE.domain.repair.service;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneRepository;
import com.engineer.Trinity_BE.domain.maintenance.entity.MaintenanceReport;
import com.engineer.Trinity_BE.domain.maintenance.exception.MaintenanceException;
import com.engineer.Trinity_BE.domain.repair.dto.request.RepairRequest;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairDetailResponse;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairSummaryResponse;
import com.engineer.Trinity_BE.domain.repair.dto.response.RepairValueResponse;
import com.engineer.Trinity_BE.domain.repair.entity.Repair;
import com.engineer.Trinity_BE.domain.repair.entity.RepairField;
import com.engineer.Trinity_BE.domain.repair.entity.RepairValue;
import com.engineer.Trinity_BE.domain.repair.exception.RepairException;
import com.engineer.Trinity_BE.domain.repair.repository.RepairFieldRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairRepository;
import com.engineer.Trinity_BE.domain.repair.repository.RepairValueRepository;
import com.engineer.Trinity_BE.domain.user.entity.User;
import com.engineer.Trinity_BE.domain.user.repository.UserRepository;
import com.engineer.Trinity_BE.global.security.principal.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;
    private final RepairFieldRepository repairFieldRepository;
    private final RepairValueRepository repairValueRepository;
    private final UserRepository userRepository;
    private final AirplaneRepository airplaneRepository;

    @Transactional
    public List<RepairSummaryResponse> findAll(Long airplaneId) {
        List<Repair> repairs = repairRepository.findAllByAirplaneIdWithUser(airplaneId);
        return repairs.stream().map(repair -> RepairSummaryResponse.builder()
                .repairId(repair.getId())
                .description(repair.getDescription())
                .createdAt(repair.getCreatedAt())
                .updatedAt(repair.getUpdatedAt())
                .build()).toList();
    }

    @Transactional
    public RepairDetailResponse findOne(Long repairId) {
        Repair repair = repairRepository.findByIdWithUserAndAirplane(repairId)
                .orElseThrow(() -> new RepairException("정비이력을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        List<RepairValueResponse> repairValueResponses = repairValueRepository.findAllByRepairIdWithField(repairId)
                .stream()
                .map(value -> RepairValueResponse.builder()
                        .repairFieldId(value.getRepairField().getId())
                        .fieldLabel(value.getRepairField().getFieldLabel())
                        .fieldName(value.getRepairField().getFieldName())
                        .fieldType(value.getRepairField().getFieldType())
                        .required(value.getRepairField().isRequired())
                        .fieldOrder(value.getRepairField().getFieldOrder())
                        .value(value.getValue()).build())
                .toList();

        return RepairDetailResponse.builder()
                .repairId(repair.getId())
                .airplaneId(repair.getAirplane().getId())
                .airplaneRegistrationNumber(repair.getAirplane().getRegistrationNumber())
                .writerName(repair.getUser().getName())
                .description(repair.getDescription())
                .createdAt(repair.getCreatedAt())
                .updatedAt(repair.getUpdatedAt())
                .repairValueResponses(repairValueResponses)
                .build();
    }

    @Transactional
    public void create(RepairRequest request, CustomUserDetails userDetails) {
        User user = getUserOrThrow(userDetails.getUserId());
        Airplane airplane = getAirplaneOrThrow(request.getAirplaneId());
        Repair repair = Repair.builder()
                .user(user)
                .airplane(airplane)
                .description(request.getDescription())
                .build();

        repairRepository.save(repair);

        List<Long> ids = request.getValues().stream()
                .map(RepairRequest.RepairValueRequest::getRepairFieldId).toList();

        Map<Long,RepairField> fieldMap = repairFieldRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(
                        RepairField::getId,
                        Function.identity()
                ));

        List<RepairValue> values = request.getValues().stream()
                .map(repairValueRequest -> {
                    RepairField repairField = repairFieldRepository.findById(repairValueRequest.getRepairFieldId())
                            .orElseThrow(() -> new RepairException("입력필드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
                    return RepairValue.builder()
                            .repair(repair)
                            .repairField(repairField)
                            .value(repairValueRequest.getValue())
                            .build();
                }).toList();

        repairValueRepository.saveAll(values);
    }

    @Transactional
    public void update(Long repairId, RepairRequest request, CustomUserDetails userDetails) throws AccessDeniedException {
        Repair repair = repairRepository.findByIdWithUserAndAirplane(repairId)
                .orElseThrow(() -> new RepairException("정비이력을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        validateWritePermission(repair, userDetails);

        repairValueRepository.deleteAllByRepairId(repairId);

        repair.changeDescription(request.getDescription());

        List<RepairValue> values = request.getValues().stream().map(repairValueRequest -> {
            RepairField repairField = repairFieldRepository.findById(repairValueRequest.getRepairFieldId())
                    .orElseThrow(() -> new RepairException("입력필드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
            return RepairValue.builder()
                    .repair(repair)
                    .repairField(repairField)
                    .value(repairValueRequest.getValue())
                    .build();
        }).toList();
    }

    @Transactional
    public void delete(Long repairId, CustomUserDetails userDetails) throws AccessDeniedException {
        Repair repair = repairRepository.findByIdWithUserAndAirplane(repairId)
                .orElseThrow(() -> new RepairException("정비이력을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        validateWritePermission(repair, userDetails);

        repairValueRepository.deleteAllByRepairId(repairId);

        repairRepository.delete(repair);
    }

    private void validateWritePermission(Repair repair, CustomUserDetails userDetails) throws AccessDeniedException {
        if("ADMIN".equals(userDetails.getUserRole())) {
            return;
        }

        if(!repair.getUser().getId().equals(userDetails.getUserId())) {
            throw new AccessDeniedException("본인이 작성한 정비 이력만 수정/삭제 할 수 있습니다.");
        }
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RepairException("사용자를 찾을 수 없습니다. id=" + userId, HttpStatus.NOT_FOUND));
    }

    private Airplane getAirplaneOrThrow(Long airplaneId) {
        return airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new RepairException("항공기를 찾을 수 없습니다. id=" + airplaneId, HttpStatus.NOT_FOUND));
    }
}
