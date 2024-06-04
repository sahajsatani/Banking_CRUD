package com.bankboot.kotak.controller;

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
    public String depositeBalanceById(@RequestBody Bank bank){
        return bankService.depositeBalanceById(bank);
    }

    @PutMapping("/withdraw")
    public String withdrawBalanceById(@RequestBody Bank bank){
        return bankService.withdrawBalanceById(bank);
    }

    @PutMapping("/transfer")
    public String transferAmountById(@RequestBody Bank bank){
        return bankService.transferAmountById(bank);
    }

    @GetMapping("/show")
    public String showBalanceById(@RequestBody Bank bank){
        return bankService.showBalanceById(bank);
    }
}
