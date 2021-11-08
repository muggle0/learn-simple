package com.muggle.sms.entity;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mpc_group")
public class GroupEntity {

    private Long id;
    private String code;
    private String groupName;
    private Byte groupStatus;
    private String comment;
    private String createdBy;
    private String updatedBy;
    private Timestamp updateTime;
    private Timestamp createTime;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 32)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "group_name", nullable = true, length = 255)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "group_status", nullable = true)
    public Byte getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Byte groupStatus) {
        this.groupStatus = groupStatus;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 1024)
    public String getComment() {
        return comment;
    }

    public void setComment(String description) {
        this.comment = description;
    }

    @Basic
    @Column(name = "created_by", nullable = true, length = 255)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "updated_by", nullable = true, length = 255)
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupEntity that = (GroupEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects
                .equals(groupName, that.groupName) && Objects.equals(groupStatus, that.groupStatus) && Objects
                .equals(comment, that.comment) && Objects.equals(createdBy, that.createdBy) && Objects
                .equals(updatedBy, that.updatedBy) && Objects.equals(updateTime, that.updateTime) && Objects
                .equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, groupName, groupStatus, comment, createdBy, updatedBy, updateTime, createTime);
    }
}
