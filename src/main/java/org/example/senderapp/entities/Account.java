package org.example.senderapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.senderapp.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private String accountNumber;

    @Column(nullable = true)
    private String type;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private boolean isDeleted = false;

    private Currency currency;
    private LocalDate birthDate;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
