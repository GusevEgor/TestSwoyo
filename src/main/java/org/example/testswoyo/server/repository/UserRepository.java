package org.example.testswoyo.server.repository;

import org.example.testswoyo.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    Boolean existsByName(String name);
}
