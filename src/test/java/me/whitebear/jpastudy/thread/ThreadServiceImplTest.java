package me.whitebear.jpastudy.thread;

import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.channel.ChannelRepository;
import me.whitebear.jpastudy.common.PageDTO;
import me.whitebear.jpastudy.mention.ThreadMention;
import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ThreadServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    ThreadService threadService;

    @Test
    void getMentionedThreadList() {
        // given
        var newUser = User.builder().username("new").password("1").build();
        var savedUser = userRepository.save(newUser);
        var newThread = Thread.builder().message("message").build();
        newThread.addMention(newUser);
        threadService.insert(newThread);

        var newThread2 = Thread.builder().message("message2").build();
        newThread2.addMention(newUser);
        threadService.insert(newThread2);

        // when
        var mentionedThreads = savedUser.getThreadMentions().stream().map(ThreadMention::getThread).toList();
//        var mentionedThreads = threadService.selectMentionedThreadList(savedUser);

        // then
        assert mentionedThreads.containsAll(List.of(newThread, newThread2));
    }

    @Test
    void getNotEmptyThreadList() {
        // given
        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var newThread = Thread.builder().message("message").build();
        newThread.setChannel(newChannel);
        threadService.insert(newThread);

        var newThread2 = Thread.builder().message("").build();
        newThread2.setChannel(newChannel);
        threadService.insert(newThread2);

        // when
        var notEmptyThreads = threadService.selectChannelThreads(savedChannel);

        // then
        assert !notEmptyThreads.contains(newThread2);
    }

    @Test
    @DisplayName("천제 채널에서 내가 멘션된 쓰레드 상세정보 목록 테스트")
    void selectMentionedThreadListTest() {
        // given
        var user = getTestUser();

        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var thread1 = getTestThread("message", savedChannel, user);
        var thread2 = getTestThread("", savedChannel, user);

        // when
        var pageDto = PageDTO.builder().currentPage(1).size(100).build();
        var mentionedTheadList = threadService.selectMentionedTheadList(user.getId(), pageDto);

        // then
        assert mentionedTheadList.getTotalElements() == 2; // 사이즈가 두 개가 나와야 함
    }

    private User getTestUser() {
        var newUser = User.builder().username("new").password("1").build();
        return userRepository.save(newUser);
    }

    private Thread getTestThread(String message, Channel savedChannel) {
        var newThread = Thread.builder().message(message).build();
        newThread.setChannel(savedChannel);
        return threadService.insert(newThread);
    }

    private Thread getTestThread(String message, Channel channel, User mentionedUser) {
        var newThead = getTestThread(message, channel);
        newThead.addMention(mentionedUser);
        return threadService.insert(newThead);
    }
}