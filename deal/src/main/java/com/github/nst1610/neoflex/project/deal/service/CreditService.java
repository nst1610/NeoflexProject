package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.repository.entity.Credit;
import com.github.nst1610.neoflex.project.deal.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository repository;

    public void save(Credit credit) {
        repository.save(credit);
    }
}
