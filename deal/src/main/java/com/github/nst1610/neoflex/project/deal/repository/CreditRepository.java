package com.github.nst1610.neoflex.project.deal.repository;

import com.github.nst1610.neoflex.project.deal.repository.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
}
