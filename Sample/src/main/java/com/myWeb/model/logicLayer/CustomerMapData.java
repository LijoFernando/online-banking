
package com.myWeb.model.logicLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.comparatore.TransactionDateTime;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.pojo.AccountInfo;
import com.myWeb.model.pojo.BeneficiaryInfo;
import com.myWeb.model.pojo.Customer;
import com.myWeb.model.pojo.TransactionHistory;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

public class CustomerMapData {
	public Customer customerProfile = null;
	public Map<Long, AccountInfo> userAccountInfoDataMap = null;
	public Set<Long> accountNoList = null;
	public Map<Long, List<TransactionHistory>> userTransactionDataMap = null;
	public List<BeneficiaryInfo> beneficiaryList = null;
	Map<Long, AccountInfo> customerAccountDetail = new HashMap<Long, AccountInfo>();
	private static int cusID = 0;

	public int loadUserData(String username) throws CustomizedException {

		if (loadCustomerProfileData(username)) {
			if (loadAccountData(cusID)) {
				if (loadTransactionForAccNos(accountNoList)) {
					if (userTransactionDataMap == null) {
						return 0;
					}
					return 1;
				}
				return 2;
			}
			return 3;
		}
		return 4;
	}

	public boolean loadCustomerProfileData(String username) throws CustomizedException {

		cusID = DataHandler.getPersistenceManager().getCusidByUsername(username);
		customerProfile = new Customer();
		customerProfile = DataHandler.getPersistenceManager().getCustomerInfoByCusID(cusID);

		if (customerProfile != null) {
			return true;
		}
		return false;
	}

	public boolean loadAccountData(int CusID) throws CustomizedException {

		userAccountInfoDataMap = new HashMap<Long, AccountInfo>();

		userAccountInfoDataMap = DataHandler.getPersistenceManager().getAccountDetailsforUserByCusid(CusID);

		System.out.println(userAccountInfoDataMap + " after load ");

		accountNoList = userAccountInfoDataMap.keySet();

		System.out.println(accountNoList + " loadaccno");

		

		loadTransactionForAccNos(accountNoList);

		return true;
	}

	public boolean loadTransactionForAccNos(Set<Long> accountList) {

		for (Long accountNo : accountList) {

			// code for gettransactionHistory
			List<TransactionHistory> transactionHistory = TransactHelper.getTransactionsForAccNo(accountNo);

			//Collections.sort(transactionHistory, new TransactionDateTime());

			System.out.println(transactionHistory);

			userTransactionDataMap = new HashMap<Long, List<TransactionHistory>>();

			userTransactionDataMap.put(accountNo, transactionHistory);

		}
		if (userTransactionDataMap != null) {

			System.out.println(userTransactionDataMap.entrySet());

			return true;
		}

		return false;
	}

	public Map<Long, AccountInfo> getUserAccounts() {

		System.out.println(userAccountInfoDataMap);

		return userAccountInfoDataMap;

	}

	public Customer getCustomerInfo() {

		if (customerProfile != null) {
			return customerProfile;
		}
		return null;
	}

	public Map<Long, List<TransactionHistory>> getUserTransactions() {

		if (userTransactionDataMap != null) {

			return userTransactionDataMap;
		}
		return null;
	}

	public List<BeneficiaryInfo> getBeneficiaryList(int cusId) {
	
		beneficiaryList = BeneficiaryProcessor.getBeneficiaryListForCusId(cusId);
		
		return beneficiaryList;
	}

}
