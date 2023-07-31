package me.whitebear.jpastudy.channel;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    void insertSelectGroupTest() {
        // given
        var newChannel = Channel.builder().name("new-group").build();

        // when
        var savedChannel = channelRepository.save(newChannel);

        // then
        var foundChannel = channelRepository.findById(savedChannel.getId());
        assert foundChannel.get().getId().equals(savedChannel.getId());
    }

    @Test
    void queryDslTest() {
        // given
        var newChannel = Channel.builder().name("suyeon").build();
        channelRepository.save(newChannel);

        Predicate predicate = QChannel.channel
                .name.equalsIgnoreCase("SUYEON");

        // when
        Optional<Channel> optionalChannel = channelRepository.findOne(predicate);

        // then
        assert optionalChannel.get().getName().equals(newChannel.getName());
    }


}