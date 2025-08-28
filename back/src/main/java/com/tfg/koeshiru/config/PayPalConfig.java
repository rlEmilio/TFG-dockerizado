package com.tfg.koeshiru.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    public PayPalHttpClient payPalClient() {
        PayPalEnvironment environment = mode.equalsIgnoreCase("sandbox") ?
                new PayPalEnvironment.Sandbox(clientId, clientSecret) :
                new PayPalEnvironment.Live(clientId, clientSecret);
        return new PayPalHttpClient(environment);
    }
}
