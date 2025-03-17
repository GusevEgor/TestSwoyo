package org.example.testswoyo.server.repository;

import org.example.testswoyo.server.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByTitle(String title);

    Boolean existsByTitle(String title);
}
