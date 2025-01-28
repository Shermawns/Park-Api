package com.Park_Api.entity;

import com.Park_Api.entity.enums.ParkingStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "tb_parking_spot")
@EntityListeners(AuditingEntityListener.class)
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cod;
    @Enumerated(EnumType.STRING)
    private ParkingStatus status;
    @CreatedDate
    private LocalDate createdDate;
    @LastModifiedDate
    private LocalDate modificationDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updateBy;

    public ParkingSpot() {
    }

    public ParkingSpot(Long id, String cod, ParkingStatus status, LocalDate createdDate, LocalDate modificationDate, String createdBy, String updateBy) {
        this.id = id;
        this.cod = cod;
        this.status = status;
        this.createdDate = createdDate;
        this.modificationDate = modificationDate;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
    }

    public ParkingSpot(String code, ParkingStatus parkingStatus) {
        this.cod = code;
        this.status = parkingStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public ParkingStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingStatus status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
