package org.bankapi.model.convert;

import org.bankapi.model.Bank;
import org.bankapi.model.dto.BankDTO;

public class BankDTOtoBank {

    public static BankDTO convertBankDTO(Bank bank) {
        return BankDTO.builder()
                .fiid(bank.getFiid())
                .name(bank.getName())
                .availableBank(bank.getAvailableBank())
                .build();
    }

    public static Bank convertBank(BankDTO bankDTO) {
        Bank bank = new Bank();
        bank.setFiid(bankDTO.getFiid());
        bank.setName(bankDTO.getName());
        bank.setAvailableBank(bankDTO.getAvailableBank());
        return bank;
    }
}
