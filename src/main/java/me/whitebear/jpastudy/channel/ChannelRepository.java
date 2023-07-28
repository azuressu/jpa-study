package me.whitebear.jpastudy.channel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
