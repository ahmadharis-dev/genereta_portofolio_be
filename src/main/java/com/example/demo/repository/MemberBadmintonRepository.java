package com.example.demo.repository;

import com.example.demo.entity.MemberBadminton;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBadmintonRepository extends JpaRepository<MemberBadminton, Integer> {
    Page<MemberBadminton> findByNameContainingIgnoreCaseOrClassnameContainingIgnoreCase(
            String nameKeyword,
            String classnameKeyword,
            String classesKeyword,
            Pageable pageable
    );
}
