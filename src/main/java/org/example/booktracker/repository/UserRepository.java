package org.example.booktracker.repository;

import org.example.booktracker.domain.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
            SELECT (COUNT(*) > 0)
            FROM UserEntity ue
            WHERE ue.email = :email                       
            """)
    boolean isExists(String email);
}
