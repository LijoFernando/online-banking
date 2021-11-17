package com.myWeb.model.logicLayer;

import javax.xml.crypto.Data;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.pojo.AuthInfo;

public class AuthorizeHelper {

	// check new admin username exists;
	public static boolean isAdminUsernameAvailable(String name) throws CustomizedException {
		boolean isUsernameExist = false;

		if (!DataHandler.getPersistenceManager().isUsernameExists(name)) {
			isUsernameExist = true; // true while usernmae no one is used
		}
		return isUsernameExist;// otherwise false
	}

	/*
	 * public static boolean isEmailUsed(String email) { boolean isEmailExist =
	 * false;
	 * 
	 * return isEmailExist; }
	 */

	public static boolean addAdminAuthInfo(AuthInfo authinfo) throws CustomizedException {

		boolean authInfoSubmitStatus = false;

		int authid = DataHandler.getPersistenceManager().insertAdminAuthData(authinfo);
		if (authid != 0) {
			authInfoSubmitStatus = true;
		}
		return authInfoSubmitStatus;
	}

	public static int changeUserPassword(AuthInfo authInfo) throws CustomizedException {
		
		int changeStatus = 0;

		changeStatus = DataHandler.getPersistenceManager().changeUserPassword(authInfo);

		return changeStatus;
	}
}
