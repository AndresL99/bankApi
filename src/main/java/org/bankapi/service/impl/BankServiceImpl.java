package org.bankapi.service.impl;

import org.bankapi.exception.BankException;
import org.bankapi.model.Bank;
import org.bankapi.model.convert.BankDTOtoBank;
import org.bankapi.model.dto.BankDTO;
import org.bankapi.repository.BankRepository;
import org.bankapi.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public BankDTO getBankByFiid(String fiid) throws BankException {
        if(bankRepository.existsByFiid(fiid))
        {
            return bankRepository.findByFiid(fiid);
        }
        else
        {
            throw new BankException("Bank not found with Fiid: " + fiid);
        }
    }

    @Override
    public List<BankDTO> getAllBanks(){
        List<Bank> bank = bankRepository.findAll();
        List<BankDTO> bankDTOs = new ArrayList<>();
        for (Bank b : bank) {
                BankDTO bankDTO = BankDTOtoBank.convertBankDTO(b);
                bankDTOs.add(bankDTO);
            }
        return bankDTOs;
    }

    @Override
    public Bank updateBankByFiid(BankDTO bank) throws BankException {
        if(bankRepository.existsByFiid(bank.getFiid())) {
            Bank source = BankDTOtoBank.convertBank(bank);
            return bankRepository.save(source);
        }
        else {
            throw new BankException("Error: Bank does not exist" + bank.getFiid());
        }
    }

    @Override
    public Bank addBank(BankDTO bankDTO) throws BankException {
        Bank source = BankDTOtoBank.convertBank(bankDTO);
        if(!bankRepository.existsByFiid(bankDTO.getFiid())) {
            return bankRepository.save(source);
        }
        else {
            throw new BankException("Bank already exists with fiid: " + bankDTO.getFiid());
        }
    }

    @Override
    public BankDTO removeBank(String fiid) throws BankException {
        if(bankRepository.existsByFiid(fiid))
        {
            return bankRepository.deleteByFiid(fiid);
        }
        else {
            throw new BankException("Error deleting bank with fiid" + fiid);
        }
    }
}
