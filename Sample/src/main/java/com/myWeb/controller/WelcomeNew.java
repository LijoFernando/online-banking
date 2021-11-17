package com.myWeb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.logicLayer.AuthorizeHelper;
import com.myWeb.model.logicLayer.BeneficiaryProcessor;
import com.myWeb.model.logicLayer.CustomerMapData;
import com.myWeb.model.logicLayer.DataStoreHelper;
import com.myWeb.model.logicLayer.TransactHelper;
import com.myWeb.model.logicLayer.UserProfileStoreHelper;
import com.myWeb.model.logicLayer.LoadDataToHMap;
import com.myWeb.model.pojo.*;

/**
 * Servlet implementation class WelcomeNew
 */
@WebServlet("/WelcomeNew")
public class WelcomeNew extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String pagePath = null;

//	/**
//	 * Default constructor.
//	 */
//	public WelcomeNew() {
//		// TODO Auto-generated constructor stub
//	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("hi+post");
		String name = request.getParameter("name");

		if (name.equalsIgnoreCase("addAccount")) {

			pagePath = "/jsp/admin/addAccount.jsp";

		} else if (name.equalsIgnoreCase("createCustomer")) {
			pagePath = "/jsp/admin/createCustomer.jsp";

		} else if (name.equalsIgnoreCase("searchtransaction")) {

			pagePath = "/jsp/admin/searchTransaction.jsp";

		} else if (name.equalsIgnoreCase("adminHome")) {

			pagePath = "/jsp/admin/Index.jsp";

		}

		else if (name.equalsIgnoreCase("userHome")) {

			pagePath = "/jsp/user/UserPage.jsp";

		}

		else if (name.equalsIgnoreCase("isusernameexist")) {

			String username = request.getParameter("username");
			username = username.toLowerCase();
			System.out.println(username);
			boolean status = false;
			try {
				status = DataStoreHelper.isUsernameExists(username);
				if (status) {
					request.setAttribute("message", "Username Already Exists");

				}
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("status", status);

		} else if (name.equalsIgnoreCase("checkadminUsername")) {

			String uname = request.getParameter("uname");

			boolean exists = true;
			try {
				exists = AuthorizeHelper.isAdminUsernameAvailable(uname);
				System.out.println(exists);

			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter prinwriter = response.getWriter();
			prinwriter.write(String.valueOf(exists));
			prinwriter.close();
			pagePath = null;

		} else if (name.equalsIgnoreCase("addnewAdmin")) {
			String adminname = request.getParameter("uname");
			String password = request.getParameter("password");
			String mail = request.getParameter("mail");
			String userType = "admin";
			AuthInfo authInfo = new AuthInfo();
			authInfo.setUsername(adminname);
			authInfo.setPassword(password);
			authInfo.setEmail(mail);
			authInfo.setUsertype(userType);
			boolean authInfoSubmitStatus = false;
			String message = "Submission Failed";
			try {
				authInfoSubmitStatus = AuthorizeHelper.addAdminAuthInfo(authInfo);
				if (authInfoSubmitStatus) {
					message = "Submitted SuccessFully";
				}
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("submittedStatus", message);
			pagePath = "/jsp/admin/AddAdmin.jsp";

		}

		else if (name.equalsIgnoreCase("getDataForUserNameKeyword")) {

			String userNameKeyword = request.getParameter("uname");
			if (!userNameKeyword.isEmpty()) {
				Map<String, Long> userNameKeywordDataObject = TransactHelper.getDataofUserNameKeyword(userNameKeyword);
				if (userNameKeywordDataObject.size() != 0) {
					Gson gson = new Gson();
					Object json = gson.toJson(userNameKeywordDataObject);
					response.setContentType("json");
					response.getWriter().println(json);
				} else {
					request.setAttribute("message", "No RecordFound");
				}
			} else if (userNameKeyword.isEmpty()) {
				request.setAttribute("message", "Enter UserName");
			}
			pagePath = null;

			// getAccountfor this customerId
		} else if (name.equalsIgnoreCase("getAccNumbers")) {
			int cusId = Integer.parseInt(request.getParameter("customerId"));
			if (cusId != 0) {
				List<Long> accountNumberList = DataStoreHelper.getAccountlistforCusID(cusId);
				System.out.print(Arrays.deepToString(accountNumberList.toArray()));
				Gson gson = new Gson();
				Object json = gson.toJson(accountNumberList);
				response.setContentType("application/json");
				response.getWriter().print(json);
			} else {
				request.setAttribute("message", "Invalid CusID");
			}

		} else if (name.equalsIgnoreCase("getTransactions")) {
			long accountNo = Long.parseLong(request.getParameter("accNo"));
			// writer.print("accNo is" + accountNo);
			if (TransactHelper.validateTransactor(accountNo)) {
				List<TransactionHistory> transationHistoryList = TransactHelper.getTransactionsForAccNo(accountNo);
				// request.setAttribute("transactionHistory", transationHistoryList);
				Gson gson = new Gson();
				JsonElement element = gson.toJsonTree(transationHistoryList, new TypeToken<List<TransactionHistory>>() {
				}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				response.setContentType("application/json");
				response.getWriter().print(jsonArray);
				pagePath = null;
				// System.out.println("am here sucess");
			} else {
				//
				System.out.println("am here");
				request.setAttribute("message", "InvalidAccountNo");
			}
		}
		// Online Transaction
		else if (name.equalsIgnoreCase("doonlinetransaction")) {
			System.out.println("doonlinetransaction+Servlet");
			long senderAccNo = Long.parseLong(request.getParameter("accno"));
			long BenifaccNo = Long.parseLong(request.getParameter("benifaccno"));
			String transactType = "debit";
			String modeOfTransfer = "OnlineTransfer";
			int amount = Integer.parseInt(request.getParameter("amount"));
			if (amount >= 0) {
				if (TransactHelper.validateTransactor(senderAccNo) && TransactHelper.validateTransactor(senderAccNo)) {
					Transaction transaction = new Transaction();
					transaction.setAccountNo(senderAccNo);
					transaction.setBenifAccountNo(BenifaccNo);
					transaction.setTransactionType(transactType);
					transaction.setAmount(amount);
					transaction.setModeOfTransfer(modeOfTransfer);
					int errorNo = TransactHelper.doOnlineTranscation(transaction);
					if (errorNo == 3) {
						request.setAttribute("message", "Invalid Balance");
					} else if (errorNo == 2) {
						request.setAttribute("message", "Balance is lesser than Transfer Amount");
					} else if (errorNo == 1) {
						request.setAttribute("message", "Fund Transaction Failed");
					} else if (errorNo == 0) {
						request.setAttribute("message", "Transaction SuccessFull");
					}
				} else {
					request.setAttribute("message", "Your AccountNo or Benificiary AccNo is Invalid");
					System.out.println("invalid accno");
				}

			} else {
				request.setAttribute("message", "Enter Valid Amount");
			}
		} else if (name.equalsIgnoreCase("getuserCustomerInfo")) {

			int authid = Integer.parseInt(request.getParameter("authId"));
			Cookie[] cookies = request.getCookies();

			try {
				CustomerMapData customerData = new CustomerMapData();
				int status = customerData.loadUserData(name);
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (name.equalsIgnoreCase("cashTransaction")) {
			long accNo = Long.parseLong(request.getParameter("benifaccno"));
			try {
				Integer cusId = DataStoreHelper.checkValidAccount(accNo);
				if (cusId != 0) {

					long accountNo = Long.parseLong(request.getParameter("benifaccno"));
					String transactType = request.getParameter("transactType");
					int amount = Integer.parseInt(request.getParameter("amount"));
					System.out.println(accNo + " " + accountNo);

					Transaction transaction = new Transaction();
					transaction.setAccountNo(accountNo);
					transaction.setTransactionType(transactType);
					transaction.setAmount(amount);
					int errorNo = TransactHelper.cashTransaction(transaction);
					if (errorNo == 3) {
						request.setAttribute("message", "Invalid Balance");
					} else if (errorNo == 2) {
						request.setAttribute("message", "Balance is lesser than Transfer Amount");
					} else if (errorNo == 1) {
						request.setAttribute("message", "Fund Transaction Failed");
					} else if (errorNo == 0) {
						request.setAttribute("message", "Transaction SuccessFull");
					}
				} else {
					request.setAttribute("message", "Account Number Not Found");
					pagePath = "/jsp/admin/transactionPage.jsp";
				}
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				System.out.print("here+do transaction");
				e.printStackTrace();
			}

		} else if (name.equalsIgnoreCase("activecustomer")) {

			String[] selectedId = request.getParameterValues("activatecusid");
			System.out.println(selectedId);
			int length = selectedId.length;
			int[] cusID = new int[length];
			if (selectedId.length != 0) {
				for (int i = 0; i < selectedId.length; i++) {
					String cusid = selectedId[i];
					if (cusid != null) {
						cusID[i] = Integer.parseInt(cusid);
					}
				}
				try {
					DataStoreHelper.activateCustomer(cusID);
					List<Customer> customerList = LoadDataToHMap.getCustomerList();
					List<Customer> inactiveCustomers = LoadDataToHMap.deletedCustomerList();
					System.out.print(inactiveCustomers);
					request.setAttribute("inactiveCustomer", inactiveCustomers);
					request.setAttribute("customer", customerList);
				} catch (CustomizedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			pagePath = "/jsp/admin/customerPage.jsp";

		}

		/// get data for login user moudle

		// get profile Info data for cusid
		else if (name.equalsIgnoreCase("getProfileInfo")) {

			System.out.println("print Cookies");

			pagePath = "/jsp/user/UserProfile.jsp";

		} else if (name.equalsIgnoreCase("submitProfileChanges")) {

			Customer profileInfo = (Customer) request.getSession().getAttribute("profileInfo");

			int cusid = profileInfo.getCusID();

			String fullname = request.getParameter("fulName");
			String dofBirth = request.getParameter("dofB");
			String userAddress = request.getParameter("address");

			System.out.println(fullname + "" + dofBirth + "" + userAddress);
			Customer customerProfileInfo = new Customer();

			customerProfileInfo.setCusID(cusid);
			customerProfileInfo.setName(fullname);
			customerProfileInfo.setDofBirth(dofBirth);
			customerProfileInfo.setLocation(userAddress);
			int submitStatus = 0;
			try {

				submitStatus = UserProfileStoreHelper.updateUserProfileInfo(customerProfileInfo);

			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String message = "";
			if (submitStatus == 1) {
				profileInfo.setName(fullname);
				profileInfo.setDofBirth(dofBirth);
				profileInfo.setLocation(userAddress);
				message = "Submitted Successfully!!";
			} else if (submitStatus == 0) {
				message = "Submitted UnSuccessful!";
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter prinwriter = response.getWriter();
			prinwriter.write(message);
			prinwriter.close();

			// dispatcher Null
			pagePath = null;
		}

		else if (name.equalsIgnoreCase("userAccountInfo")) {

			pagePath = "/jsp/user/UserAccountInfo.jsp";

		} else if (name.equalsIgnoreCase("loadAccountList")) {
			Set<Long> accountList = (Set<Long>) request.getSession().getAttribute("acountslist");

			Gson gson = new Gson();
			JsonElement element = gson.toJsonTree(accountList, new TypeToken<Set<Long>>() {
			}.getType());
			JsonArray jsonArray = element.getAsJsonArray();
			response.setContentType("application/json");
			response.getWriter().print(jsonArray);
			pagePath = null;

		} else if (name.equalsIgnoreCase("getAccountInfo")) {

			Long accNo = Long.parseLong(request.getParameter("selectAccNo"));

			CustomerMapData customerMapData = (CustomerMapData) request.getSession().getAttribute("customerDataMap");

			Map<Long, AccountInfo> customerAccountInfo = customerMapData.getUserAccounts();

			System.out.println(customerAccountInfo.entrySet());

			AccountInfo accountInfo = customerAccountInfo.get(accNo);

			Gson gson = new Gson();
			Object jsonObj = gson.toJson(accountInfo);
			response.setContentType("application/json");
			response.getWriter().print(jsonObj);
			pagePath = null;

		}

		else if (name.equalsIgnoreCase("banktransaction")) {

			pagePath = "/jsp/user/userTransaction.jsp";
		}

		else if (name.equalsIgnoreCase("doUserBankTransaction")) {

			System.out.println("doUserBankTransaction");

			long senderAccNo = Long.parseLong(request.getParameter("sender"));

			long BenifaccNo = Long.parseLong(request.getParameter("benfAcc"));

			String transactType = "debit";

			String modeOfTransfer = "OnlineTransfer";

			int amount = Integer.parseInt(request.getParameter("transferFund"));

			String message = "";

			if (amount > 0) {

				if (TransactHelper.validateTransactor(senderAccNo) && TransactHelper.validateTransactor(BenifaccNo)) {

					Transaction transaction = new Transaction();
					transaction.setAccountNo(senderAccNo);
					transaction.setBenifAccountNo(BenifaccNo);
					transaction.setTransactionType(transactType);
					transaction.setAmount(amount);
					transaction.setModeOfTransfer(modeOfTransfer);

					int errorNo = TransactHelper.doOnlineTranscation(transaction);

					if (errorNo == 4) {
						message = "Sender and Beneficiary AccountNo are Same:Invalid Transaction";
					} else if (errorNo == 3) {
						message = "Invalid Balance";
					} else if (errorNo == 2) {
						message = "Balance is lesser than Transfer Amount";
					} else if (errorNo == 1) {
						message = "Fund Transaction Failed";
					} else if (errorNo == 0) {
						message = "Transaction SuccessFull";
					}
				} else {
					message = " Benificiary AccNo is Invalid";
					System.out.println("invalid accno");
				}

			} else {
				message = "Enter Valid Amount";
			}
			Customer profileInfo = (Customer) request.getSession().getAttribute("profileInfo");

			int cusid = profileInfo.getCusID();

			CustomerMapData customerMapData = new CustomerMapData();

			try {
				customerMapData.loadAccountData(cusid);
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Map<Long, AccountInfo> accountInfoMap = customerMapData.getUserAccounts();

			// System.out.print(accountInfoMap);

			Set<Long> set = accountInfoMap.keySet();

			// System.out.println(set);

			request.getSession().setAttribute("acountslist", set);

			request.getSession().setAttribute("customerDataMap", customerMapData);
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter prinwriter = response.getWriter();
			prinwriter.write(message);
			prinwriter.close();

			pagePath = null;
		}

		else if (name.equalsIgnoreCase("userCashTransaction")) {

			pagePath = "/jsp/user/CashTransaction.jsp";
		}

		else if (name.equalsIgnoreCase("doUserCashTransaction")) {

			long accNo = Long.parseLong(request.getParameter("accNo")); ///
			String message = "";
			try {
				Integer cusId = DataStoreHelper.checkValidAccount(accNo);
				if (cusId != 0) {
					long accountNo = Long.parseLong(request.getParameter("accNo"));
					String transactType = request.getParameter("transactType");
					int amount = Integer.parseInt(request.getParameter("transferFund"));

					System.out.println(accNo + " " + accountNo);

					if (amount > 0) {
						Transaction transaction = new Transaction();
						transaction.setAccountNo(accountNo);
						transaction.setTransactionType(transactType);
						transaction.setAmount(amount);
						int errorNo = TransactHelper.cashTransaction(transaction);

						if (errorNo == 3) {
							message = "Invalid Balance";
						} else if (errorNo == 2) {
							message = "Balance is lesser than Transfer Amount";
						} else if (errorNo == 1) {
							message = "Fund Transaction Failed";
						} else if (errorNo == 0) {
							message = "Transaction SuccessFull";
						}
					} else {
						message = "Enter Valid Amount";
					}
				} else {
					message = "Invalid Account Number";

				}
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				System.out.print("here+do transaction");
				e.printStackTrace();
			}

			Customer profileInfo = (Customer) request.getSession().getAttribute("profileInfo");

			int cusid = profileInfo.getCusID();

			CustomerMapData customerMapData = new CustomerMapData();

			try {
				customerMapData.loadAccountData(cusid);
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Map<Long, AccountInfo> accountInfoMap = customerMapData.getUserAccounts();

			// System.out.print(accountInfoMap);

			Set<Long> set = accountInfoMap.keySet();

			// System.out.println(set);

			request.getSession().setAttribute("acountslist", set);

			request.getSession().setAttribute("customerDataMap", customerMapData);

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter prinwriter = response.getWriter();
			prinwriter.write(message);
			prinwriter.close();
			pagePath = null;
		}

		else if (name.equalsIgnoreCase("userTransactionHistory")) {

			pagePath = "/jsp/user/UserTransactionHistory.jsp";

		} else if (name.equalsIgnoreCase("getUserTransactionHistory")) {

			String message = "";

			long accountNo = Long.parseLong(request.getParameter("accNo"));

			System.out.println(accountNo + "accno");

			Customer profileInfo = (Customer) request.getSession().getAttribute("profileInfo");

			int cusid = profileInfo.getCusID();

			System.out.println(cusid + "cus ID");

			if (TransactHelper.validateTransactor(accountNo)) {

				CustomerMapData customerMapData = new CustomerMapData();

				try {

					customerMapData.loadAccountData(cusid);

				} catch (CustomizedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				List<TransactionHistory> transactionHistoryList = customerMapData.getUserTransactions().get(accountNo);

				System.out.println(transactionHistoryList + "transct out+welcome");

				if (transactionHistoryList == null) {

					message = "Tranaction record NotFound";

				} else if (transactionHistoryList != null) {

					// request.getSession().setAttribute("transactionHistory",
					// transactionHistoryList);

					Gson gson = new Gson();
					Object json = gson.toJson(transactionHistoryList);
					response.setContentType("json");
					response.getWriter().println(json);
					message = "data loaded Sucessfully";
				}
				pagePath = null;

			} else {
				//
				System.out.println("am here");
				request.setAttribute("message", "InvalidAccountNo");
			}

		} else if (name.equalsIgnoreCase("loadTransactDatePicker")) {

			Customer profileInfo = (Customer) request.getSession().getAttribute("profileInfo");

			int cusId = profileInfo.getCusID();

			long accno = Long.parseLong(request.getParameter("accno"));

			TransactionHistory transactHistory = null;

			try {

				transactHistory = TransactHelper.getlastFistTransactionTime(accno);

			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(cusId + "," + accno);
			Gson gson = new Gson();
			Object json = gson.toJson(transactHistory);
			response.setContentType("json");
			response.getWriter().println(json);
			pagePath = null;

		} else if (name.equalsIgnoreCase("userPasswordChange")) {

			Customer profileInfo = (Customer) request.getSession().getAttribute("profileInfo");

			int authID = profileInfo.getAuthId();

			String userName = profileInfo.getUserName();

			String newPassword = request.getParameter("newPassword");

			String oldPassword = request.getParameter("oldPassword");

			AuthInfo authInfo = new AuthInfo();

			authInfo.setAuthId(authID);
			authInfo.setUsername(userName);
			authInfo.setPassword(oldPassword);
			authInfo.setNewPassword(newPassword);

			int changeStatus = 0;

			if (!oldPassword.equals(newPassword)) {

				try {

					changeStatus = AuthorizeHelper.changeUserPassword(authInfo);

				} catch (CustomizedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				changeStatus = 808;// oldpassword and new Password should not be same;
			}

			response.setContentType("text/plain");

			PrintWriter prinwriter = response.getWriter();

			prinwriter.print(changeStatus);

			System.out.println(changeStatus);

			System.out.println(userName + " " + authID + " " + newPassword + " " + oldPassword);

			pagePath = null;
		}

		else if (name.equalsIgnoreCase("getAccBalance")) {

			Long accountNo = Long.parseLong(request.getParameter("accNo"));

			System.out.println(accountNo);

			CustomerMapData customerMapData = (CustomerMapData) request.getSession().getAttribute("customerDataMap");

			Map<Long, AccountInfo> customerAccountInfo = customerMapData.getUserAccounts();

			System.out.println(customerAccountInfo.entrySet());

			AccountInfo accountInfo = customerAccountInfo.get(accountNo);

			int balance = accountInfo.getAccBalance();

			System.out.println(balance);

			response.setContentType("text/plain");

			PrintWriter prinwriter = response.getWriter();

			prinwriter.print(balance);

			pagePath = null;
		} else if (name.equalsIgnoreCase("addBeneficiaryPage")) {

			pagePath = "/jsp/user/AddBeneficiary.jsp";

		} else if (name.equalsIgnoreCase("addNewBeneficiary")) {

			// nickName: feild1, fullName: feild2, accNo: feild3, branchName: feild4

			String benefNickName = request.getParameter("nickName");
			String benefFullName = request.getParameter("fullName");
			Long benefAccNo = Long.parseLong(request.getParameter("accNo"));
			String benefBankBranch = request.getParameter("branchName");

			// getCusID
			Customer profileInfo = (Customer) request.getSession().getAttribute("profileInfo");
			int cusid = profileInfo.getCusID();

			// load to Beneficiary Pojo
			BeneficiaryInfo benefInfo = new BeneficiaryInfo();
			benefInfo.setBenefNickName(benefNickName);
			benefInfo.setBenefFullName(benefFullName);
			benefInfo.setBenefAccNo(benefAccNo);
			benefInfo.setBenefBankBranch(benefBankBranch);
			benefInfo.setCusId(cusid);

			BeneficiaryProcessor benefProcessor = new BeneficiaryProcessor();

			int processState = benefProcessor.submitBeneficiaryRecord(benefInfo);

			System.out.println(processState);

			response.setContentType("text/plain");

			PrintWriter prinwriter = response.getWriter();

			prinwriter.print(processState);

			pagePath = null;

		} else if (name.equalsIgnoreCase("getUserBeneficiary")) {

			Customer profileInfo = (Customer) request.getSession().getAttribute("profileInfo");

			int cusid = profileInfo.getCusID();

			List<BeneficiaryInfo> beneficiaryList = BeneficiaryProcessor.getBeneficiaryListForCusId(cusid);

			Gson gson = new Gson();
			JsonElement element = gson.toJsonTree(beneficiaryList, new TypeToken<List<TransactionHistory>>() {
			}.getType());
			JsonArray jsonArray = element.getAsJsonArray();
			response.setContentType("application/json");
			response.getWriter().print(jsonArray);

			pagePath = null;
		} 
//		else if (name.equalsIgnoreCase("dowloadTransactionsAsXsl")) {
//
//			long accno = Long.parseLong(request.getParameter("accNo"));
//			String transferType = request.getParameter("transactType");
//			String fromDate = request.getParameter("dateFrom");
//			String TillDate = request.getParameter("dateTill");
//
//			try {
//
//				/*
//				 * ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
//				 * workbook.write(outByteStream); // byte[] outArray =
//				 * outByteStream.toByteArray(); response.setContentType(
//				 * "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //
//				 * response.setContentLength(outArray.length); response.setHeader("Expires:",
//				 * "0"); // eliminates browser caching // String fileName =
//				 * URLDecoder.decode("MyBook.xls", "ISO8859_1");
//				 * response.setHeader("Content-Disposition",
//				 * "attachment; filename=MyBook.xlsx"); // OutputStream outStream =
//				 * response.getOutputStream(); // outStream.write(outArray);
//				 * workbook.write(outStream); workbook.close();
//				 */
//				File file = TransactHelper.exportTransactionHistoryasXsl(accno, transferType, fromDate, TillDate);
//				InputStream in = new FileInputStream(file);
//				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//				response.setHeader("Content-Disposition", "attachment;filename=filename.xlsx");
//				response.setContentLength((int) file.length());
//				OutputStream outstream = response.getOutputStream();
//				byte[] buffer = new byte[1024];
//				int len;
//				while ((len = in.read(buffer)) != -1) {
//					outstream.write(buffer, 0, len);
//				}
//				outstream.close();
//				in.close();
//
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				// System.out.println("download transact servlet error");
//			} catch (CustomizedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			pagePath = null;
//		}

		// RequestDispatcher
		if (pagePath != null) {
			// System.out.println("Page Path"+pagePath);
			RequestDispatcher rd = request.getRequestDispatcher(pagePath);
			rd.forward(request, response);
		} else {
			System.out.println("Requested Page NOT Found");
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.print("hi+get");
		try {
			LoadDataToHMap.loadHashMap();
		} catch (CustomizedException e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}

		String name = request.getParameter("name");
		if (name == null) {
			System.out.println("name query is empty");
		}

		if (name.equalsIgnoreCase("customer")) {

			try {
				List<Customer> customerList = LoadDataToHMap.getCustomerList();
				List<Customer> inactiveCustomers = LoadDataToHMap.deletedCustomerList();
				System.out.print(inactiveCustomers);
				request.setAttribute("inactiveCustomer", inactiveCustomers);
				request.setAttribute("customer", customerList);
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			pagePath = "/jsp/admin/customerPage.jsp";
		} else if (name.equalsIgnoreCase("account")) {
			List<AccountInfo> accountList = LoadDataToHMap.getAccountList();
			request.setAttribute("account", accountList);
			pagePath = "/jsp/admin/AccountPage.jsp";

		} else if (name.equalsIgnoreCase("transact")) {
			try {
				List<Transaction> allTransactionList = DataStoreHelper.getAllTransactions();
				request.setAttribute("transaction", allTransactionList);
				pagePath = "/jsp/admin/transactionPage.jsp";
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (name.equalsIgnoreCase("insertCustomer")) {

			Customer customer = new Customer();
			String username = request.getParameter("username").toLowerCase();
			String userPassword = request.getParameter("password");
			String cusName = request.getParameter("fname");
			String dob = request.getParameter("dob");
			String mail = request.getParameter("email");
			String location = request.getParameter("location");
			customer.setUserName(username);
			customer.setUserPassword(userPassword);
			customer.setDofBirth(dob);
			customer.setUserType("customer");
			customer.setName(cusName);
			customer.setEmail(mail);
			customer.setLocation(location);

			String message = "";
			try {
				int authId = DataHandler.getPersistenceManager().insertAuthData(customer);
				System.out.print(authId);
				customer.setAuthId(authId);
				List<Customer> insertCustomerList = new ArrayList<>();
				insertCustomerList.add(customer);
				int[] flag = DataHandler.getPersistenceManager().persistCustomerList(insertCustomerList);

				if (flag.length > 0) {
					message = "Record Inserted Successfully";
				} else {
					message = "Error!!";
				}

			} catch (Exception e) { // TODO: handle exception
				System.out.println(e.getMessage());
			}

		} else if (name.equalsIgnoreCase("insertaccount")) {
			AccountInfo accountInfo = new AccountInfo();
			accountInfo.setAccNo(Long.parseLong(request.getParameter("accno")));
			accountInfo.setAccBalance(Integer.parseInt(request.getParameter("accBalance")));
			accountInfo.setAccBranch(request.getParameter("city"));
			Map<Integer, Customer> customerHM = LoadDataToHMap.getCustomerHMap();
			System.out.println(customerHM.entrySet());

			int cusId = Integer.parseInt(request.getParameter("cusId"));
			if (customerHM.containsKey(cusId)) {
				List<AccountInfo> accCustomersList = new ArrayList<>();
				accCustomersList.add(accountInfo);
				int[] arr = new int[1];
				arr[0] = cusId;
				try {
					DataHandler.getPersistenceManager().insertAccountToDB(arr, accCustomersList);
				} catch (Exception e) { // TODO: handle exception
					System.out.println(e.getMessage());
				}
			} else {
				request.setAttribute("message", "Invalid CustomerID");
			}

			/*
			 * try { List<AccountInfo> accountList = LoadDataToHMap.getAccountList();
			 * request.setAttribute("account", accountList); } catch (Exception e) { //
			 * TODO: handle exception } pagePath = "/jsp/AccountPage.jsp";
			 */

		} else if (name.equalsIgnoreCase("deleteacc")) {
			String[] selectedId = request.getParameterValues("selectacc");
			for (int i = 0; i < selectedId.length; i++) {
				int cusid = Integer.parseInt(selectedId[i]);
				try {
					DataHandler.getPersistenceManager().removeAccountDetail(cusid);
				} catch (Exception e) {
					request.setAttribute("account", e);
				}
			}
			try {
				List<AccountInfo> accountList = LoadDataToHMap.getAccountList();
				request.setAttribute("account", accountList);
			} catch (Exception e) {
				// TODO: handle exception
			}
			pagePath = "/jsp/admin/AccountPage.jsp";

		} else if (name.equalsIgnoreCase("addadminpage")) {
			pagePath = "/jsp/admin/AddAdmin.jsp";
		} else if (name.equalsIgnoreCase("deletecustomer")) {

			String[] selectedId = request.getParameterValues("selectcusid");
			if (selectedId.length != 0) {

				for (int i = 0; i < selectedId.length; i++) {
					int cusid = Integer.parseInt(selectedId[i]);
					try {

						DataHandler.getPersistenceManager().deleteCustomer(cusid);
					} catch (Exception e) {
						request.setAttribute("account", e);
					}
				}
				List<Customer> customerList = LoadDataToHMap.getCustomerList();
				request.setAttribute("customer", customerList);
				pagePath = "/jsp/admin/customerPage.jsp";
			}
		} else if (name.equalsIgnoreCase("dowloadTransactionsAsXsl")) {

			long accno = Long.parseLong(request.getParameter("accNo"));
			String transferType = request.getParameter("transactType");
			String fromDate = request.getParameter("dateFrom");
			String TillDate = request.getParameter("dateTill");

			try {
				XSSFWorkbook workbook = TransactHelper.exportTransactionHistoryasXsl(accno, transferType, fromDate,
						TillDate);
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Expires:", "0"); 
				response.setHeader("Content-Disposition", "attachment; filename=MyBook.xlsx"); 
				OutputStream outStream =	response.getOutputStream(); 
				workbook.write(outStream);
				workbook.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// System.out.println("download transact servlet error");
			} catch (CustomizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			pagePath = null;
		} else {
			System.out.println("Request Declined");
		}

		if (pagePath != null) {
			RequestDispatcher rd = request.getRequestDispatcher(pagePath);
			rd.forward(request, response);
		} else {
			System.out.println("Requested Page NOT Found");
		}

	}

}
