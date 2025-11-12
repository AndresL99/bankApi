package org.bankapi.controller;

import org.bankapi.exception.BankException;
import org.bankapi.model.Bank;
import org.bankapi.model.dto.BankDTO;
import org.bankapi.service.BankService;
import org.bankapi.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BankControllerTest extends AbstractBankControllerTest{

    private BankService bankService;
    private BankController bankController;
    private WebClient.Builder webClient;

    @BeforeEach
    public void setUp(){
        webClient = Mockito.mock(WebClient.builder());
        bankService = Mockito.mock(BankService.class);
        bankController = new BankController(bankService,webClient);
    }

    @Test
    void getAllBanks(){
        when(bankService.getAllBanks()).thenReturn(TestUtils.getAllBanksDto());
        ResponseEntity<List<BankDTO>> response = bankController.getAllBanks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TestUtils.getAllBanksDto(), response.getBody());
    }

    @Test
    void addBank() throws BankException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(bankService.addBank(TestUtils.getBankDTO())).thenReturn(TestUtils.getBank());
        ResponseEntity<Bank>response = bankController.save(TestUtils.getBankDTO());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(TestUtils.getBankDTO().getFiid(), response.getBody().getFiid());
    }

    @Test
    void updateBank() throws BankException {

        when(bankService.updateBankByFiid(TestUtils.getBankDTO())).thenReturn(TestUtils.getBank());
        ResponseEntity<Bank>response = bankController.updateBank(TestUtils.getBankDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TestUtils.getBankDTO().getFiid(), response.getBody().getFiid());
    }

    @Test
    void removeBank() throws BankException {
       when(bankService.removeBank(TestUtils.getBankDTO().getFiid())).thenReturn(TestUtils.getBankDTO());
       ResponseEntity<BankDTO>response = bankController.deleteBank(TestUtils.getBankDTO().getFiid());

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(TestUtils.getBankDTO().getFiid(), response.getBody().getFiid());
       verify(bankService,times(1)).removeBank(TestUtils.getBankDTO().getFiid());
    }
}
