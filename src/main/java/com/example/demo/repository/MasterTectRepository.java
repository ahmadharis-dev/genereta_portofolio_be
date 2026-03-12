package com.example.demo.repository;

import com.example.demo.entity.MasterTech;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MasterTectRepository extends JpaRepository<MasterTech, Integer> {
    boolean existsById(@Param("id") Integer id);

    void deleteById(Id id);

    boolean existsByName(String name);

    @Query(value = """ 
            DELETE FROM master_tech m WHERE m.id = :id
            """, nativeQuery = true)
    int deleteByIdCustom(@Param("id") Integer id);
}
