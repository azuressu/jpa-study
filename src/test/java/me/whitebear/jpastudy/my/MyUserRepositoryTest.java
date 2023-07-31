package me.whitebear.jpastudy.my;

import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.user.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MyUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void myUserRepositoryDeleteTest() {
        // given
        var newUser = User.builder().username("new").password("pass").build();
        userRepository.save(newUser);

        // when
        userRepository.delete(newUser);
    }

//    @Test
//    void myUserRepositoryFindNameAllTest() {
//        // given
//        var newUser1 = User.builder().username("new1").password("pass").build();
//        var newUser2 = User.builder().username("new2").password("pass").build();
//        userRepository.save(newUser1);
//        userRepository.save(newUser2);
//
//        // when
//        var userNameList = userRepository.findNameAll();
//
//        // then
//        Assertions.assertThat(userNameList.containsAll(List.of(newUser1.getUsername(), newUser2.getUsername())));
//    }

}
