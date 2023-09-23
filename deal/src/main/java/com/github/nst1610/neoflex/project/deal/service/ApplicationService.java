package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.repository.entity.Application;
import com.github.nst1610.neoflex.project.deal.api.exception.ApplicationNotFoundException;
import com.github.nst1610.neoflex.project.deal.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository repository;

    @Transactional
    public Application save(Application application) {
        return repository.save(application);
    }

    public Application get(Long id) {
        return repository.findById(id).orElseThrow(ApplicationNotFoundException::new);
    }

    public List<Application> getAll() {
        return repository.findAll();
    }
}
