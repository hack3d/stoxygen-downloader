package de.stoxygen.model;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedBy
    protected U insertUser;

    @CreatedDate
    private Date insertTimestamp;

    @LastModifiedBy
    protected U modifyUser;

    @LastModifiedDate
    private Date modifyTimestamp;

    public Date getInsertTimestamp() {
        return insertTimestamp;
    }

    public Date getModifyTimestamp() {
        return modifyTimestamp;
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        this.insertTimestamp = now;
        this.modifyTimestamp = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifyTimestamp = new Date();
    }
}
