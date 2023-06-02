package binarbej.challenge.chapter7.repository;

import binarbej.challenge.chapter7.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, String> {
    @Query(value = "SELECT u FROM Users u WHERE userId = :userId")
    Users getById(@Param("userId") String userId);

}