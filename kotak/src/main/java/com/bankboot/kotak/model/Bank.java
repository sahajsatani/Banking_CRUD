package com.bankboot.kotak.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tblkotak")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    @Id
    @Column(name = "custid")
    long id;

    @Column(name = "custname")
    String customerName;

    @Column(name = "accType")
    String accountType;

    @Column(name = "custdob")
    LocalDate dob;

    @Column(name = "age")
    int age;

    @Column(name = "custbalance")
    int balance;

    long transferId;
    public Bank setDob(String dob) {
        this.dob = LocalDate.parse(dob);
        return this;
    }

}
