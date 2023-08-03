package me.whitebear.jpastudy.user;

import me.whitebear.jpastudy.projection.UserInfo;
import me.whitebear.jpastudy.projection.UserProfile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 아래와 같이 AS user_password로 Alias(AS)를 걸어주면
    @Query("SELECT u, u.password AS customField FROM User u WHERE u.username = ?1")
    List<User> findByUsernameWithCustomField(String username, Sort sort);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    List<User> findByUsername(String username, Sort sort);

    Optional<User> findByUsername(String username);

    // Projection을 Interface로 사용
    List<UserProfile> findByPassword(String password);

    // Projection을 DTO 클래스 처럼 사용
//    List<UserInfo> findByPassword(String password);

    // Dynamic Projection
    <T> List <T> findByPasswordStartingWith(String passwordStartingWith, Class<T> type);


}