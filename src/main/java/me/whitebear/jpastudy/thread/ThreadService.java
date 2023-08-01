package me.whitebear.jpastudy.thread;

import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.common.PageDTO;
import me.whitebear.jpastudy.user.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ThreadService {

    List<Thread> selectChannelThreads(Channel channel);

    Page<Thread> selectMentionedTheadList(Long userId, PageDTO pageDTO);

    Thread insert(Thread thread);
}
