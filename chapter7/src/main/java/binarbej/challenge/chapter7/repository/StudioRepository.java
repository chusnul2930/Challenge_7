package binarbej.challenge.chapter7.repository;

import binarbej.challenge.chapter7.model.Studio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends PagingAndSortingRepository<Studio, String> {
    @Query(value = "SELECT s FROM Studio s WHERE studioId = :studioId")
    Studio getById(@Param("studioId") String studioId);

}