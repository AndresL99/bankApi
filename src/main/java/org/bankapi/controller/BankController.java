package org.bankapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.bankapi.exception.BankException;
import org.bankapi.model.Bank;
import org.bankapi.model.dto.BankDTO;
import org.bankapi.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/bank")
@Log4j2
@Tag(name = "Banks", description = "Endpoints to expose CRUD of Banks")
public class BankController {


    private final BankService bankService;
    private final WebClient webClient;

    public BankController(BankService bankService, WebClient.Builder builder) {
        this.bankService = bankService;
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    @Operation(summary = "Save Bank", description = "Save bank.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bank> save(@RequestBody BankDTO bankDTO){
        try {
            return new ResponseEntity<>(bankService.addBank(bankDTO), HttpStatus.CREATED);
        }
        catch (BankException e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get Banks", description = "Get All Banks.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankDTO>> getAllBanks(){
        return new ResponseEntity<>(bankService.getAllBanks(), HttpStatus.OK);
    }

    @Operation(summary = "Get a bank", description = "Get banks with fiid.")
    @GetMapping(value = "/{fiid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankDTO> getBank(@PathVariable("fiid") String fiid){
        try {
            return new ResponseEntity<>(bankService.getBankByFiid(fiid), HttpStatus.OK);
        }
        catch (BankException e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete Banks", description = "Delete bank with fiid.")
    @DeleteMapping(value = "/{fidd}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankDTO> deleteBank(@PathVariable("fiid") String fiid){
        try
        {
            return new ResponseEntity<>(bankService.removeBank(fiid), HttpStatus.OK);
        }
        catch (BankException e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update Bank", description = "Updates banks with fiid.")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bank> updateBank(@RequestBody BankDTO bankDTO){
        try {
            return new ResponseEntity<>(bankService.updateBankByFiid(bankDTO),HttpStatus.OK);
        }
        catch (BankException e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Self Call Bank Api", description = "Self Call.")
    @GetMapping("/selfcall/{fiid}")
    public ResponseEntity<BankDTO> selfCallByFiid(@RequestParam("fiid") String fiid) {
        try
        {
            String url = UriComponentsBuilder.fromPath("/bank/{fiid}")
                    .buildAndExpand(fiid)
                    .toUriString();
            BankDTO bankDTO = webClient.get()
                    .uri(url)
                    .exchange()
                    .flatMap(clientResponse -> {
                        if (clientResponse.statusCode() == HttpStatus.OK) {
                            return clientResponse.bodyToMono(BankDTO.class);
                        } else {
                            log.error("Error calling internal endpoint. Status: ", clientResponse.statusCode());
                            return Mono.error(new RuntimeException("Error to call internal endpoint"));
                        }
                    })
                    .block();
            return new ResponseEntity<>(bankDTO, HttpStatus.OK);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
