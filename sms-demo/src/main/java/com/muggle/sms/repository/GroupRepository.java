package com.muggle.sms.repository;

import com.muggle.sms.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long>, JpaSpecificationExecutor<GroupEntity> {

    GroupEntity findFirstByGroupNameAndCreatedBy(String name, String createdBy);

    void deleteAllByCode(String code);

    Optional<GroupEntity> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByGroupNameAndCreatedBy(String groupName, String createBy);
}
