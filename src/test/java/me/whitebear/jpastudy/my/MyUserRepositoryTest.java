package me.whitebear.jpastudy.my;

import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MyUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void myUserRepositoryDeleteTest() {
        var newUser = User.builder().username("new").password("pass").build();
        userRepository.save(newUser);

        // when
        userRepository.delete(newUser);
    }

}
