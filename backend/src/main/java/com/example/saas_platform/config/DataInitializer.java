package com.example.saas_platform.config;

import com.example.saas_platform.model.Plan;
import com.example.saas_platform.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PlanRepository planRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            if (planRepository.count() == 0) {
                planRepository.save(Plan.builder()
                        .name("Basic")
                        .description("Perfect for individuals")
                        .price(new BigDecimal("9.99"))
                        .interval("month")
                        .stripePriceId("price_basic_id")
                        .build());

                planRepository.save(Plan.builder()
                        .name("Pro")
                        .description("Best for small teams")
                        .price(new BigDecimal("29.99"))
                        .interval("month")
                        .stripePriceId("price_pro_id")
                        .build());

                planRepository.save(Plan.builder()
                        .name("Enterprise")
                        .description("Unlimited possibilities")
                        .price(new BigDecimal("99.99"))
                        .interval("month")
                        .stripePriceId("price_enterprise_id")
                        .build());
            }
        } catch (Exception e) {
            System.err.println("Data initialization failed: " + e.getMessage());
        }
    }
}
