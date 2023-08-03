package me.whitebear.jpastudy.projection;

import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserProjectionTest {

    /* Projection의 기능
    *  1) 원하는 필드만 지정해서 조회가 가능하다.
    *  2) 여러 필드를 합쳐서 재정의 필드 (Alias - 별칭) 조회가 가능하다. (Nested 프로젝션)
    *  3) 조회 필드 그룹을 인터페이스 혹은 클래스로 만들어놓고 재사용이 가능하다.
    *
    *  Projection 필드 사용 방법
    *  1) get 필드() 메소드로 정의
    *  2) @Value로 정의 */


    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Projection을 Interface 처럼 사용")
    void projectionInterfaceTest() {
//        // given
//        var newUser = User.builder().username("useruser").password("비밀번호").build();
//
//        // when
//        var savedUser = userRepository.save(newUser);
//
//        // then
//        var userProfiles = userRepository.findByPassword("비밀번호");
//        System.out.println("interface projection: ");
//        userProfiles.forEach(userProfile -> System.out.println(userProfile.hasPassword()));
//        assert !userProfiles.isEmpty();
    }

    @Test
    @DisplayName("Projection을 DTO 클래스 처럼 사용")
    void projectionTest() {
//        // given
//        var newUser = User.builder().username("user").password("pass").build();
//
//        // when
//        var savedUser = userRepository.save(newUser);
//
//        // then class projection
//        var userInfos = userRepository.findByPassword("pass");
//        System.out.println("class projection: " );
//        userInfos.forEach(userInfo -> System.out.println(userInfo.getUserInfo()));
//        assert !userInfos.isEmpty();
    }

    @Test
    @DisplayName("Dynamic Projection")
    void dynamicProjectionTest() {
        // given
        var newUser = User.builder().username("userr").password("PAss").build();

        // when
        var savedUser = userRepository.save(newUser);

        // then dynamic projection
        var userProfiles2 = userRepository.findByPasswordStartingWith("PA", UserProfile.class);
        System.out.print("dynamic projection1: ");
        userProfiles2.forEach(userProfile -> System.out.println(userProfile.getPassword()));
        assert !userProfiles2.isEmpty();

        // then dynamic projection
        var userInfos2 = userRepository.findByPasswordStartingWith("PA", UserInfo.class);
        System.out.print("dynamic projection2: ");
        userInfos2.forEach(userInfo -> System.out.println(userInfo.getPassword()));
        assert !userInfos2.isEmpty();
    }


}
