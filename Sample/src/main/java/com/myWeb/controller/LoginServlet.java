
package com.myWeb.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.logicLayer.CustomerMapData;
import com.myWeb.model.pojo.AccountInfo;
import com.myWeb.model.pojo.Customer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	final String pagePath = null;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub'

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.out.println(username + " " + password);

		String userType = null;

		try {
			userType = DataHandler.getPersistenceManager().getVerifyAuthorizedUser(username, password);

		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userType == null) {

			request.setAttribute("message", "user name or password Invalid");
			response.sendRedirect("./SignInPage.jsp");

		} else if (userType.equals("admin")) {

			System.out.println("hai admin");
			HttpSession session = request.getSession(true);
			session.setAttribute("name", username);
			Cookie user = new Cookie("name", username);
			user.setHttpOnly(true);
			response.addCookie(user);
			response.sendRedirect("./jsp/admin/Index.jsp");

		} else if (userType.equals("customer")) {

			HttpSession session = request.getSession(true);
			session.setAttribute("name", username);
			Customer profileInfo = new Customer();
			try {
				profileInfo = DataHandler.getPersistenceManager().getUserProfileInfo(username, password);
				if (profileInfo != null) {

					session.setAttribute("profileInfo", profileInfo);

					int cusid = profileInfo.getCusID();	// System.out.println(profileInfo.getCusID());

					CustomerMapData customerData = new CustomerMapData();

					try {

						customerData.loadAccountData(cusid);

						Map<Long, AccountInfo> accountInfoMap = customerData.getUserAccounts();
						
						//System.out.print(accountInfoMap);

						Set<Long> set = accountInfoMap.keySet();

						//System.out.println(set);

						session.setAttribute("acountslist", set);
						
						session.setAttribute("customerDataMap", customerData);

					} catch (CustomizedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (CustomizedException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

			Cookie user = new Cookie("name", username);
		//	Cookie passwd = new Cookie("password", password);
			user.setHttpOnly(true);
			response.addCookie(user);
		//	response.addCookie(passwd);
			response.sendRedirect("./jsp/user/UserPage.jsp");

		}

		System.out.println(userType);

	}

}
