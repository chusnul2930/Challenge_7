package binarbej.challenge.chapter7.repository;

import binarbej.challenge.chapter7.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<Payment, String> {
    @Query(value = "SELECT p FROM Payment p WHERE paymentId = :paymentId")
    Payment getById(@Param("paymentId") String paymentId);

    @Query(value = "SELECT p FROM Payment p WHERE bookingId = :bookingId")
    List<Payment> getByBooking(@Param("bookingId") String bookingId);

}