package com.tfg.koeshiru.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.tfg.koeshiru.config.PayPalConfig;
import com.tfg.koeshiru.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PayPalService {

    private final PayPalHttpClient client;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    public PayPalService(PayPalConfig config) {
        this.client = config.payPalClient();
    }

    //aqui se crea la orden de cobro
    public String createOrder() throws IOException {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("TFG Premium")
                .landingPage("NO_PREFERENCE")
                .cancelUrl("https://example.com/cancel")
                .returnUrl("https://example.com/success")
                .userAction("PAY_NOW");

        AmountWithBreakdown amount = new AmountWithBreakdown().currencyCode("EUR").value("1000");
        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest().amountWithBreakdown(amount);

        orderRequest.applicationContext(applicationContext);
        orderRequest.purchaseUnits(List.of(purchaseUnit));

        OrdersCreateRequest request = new OrdersCreateRequest()
                .requestBody(orderRequest);

        HttpResponse<Order> response = client.execute(request);
        return response.result().id(); // Devuelve ID de la orden (para el frontend)
    }


    //aqui se captura la orden después de la aprobación del pago
    //tambien recibimos los datos del pagador, como correo, nombre e id.
    public Map<String, String> captureOrder(String orderId, String userId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());

        HttpResponse<Order> response = client.execute(request);

        if (response.statusCode() == 201) {
            Order order = response.result();

            String status = order.status();
            String payerEmail = order.payer().email();
            String payerName = order.payer().name().givenName() + " " + order.payer().name().surname();
            String payerId = order.payer().payerId();

            if ("COMPLETED".equals(status)) {
                // ⚠️ Actualiza usuario como premium
                usuarioRepository.findById(Long.parseLong(userId)).ifPresent(usuario -> {
                    usuario.setPremium(true);
                    usuarioRepository.save(usuario);
                });
            }

            Map<String, String> result = new HashMap<>();
            result.put("status", status);
            result.put("payerEmail", payerEmail);
            result.put("payerName", payerName);
            result.put("payerId", payerId);

            return result;
        } else {
            throw new RuntimeException("No se pudo capturar la orden");
        }
    }

}
