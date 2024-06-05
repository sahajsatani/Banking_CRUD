package com.bankboot.kotak.dto;

import lombok.Data;

@Data
public class BankTransfer {
    private long id;
    private long toId;
    private int amount;
}
