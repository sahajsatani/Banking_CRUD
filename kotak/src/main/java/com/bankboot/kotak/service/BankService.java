package com.bankboot.kotak.service;

import com.bankboot.kotak.model.Bank;
import com.bankboot.kotak.repositories.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
public class BankService {
    @Autowired
    private BankRepo bankRepo;
    public static int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        if ((birthDate != null) && (birthDate.isBefore(currentDate))) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            throw new IllegalArgumentException("The birthDate must be before the current date");
        }
    }
    private static long generateUniqueNumber(){
        //First way
//        long timestamp = System.currentTimeMillis()%1_000_0000L; //Last 10 digit of time stamp
//        long randomNumber = random.nextInt(1_000); //Random number with 9 digit
//        return timestamp*1_000 + randomNumber;//combine them to ensure it's 10 digit

        //Second way
        UUID uuid = UUID.randomUUID();
        long uniqueNumber = Math.abs(uuid.getMostSignificantBits() % 10000000000L);
        return uniqueNumber;
    }
    public Bank createCustByService(Bank bank) {
        int Tempage = calculateAge(bank.getDob());
        if(Tempage>5 && bank.getBalance()>=1000 && (bank.getAccountType().compareToIgnoreCase("saving")==0 || bank.getAccountType().compareToIgnoreCase("current")==0)) {
            bank.setId(generateUniqueNumber());
            bank.setAge(Tempage);
            return bankRepo.save(bank);
        }
        else{
            return null;
        }
    }
    public Bank getCustById(long Id)
    {
        return bankRepo.findById(Id).get();
    }

    public List<Bank> getCustListByName(String name) {
        return bankRepo.findByCustomerName(name);
    }

    public String depositeBalanceById(Bank bank) {
        if(bank!=null){
            Bank tempBank = getCustById(bank.getId());
            tempBank.setBalance(tempBank.getBalance() + bank.getBalance());
            bankRepo.save(tempBank);
            return "Deposite Completed Successfully";
        }
        else{
            return "Deposite Failed";
        }
    }

    public String withdrawBalanceById(Bank bank) {
        if(bank!=null ){
            Bank tempBank = getCustById(bank.getId());
            if(bank.getBalance()<=(tempBank.getBalance()-1000)){
                tempBank.setBalance(tempBank.getBalance()-bank.getBalance());
                bankRepo.save(tempBank);
                return "Withdraw Completed Successfully";
            }
            else{
                return "Withdraw Failed";
            }
        }
        else{
            return "Withdraw Failed";
        }
    }

    public String transferAmountById(Bank bank) {
        if(bank!=null && bank.getTransferId()!=0){
            Bank tempBank = getCustById(bank.getId());
            if(bank.getBalance()<=(tempBank.getBalance()-1000)){
                tempBank.setBalance(tempBank.getBalance()-bank.getBalance());
                bankRepo.save(tempBank);
                tempBank = getCustById(bank.getTransferId());
                tempBank.setBalance(tempBank.getBalance()+bank.getBalance());
                bankRepo.save(tempBank);
                return String.valueOf(bank.getBalance())+" Transaction Completed";
            }
            else{
                return "Transaction Failed";
            }
        }
        else{
            return "Transaction Failed";
        }
    }

    public String showBalanceById(Bank bank) {
        if(bank!=null){
            Bank tempBank = getCustById(bank.getId());
            String name = tempBank.getCustomerName();
            String acc_type = tempBank.getAccountType();
            String balance = String.valueOf(tempBank.getBalance());
            return "Hi, "+name+". In your "+acc_type+" account current balance is "+balance+"INR.";
        }
        else{
            return "Doesn't exist account.";
        }
    }
}
