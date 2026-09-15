package com.orbitbase.service;

import com.orbitbase.model.Launch;
import com.orbitbase.repository.LaunchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaunchService {

    private final LaunchRepository launchRepository;

    @Cacheable("launches")
    public List<Launch> getAllLaunches() {
        return launchRepository.findAll();
    }

    @Cacheable(value = "launches", key = "#agency + '_' + #status")
    public List<Launch> searchLaunches(String agency, String status) {
        return launchRepository.searchLaunches(agency, status);
    }
}
