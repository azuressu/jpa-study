package me.whitebear.jpastudy.my;

import me.whitebear.jpastudy.user.User;

import java.util.List;

public interface MyRepository<T> {

    void delete(T entity);

    List<String> findNameAll();

}
