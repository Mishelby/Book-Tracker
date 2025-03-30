package org.example.booktracker.repository;

import org.example.booktracker.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
            SELECT (COUNT(*) > 0)
            FROM UserEntity ue
            WHERE ue.email = :email                       
            """)
    boolean isExists(String email);

    @Query("""
            SELECT (COUNT(*))
            FROM UserEntity            
            """)
    Optional<Long> countAllUsers();
}
