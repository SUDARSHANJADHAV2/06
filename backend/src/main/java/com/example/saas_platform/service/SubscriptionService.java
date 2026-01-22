package com.example.saas_platform.service;

import com.example.saas_platform.model.Plan;
import com.example.saas_platform.model.Subscription;
import com.example.saas_platform.model.User;
import com.example.saas_platform.repository.PlanRepository;
import com.example.saas_platform.repository.SubscriptionRepository;
import com.example.saas_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public void updateSubscription(String stripeSubscriptionId, String stripeCustomerId, String stripePriceId, String status, Long periodEnd) {
        User user = userRepository.findByStripeCustomerId(stripeCustomerId)
                .orElseThrow(() -> new RuntimeException("User not found for customer: " + stripeCustomerId));

        Plan plan = planRepository.findByStripePriceId(stripePriceId)
                .orElseThrow(() -> new RuntimeException("Plan not found for price: " + stripePriceId));

        Subscription subscription = subscriptionRepository.findByUser(user)
                .orElse(new Subscription());

        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStripeSubscriptionId(stripeSubscriptionId);
        subscription.setStatus(status);
        subscription.setCurrentPeriodEnd(LocalDateTime.ofInstant(Instant.ofEpochSecond(periodEnd), ZoneId.systemDefault()));

        subscriptionRepository.save(subscription);
    }

    public void cancelSubscription(String stripeSubscriptionId) {
        subscriptionRepository.findByStripeSubscriptionId(stripeSubscriptionId).ifPresent(sub -> {
            sub.setStatus("canceled");
            subscriptionRepository.save(sub);
        });
    }
}
