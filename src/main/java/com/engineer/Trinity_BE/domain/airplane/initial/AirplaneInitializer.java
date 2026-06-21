package com.engineer.Trinity_BE.domain.airplane.initial;

import com.engineer.Trinity_BE.domain.airplane.entity.Airplane;
import com.engineer.Trinity_BE.domain.airplane.entity.AirplaneType;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneRepository;
import com.engineer.Trinity_BE.domain.airplane.repository.AirplaneTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirplaneInitializer {

    private final AirplaneTypeRepository airplaneTypeRepository;
    private final AirplaneRepository airplaneRepository;

    @PostConstruct
    public void init() {
        AirplaneType airplaneType;
        if(!airplaneTypeRepository.existsByName("B737-800")) {
           airplaneType = airplaneTypeRepository.save(
                   AirplaneType.builder()
                           .name("B737-800")
                           .build()
           );
        } else {
            airplaneType = airplaneTypeRepository.findByName("B737-800")
                    .orElseThrow();
        }

        if(!airplaneRepository.existsByRegistrationNumber("HL1234")) {
            airplaneRepository.save(
                    Airplane.builder()
                            .airplaneType(airplaneType)
                            .registrationNumber("HL1234")
                            .build()
            );
        }

        if(!airplaneRepository.existsByRegistrationNumber("HL2345")) {
            airplaneRepository.save(
                    Airplane.builder()
                            .airplaneType(airplaneType)
                            .registrationNumber("HL2345")
                            .build()
            );
        }
    }
}
