package com.myWeb.model.logicLayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.pojo.Transaction;

public class DataStoreHelper {

	// Delete CustomerInfo From Database
	public static void deleteExistingCustomer(Integer cusID) throws CustomizedException {
		if (!LoadDataToHMap.deletedList.contains(cusID)) {
			DataHandler.getPersistenceManager().deleteCustomer(cusID);
		}
	}

	public static List<Transaction> getAllTransactions() throws CustomizedException {
		List<Transaction> allTransactionsList = DataHandler.getPersistenceManager().getAllTransactions();
		return allTransactionsList;
	}

	public static List<Transaction> getTransactions(Integer cusId) throws CustomizedException {
		List<Transaction> transactionList = DataHandler.getPersistenceManager().getTransactionsforCusId(cusId);
		for (Transaction transaction : transactionList) {
			System.out.println(transaction.toString());
		}
		return transactionList;
	}

	public static List<Long> getAccountlistforCusID(int cusId) {
		List<Long> accountList = null;
		try {
			accountList = DataHandler.getPersistenceManager().getAccountNumbersforCusId(cusId);
		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// System.out.println(e);

		}
		return accountList;
	}

	public static void activateCustomer(int[] selectedId) throws CustomizedException {

		DataHandler.getPersistenceManager().activeCustomer(selectedId);
	}

	// CheckUsernameisExistOnTable
	public static boolean isUsernameExists(String username) throws CustomizedException {
		int custID = DataHandler.getPersistenceManager().getCusidByUsername(username);
		if (custID == -1) {
			return false;
		}
		return true;
	}

	public static int getAccID(Long accNo) {
		int accID = 0;
		try {
			accID = DataHandler.getPersistenceManager().getAccIDbyAccNO(accNo);
		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accID;
	}

	public static int checkValidAccount(Long accno) throws CustomizedException {
		int cusid = DataHandler.getPersistenceManager().getCusIdwithAccNo(accno);
		return cusid;
	}

	public static String getTimeStamp() {
		String timeStamp = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		timeStamp = formatter.format(date);
		return timeStamp;
	}

}
