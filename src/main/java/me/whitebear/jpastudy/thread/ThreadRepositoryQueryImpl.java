package me.whitebear.jpastudy.thread;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Objects;

import static me.whitebear.jpastudy.channel.QChannel.channel;
import static me.whitebear.jpastudy.thread.QThread.thread;

@RequiredArgsConstructor
public class ThreadRepositoryQueryImpl implements ThreadRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Thread> search(ThreadSearchCond cond, Pageable pageable) {
        var query = query(thread, cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 정렬하기 (내림차순으로 정렬하기)
        query.orderBy(thread.mentions.any().createdAt.desc());

        // 실제 받아올 threads
        var threads = query.fetch();
        long totalSize = countQuery(cond).fetch().get(0); // 첫 번째 응답값

        threads.stream()
                .map(Thread::getComments)
                .forEach(comments -> comments
                        .forEach(comment -> Hibernate.initialize(comment.getEmotions())));

        return PageableExecutionUtils.getPage(threads, pageable, () -> totalSize); // Page 객체로 감싸서 return
    }

    private <T> JPAQuery<T> query(Expression<T> expr, ThreadSearchCond cond) {
        return jpaQueryFactory.select(expr)
                .from(thread)
                .leftJoin(thread.channel).fetchJoin() // Lazy로 설정된 애들을 조회해온다는 의미
                .leftJoin(thread.emotions).fetchJoin()
                .leftJoin(thread.comments).fetchJoin()
                .where(
                        channelIdEq(cond.getChannelId()),
                        mentionedUserIdEq(cond.getMentionedUserId())
                );
    }

    private JPAQuery<Long> countQuery(ThreadSearchCond cond) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(thread)
//                .fetchJoin()  // -> 얘때문에 test 코드가 실행이 안됐는데, 이유가 뭐지 ?
                .where(
                        channelIdEq(cond.getChannelId()),
                        mentionedUserIdEq(cond.getMentionedUserId())
                );
    }

    // cond의 값들이 null인지 아닌지 확인하기
    private BooleanExpression channelIdEq(Long channelId) {
        return Objects.nonNull(channelId) ? thread.channel.id.eq(channelId) : null;
    }

    private BooleanExpression mentionedUserIdEq(Long mentionedUserId) {
        return Objects.nonNull(mentionedUserId) ? thread.mentions.any().user.id.eq(mentionedUserId) : null;
    }
}
