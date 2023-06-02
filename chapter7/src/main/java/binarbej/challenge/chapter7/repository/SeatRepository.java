package binarbej.challenge.chapter7.repository;

import binarbej.challenge.chapter7.model.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends PagingAndSortingRepository<Seat, String> {
    @Query(value = "SELECT s FROM Seat s WHERE seatId = :seatId")
    Seat getById(@Param("seatId") String seatId);

}