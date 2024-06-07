package com.bankboot.kotak.service;

import com.bankboot.kotak.dto.BankDipositWithdraw;
import com.bankboot.kotak.dto.BankShowDetail;
import com.bankboot.kotak.dto.BankTransfer;
import com.bankboot.kotak.model.Bank;
import com.bankboot.kotak.repositories.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;
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
    public ResponseEntity<?> createCustByService(Bank bank) {
        try{
            int Tempage = calculateAge(bank.getDob());
            if (Tempage > 5 && bank.getBalance() >= 1000 && (bank.getAccountType().compareToIgnoreCase("saving") == 0 || bank.getAccountType().compareToIgnoreCase("current") == 0)) {
                bank.setId(generateUniqueNumber());
                bank.setAge(Tempage);
                bank.setAccountType(bank.getAccountType().toLowerCase());
                return new ResponseEntity<>(bankRepo.save(bank), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Not eligible with condition",HttpStatus.NOT_ACCEPTABLE);
            }
        }
        catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> getCustById(long Id) {
        try{
            Bank bank = bankRepo.findById(Id).get();
            if(bank!=null)
                return new ResponseEntity<>(bank,HttpStatus.OK);
            else
                return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getCustListByName(String name) {
        try{
            List<Bank> list= bankRepo.findByCustomerName(name);
            if(!list.isEmpty())
                return new ResponseEntity<>(list,HttpStatus.OK);
            else
                return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> depositeBalanceById(BankDipositWithdraw bankDipositWithdraw) {
        try{
            if (bankDipositWithdraw != null) {
                Bank tempBank = bankRepo.getById(bankDipositWithdraw.getId());
                tempBank.setBalance(tempBank.getBalance() + bankDipositWithdraw.getAmount());
                bankRepo.save(tempBank);
                return new ResponseEntity<>(bankDipositWithdraw.getAmount() + " Deposite Completed Successfully", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Deposite Failed",HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> withdrawBalanceById(BankDipositWithdraw bankDipositWithdraw) {
        try{
            if (bankDipositWithdraw != null) {
                Bank tempBank = bankRepo.getById(bankDipositWithdraw.getId());
                if (bankDipositWithdraw.getAmount() <= (tempBank.getBalance() - 1000)) {
                    tempBank.setBalance(tempBank.getBalance() - bankDipositWithdraw.getAmount());
                    bankRepo.save(tempBank);
                    return new ResponseEntity<>(bankDipositWithdraw.getAmount() + " Withdraw Completed Successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Insufficient Balance", HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                return new ResponseEntity<>("Withdraw Failed", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> transferAmountById(BankTransfer bankTransfer) {
        try{
            if (bankTransfer != null && bankTransfer.getToId() != 0) {
                Bank tempBank = bankRepo.getById(bankTransfer.getId());
                if (bankTransfer.getAmount() <= (tempBank.getBalance() - 1000)) {
                    tempBank.setBalance(tempBank.getBalance() - bankTransfer.getAmount());
                    bankRepo.save(tempBank);
                    tempBank = bankRepo.getById(bankTransfer.getToId());
                    tempBank.setBalance(tempBank.getBalance() + bankTransfer.getAmount());
                    bankRepo.save(tempBank);
                    return new ResponseEntity<>(bankTransfer.getAmount() + " Transaction Completed", HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("Insufficient Balance", HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                return new ResponseEntity<>("Transaction Failed", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> showBalanceById(BankShowDetail bankShowDetail) {
        try{
            if (bankShowDetail != null) {
                Bank tempBank = bankRepo.getById(bankShowDetail.getId());
                if(tempBank!=null){
                    String name = tempBank.getCustomerName();
                    String acc_type = tempBank.getAccountType();
                    String balance = String.valueOf(tempBank.getBalance());
                    return new ResponseEntity<>("Hi, " + name + ". In your Kotak '" + acc_type + "' type account total balance is " + balance + " INR.", HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Doesn't exist account.",HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            e.getStackTrace();
            return new ResponseEntity<>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
