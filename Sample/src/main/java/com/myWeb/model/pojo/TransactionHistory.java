package com.myWeb.model.pojo;

public class TransactionHistory {
	private int transactionId;
	private long senderAccNo;
	private long receiverAccNo;
	private int transactAmout;
	private int creditAmount;
	private int debitAmount;
	private String timeOfTransaction;
	private String modeOfTransfer;
	private String firstTransactionTime;
	private String LastTransactionTime;
	
	public String getFirstTransactionTime() {
		return firstTransactionTime;
	}

	public void setFirstTransactionTime(String firstTransactionTime) {
		this.firstTransactionTime = firstTransactionTime;
	}

	public String getLastTransactionTime() {
		return LastTransactionTime;
	}

	public void setLastTransactionTime(String lastTransactionTime) {
		LastTransactionTime = lastTransactionTime;
	}



	public long getSenderAccNo() {
		return senderAccNo;
	}

	public void setSenderAccNo(long senderAccNo) {
		this.senderAccNo = senderAccNo;
	}

	public long getReceiverAccNo() {
		return receiverAccNo;
	}

	public void setReceiverAccNo(long receiverAccNo) {
		this.receiverAccNo = receiverAccNo;
	}

	public int getTransactAmout() {
		return transactAmout;
	}

	public void setTransactAmout(int transactAmout) {
		this.transactAmout = transactAmout;
	}

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

	public String getTimeOfTransaction() {
		return timeOfTransaction;
	}

	public void setTimeOfTransaction(String timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getModeOfTransfer() {
		return modeOfTransfer;
	}

	public void setModeOfTransfer(String modeOfTransfer) {
		this.modeOfTransfer = modeOfTransfer;
	}

}
