package org.bankapi.service;


import org.bankapi.exception.BankException;
import org.bankapi.model.Bank;
import org.bankapi.model.dto.BankDTO;

import java.util.List;

public interface BankService {

    BankDTO getBankByFiid(String fiid) throws BankException;
    List<BankDTO> getAllBanks();
    Bank updateBankByFiid(BankDTO bank) throws BankException;
    Bank addBank(BankDTO bank) throws BankException;
    BankDTO removeBank(String fiid) throws BankException;
}
