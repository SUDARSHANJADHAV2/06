package com.example.saas_platform.service;

import com.example.saas_platform.model.User;
import com.example.saas_platform.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public String createCheckoutSession(User user, String priceId) throws StripeException {
        if (user.getStripeCustomerId() == null) {
            CustomerCreateParams params = CustomerCreateParams.builder()
                    .setEmail(user.getEmail())
                    .build();
            Customer customer = Customer.create(params);
            user.setStripeCustomerId(customer.getId());
            userRepository.save(user);
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setCustomer(user.getStripeCustomerId())
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(priceId)
                        .setQuantity(1L)
                        .build())
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
