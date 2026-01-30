package com.example.demo.repository;

import com.example.demo.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    @Query(value = """
    SELECT * FROM experience WHERE user_id = :userId
""", nativeQuery = true)
    List<Experience> getByUser(@Param("userId") Integer userId);
}
