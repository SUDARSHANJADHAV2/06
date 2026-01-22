package com.example.saas_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse {
    private String status;
    private String planName;
    private LocalDateTime currentPeriodEnd;
}
