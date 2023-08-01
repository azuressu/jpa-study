package me.whitebear.jpastudy.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void dynamicInsertTest() {
        // given
        var newUser = User.builder().username("user").build();

        // when
        userRepository.save(newUser);

        // then
        // 부분 생성 쿼리
    }

    @Test
    void dynamicUpdateTest() {
        // given
        var newUser = User.builder().username("user").password("password").build();
        userRepository.save(newUser);

        // when
        newUser.updatePassword("new password");
        userRepository.save(newUser);

        // then
        // 부분 수정 쿼리

    }

}