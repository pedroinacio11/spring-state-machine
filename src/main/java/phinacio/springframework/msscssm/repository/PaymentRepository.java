package phinacio.springframework.msscssm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phinacio.springframework.msscssm.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
