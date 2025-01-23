package com.Park_Api.entity;

import com.Park_Api.entity.enums.Role;
import jakarta.persistence.*;
@Entity
@Table(name = "tb_client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_CLIENT;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public Client() {
    }

    public Client(Long id, String name, String cpf, User user, Role role) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.user = user;
        this.role = role;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

}
