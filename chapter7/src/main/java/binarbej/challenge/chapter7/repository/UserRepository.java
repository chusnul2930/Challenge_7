package binarbej.challenge.chapter7.repository;

import binarbej.challenge.chapter7.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    @Query(value = "SELECT u FROM User u WHERE userId = :userId")
    User getById(@Param("userId") String userId);

}