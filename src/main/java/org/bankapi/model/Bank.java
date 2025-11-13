package org.bankapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bank")
    private Long id;

    @Column(name = "fiid")
    @NotBlank
    @NotNull(message = "Fiid must not be null")
    private String fiid;

    @Column(name = "bank_name")
    @NotBlank
    @NotBlank
    private String name;

    @NotNull
    @Column(name = "available_bank")
    private Boolean availableBank;

}
