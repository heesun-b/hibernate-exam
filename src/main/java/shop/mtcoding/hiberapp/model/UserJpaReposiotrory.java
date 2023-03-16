package shop.mtcoding.hiberapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaReposiotrory extends JpaRepository<User, Long> {
    // JpaRepository 1: 오브젝트 / 2: 프라이머리 키 기입

}
