package com.bankboot.kotak.controller;

import com.bankboot.kotak.dto.BankDipositWithdraw;
import com.bankboot.kotak.dto.BankShowDetail;
import com.bankboot.kotak.dto.BankTransfer;
import com.bankboot.kotak.model.Bank;
import com.bankboot.kotak.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // = @RequestBody + @Controller
@RequestMapping("/kotak")
public class BankControler {
    @Autowired
    private BankService bankService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCustByObject(@RequestBody Bank bank) {
        return bankService.createCustByService(bank);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<?> getCustById(@RequestParam long Id) {
        return bankService.getCustById(Id);
    }

    @GetMapping("/findbyname")
    public ResponseEntity<?> getCustListByName(@RequestParam String name) {
        return bankService.getCustListByName(name);
    }

    //    @PutMapping("/deposit")
//    public Bank updateBalanceById(@RequestParam long id,@RequestParam int amount){
//        return bankService.updateBalanceById(id,amount);
//    }
    @PutMapping("/deposit")
    public ResponseEntity<?> depositeBalanceById(@RequestBody BankDipositWithdraw bankDipositWithdraw) {
        return bankService.depositeBalanceById(bankDipositWithdraw);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<?> withdrawBalanceById(@RequestBody BankDipositWithdraw bankDipositWithdraw) {
        return bankService.withdrawBalanceById(bankDipositWithdraw);
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> transferAmountById(@RequestBody BankTransfer bankTransfer) {
        return bankService.transferAmountById(bankTransfer);
    }

    @GetMapping("/show")
    public ResponseEntity<?> showBalanceById(@RequestBody BankShowDetail bankShowDetail) {
        return bankService.showBalanceById(bankShowDetail);
    }
}
