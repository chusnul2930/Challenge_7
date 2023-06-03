package binarbej.challenge.chapter7.repository;

import binarbej.challenge.chapter7.model.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, String> {
    @Query(value = "SELECT b FROM Booking b WHERE bookingId = :bookingId")
    Booking getById(@Param("bookingId") String bookingId);

    @Query(value = "SELECT b FROM Booking b WHERE scheduleId = :scheduleId")
    List<Booking> getBySchedule(@Param("scheduleId") String scheduleId);

}