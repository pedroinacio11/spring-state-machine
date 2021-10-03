package phinacio.springframework.msscssm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;
import phinacio.springframework.msscssm.domain.Payment;
import phinacio.springframework.msscssm.domain.PaymentEvent;
import phinacio.springframework.msscssm.domain.PaymentState;
import phinacio.springframework.msscssm.repository.PaymentRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {

        payment = Payment.builder().amount(new BigDecimal("12.99")).build();

    }

    @Transactional
    @Test
    void preAuth() {

        Payment savedPayment = paymentService.newPayment(payment);

        System.out.println("Deve ser NEW");
        System.out.println(savedPayment.getState());

        StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());
        Payment preAuthedPayment = paymentRepository.getById(savedPayment.getId());

        System.out.println("Deve ser PRE_AUTH ou PRE_AUTH_ERROR");
        System.out.println(sm.getState().getId());

        System.out.println(preAuthedPayment);

    }

    @Transactional
    @RepeatedTest(10)
    void testAuth() {

        Payment savedPayment = paymentService.newPayment(payment);

        StateMachine<PaymentState, PaymentEvent> preAuthSM = paymentService.preAuth(savedPayment.getId());

        if (preAuthSM.getState().getId() == PaymentState.PRE_AUTH) {
            System.out.println("Pagamento foi pré autorizado");

            StateMachine<PaymentState, PaymentEvent> authSM = paymentService.authorizePayment(savedPayment.getId());
            System.out.println("Resultado do Autorização " + authSM.getState().getId());
        } else {
            System.out.println("Pagamento falhou na pre autorização...");
        }

    }
}