package com.bankboot.kotak.repositories;

import com.bankboot.kotak.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepo extends JpaRepository<Bank,Long> {
     List<Bank> findByCustomerName(String name);

}
