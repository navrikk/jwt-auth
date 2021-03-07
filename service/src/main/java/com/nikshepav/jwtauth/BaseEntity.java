package com.nikshepav.jwtauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public abstract class BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "bigint")
    protected Long id;

    @JsonIgnore
    @Version
    @Column(name = "version")
    protected Long version;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_at")
    protected LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @JsonIgnore
    @CreatedBy
    @Column(name = "created_by")
    protected String createdBy;

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "last_modified_by")
    protected String lastModifiedBy;
}
