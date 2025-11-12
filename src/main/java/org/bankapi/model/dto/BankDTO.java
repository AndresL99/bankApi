package org.bankapi.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@Data
public class BankDTO {

    private String fiid;

    private String name;

    private Boolean availableBank;
}
