package me.whitebear.jpastudy.thread;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Thread.class, idClass = Long.class)
public interface ThreadRepository extends JpaRepository<Thread, Long>,
        QuerydslPredicateExecutor<Thread>, ThreadRepositoryQuery {
}


/* ThreadRepository에 JPARepository 기능도 있고, QuerydslPredicateExecutor기능도 있고, ThreadRepositoryQuery 기능도 존재함 */
