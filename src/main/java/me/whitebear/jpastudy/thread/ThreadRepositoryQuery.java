package me.whitebear.jpastudy.thread;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThreadRepositoryQuery {

    // search를 넣어서 condition을 확인함
    Page<Thread> search(ThreadSearchCond cond, Pageable pageable);

}
