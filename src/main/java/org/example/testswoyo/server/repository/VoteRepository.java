package org.example.testswoyo.server.repository;

import org.example.testswoyo.server.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByTitle(String title);

    Boolean existsByTitle(String title);

    void deleteById(Long id);
}
