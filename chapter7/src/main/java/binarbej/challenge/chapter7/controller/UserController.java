package binarbej.challenge.chapter7.controller;

import binarbej.challenge.chapter7.model.User;
import binarbej.challenge.chapter7.repository.UserRepository;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data user
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<User> param) {
        MessageModel msg = new MessageModel();
        try {
            List<User> userList = new ArrayList<>();
            for (User data : param) {
                User user = new User();
                String uuid = UUID.randomUUID().toString();

                user.setUserId(uuid);
                user.setEmail(data.getEmail());
                user.setNoTelp(data.getNoTelp());
                user.setUsername(data.getUsername());
                user.setPassword(data.getPassword());
                user.setLevelUser(data.getLevelUser());
                user.setCreated(new Date());
                user.setCreatedBy(data.getCreatedBy());
                user.setUpdated(new Date());
                user.setUpdatedBy("SYSTEM");
                user.setIsactive("Y");

                userList.add(user);
            }
            userRepository.saveAll(userList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(userList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data user
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<User> param) {
        MessageModel msg = new MessageModel();
        try {
            List<User> userList = new ArrayList<>();
            for (User data : param) {
                User user = userRepository.getById(data.getUserId());

                user.setEmail(data.getEmail());
                user.setNoTelp(data.getNoTelp());
                user.setUsername(data.getUsername());
                user.setPassword(data.getPassword());
                user.setLevelUser(data.getLevelUser());
                user.setUpdated(new Date());
                user.setUpdatedBy(data.getUpdatedBy());
                user.setIsactive(data.getIsactive());

                userList.add(user);
            }
            userRepository.saveAll(userList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(userList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete data user By user_id
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("userId") String userId) {
        MessageModel msg = new MessageModel();
        try {
            userRepository.deleteById(userId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data user By user_id
    @GetMapping("/getData/{userId}")
    public ResponseEntity<MessageModel> getById(@PathVariable("userId") String userId) {
        MessageModel msg = new MessageModel();
        try {
            User data = userRepository.getById(userId);

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

    // Get List All Data user
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<User> data = (List<User>) userRepository.findAll();

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

    // Get Data Pagination From users
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<User> data = userRepository.findAll(pageRequest);

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