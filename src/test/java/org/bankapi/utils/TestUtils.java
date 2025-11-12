package org.bankapi.utils;

import org.bankapi.model.Bank;
import org.bankapi.model.dto.BankDTO;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static Bank getBank() {
        Bank bank = new Bank();
        bank.setFiid("Santander");
        bank.setName("Santander RIO");
        bank.setAvailableBank(true);
        return bank;
    }

    public static BankDTO getBankDTO() {
       return BankDTO.builder().fiid("Santander").name("Santander RIO").availableBank(true).build();
    }

    public static List<BankDTO> getAllBanksDto() {
        List<BankDTO> banks = new ArrayList<>();
        banks.add(getBankDTO());
        return banks;
    }

    public static List<Bank>getBanks()
    {
        List<Bank> banks = new ArrayList<>();
        banks.add(getBank());
        return banks;
    }
}
