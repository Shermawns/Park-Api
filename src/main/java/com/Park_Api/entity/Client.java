package com.Park_Api.entity;

import com.Park_Api.entity.enums.Role;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@Entity
@Table(name = "tb_client")
@EntityListeners(AuditingEntityListener.class)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_CLIENT;

    @CreatedDate
    private LocalDate createdDate;
    @LastModifiedDate
    private LocalDate modificationDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updateBy;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public Client() {
    }

    public Client(Long id, String name, String cpf, Role role, LocalDate createdDate, LocalDate modificationDate, String createdBy, String updateBy, User user) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.role = role;
        this.createdDate = createdDate;
        this.modificationDate = modificationDate;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
        this.user = user;
    }

    public Client(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
