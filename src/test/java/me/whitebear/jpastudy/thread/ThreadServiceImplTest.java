package me.whitebear.jpastudy.thread;

import me.whitebear.jpastudy.channel.Channel;
import me.whitebear.jpastudy.channel.ChannelRepository;
import me.whitebear.jpastudy.comment.Comment;
import me.whitebear.jpastudy.comment.CommentRepository;
import me.whitebear.jpastudy.common.PageDTO;
import me.whitebear.jpastudy.emotion.CommentEmotion;
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
    @Autowired
    CommentRepository commentRepository;

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

    /* 아래 세 개의 테스트의 차이점은 ThreadRepositoryQueryImpl 파일에 fetchJoin()을 확인하면 됨 (하나씩 늘어남) */

    @Test
    @DisplayName("전체 채널에서 내가 멘션된 쓰레드 상세정보 목록 테스트")
    void selectMentionedThreadListTest() {
        // given
        var user = getTestUser("1" , "2");

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

    @Test
    @DisplayName("전체 채널에서 내가 멘션된 쓰레드 및 이모티콘 상세정보 목록 테스트")
    void selectMentionedThreadEmotionListTest() {
        // given
        var user = getTestUser("1", "1");
        var user2 = getTestUser("2", "2");
        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var thread1 = getTestThread("message", savedChannel, user, user2, "e1");
        var thread2 = getTestThread("", savedChannel, user, user2, "e2");

        // when
        var pageDto = PageDTO.builder().currentPage(1).size(100).build();
        var mentionedTheadList = threadService.selectMentionedTheadList(user.getId(), pageDto);

        // then
        assert mentionedTheadList.getTotalElements() == 2; // 사이즈가 두 개가 나와야 함
    }

    @Test
    @DisplayName("전체 채널에서 내가 멘션된 쓰레드 및 이모티콘 및 댓글(with 이모지) 상세정보 목록 테스트")
    void selectMentionedThreadEmotionCommentListTest() {
        // given
        var user = getTestUser("1", "1");
        var user2 = getTestUser("2", "2");
        var user3 = getTestUser("3", "3");
        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var thread1 = getTestThread("message", savedChannel, user, user2, "e1");
        var thread2 = getTestThread("", savedChannel, user, user2, "e2");

        // when
        var pageDto = PageDTO.builder().currentPage(1).size(100).build();
        var mentionedTheadList = threadService.selectMentionedTheadList(user.getId(), pageDto);

        // then
        assert mentionedTheadList.getTotalElements() == 2; // 사이즈가 두 개가 나와야 함
    }

    @Test
    @DisplayName("전체 채널에서 내가 멘션된 쓰레드 및 이모티콘 및 댓글(with 이모지) 상세정보 목록 테스트")
    void selectMentionedThreadEmotionCommentEmotionListTest() {
        // given
        var user = getTestUser("1", "1");
        var user2 = getTestUser("2", "2");
        var user3 = getTestUser("3", "3");
        var user4 = getTestUser("4", "4");
        var newChannel = Channel.builder().name("c1").type(Channel.Type.PUBLIC).build();
        var savedChannel = channelRepository.save(newChannel);
        var thread1 = getTestThread("message", savedChannel, user,
                user2, "e1", user3, "c1", user4, "ce1");
        var thread2 = getTestThread("", savedChannel, user,
                user2, "e2", user3, "c2", user4, "ce2");

        // when
        var pageDto = PageDTO.builder().currentPage(1).size(100).build();
        var mentionedTheadList = threadService.selectMentionedTheadList(user.getId(), pageDto);

        // then
        assert mentionedTheadList.getTotalElements() == 2; // 사이즈가 두 개가 나와야 함
    }

    private User getTestUser(String username, String password) {
        var newUser = User.builder().username(username).password(password).build();
        return userRepository.save(newUser);
    }

    private Comment getTestComment(User user, String message) {
        var newComment = Comment.builder().message(message).build();
        newComment.setUser(user);
        return commentRepository.save(newComment);
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

    private Thread getTestThread(String message, Channel channel, User mentionedUser,
                                 User emotionUser, String emotionValue) {
        var newThead = getTestThread(message, channel, mentionedUser);
        newThead.addEmotion(emotionUser, emotionValue);
        return threadService.insert(newThead);
    }

    private Thread getTestThread(String message, Channel channel, User mentionedUser,
                                 User emotionUser, String emotionValue, User commentUser, String commentMessage) {
        var newThead = getTestThread(message, channel, mentionedUser, emotionUser, emotionValue);
        newThead.addComment(getTestComment(commentUser, commentMessage));
        return threadService.insert(newThead);
    }

    private Thread getTestThread(String message, Channel channel, User mentionedUser,
                                 User emotionUser, String emotionValue, User commentUser, String commentMessage,
                                 User commentEmotionuser, String commentEmotionValue) {
        var newThead = getTestThread(message, channel, mentionedUser, emotionUser, emotionValue, commentUser, commentMessage);
        var newComment = getTestComment(commentUser, commentMessage);

        newThead.getComments()
                .forEach(comment -> comment.addEmotion(commentEmotionuser, commentEmotionValue));

        return threadService.insert(newThead);
    }
}