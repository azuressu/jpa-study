package me.whitebear.jpastudy.my;

import jakarta.persistence.EntityManager;
import me.whitebear.jpastudy.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MyUserRepositoryImpl implements MyRepository<User>{

    @Autowired
    EntityManager entityManager;

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public List<String> findNameAll() {
        return entityManager.createQuery("SELECT u.username FROM User AS u", String.class).getResultList();
    }
}
