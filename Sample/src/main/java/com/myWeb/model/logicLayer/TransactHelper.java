package com.myWeb.model.logicLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.comparatore.TransactionDateTime;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.pojo.Transaction;
import com.myWeb.model.pojo.TransactionHistory;

public class TransactHelper {

	// OnlineTransaction
	public static int doOnlineTranscation(Transaction transactionData) {
		System.out.println(transactionData);
		long senderAccNo = transactionData.getAccountNo();
		long benificeryAccNo = transactionData.getBenifAccountNo();
		int amount = transactionData.getAmount();
		int senderAccBalance = getBalance(senderAccNo);
		if (senderAccNo != benificeryAccNo) {
			if (senderAccBalance != 0) {
				transactionData.setAccBalance(senderAccBalance);
				if (isValidFundtoTransfer(senderAccBalance, amount)) {
					int benifAccBalance = getBalance(benificeryAccNo);
					boolean transactStatus = bankTansferBalanceUpdater(senderAccNo, senderAccBalance, benificeryAccNo,
							benifAccBalance, amount);
					if (transactStatus) {
						// transactionData.setTimeOfTransaction(getTimeStamp());
						int transactionId = -1;
						try {
							transactionId = DataHandler.getPersistenceManager().insertTransactionData(transactionData);
						} catch (CustomizedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (transactionId != 0) {
							return 0;
						}
					}
					return 1;
				}
				return 2;
			}
			return 3;
		}
		return 4;
	}

	public static boolean validateTransactor(Long senderAccNo) {
		int sendercusid;
		try {
			sendercusid = DataStoreHelper.checkValidAccount(senderAccNo);
			if (sendercusid != 0) {
				return true;
			}
		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static boolean bankTansferBalanceUpdater(long senderAccNo, int senderAccBalance, long benifAccNo,
			int benifAccBalance, int transactAmount) {

		int senderUpdtaedBalanace = senderAccBalance - transactAmount;
		int benificaryUpdtaedBalance = benifAccBalance + transactAmount;
		try {
			DataHandler.getPersistenceManager().updateBalance(senderAccNo, senderUpdtaedBalanace);
			DataHandler.getPersistenceManager().updateBalance(benifAccNo, benificaryUpdtaedBalance);
		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean cashTransferBalanceUpdater(long accNo, int accBalance, int transactAmount) {

		int updtaedBalance = accBalance + transactAmount;
		try {
			DataHandler.getPersistenceManager().updateBalance(accNo, updtaedBalance);
		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * public static String getTimeStamp() { String timeStamp = null;
	 * SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	 * Date date = new Date(); timeStamp = formatter.format(date);
	 * System.out.println(timeStamp); return timeStamp; }
	 */
	public static int getBalance(Long accNo) {
		int accBalance = 0;
		try {
			accBalance = DataHandler.getPersistenceManager().getBalanceAmount(accNo);
		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accBalance;
	}

	// decide transaction Whether it bank to bank or cash
	/*
	 * public static boolean TransactDecider(Transaction transactiondata) { boolean
	 * transactedStatus = false; String modeofTransfer =
	 * transactiondata.getModeOfTransfer(); long benefAccountNo =
	 * transactiondata.getBenifAccountNo();
	 * 
	 * // cash Bank to Bank Transaction Operation to db if
	 * (modeofTransfer.equalsIgnoreCase("OnlineTransfer")) { try {
	 * 
	 * // transactedStatus = //
	 * DataHandler.getPersistenceManager().insertBankTransaction(benefAccountNo,
	 * transactionId); } catch (CustomizedException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * }
	 */

	// cash withdraw/deposit operation to db
	/*
	 * else if (modeofTransfer.equalsIgnoreCase("cashDeposit") ||
	 * modeofTransfer.equalsIgnoreCase("cashWithdraw")) {
	 * transactiondata.setModeOfTransfer("cash"); System.out.println("cw deposit");
	 * try { int transactionId =
	 * DataHandler.getPersistenceManager().insertTransactionData(transactiondata);
	 * if (transactionId != 0) { transactedStatus = true; }
	 * 
	 * } catch (CustomizedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } return transactedStatus; }
	 */

	public static Map<String, Long> getDataofUserNameKeyword(String usernameKeyword) {
		Map<String, Long> usernameAccountNoMap = new HashMap<String, Long>();
		try {
			ResultSet rs = null;
			rs = DataHandler.getPersistenceManager().getDataforUserNameLike(usernameKeyword);
			while (rs.next()) {
				long accountNo = rs.getLong(1);
				String userName = rs.getString(2);
				System.out.print(accountNo + "<-->" + userName);
				usernameAccountNoMap.put(userName, accountNo);
			}
		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usernameAccountNoMap;

	}

	public static boolean isValidFundtoTransfer(int accBalance, int transactAmount) {
		if (accBalance >= transactAmount) {
			return true;
		}
		return false;
	}

	// ATM PROCESS
	// AccountNo,TransactType,Amount,CusId
	public static int cashTransaction(Transaction transactData) throws CustomizedException {
		int transactStatus = 1;
		String transactType = transactData.getTransactionType();
		long AccNo = transactData.getAccountNo();
		if (transactType.equals("deposit")) {
			String transactionType = "credit";
			String modeofTransaction = "cash";
			transactData.setTransactionType(transactionType);
			transactData.setModeOfTransfer(modeofTransaction);
			transactStatus = cashDeposit(transactData);
			return transactStatus;
		} else if (transactType.equals("withdraw")) {
			String transactionType = "debit";
			String modeofTransaction = "cash";
			transactData.setTransactionType(transactionType);
			transactData.setModeOfTransfer(modeofTransaction);
			transactStatus = cashWithdraw(transactData);
			return transactStatus;
		}
		// System.out.println("am out THelper");
		return transactStatus;
	}

	public static int cashDeposit(Transaction transactData) throws CustomizedException {
		int transactAmount = transactData.getAmount();
		long accountNo = transactData.getAccountNo();
		int accountBalance = getBalance(accountNo);
		transactData.setBenifAccountNo(accountNo);
		boolean transferStatus = cashTransferBalanceUpdater(accountNo, transactAmount, accountBalance);
		if (transferStatus) {
			int transactionId = DataHandler.getPersistenceManager().insertcashTransactionData(transactData);
			if (transactionId != 0) {
				return 0;
			}

		}
		return 1;
	}

	public static int cashWithdraw(Transaction transactionData) throws CustomizedException {
		long accountNo = transactionData.getAccountNo();
		int transferAmount = transactionData.getAmount();
		int accountBalance = getBalance(accountNo);
		if (accountBalance != 0) {
			if (isValidFundtoTransfer(accountBalance, transferAmount)) {
				boolean transactStatus = cashTransferBalanceUpdater(accountNo, accountBalance, transferAmount);
				if (transactStatus) {
					int transactionId = DataHandler.getPersistenceManager().insertcashTransactionData(transactionData);
					if (transactionId != 0) {
						return 0;
					}
				}
				return 1;
			}
			return 2;
		}
		return 3;
	}

	public static List<TransactionHistory> getTransactionsForAccNo(long accountNo) {

		List<TransactionHistory> transactionList = new ArrayList<TransactionHistory>();

		try {
			ResultSet resultSetForAccNo = DataHandler.getPersistenceManager().getTransactionsForAccNo(accountNo);

			while (resultSetForAccNo.next()) {
				TransactionHistory transactionHistory = new TransactionHistory();
				int transactId = resultSetForAccNo.getInt(1);
				long senderAccNo = resultSetForAccNo.getLong(2);
				long reciverAccNo = resultSetForAccNo.getLong(3);
				// cashTransactionCheck(Long accNo, Long BenefAccNo);
				String transactType = resultSetForAccNo.getString(4);
				String timeStamp = String.valueOf(resultSetForAccNo.getTimestamp(5));
				int transactAmount = resultSetForAccNo.getInt(6);
				String modeOfTransfer = resultSetForAccNo.getString(7);
				// System.out.println(creditAmount);
				// System.out.println(debitAmount);
				transactionSplitter(senderAccNo, reciverAccNo, accountNo, transactAmount, transactType, modeOfTransfer,
						transactionHistory);
				transactionHistory.setTransactionId(transactId);
				transactionHistory.setSenderAccNo(senderAccNo);
				transactionHistory.setReceiverAccNo(reciverAccNo);
				transactionHistory.setTransactAmout(transactAmount);
				transactionHistory.setTimeOfTransaction(timeStamp);
				transactionHistory.setModeOfTransfer(modeOfTransfer);

				System.out.println(transactionHistory);

				transactionList.add(transactionHistory);

			}
			Collections.sort(transactionList, new TransactionDateTime());

		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactionList;
	}

	public static String cashTransactionCheck(Long accNo, Long BenefAccNo) {
		String processTo = "";
		if (accNo == null && BenefAccNo != null) {
			processTo = String.valueOf(BenefAccNo);
		} else if (accNo != null && BenefAccNo == null) {
			processTo = String.valueOf(accNo);
		} else {
			System.out.println("THelper end");
		}
		return processTo;
	}

	public static void transactionSplitter(Long senderAccNo, Long reciverAccNo, Long transactorAccNo, int amount,
			String transactType, String modeOfTransfer, TransactionHistory transactionHistory) {

		System.out.println(senderAccNo + " == " + transactorAccNo);

		// Online Transfer
		if (transactType.equals("debit") && modeOfTransfer.equals("OnlineTransfer")) {
			if (senderAccNo.equals(transactorAccNo)) {
				transactionHistory.setDebitAmount(amount);
				transactionHistory.setCreditAmount(0);
			} else if (reciverAccNo.equals(transactorAccNo)) {
				transactionHistory.setDebitAmount(0);
				transactionHistory.setCreditAmount(amount);
			}
		}

		// Cash Transfer
		else if (senderAccNo.equals(transactorAccNo) && modeOfTransfer.equals("cash")) {

			if (transactType.equals("debit")) {
				transactionHistory.setDebitAmount(amount);
				transactionHistory.setCreditAmount(0);
			} else if (transactType.equals("credit")) {
				transactionHistory.setDebitAmount(0);
				transactionHistory.setCreditAmount(amount);
			}
		}
	}

	public static TransactionHistory getlastFistTransactionTime(long accNo) throws CustomizedException {
		TransactionHistory lastFistTransactTime = DataHandler.getPersistenceManager()
				.getFirstLastTransactionTime(accNo);
		String firstTransactTime = lastFistTransactTime.getFirstTransactionTime();
		String lastTransactTime = lastFistTransactTime.getLastTransactionTime();
		if (firstTransactTime == null || lastTransactTime == null) {
			return null;
		}
		return lastFistTransactTime;
	}

	/// Export Transaction History As xsl format code

	public static XSSFWorkbook exportTransactionHistoryasXsl(long accNo, String transactType, String fromDate, String TillDate)
			throws CustomizedException, IOException {

		if (transactType.equals("all")) {
			transactType = "";
		}

		ResultSet rs = DataHandler.getPersistenceManager().getDownloadAsXslForAccNo(accNo, transactType, fromDate,
				TillDate);
		XSSFWorkbook workbook = new XSSFWorkbook();
		
	//	File exportFile = null;
		
		try {
			
			XSSFSheet sheet = workbook.createSheet("transactions");
			writeHeaderLine(sheet);
			writeDataLines(rs, workbook, sheet);
		//	exportFile = new File("History" + fromDate + "_" + TillDate + ".xlsx");
		//	FileOutputStream outputStream = new FileOutputStream(exportFile);
			//workbook.write(outputStream);
		//	workbook.close();
		//	outputStream.flush();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workbook ;

	}

	private static void writeHeaderLine(XSSFSheet sheet) {

		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("TransactionId");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("AccountNo");

		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("BeneficiaryAccNo");

		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("TransactType");

		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("TimeStamp");

		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("TransactAmount");

		headerCell = headerRow.createCell(6);
		headerCell.setCellValue("TransactAmount");

	}

	private static void writeDataLines(ResultSet result, XSSFWorkbook workbook, XSSFSheet sheet) throws SQLException {
		int rowCount = 1;

		while (result.next()) {

			int transactId = result.getInt(1);
			long senderAccNo = result.getLong(2);
			long reciverAccNo = result.getLong(3);
			String transactType = result.getString(4);
			String timeStamp = String.valueOf(result.getTimestamp(5));
			int transactAmount = result.getInt(6);
			String modeOfTransfer = result.getString(7);
			System.out.println(transactId + "," + senderAccNo + "," + reciverAccNo + "," + transactType + ","
					+ timeStamp + "," + transactAmount + "," + modeOfTransfer);
			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;

			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(transactId);

			cell = row.createCell(columnCount++);
			cell.setCellValue(senderAccNo);

			cell = row.createCell(columnCount++);
			cell.setCellValue(reciverAccNo);

			cell = row.createCell(columnCount++);
			cell.setCellValue(transactType);

			cell = row.createCell(columnCount++);
			cell.setCellValue(timeStamp);
			/*
			 * XSSFCellStyle cellStyle = workbook.createCellStyle(); XSSFCreationHelper
			 * creationHelper = workbook.getCreationHelper();
			 * cellStyle.setDataFormat(creationHelper.createDataFormat().
			 * getFormat("yyyy-MM-dd HH:mm:ss")); cell.setCellStyle(cellStyle);
			 */

			cell = row.createCell(columnCount++);
			cell.setCellValue(transactAmount);

			cell = row.createCell(columnCount++);
			cell.setCellValue(modeOfTransfer);

		}
	}
}
