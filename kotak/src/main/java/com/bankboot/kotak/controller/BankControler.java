package com.bankboot.kotak.controller;

import com.bankboot.kotak.dto.BankDipositWithdraw;
import com.bankboot.kotak.dto.BankShowDetail;
import com.bankboot.kotak.dto.BankTransfer;
import com.bankboot.kotak.model.Bank;
import com.bankboot.kotak.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // = @RequestBody + @Controller
@RequestMapping("/kotak")
public class BankControler {
    @Autowired
    private BankService bankService;
    @PostMapping("/save")
    public Bank saveCustByObject(@RequestBody Bank bank){
        return bankService.createCustByService(bank);
    }
    @GetMapping("/findbyid")
    public Bank getCustById(@RequestParam long Id){
        return bankService.getCustById(Id);
    }

    @GetMapping("/findbyname")
    public List<Bank> getCustListByName(@RequestParam String name){
        return bankService.getCustListByName(name);
    }

//    @PutMapping("/deposit")
//    public Bank updateBalanceById(@RequestParam long id,@RequestParam int amount){
//        return bankService.updateBalanceById(id,amount);
//    }
    @PutMapping("/deposit")
    public String depositeBalanceById(@RequestBody BankDipositWithdraw bankDipositWithdraw){
        return bankService.depositeBalanceById(bankDipositWithdraw);
    }

    @PutMapping("/withdraw")
    public String withdrawBalanceById(@RequestBody BankDipositWithdraw bankDipositWithdraw){
        return bankService.withdrawBalanceById(bankDipositWithdraw);
    }

    @PutMapping("/transfer")
    public String transferAmountById(@RequestBody BankTransfer bankTransfer){
        return bankService.transferAmountById(bankTransfer);
    }

    @GetMapping("/show")
    public String showBalanceById(@RequestBody BankShowDetail bankShowDetail){
        return bankService.showBalanceById(bankShowDetail);
    }
}
