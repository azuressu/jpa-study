package me.whitebear.jpastudy.userChannel;

import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.channel.ChannelRepository;
import me.whitebear.jpastudy.common.PageDTO;
import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // 실무에서 사용시 주의해야 함 (테스트 대상 환경에 영향을 줌)
@Rollback(value = false)
class UserChannelRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

//    @Test
//    void userJoinChannelTest() {
//        //given
//        var newChannel = Channel.builder().name("new-channel").build();
//        var newUser = User.builder().username("new_user").password("new-pass").build();
//        var newUserChannel = newChannel.joinUser(newUser);
//
//        // when
//        var savedChannel = channelRepository.insertChannel(newChannel);
//        var savedUser = userRepository.save(newUser);
//
//        // then
//        // Channel을 가져와서 테스트 하는 방법 !
//        var foundChannel = channelRepository.selectChannel(savedChannel.getId());
//        assert foundChannel.getUserChannels().stream()
//                .map(UserChannel::getChannel)
//                .map(Channel::getName)
//                .anyMatch(name -> name.equals(newChannel.getName()));
//        /* 저장한 Channel의 UserChannels의 UserChannel에서 Channel을 가져와라
//        *  그 다음에 Channel에서 name을 가져오고
//        *  그런 다음 그 이름이 newChannel(우리가 설정한 채널)의 이름과 같은지 비교하라 */
//    }
//
//
//    @Test
//    void userJoinChannelWithCascadeTest() {
//        // UserChannel을 저장하지 않아도 userchannel이 저장되는지 확인 !
//        //given
//        var newChannel = Channel.builder().name("new-channel").build();
//        var newUser = User.builder().username("new_user").password("new-pass").build();
//        newChannel.joinUser(newUser);
//
//        // insertChannel을 할 때, 해당 Channel에 엮여 있는 userChannels에도 Cascade가 전파됨 !
//
//        // when
//        var savedChannel = channelRepository.insertChannel(newChannel);
//        var savedUser = userRepository.save(newUser);
//
//        // then
//        // Channel을 가져와서 테스트 하는 방법 !
//        var foundChannel = channelRepository.selectChannel(savedChannel.getId());
//        assert foundChannel.getUserChannels().stream()
//                .map(UserChannel::getChannel)
//                .map(Channel::getName)
//                .anyMatch(name -> name.equals(newChannel.getName()));
//    }

    @Test
    void userCustomFieldSortingTst() {
        // given
        var newUser1 = User.builder().username("new_user").password("new-pass1").build();
        var newUser2 = User.builder().username("new_user").password("new-pass2").build();
        userRepository.save(newUser1);
        userRepository.save(newUser2);

        // when
        var users = userRepository.findByUsernameWithCustomField("new_user", Sort.by("customField"));

        // then
        assert users.get(0).getPassword().equals(newUser1.getPassword());

        // when
        users = userRepository.findByUsernameWithCustomField("new_user", Sort.by("customField").descending());

        // then
        assert users.get(0).getPassword().equals(newUser2.getPassword());

        // given
        var newUser3 = User.builder().username("new_user").password("3").build();
        userRepository.save(newUser3);

        // when
        users = userRepository.findByUsername("new_user", JpaSort.unsafe("LENGTH(password)"));

        // then
        assert users.get(0).getPassword().equals(newUser3.getPassword());
    }

    @Test
    void pageDTOTest() {
        // given
        var newUser1 = User.builder().username("new_user").password("new-pass1").build();
        var newUser2 = User.builder().username("new_user").password("new-pass2").build();
        var newUser3 = User.builder().username("new_user").password("new-pass3").build();
        userRepository.save(newUser1);
        userRepository.save(newUser2);
        userRepository.save(newUser3);
        var pageDTO = new PageDTO(1, 2, "password");

        // when
        var page = userRepository.findAll(pageDTO.toPageable()); // 바로 이 페이지를 기준으로 조회가 됨

        // then
        assert page.getContent().size() == 2;
    }
}