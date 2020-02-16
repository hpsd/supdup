package com.deltaa.superduper.domain.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Version
    @Column(name = "version")
    private int version;

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public int getVersion() {
        return version;
    }

    public abstract Long getId();
}
