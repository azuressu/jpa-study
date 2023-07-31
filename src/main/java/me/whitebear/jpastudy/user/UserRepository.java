package me.whitebear.jpastudy.user;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // 아래와 같이 AS user_password로 Alias(AS)를 걸어주면
    @Query("SELECT u, u.password AS customField FROM User u WHERE u.username = ?1")
    List<User> findByUsernameWithCustomField(String username, Sort sort);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    List<User> findByUsername(String username, Sort sort);

}
