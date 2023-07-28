package me.whitebear.jpastudy.user;

import me.whitebear.jpastudy.my.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, MyRepository<User> {

}
