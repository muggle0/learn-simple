package com.lcat.common.repository;

import com.lcat.entity.dto.FlowableParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<FlowableParams, Long> {
    FlowableParams findByUsername(String username);
}
