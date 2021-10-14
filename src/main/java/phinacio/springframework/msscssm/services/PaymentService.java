package phinacio.springframework.msscssm.services;

import org.springframework.statemachine.StateMachine;
import phinacio.springframework.msscssm.domain.Payment;
import phinacio.springframework.msscssm.domain.PaymentEvent;
import phinacio.springframework.msscssm.domain.PaymentState;

public interface PaymentService {

    Payment newPayment(Payment payment);
    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId);

}
