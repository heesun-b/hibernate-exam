package shop.mtcoding.hiberapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import shop.mtcoding.hiberapp.model.UserJpaReposiotrory;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserJpaReposiotrory userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(User user) {
        User userPS = userRepository.save(user);
        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, User user) {
        // 업데이트 조건
        // 1. 신뢰성 검사 - 주소로 받은 데이터는 무조건 해야 함
        User userPS = userRepository.findById(id).get(); // .get()은 잘 안 쓰는 기법, 일단 지금은 사용
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }

        userPS.update(user.getPassword(), user.getEmail());
        User updateUserPS = userRepository.save(userPS); // update는 지원 x, 그래서 save를 쓰는데 객체가 primary key를 가지고 있으면 update
                                                         // 실행, 없으면 insert 실행
        return new ResponseEntity<>(updateUserPS, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // 업데이트 조건
        // 1. 신뢰성 검사 - 주소로 받은 데이터는 무조건 해야 함
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(userPS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findUsers(@RequestParam(defaultValue = "0") int page) {

        Page<User> userListPS = userRepository.findAll(PageRequest.of(page, 2));
        // 근데 LIST로 받는 건 안 좋음
        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }
}
