package me.whitebear.jpastudy.my;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface MyRepository<User, ID extends Serializable> extends Repository<User, ID> {

    Optional<User> findByUsername(String username);

}
