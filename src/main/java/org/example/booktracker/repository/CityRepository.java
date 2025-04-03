package org.example.booktracker.repository;

import org.example.booktracker.domain.city.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    @Query("""
            SELECT c
            FROM CityEntity c
            WHERE c.name = :name                       
            """)
    Optional<CityEntity> findByName(String name);

    @Query("""
            SELECT c
            FROM AuthorEntity ae
            JOIN CityEntity c ON ae.city.id = c.id
            WHERE ae.id = :id                                   
            """)
    Optional<CityEntity> findByAuthorId(Long id);
}
