package binarbej.challenge.chapter7.controller;

import binarbej.challenge.chapter7.model.Seat;
import binarbej.challenge.chapter7.repository.SeatRepository;
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
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data seat
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Seat> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Seat> seatList = new ArrayList<>();
            for (Seat data : param) {
                Seat seat = new Seat();
                String uuid = UUID.randomUUID().toString();

                seat.setSeatId(uuid);
                seat.setNoKursi(data.getNoKursi());
                seat.setStudioId(data.getStudioId());
                seat.setCreated(new Date());
                seat.setCreatedBy(data.getCreatedBy());
                seat.setUpdated(new Date());
                seat.setUpdatedBy("SYSTEM");
                seat.setIsactive("Y");

                seatList.add(seat);
            }
            seatRepository.saveAll(seatList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(seatList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data seat
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Seat> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Seat> seatList = new ArrayList<>();
            for (Seat data : param) {
                Seat seat = seatRepository.getById(data.getSeatId());

                seat.setNoKursi(data.getNoKursi());
                seat.setStudioId(data.getStudioId());
                seat.setUpdated(new Date());
                seat.setUpdatedBy(data.getUpdatedBy());
                seat.setIsactive(data.getIsactive());

                seatList.add(seat);
            }
            seatRepository.saveAll(seatList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(seatList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete Data seat By seat_id
    @DeleteMapping("/delete/{seatId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("seatId") String seatId) {
        MessageModel msg = new MessageModel();
        try {
            seatRepository.deleteById(seatId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data seat By seat_id
    @GetMapping("/getData/{seatId}")
    public ResponseEntity<MessageModel> getById(@PathVariable("seatId") String seatId) {
        MessageModel msg = new MessageModel();
        try {
            Seat data = seatRepository.getById(seatId);

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

    // Get List Data seat
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Seat> data = (List<Seat>) seatRepository.findAll();

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

    // Get Data Pagination From seat
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Seat> data = seatRepository.findAll(pageRequest);

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

}