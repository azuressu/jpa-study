package me.whitebear.jpastudy.thread;

import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.user.User;

import java.util.List;

public interface ThreadService {

    List<Thread> selectChannelThreads(Channel channel);

    Thread insert(Thread thread);
}
