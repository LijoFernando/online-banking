package com.myWeb.model.pojo;

import java.sql.Date;

public class Transaction {

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	private int transactionId;
	private Long accountNo;
	private Long benifAccountNo;
	private String transactionType;
	private int amount;
	private int creditAmount;
	private String modeOfTransfer;
	private int debitAmount;
	private String timeOfTransaction;
	private int accID;
	private int accBalance;
	private int benfAccBalance;

	public int getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(int creditAmount) {
		this.creditAmount = creditAmount;
	}

	public int getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(int debitAmount) {
		this.debitAmount = debitAmount;
	}

	@Override
	public String toString() {
		return transactionId + " " + accountNo + " " + transactionType + " " + amount;
	}

	public int getAccID() {
		return accID;
	}

	public void setAccID(int accID) {
		this.accID = accID;
	}

	public Long getBenifAccountNo() {
		return benifAccountNo;
	}

	public void setBenifAccountNo(Long benifAccountNo) {
		this.benifAccountNo = benifAccountNo;
	}

	public int getAccBalance() {
		return accBalance;
	}

	public void setAccBalance(int accBalance) {
		this.accBalance = accBalance;
	}

	public int getBenfAccBalance() {
		return benfAccBalance;
	}

	public void setBenfAccBalance(int benfAccBalance) {
		this.benfAccBalance = benfAccBalance;
	}

	public String getTimeOfTransaction() {
		return timeOfTransaction;
	}

	public void setTimeOfTransaction(String timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}

	public String getModeOfTransfer() {
		return modeOfTransfer;
	}

	public void setModeOfTransfer(String modeOfTransfer) {
		this.modeOfTransfer = modeOfTransfer;
	}
}
