package phinacio.springframework.msscssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import phinacio.springframework.msscssm.domain.Payment;
import phinacio.springframework.msscssm.domain.PaymentEvent;
import phinacio.springframework.msscssm.domain.PaymentState;
import phinacio.springframework.msscssm.services.PaymentServiceImpl;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class PaymentController {

    @Autowired
    PaymentServiceImpl paymentService;

    @GetMapping("/iniciar")
    public void iniciarStateMachine() {

        paymentService.newPayment(paymentBuilder());
        paymentService.preAuth(Long.valueOf(1));
        paymentService.authorizePayment(Long.valueOf(1));


    }

    public Payment paymentBuilder() {

        return Payment.builder()
                .id(Long.valueOf(1))
                .state(null)
                .amount(BigDecimal.TEN)
                .build();
    }

}
