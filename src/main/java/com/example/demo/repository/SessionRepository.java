package com.example.demo.repository;

import com.example.demo.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    boolean existsBySessionId(String sessionId);
    @Query(value = """
        SELECT COUNT(*) 
        FROM sessions 
        WHERE session_id = :sessionId 
        AND is_expired = 0
        """, nativeQuery = true)
    int checkSession(@Param("sessionId") String sessionId);
    @Query(value = """
            SELECT * FROM sessions WHERE session_id
            """, nativeQuery = true)
    Session findBySessionId(String sessionId);
}
