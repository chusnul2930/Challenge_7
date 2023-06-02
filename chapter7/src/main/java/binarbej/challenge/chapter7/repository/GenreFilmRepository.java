package binarbej.challenge.chapter7.repository;

import binarbej.challenge.chapter7.model.GenreFilm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreFilmRepository extends PagingAndSortingRepository<GenreFilm, String> {
    @Query(value = "SELECT gf FROM GenreFilm gf WHERE genreId = :genreId")
    GenreFilm getById(@Param("genreId") String genreId);

}