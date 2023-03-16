package shop.mtcoding.hiberapp.model;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    // api는 db를 건드리는 작업을 할 때 해당 값을 리턴해줌 - dto로
    public User save(User user) {
        em.persist(user); // 하드디스크에 기록
        return user;
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void delete(User user) {
        em.remove(user);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll(int page, int row) {
        return em.createQuery("select u from User u",
                User.class).setFirstResult(page * row).setMaxResults(row)
                .getResultList();
    }
    // // createQuery = hibernate가 지원하는 쿼리 형식, 테이블 자리에 클래스 이름을 넣으면 됨, u는 별칭
    // // 이렇게 쓰면 페이징 가능
    // // setFirstResult = 첫번 째 페이지
    // // setMaxResults = 한 페이지에 나올 row 수
    // }

    // public List<User> findAll() {
    // return em.createQuery("select u from User u", User.class)
    // .getResultList();
    // }
}
