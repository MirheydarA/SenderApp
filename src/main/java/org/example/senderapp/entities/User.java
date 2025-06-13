package org.example.senderapp.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.senderapp.enums.UserRoles;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public int getId() {
        return this.id;
    }

    @Column(nullable = false)
    private String name;
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    @Column(nullable = false)
    private String surname;

    @Column(nullable = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    public String getEmail() {
        return this.email;
    }

    @Column(nullable = false)
    private String password;
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
