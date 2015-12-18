package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;

import java.util.UUID;

/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyTransactionRecordImpl implements BankMoneyTransactionRecord {

    UUID bankTransactionId;
    String balanceType;
    String transactionType;
    float amount;
    String cashCurrencyType;
    String bankOperationType;
    String bankDocumentReference;
    String bankName;
    String bankAccountNumber;
    String bankAccountType;
    long runningBookBalance;
    long runningAvailableBalance;
    long timeStamp;
    String memo;
    String status;

    public BankMoneyTransactionRecordImpl(UUID bankTransactionId, String balanceType, String transactionType, float amount, String cashCurrencyType, String bankOperationType, String bankDocumentReference, String bankName, String bankAccountNumber, String bankAccountType, long runningBookBalance, long runningAvailableBalance, long timeStamp, String memo, String status) {
        this.bankTransactionId = bankTransactionId;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.cashCurrencyType = cashCurrencyType;
        this.bankOperationType = bankOperationType;
        this.bankDocumentReference = bankDocumentReference;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountType = bankAccountType;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
        this.status = status;
    }

    @Override
    public UUID getBankTransactionId() {
        return bankTransactionId;
    }

    @Override
    public BankTransactionStatus getStatus() {
        return null;
    }

    @Override
    public BalanceType getBalanceType() {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return null;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public FiatCurrency getCurrencyType() {
        try {
            FiatCurrency.getByCode(cashCurrencyType);
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public BankOperationType getBankOperationType() {
        try{
            return BankOperationType.getByCode(bankOperationType);
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public String getBankDocumentReference() {
        return null;
    }

    @Override
    public String getBankName() {
        return null;
    }

    @Override
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    @Override
    public BankAccountType getBankAccountType() {
        try {
            return BankAccountType.getByCode(bankAccountType);
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public long getTimestamp() {
        return timeStamp;
    }

    @Override
    public long getRunningBookBalance() {
        return runningBookBalance;
    }

    @Override
    public long getRunningAvailableBalance() {
        return runningAvailableBalance;
    }

    @Override
    public String getMemo() {
        return memo;
    }
}
