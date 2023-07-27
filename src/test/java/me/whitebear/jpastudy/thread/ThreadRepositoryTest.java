package me.whitebear.jpastudy.thread;

import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.channel.ChannelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ThreadRepositoryTest {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ChannelRepository channelRepository;
    @Test
    void insertSelectThreadTest() {
        // given
        var newChannel = Channel.builder().name("new-group").build();
        var newThread = Thread.builder().message("new-message").build();
        var newThread2 = Thread.builder().message("new-message2").build();
        newThread.setChannel(newChannel);
        newThread2.setChannel(newChannel);

        // when
        var savedChannel = channelRepository.insertChannel(newChannel); // channel도 같이 저장해주어야 함
        var savedThread = threadRepository.inserThread(newThread);
        var savedThread2 = threadRepository.inserThread(newThread2);

        // then
//        var foundThread = threadRepository.selectThread(savedThread.getId());
//        assert foundThread.getChannel().getName().equals(newChannel.getName());
        var foundChannel = channelRepository.selectChannel(savedChannel.getId());
        assert foundChannel.getThreads().containsAll(Set.of(savedThread, savedThread2));
    }
}