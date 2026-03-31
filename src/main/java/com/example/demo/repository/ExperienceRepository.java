package com.example.demo.repository;

import com.example.demo.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    @Query(value = """
    SELECT * FROM experience WHERE user_id = :userId
""", nativeQuery = true)
    List<Experience> getByUser(@Param("userId") Integer userId);

    @Query(value = """
            SELECT COALESCE(MAX(`order`), 0)
            FROM experience
            WHERE user_id = :userId
            """, nativeQuery = true)
    Integer getLastOrderByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "UPDATE experience SET `order` = :order WHERE id = :id", nativeQuery = true)
    void updateSortOrder(@Param("id") Integer id, @Param("order") Integer order);
}
