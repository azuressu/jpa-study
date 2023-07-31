package me.whitebear.jpastudy.thread;

import com.mysema.commons.lang.IteratorAdapter;
import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadServiceImpl implements ThreadService{

    @Autowired
    ThreadRepository threadRepository;

    @Override
    public List<Thread> selectChannelThreads(Channel channel) {
        // 멘션된 전체를 가져와야 함 (?
        var thread = QThread.thread;
        // 메세지가 비어있지 않은 해당 채널의 쓰레드 목록
        var predicate = thread.channel.eq(channel).and(thread.message.isNotEmpty());
        var threads = threadRepository.findAll(predicate);

        // list로 바꿔줌
        return IteratorAdapter.asList(threads.iterator());
    }

    @Override
    public Thread insert(Thread thread) {
        return threadRepository.save(thread);
    }
}
