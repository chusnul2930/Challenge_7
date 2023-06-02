package binarbej.challenge.chapter7.controller;

import binarbej.challenge.chapter7.model.Film;
import binarbej.challenge.chapter7.repository.FilmRepository;
import binarbej.challenge.chapter7.service.SortAscDesc;
import binarbej.challenge.chapter7.utils.MessageModel;
import binarbej.challenge.chapter7.utils.MessageModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/film")
public class FilmController {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data film
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Film> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Film> filmList = new ArrayList<>();
            for (Film data : param) {
                Film film = new Film();
                String uuid = UUID.randomUUID().toString();

                film.setFilmId(uuid);
                film.setGenreId(data.getGenreId());
                film.setKode(data.getKode());
                film.setJudul(data.getJudul());
                film.setKategori(data.getKategori());
                film.setDesc(data.getDesc());
                film.setCreated(new Date());
                film.setCreatedBy(data.getCreatedBy());
                film.setUpdated(new Date());
                film.setUpdatedBy("SYSTEM");
                film.setIsactive("Y");

                filmList.add(film);
            }
            filmRepository.saveAll(filmList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(filmList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data film
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Film> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Film> filmList = new ArrayList<>();
            for (Film data : param) {
                Film film = filmRepository.getById(data.getFilmId());

                film.setGenreId(data.getGenreId());
                film.setKode(data.getKode());
                film.setJudul(data.getJudul());
                film.setKategori(data.getKategori());
                film.setDesc(data.getDesc());
                film.setUpdated(new Date());
                film.setUpdatedBy(data.getUpdatedBy());
                film.setIsactive(data.getIsactive());

                filmList.add(film);
            }
            filmRepository.saveAll(filmList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(filmList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete data film By film_id
    @DeleteMapping("/delete/{filmId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("filmId") String filmId) {
        MessageModel msg = new MessageModel();
        try {
            filmRepository.deleteById(filmId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data film By film_id
    @GetMapping("/getData/{filmId}")
    public ResponseEntity<MessageModel> detById(@PathVariable("filmId") String filmId) {
        MessageModel msg = new MessageModel();
        try {
            Film data = filmRepository.getById(filmId);

            msg.setStatus(true);
            msg.setMessage("Success to get data..");
            msg.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get List All Data film
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Film> data = (List<Film>) filmRepository.findAll();

            msg.setStatus(true);
            msg.setMessage("Success to get all data..");
            msg.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data Pagination From film
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Film> data = filmRepository.findAll(pageRequest);

            msg.setStatus(true);
            msg.setMessage("Success to get all data..");
            msg.setData(data.getContent());
            msg.setCurrentPage(data.getNumber());
            msg.setTotalPages(data.getTotalPages());
            msg.setTotalItems((int) data.getTotalElements());
            msg.setNumberOfElement(data.getNumberOfElements());

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data film By genre_id
    @GetMapping("/getData/genre/{genreId}")
    public ResponseEntity<MessageModel> getByGenre(@PathVariable("genreId") String genreId) {
        MessageModel msg = new MessageModel();
        try {
            List<Film> data = filmRepository.getByGenre(genreId);

            msg.setStatus(true);
            msg.setMessage("Success to get data..");
            msg.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

}