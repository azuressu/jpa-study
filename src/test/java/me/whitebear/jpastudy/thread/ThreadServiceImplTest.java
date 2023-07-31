package me.whitebear.jpastudy.thread;

import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.channel.ChannelRepository;
import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.event.MenuEvent;
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
        var mentionedThreads = savedUser.getMentions().stream().map(MenuEvent::new).toList();
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
}