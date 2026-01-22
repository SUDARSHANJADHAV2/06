package com.example.saas_platform.controller;

import com.example.saas_platform.dto.SubscriptionResponse;
import com.example.saas_platform.model.Plan;
import com.example.saas_platform.model.User;
import com.example.saas_platform.repository.UserRepository;
import com.example.saas_platform.service.PaymentService;
import com.example.saas_platform.service.SubscriptionService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/plans")
    public List<Plan> getPlans() {
        return subscriptionService.getAllPlans();
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> createCheckoutSession(@RequestBody Map<String, String> request, Authentication authentication) throws StripeException {
        String priceId = request.get("priceId");
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        String url = paymentService.createCheckoutSession(user, priceId);
        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getSubscriptionStatus(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.getSubscription() == null) {
            return ResponseEntity.ok().build();
        }

        SubscriptionResponse response = SubscriptionResponse.builder()
                .status(user.getSubscription().getStatus())
                .planName(user.getSubscription().getPlan().getName())
                .currentPeriodEnd(user.getSubscription().getCurrentPeriodEnd())
                .build();

        return ResponseEntity.ok(response);
    }
}
