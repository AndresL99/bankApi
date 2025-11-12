package org.bankapi.service;

import org.bankapi.exception.BankException;
import org.bankapi.model.Bank;
import org.bankapi.model.dto.BankDTO;
import org.bankapi.repository.BankRepository;
import org.bankapi.service.impl.BankServiceImpl;
import org.bankapi.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BankServiceTest {

    private BankService bankService;
    private BankRepository bankRepository;

    @BeforeEach
    void setUp() {
        bankRepository = Mockito.mock(BankRepository.class);
        bankService = new BankServiceImpl(bankRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllBanks() throws BankException {
        when(bankRepository.findAll()).thenReturn(TestUtils.getBanks());
        List<BankDTO> bankDTOS = bankService.getAllBanks();

        assertEquals(TestUtils.getAllBanksDto().size(),bankDTOS.size());

        verify(bankRepository,times(1)).findAll();
    }

    @Test
    void getBankByFiid() throws BankException {
        String fiid = TestUtils.getBank().getFiid();
        when(bankRepository.existsByFiid(fiid)).thenReturn(true);
        when(bankRepository.findByFiid(fiid)).thenReturn(TestUtils.getBankDTO());
        BankDTO bankDTO = bankService.getBankByFiid(fiid);
        verify(bankRepository,times(1)).findByFiid(bankDTO.getFiid());
    }

    @Test
    void getBankByFiidFail() {
        when(bankRepository.existsByFiid("Galicia")).thenReturn(false);
        assertThrows(BankException.class,()->bankService.getBankByFiid("Galicia"));
    }
}
