package org.example.booktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.booktracker.domain.city.CityEntity;
import org.example.booktracker.repository.CityRepository;
import org.example.booktracker.utils.SuccessCreated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    private static final LocalDateTime DEFAULT_CREATED_AT = LocalDateTime.now();

    @Transactional
    public SuccessCreated createCity(
            String name
    ){
        var savedCity = cityRepository.save(new CityEntity(name));

        return new SuccessCreated(
                savedCity.toString(),
                "Message",
                DEFAULT_CREATED_AT.toString()
        );
    }
}
