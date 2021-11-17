package com.myWeb.model.persistence;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.pojo.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PersistenceManager {

	List<Customer> customerInfoRecords() throws CustomizedException;

	List<Customer> getListOfInactiveCustomers() throws CustomizedException;

	List<AccountInfo> accountInfoRecords() throws CustomizedException;

	int[] persistCustomerList(List<Customer> customerArrayList) throws CustomizedException;

	int insertAdminAuthData(AuthInfo adminAuthInfo) throws CustomizedException;

	int insertAuthData(Customer customer) throws CustomizedException;

	boolean isUsernameExists(String username) throws CustomizedException;

	void insertAccountToDB(int[] cusID, List<AccountInfo> accountInfoArrayList) throws CustomizedException;

	void removeAccountDetail(int cusId) throws CustomizedException;

	void deleteCustomer(Integer cusId) throws CustomizedException;

	void activeCustomer(int[] selectedIds) throws CustomizedException;

	Map<Long, AccountInfo> getAccountDetailsforUserByCusid(int Cusid);

	Customer getCustomerInfoByCusID(int CusID) throws CustomizedException;

	int getCusidByUsername(String username) throws CustomizedException;

	Customer getUserProfileInfo(String username, String password) throws CustomizedException;

	int changeUserPassword(AuthInfo authInfo)
			throws CustomizedException;

	List<Long> getAccountNumbersforCusId(int cusId) throws CustomizedException;

	int getCusIdwithAccNo(Long accNo) throws CustomizedException;
	
	boolean validateAccNo(Long accNo) throws CustomizedException;

	int getAccIDbyAccNO(Long accNo) throws CustomizedException;

	int getBalanceAmount(Long accNo) throws CustomizedException;

	ResultSet getDataforUserNameLike(String userNamekeyword) throws CustomizedException;
	
	TransactionHistory getFirstLastTransactionTime(long accNo) throws CustomizedException ;
	
	ResultSet getDownloadAsXslForAccNo(long accNo, String transactType, String fromDate, String TillDate)
			throws CustomizedException;

	boolean updateBalance(Long accNo, Integer amount) throws CustomizedException;
	
	boolean updateUserProfileInfo(int cusId, String fullName, String dofBirth, String address)
			throws CustomizedException ;

	int insertTransactionData(Transaction transaction) throws CustomizedException;

	int insertcashTransactionData(Transaction transaction) throws CustomizedException;

	String getVerifyAuthorizedUser(String username, String password) throws CustomizedException;

	//boolean insertBankTransaction(long benefAccNo, Integer transactionID) throws CustomizedException;

	//void makeTransaction(Transaction transaction) throws CustomizedException;

	List<Transaction> getAllTransactions() throws CustomizedException;

	List<Transaction> getTransactionsforCusId(Integer CusId) throws CustomizedException;

	ResultSet getTransactionsForAccNo(long accNo) throws CustomizedException;
	
	//add BeneficiaryInfo
	boolean insertBeneficiaryInfo(BeneficiaryInfo benefInfo) throws CustomizedException;
	
	List<BeneficiaryInfo> getBeneficiariesforCusID(int CusID) throws CustomizedException ;

}
