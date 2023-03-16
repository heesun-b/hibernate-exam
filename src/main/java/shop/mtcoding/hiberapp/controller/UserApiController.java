package shop.mtcoding.hiberapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.hiberapp.model.User;
import shop.mtcoding.hiberapp.model.UserRepository;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(User user) {
        User userPS = userRepository.save(user);
        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, User user) {
        // 업데이트 조건
        // 1. 신뢰성 검사 - 주소로 받은 데이터는 무조건 해야 함
        User userPS = userRepository.findById(id);
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }

        userPS.update(user.getPassword(), user.getEmail());
        User updateUserPS = userRepository.update(userPS);
        return new ResponseEntity<>(updateUserPS, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // 업데이트 조건
        // 1. 신뢰성 검사 - 주소로 받은 데이터는 무조건 해야 함
        User userPS = userRepository.findById(id);
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(userPS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findUsers(@RequestParam(defaultValue = "0") int page) {
        List<User> userListPS = userRepository.findAll(page, 2);
        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }

    @GetMapping("/users/{ID}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        User userPS = userRepository.findById(id);
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }
}
