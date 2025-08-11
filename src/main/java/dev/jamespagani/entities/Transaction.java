package dev.jamespagani.entities;

import java.time.Instant;
import java.util.UUID;

import dev.jamespagani.enums.MovementType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transaction_d;

    @Column(name = "internal_transaction_id")
    private String internalTransactionId;

    @Column
    private MovementType type;

    @Column
    private Instant date;

    @Column
    private long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contact contact;
}
