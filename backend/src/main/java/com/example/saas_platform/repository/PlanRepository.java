package com.example.saas_platform.repository;

import com.example.saas_platform.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByStripePriceId(String stripePriceId);
}
