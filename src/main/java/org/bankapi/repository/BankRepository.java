package org.bankapi.repository;

import org.bankapi.model.Bank;
import org.bankapi.model.dto.BankDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    BankDTO findByFiid(String fiid);

    boolean existsByFiid(String fiid);

    BankDTO deleteByFiid(String fiid);

}
