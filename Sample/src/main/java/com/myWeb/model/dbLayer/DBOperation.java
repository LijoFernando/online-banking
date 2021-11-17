package com.myWeb.model.dbLayer;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.persistence.PersistenceManager;
import com.myWeb.model.pojo.*;

import sun.util.resources.ar.CurrencyNames_ar_SY;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DBOperation implements PersistenceManager {
	private static Connection con;

	private static void loadConnection() throws CustomizedException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer", "root", "Root@123");
		} catch (SQLException e) {
			// TODO: handle exception
			throw new CustomizedException("JDBC Driver Error", e);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			throw new CustomizedException("Class Not Found in JDBC Driver,here", e);
		}

	}

	public static Connection getConnection() throws CustomizedException {
		if (con == null) {
			loadConnection();
		}
		return con;
	}

	// All Query
	private static final String selectAllInactiveCustomers = "SELECT * FROM CustomerInfo where CusStatus ='Inactive' ";
	private static final String selectAllRecordsFromCustomerInfo = "SELECT * FROM CustomerInfo WHERE CusStatus != 'Inactive' ";
	private static final String selectAllCustomerInfobyCusid = "select CusName,CusDoB,Location from customerInfo where CusId= ? and CusStatus!='Inactive'";
	private static final String selectAllAccountInfoforCustomerbyCusid = "select AccNumber, AccBalance, Branch from accountInfo where CusId = ?";
	private static final String selectAllRecordsFromAccountInfo = "SELECT * FROM AccountInfo inner join CustomerInfo ON AccountInfo.CusID = CustomerInfo.CusId AND CustomerInfo.CusStatus ='active'";
	private static final String selectAllRecordsFromTransactionTable = "select * from transaction";
	private static final String insertRecordsToCustomerTable = "insert into CustomerInfo (CusName, CusDoB, Location, UserName, authId) values (?, ?, ?, ?,?)";
	private static final String updateUserProfileInfo = "update customerinfo set CusName =?,CusDoB = ?,Location = ? where CusId = ?";
	private static final String insertUserAuthInfoToAuthIfoTable = " insert into userauthinfo (username, email, password, usertype) values (?, ?, ?, ?)";
	private static final String insertAuthInfoToAuthIfoTable = " insert into userauthinfo (username, email, password, usertype) values (?, ?, ?, ?)";
	private static final String insertRecordsToAccountInfoTable = "insert into AccountInfo (AccNumber, AccBalance, Branch, CusID ) values (?, ?, ?,?)";
	private static final String insertTransactionToTable = "insert into Transaction (AccountNo,BeneficiaryAccNo, TransactType,  TimeStamp, TransactAmount, ModeOfTransfer) values (? , ? ,?, now() , ?, ? )";
	private static final String insertCashTransactionToTable = "insert into Transaction (AccountNo, TransactType,  TimeStamp, TransactAmount, ModeOfTransfer) values (? , ?, now() , ?, ? )";
	private static final String insertBeneficaryInfo = "insert into customerbeneficiary (benefNickName,benefFullName,benefAccountNo,benefAccountBranch,customerId) values (?, ?, ?, ?, ?) ";
	private static final String getUserTypeforUser = "select usertype from userauthinfo where username = ? or email = ? and password = ?";
	private static final String getTransactionHistoryFilterByForAccNo = "select * from (select * from transaction where  (AccountNo = ? OR BeneficiaryAccNo = ?) and TransactType like(?) order by TimeStamp) as dum where dum.TimeStamp between ? and  ?";
	private static final String getBenefInfoListForCusID = "select beneficiaryId, benefNickName, benefFullName, benefAccountNo, benefAccountBranch from customerbeneficiary where customerId = ?";
	private static final String getProfileInfoByUserNamePassword = "select customerInfo.* from userauthinfo"
			+ " inner join customerInfo on userauthinfo.useAuthid = customerInfo.AuthId and userauthinfo.username = ? "
			+ "and userauthinfo.password = ? and customerInfo.CusStatus='Active'";

	@Override
	public List<Customer> customerInfoRecords() throws CustomizedException {
		List<Customer> customerInfoList = new ArrayList<>();
		try {
			PreparedStatement ps = getConnection().prepareStatement(selectAllRecordsFromCustomerInfo);
			ResultSet rs = ps.executeQuery();
			try {
				while (rs.next()) {
					Customer customerInfo = new Customer();
					customerInfo.setCusID(rs.getInt(1));
					customerInfo.setName(rs.getString(2));
					customerInfo.setDofBirth(rs.getString(3));
					customerInfo.setLocation(rs.getString(4));
					customerInfo.setCusStatus(rs.getString(5));
					customerInfo.setUserName(rs.getString(6));
					customerInfoList.add(customerInfo);
				}
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomizedException("Customer HashMap Loading is Unsuccessful !!");
		}
		return customerInfoList;
	}

	public boolean isUsernameExists(String username) throws CustomizedException {
		boolean isExits = false;
		ResultSet rs = null;
		String isUserNameExits = "select * from userauthinfo where username = ?";
		try {
			PreparedStatement ps = getConnection().prepareStatement(isUserNameExits);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				isExits = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("Failed while while username Validation");
		}
		return isExits;
	}

	public int getCusidByUsername(String username) throws CustomizedException {
		int customerId = -1;
		final String getCusidByUsername = "select customerInfo.CusID from customerInfo where UserName = ?";
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getCusidByUsername);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				customerId = rs.getInt(1);
			}
			System.out.println(rs);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("Error while fetching Customer data");
		}
		return customerId;

	}

	public List<Customer> getListOfInactiveCustomers() throws CustomizedException {
		ResultSet rs = null;
		List<Customer> inactiveCustomers = new ArrayList<Customer>();
		try {
			PreparedStatement ps = getConnection().prepareStatement(selectAllInactiveCustomers);
			try {
				rs = ps.executeQuery();
				while (rs.next()) {
					Customer customerInfo = new Customer();
					customerInfo.setCusID(rs.getInt(1));
					customerInfo.setName(rs.getString(2));
					customerInfo.setDofBirth(rs.getString(3));
					customerInfo.setLocation(rs.getString(4));
					customerInfo.setCusStatus(rs.getString(5));
					customerInfo.setUserName(rs.getString(6));
					inactiveCustomers.add(customerInfo);
				}
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			throw new CustomizedException("Error while fetching records");
		}
		return inactiveCustomers;
	}

	@Override
	public List<AccountInfo> accountInfoRecords() throws CustomizedException {
		List<AccountInfo> accountInfoArray = new ArrayList<>();
		try {
			PreparedStatement ps = getConnection().prepareStatement(selectAllRecordsFromAccountInfo);
			ResultSet rs = ps.executeQuery();
			try {
				while (rs.next()) {
					AccountInfo accountInfo = new AccountInfo();
					accountInfo.setAccId(rs.getInt(1));
					accountInfo.setAccNo(rs.getLong(2));
					accountInfo.setAccBalance(rs.getInt(3));
					accountInfo.setAccBranch(rs.getString(4));
					accountInfo.setCusId(rs.getInt(5));
					accountInfoArray.add(accountInfo);
				}
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomizedException("Account HashMap Loading is Unsuccessful,Invalid Query!!");
		}
		return accountInfoArray;
	}

	// insert Customer Info to Database
	public int[] persistCustomerList(List<Customer> customerArrayList) throws CustomizedException {
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(insertRecordsToCustomerTable,
					Statement.RETURN_GENERATED_KEYS);
			int[] cusId = new int[customerArrayList.size()];
			try {
				for (Customer customer : customerArrayList) {
					String username = customer.getUserName();
					String name = customer.getName();
					String date = customer.getDofBirth();
					String location = customer.getLocation();
					int authId = customer.getAuthId();
					ps.setString(1, name);
					ps.setString(2, date);
					ps.setString(3, location);
					ps.setString(4, username);
					ps.setInt(5, authId);
					ps.addBatch();
				}
				ps.executeBatch();
				rs = ps.getGeneratedKeys();
				int i = 0;
				while (rs.next()) {
					cusId[i] = rs.getInt(1);
					i++;
				}
				return cusId; // return Customers ID
			} catch (SQLException e) {
				throw new CustomizedException("SQL Exception " + e, e);
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			throw new CustomizedException("Customer Records Submission Unsuccessful " + e, e);
		}

	}

	// insert new admin AuthInfo
	public int insertAdminAuthData(AuthInfo adminAuthInfo) throws CustomizedException {
		ResultSet rs = null;
		int authid = 0;
		String userName = adminAuthInfo.getUsername();
		String password = adminAuthInfo.getPassword();
		String email = adminAuthInfo.getEmail();
		String userType = adminAuthInfo.getUsertype();
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement(insertAuthInfoToAuthIfoTable, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, userName);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, userType);
			ps.execute();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				authid = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomizedException("failed while submitting new Admin data" + e);
		}
		return authid;

	}

	public Customer getUserProfileInfo(String username, String password) throws CustomizedException {
		ResultSet rs = null;
		Customer customerProfileInfo = null;

		try {
			PreparedStatement ps = getConnection().prepareStatement(getProfileInfoByUserNamePassword);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (rs.next()) {
				int cusID = rs.getInt(1);
				String cusName = rs.getString(2);
				String dob = rs.getString(3);
				String location = rs.getString(4);
				String status = rs.getString(5);
				String usename = rs.getString(6);
				int authId = rs.getInt(7);
				customerProfileInfo = new Customer();
				customerProfileInfo.setCusID(cusID);
				customerProfileInfo.setName(cusName);
				customerProfileInfo.setDofBirth(dob);
				customerProfileInfo.setLocation(location);
				customerProfileInfo.setCusStatus(status);
				customerProfileInfo.setUserName(username);
				customerProfileInfo.setAuthId(authId);
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("Error while getting profile info");
		}
		return customerProfileInfo;

	}

	public int insertAuthData(Customer customer) throws CustomizedException {
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(insertUserAuthInfoToAuthIfoTable,
					Statement.RETURN_GENERATED_KEYS);
			String username = customer.getUserName();
			String password = customer.getUserPassword();
			String mail = customer.getEmail();
			String userType = customer.getUserType();
			ps.setString(1, username);
			ps.setString(2, mail);
			ps.setString(3, password);
			ps.setString(4, userType);
			ps.execute();

			rs = ps.getGeneratedKeys();
			int authid = 0;
			if (rs.next()) {
				authid = rs.getInt(1);
			}
			rs.close();
			ps.close();
			return authid;

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("failed while sumbitting authInfo" + e);
		}
	}

	// Insert AccountInfo to Database
	public void insertAccountToDB(int[] cusID, List<AccountInfo> accountInfoArrayList) throws CustomizedException {
		try {
			PreparedStatement ps = getConnection().prepareStatement(insertRecordsToAccountInfoTable);
			try {
				for (int i = 0; i < accountInfoArrayList.size(); i++) {
					AccountInfo accountInfo = accountInfoArrayList.get(i);
					long accNo = accountInfo.getAccNo();
					int accBalance = accountInfo.getAccBalance();
					String accBranch = accountInfo.getAccBranch();
					int cusId = cusID[i];
					ps.setLong(1, accNo);
					ps.setInt(2, accBalance);
					ps.setString(3, accBranch);
					ps.setInt(4, cusId);
					ps.addBatch();
				}
				ps.executeBatch();
				System.out.println("Account Record inserted");
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			throw new CustomizedException("Account Detail Submission Failed, Query Error");
		}
	}

	public void removeAccountDetail(int cusId) throws CustomizedException {
		String query = "DELETE FROM AccountInfo WHERE CusID =" + cusId + "";
		try {
			Statement st = getConnection().createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			throw new CustomizedException("Account Deletion Failed");
		}
	}

//	public void makeTransaction(Transaction transaction) throws CustomizedException {
//		try {
//			PreparedStatement ps = getConnection().prepareStatement(insertTransactionToTable);
//			long accountNo = transaction.getAccountNo();
//			String transactType = transaction.getTransactionType();
//			int amount = transaction.getAmount();
//			int accId = transaction.getAccID();
//			long benificeryAccNo = transaction.getBenifAccountNo();
//			// TransactionId,AccountNo,TransactType,Amount,CusId
//			ps.setLong(1, accountNo);
//			ps.setString(2, transactType);
//			ps.setInt(3, amount);
//			ps.setInt(4, accId);
//			ps.setLong(5, benificeryAccNo);
//			ps.execute();
//			ps.close();
//
//		} catch (SQLException | CustomizedException e) {
//
//			throw new CustomizedException("Transaction Records Submission Unsuccessful" + e, e);
//		}
//	}

	public int insertTransactionData(Transaction transaction) throws CustomizedException {
		boolean executeStatus = false;
		ResultSet rs = null;
		int transactionID = 0;
		try {
			PreparedStatement ps = getConnection().prepareStatement(insertTransactionToTable,
					Statement.RETURN_GENERATED_KEYS);
			long senderAccNo = transaction.getAccountNo();
			System.out.println(senderAccNo + "1");
			long benifAccNo = transaction.getBenifAccountNo();
			System.out.println(benifAccNo + "2");
			String transactType = transaction.getTransactionType();
			// System.out.println(transactType+"3");
			// String timeStamp = transaction.getTimeOfTransaction();
			String modeOfTransfer = transaction.getModeOfTransfer();
			int transactAmount = transaction.getAmount();
			System.out.println(transactAmount + "4");
			ps.setLong(1, senderAccNo);
			ps.setLong(2, benifAccNo);
			ps.setString(3, transactType);
			ps.setInt(4, transactAmount);
			ps.setString(5, modeOfTransfer);
			executeStatus = ps.execute();
			rs = ps.getGeneratedKeys();
			while (rs.next()) {
				transactionID = rs.getInt(1);

			}
			System.out.println(ps);
			ps.close();

		} catch (SQLException e) {

			throw new CustomizedException("Transaction Record insertion Failed" + e);
		}
		return transactionID;

	}

	public int insertcashTransactionData(Transaction transaction) throws CustomizedException {
		boolean executeStatus = false;
		ResultSet rs = null;
		int transactionID = 0;
		try {
			PreparedStatement ps = getConnection().prepareStatement(insertCashTransactionToTable,
					Statement.RETURN_GENERATED_KEYS);
			long accNo = transaction.getAccountNo();
			// System.out.println(accNo + "1");
			// System.out.println(benifAccNo + "2");
			String transactType = transaction.getTransactionType();
			// System.out.println(transactType+"3");
			// String timeStamp = transaction.getTimeOfTransaction();
			String modeOfTransfer = transaction.getModeOfTransfer();
			int transactAmount = transaction.getAmount();
			System.out.println(transactAmount + "4");
			ps.setLong(1, accNo);
			ps.setString(2, transactType);
			ps.setInt(3, transactAmount);
			ps.setString(4, modeOfTransfer);
			executeStatus = ps.execute();
			rs = ps.getGeneratedKeys();
			while (rs.next()) {
				transactionID = rs.getInt(1);

			}
			System.out.println(ps);
			ps.close();

		} catch (SQLException e) {

			throw new CustomizedException("Transaction Record insertion Failed" + e);
		}
		return transactionID;

	}

	/*
	 * public boolean insertBankTransaction(long benefAccNo, Integer transactionID)
	 * throws CustomizedException { String insertBanktranferData =
	 * "insert into banktransaction (BeneficiaryAccNo, transactionId) values (?,  ?)"
	 * ; try { PreparedStatement ps =
	 * getConnection().prepareStatement(insertBanktranferData); ps.setLong(1,
	 * benefAccNo); ps.setInt(2, transactionID); ps.execute(); ps.close(); } catch
	 * (SQLException e) { throw new
	 * CustomizedException("Error while submit BankTransaction data, " + e); }
	 * return false; }
	 */

	public List<Transaction> getAllTransactions() throws CustomizedException {

		List<Transaction> transactionList = new ArrayList<>();
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(selectAllRecordsFromTransactionTable);
			rs = ps.executeQuery();
			try {
				while (rs.next()) {
					int transactId = rs.getInt(1);
					long accNo = rs.getLong(2);
					long benifAccNo = rs.getLong(3);
					String transactionType = rs.getString(4);
					String timeStamp = String.valueOf(rs.getTimestamp(5));
					int amount = rs.getInt(6);
					String modeofTransaction = rs.getString(7);

					Transaction transaction = new Transaction();
					transaction.setTransactionId(transactId);
					transaction.setAccountNo(accNo);
					transaction.setBenifAccountNo(benifAccNo);
					transaction.setTransactionType(transactionType);
					transaction.setTimeOfTransaction(timeStamp);
					transaction.setAmount(amount);
					transaction.setModeOfTransfer(modeofTransaction);
					transactionList.add(transaction);
				}
				return transactionList;
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {

			throw new CustomizedException("All Transaction Record Not Found");
		}
	}

	public List<Transaction> getTransactionsforCusId(Integer cusID) throws CustomizedException {
		final String getTransactionWithCusId = "select * from Transaction where AccountNo IN ( select AccNumber from AccountInfo where CusId = "
				+ cusID + " )";
		List<Transaction> transactionList = new ArrayList<>();
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getTransactionWithCusId);
			rs = ps.executeQuery();
			try {

				while (rs.next()) {
					int transactId = rs.getInt(1);
					long accNo = rs.getLong(2);
					String transactionType = rs.getString(3);
					int amount = rs.getInt(4);
					int accId = rs.getInt(5);
					Transaction transaction = new Transaction();
					transaction.setTransactionId(transactId);
					transaction.setAccountNo(accNo);
					transaction.setTransactionType(transactionType);
					transaction.setAmount(amount);
					transaction.setAccID(accId);
					transactionList.add(transaction);
				}
				return transactionList;
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			throw new CustomizedException("Transaction Record Not Found");
		}
	}

	public ResultSet getTransactionsForAccNo(long accNo) throws CustomizedException {
		// List<TransactionHistory> transactionList = new
		// ArrayList<TransactionHistory>();
		ResultSet rs = null;
		final String getTransactionForAccNo = "select transactionId, dum.AccountNo, dum.BeneficiaryAccNo,"
				+ " dum.TransactType, TimeStamp, TransactAmount, ModeOfTransfer, "
				+ "ProcessTo from (select transaction.*, BeneficiaryAccNo as ProcessTo"
				+ " from transaction where AccountNo = ? and TransactType = 'debit' "
				+ "union select transaction.*,  AccountNo as ProcessTo from  transaction "
				+ "where BeneficiaryAccNo = ? and TransactType = 'debit' union select transaction.*,"
				+ " transaction.AccountNo as BeneficiaryAccNo from transaction where TransactType = 'credit'"
				+ " and AccountNo = ? ) dum  ORDER BY TimeStamp desc";

		try {
			PreparedStatement ps = getConnection().prepareStatement(getTransactionForAccNo);
			ps.setLong(1, accNo);
			ps.setLong(2, accNo);
			ps.setLong(3, accNo);
			rs = ps.executeQuery();

		} catch (SQLException e) {
			throw new CustomizedException("TransactionList for AccountNo NotFound");
		}
		return rs;
	}

	public TransactionHistory getFirstLastTransactionTime(long accNo) throws CustomizedException {
		TransactionHistory transactionHistory = new TransactionHistory();
		ResultSet rs = null;
		final String getFirstLastTransactTime = "(select TimeStamp from customer.transaction where  AccountNo = ? OR BeneficiaryAccNo = ? order by TimeStamp asc limit 1 ) union all( select TimeStamp from customer.transaction where  AccountNo = ? OR BeneficiaryAccNo = ? order by TimeStamp desc limit 1)";
		try {
			PreparedStatement ps = getConnection().prepareStatement(getFirstLastTransactTime);
			ps.setLong(1, accNo);
			ps.setLong(2, accNo);
			ps.setLong(3, accNo);
			ps.setLong(4, accNo);
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				String timeStamp = String.valueOf(rs.getTimestamp(1));
				if (i == 0) {
					transactionHistory.setFirstTransactionTime(timeStamp);
					i++;
				} else if (i == 1) {
					transactionHistory.setLastTransactionTime(timeStamp);
				}
			}

		} catch (SQLException e) {
			throw new CustomizedException("TransactionList for AccountNo NotFound");
		}
		return transactionHistory;
	}

	public ResultSet getDownloadAsXslForAccNo(long accNo, String transactType, String fromDate, String TillDate)
			throws CustomizedException {
		// List<TransactionHistory> transactionList = new
		// ArrayList<TransactionHistory>();
		String typeOfTransact = "%"+transactType;
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getTransactionHistoryFilterByForAccNo);
			ps.setLong(1, accNo);
			ps.setLong(2, accNo);
			ps.setString(3, typeOfTransact);
			ps.setString(4, fromDate+" 00:00:00");
			ps.setString(5, TillDate+" 00:00:00");
			rs = ps.executeQuery();

		} catch (SQLException e) {
			
			throw new CustomizedException("Failed while getting TransactionHistory");
		}
		return rs;
	}

	public List<Long> getAccountNumbersforCusId(int cusId) throws CustomizedException {
		List<Long> accountNumbersList = null;
		String getAccountNumberForCusId = "select AccNumber from accountInfo where CusID = ? ";
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getAccountNumberForCusId);
			ps.setInt(1, cusId);
			rs = ps.executeQuery();
			accountNumbersList = new ArrayList<Long>();
			while (rs.next()) {
				accountNumbersList.add(rs.getLong(1));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new CustomizedException("Error while Fetching AccountList");
		}
		return accountNumbersList;
	}

	public String getVerifyAuthorizedUser(String username, String password) throws CustomizedException {
		String userType = null;
		ResultSet rs = null;

		try {
			PreparedStatement ps = getConnection().prepareStatement(getUserTypeforUser);
			ps.setString(1, username);
			ps.setString(2, username);
			ps.setString(3, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				userType = rs.getString(1);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("failed while Verify username password");
		}
		return userType;
	}

	public int getBalanceAmount(Long accNo) throws CustomizedException {
		final String getAmountQuery = "select AccBalance from accountInfo where AccNumber = ? ";
		ResultSet rs = null;
		int accBalance = 0;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getAmountQuery);
			ps.setLong(1, accNo);
			try {
				rs = ps.executeQuery();
				if (rs.next()) {
					accBalance = rs.getInt(1);
				}
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			throw new CustomizedException("Account balance not Found" + e);
		}
		return accBalance;
	}

	public boolean updateBalance(Long accNo, Integer amount) throws CustomizedException {
		boolean updatedBalance = false;
		final String updateCustomerBalance = "update accountInfo set AccBalance = ? where AccNumber = ?";
		try {
			PreparedStatement ps = getConnection().prepareStatement(updateCustomerBalance);
			ps.setInt(1, amount);
			ps.setLong(2, accNo);
			try {
				ps.executeUpdate();
				updatedBalance = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ps.close();
			}
		} catch (SQLException e) {

			throw new CustomizedException("Account Transaction Failed" + e);
		}
		return updatedBalance;
	}

	public boolean updateUserProfileInfo(int cusId, String fullName, String dofBirth, String address)
			throws CustomizedException {
		boolean updatedProfile = false;
		try {
			PreparedStatement ps = getConnection().prepareStatement(updateUserProfileInfo);
			ps.setString(1, fullName);
			ps.setString(2, dofBirth);
			ps.setString(3, address);
			ps.setInt(4, cusId);
			try {
				int affectrows = ps.executeUpdate();
				if (affectrows == 1) {
					updatedProfile = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ps.close();
			}
		} catch (SQLException e) {

			throw new CustomizedException("Account Transaction Failed" + e);
		}
		return updatedProfile;
	}

	public int changeUserPassword(AuthInfo authInfo) throws CustomizedException {
		String changeUserPassword = "update userauthinfo set  password= ? where useAuthid = ? and username=? and password= ? ";
		int queryaffected = 0;
		try {

			int authid = authInfo.getAuthId();
			String username = authInfo.getUsername();
			String password = authInfo.getPassword();
			String newPassword = authInfo.getNewPassword();

			PreparedStatement ps = getConnection().prepareStatement(changeUserPassword);
			ps.setString(1, newPassword);
			ps.setInt(2, authid);
			ps.setString(3, username);
			ps.setString(4, password);
			queryaffected = ps.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("failed While changing Password");
		}
		return queryaffected;
	}

	public int getCusIdwithAccNo(Long accNo) throws CustomizedException {
		final String status = "Active";
		final String getCusIdwithAccNo = "select customerinfo.CusId from customerinfo inner join accountinfo ON customerinfo.CusId = accountinfo.CusId where accountinfo.AccNumber = ? AND customerinfo.CusStatus= ? ";

		ResultSet rs = null;
		int cusId = 0;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getCusIdwithAccNo);
			ps.setLong(1, accNo);
			ps.setString(2, status);
			try {
				rs = ps.executeQuery();
				if (rs.next()) {
					cusId = rs.getInt(1);
				}

			} finally {
				rs.close();
				ps.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("Invalid Account number / Inactive Customer " + e);
		}
		return cusId;
	}

	public boolean validateAccNo(Long accNo) throws CustomizedException {
		final String status = "Active";
		final String getCusIdwithAccNo = "select customerinfo.CusId from customerinfo inner join accountinfo ON customerinfo.CusId = accountinfo.CusId where accountinfo.AccNumber = ? AND customerinfo.CusStatus= ? ";
		boolean isValidaccount = false;
		ResultSet rs = null;
		int cusId = 0;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getCusIdwithAccNo);
			ps.setLong(1, accNo);
			ps.setString(2, status);
			try {
				rs = ps.executeQuery();
				if (rs.next()) {
					cusId = rs.getInt(1);
					if (cusId != 0) {
						isValidaccount = true;
					}
				}

			} finally {
				rs.close();
				ps.close();
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("Invalid Account number / Inactive Customer " + e);
		}
		return isValidaccount;
	}

	public boolean insertBeneficiaryInfo(BeneficiaryInfo benefInfo) throws CustomizedException {
		boolean executStatus = false;
		try {
			String benefNickName = benefInfo.getBenefNickName();
			String benefFullName = benefInfo.getBenefFullName();
			Long benefAccNo = benefInfo.getBenefAccNo();
			String benefBankBranch = benefInfo.getBenefBankBranch();
			int cusId = benefInfo.getCusId();

			PreparedStatement ps = getConnection().prepareStatement(insertBeneficaryInfo);
			ps.setString(1, benefNickName);
			ps.setString(2, benefFullName);
			ps.setLong(3, benefAccNo);
			ps.setString(4, benefBankBranch);
			ps.setInt(5, cusId);
			executStatus = ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomizedException("failed While storing Beneficiary Data " + e);
		}

		return executStatus;
	}

	public Customer getCustomerInfoByCusID(int CusID) throws CustomizedException {

		Customer customerInfo = null;

		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(selectAllCustomerInfobyCusid);
			ps.setInt(1, CusID);
			rs = ps.executeQuery();
			while (rs.next()) {
				customerInfo = new Customer();
				String name = rs.getString(1);
				String dateOfBirth = rs.getString(2);
				String address = rs.getString(3);
				customerInfo.setName(name);
				customerInfo.setDofBirth(dateOfBirth);
				customerInfo.setLocation(address);
			}
		} catch (SQLException e) {
			// TODO: handle exception

		}
		return customerInfo;
	}

	public List<BeneficiaryInfo> getBeneficiariesforCusID(int CusID) throws CustomizedException {

		List<BeneficiaryInfo> benefInfoList = new ArrayList<BeneficiaryInfo>();

		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getBenefInfoListForCusID);
			ps.setInt(1, CusID);
			rs = ps.executeQuery();
			while (rs.next()) {
				int benefId = rs.getInt(1);
				String benefNickName = rs.getString(2);
				String benefFullName = rs.getString(3);
				Long benefAccountNo = rs.getLong(4);
				String benefAccountBranch = rs.getString(5);
				BeneficiaryInfo benefInfo = new BeneficiaryInfo();
				benefInfo.setBenefId(benefId);
				benefInfo.setBenefNickName(benefNickName);
				benefInfo.setBenefFullName(benefFullName);
				benefInfo.setBenefAccNo(benefAccountNo);
				benefInfo.setBenefBankBranch(benefAccountBranch);
				benefInfoList.add(benefInfo);
			}
		} catch (SQLException e) {
			// TODO: handle exception

		}
		return benefInfoList;
	}

	public Map<Long, AccountInfo> getAccountDetailsforUserByCusid(int Cusid) {

		Map<Long, AccountInfo> accountListMapForCusID = new HashMap<Long, AccountInfo>();

		ResultSet rs = null;

		try {
			PreparedStatement ps = getConnection().prepareStatement(selectAllAccountInfoforCustomerbyCusid);
			ps.setInt(1, Cusid);
			rs = ps.executeQuery();
			while (rs.next()) {
				long accountNo = rs.getLong(1);
				int accountBalance = rs.getInt(2);
				String branch = rs.getString(3);
				AccountInfo accountInfo = new AccountInfo();
				accountInfo.setAccNo(accountNo);
				accountInfo.setAccBalance(accountBalance);
				accountInfo.setAccBranch(branch);
				accountListMapForCusID.put(accountNo, accountInfo);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return accountListMapForCusID;
	}

	public int getAccIDbyAccNO(Long accNo) throws CustomizedException {
		ResultSet rs = null;
		final String getAccidfromAccInfo = "select accountInfo.accId from accountInfo where accountInfo.accNumber = ? ";
		int accID = 0;

		try {
			PreparedStatement ps = getConnection().prepareStatement(getAccidfromAccInfo);
			ps.setLong(1, accNo);
			try {
				rs = ps.executeQuery();
				if (rs.next()) {
					accID = rs.getInt(1);
					System.out.println(accID);
				}
			} finally {
				rs.close();
				ps.close();
			}

		} catch (Exception e) {
			throw new CustomizedException("AccountID Not found" + e);
		}
		return accID;
	}

	// Delete Customer From Database
	public void deleteCustomer(Integer cusId) throws CustomizedException {
		String query = "Update CustomerInfo SET CusStatus = IF(CusStatus = 1, 2, CusStatus) WHERE CusId =" + cusId + "";
		try {
			Statement st = getConnection().createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			throw new CustomizedException("Customer Deletion Failed // No Records Found");
		}
	}

	public ResultSet getDataforUserNameLike(String userNamekeyword) throws CustomizedException {
		System.out.println(userNamekeyword);
		String keyword = "%" + userNamekeyword + "%";
		System.out.println(keyword);
		String getCusIDforUserNameLike = "select cusId, CusName from customerInfo where customerInfo.CusName like ?";
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getCusIDforUserNameLike);
			ps.setString(1, keyword);
			rs = ps.executeQuery();
			// System.out.println(rs.getString(1));

		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomizedException("Failed to get custCustomerID" + e);
		}
		return rs;
	}

	public void activeCustomer(int[] selectedIds) throws CustomizedException {
		String updateCustomerToActive = "Update CustomerInfo SET CusStatus = IF(CusStatus = 2, 1, CusStatus) WHERE CusId = ?";
		try {
			PreparedStatement ps = getConnection().prepareStatement(updateCustomerToActive);
			for (int i = 0; i < selectedIds.length; i++) {
				int customerID = selectedIds[i];
				ps.setInt(1, customerID);
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			throw new CustomizedException("Customer Activation Failed //No  Such Records Found");
		}
	}
}