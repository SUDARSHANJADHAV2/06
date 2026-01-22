package com.example.saas_platform.controller;

import com.example.saas_platform.service.SubscriptionService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(400).body("Invalid signature");
        }

        switch (event.getType()) {
            case "checkout.session.completed":
                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
                break;
            case "customer.subscription.updated":
                Subscription subscription = (Subscription) event.getDataObjectDeserializer().getObject().get();
                handleSubscriptionUpdated(subscription);
                break;
            case "customer.subscription.deleted":
                Subscription deletedSubscription = (Subscription) event.getDataObjectDeserializer().getObject().get();
                handleSubscriptionDeleted(deletedSubscription);
                break;
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("Success");
    }

    private void handleSubscriptionUpdated(Subscription stripeSubscription) {
        subscriptionService.updateSubscription(
                stripeSubscription.getId(),
                stripeSubscription.getCustomer(),
                stripeSubscription.getItems().getData().get(0).getPrice().getId(),
                stripeSubscription.getStatus(),
                stripeSubscription.getCurrentPeriodEnd()
        );
    }

    private void handleSubscriptionDeleted(Subscription stripeSubscription) {
        subscriptionService.cancelSubscription(stripeSubscription.getId());
    }
}
