package me.whitebear.jpastudy.my;

import me.whitebear.jpastudy.user.User;

public interface MyRepository<T> {

    void delete(T entity);

}
