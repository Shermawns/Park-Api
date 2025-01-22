package com.Park_Api.entity;

import com.Park_Api.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;
    private LocalDate createdDate = LocalDate.now();
    private LocalDate modificationDate;
    private String createdBy;
    private String updateBy;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    public User() {
    }

    public User(Long id, String username, String password, LocalDate createdDate, LocalDate modificationDate, String createdBy, String updateBy) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
        this.modificationDate = modificationDate;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
    }

    public User(String username, String password, Role role, String name) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdBy = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
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
