package dev.jamespagani.entities;

import java.util.UUID;

import dev.jamespagani.enums.ContactType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID contactId;

    @Column
    private String internalContactId;

    @Column
    private String name;

    @Column
    private ContactType type;
    
}
