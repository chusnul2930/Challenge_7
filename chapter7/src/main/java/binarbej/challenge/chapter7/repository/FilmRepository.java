package binarbej.challenge.chapter7.repository;

import binarbej.challenge.chapter7.model.Film;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends PagingAndSortingRepository<Film, String> {
    @Query(value = "SELECT f FROM Film f WHERE filmId = :filmId")
    Film getById(@Param("filmId") String filmId);

    @Query(value = "SELECT f FROM Film f WHERE genreId = :genreId")
    List<Film> getByGenre(@Param("genreId") String genreId);

}