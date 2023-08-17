package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.entity.Credit;
import com.github.nst1610.neoflex.project.deal.repository.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditService {
    private final CreditRepository repository;

    public void save(Credit credit) {
        repository.save(credit);
    }
}
