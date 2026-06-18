package com.engineer.Trinity_BE.domain.maintenance.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceReportRequest {

    @NotNull
    private Long airplaneId;

    private Double chapter;
    private Double station;
    private Double waterLine;
    private Double buttockLine;
    private Double stringer;
    private Double frame;
    private Double rib;
    private Double wingStation;
    private Double bodyStation;

    private String description;

    private List<Long> deleteFileIds;

    @AssertTrue(message = "위치정보 중 최소 하나는 필수로 입력해야 합니다.")
    public boolean existPositionInfo() {
        return chapter != null
                || station != null
                || waterLine != null
                || buttockLine != null
                || stringer != null
                || frame != null
                || rib != null
                || wingStation != null
                || bodyStation != null;
    }

}
