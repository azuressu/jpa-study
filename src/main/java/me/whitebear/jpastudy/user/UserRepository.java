package me.whitebear.jpastudy.user;

import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository{
    public Optional<User> findByUsername(String username);

    // findByPassword를 막기 위해 (username만 갖고 조회할 수 있도록 함)
}
