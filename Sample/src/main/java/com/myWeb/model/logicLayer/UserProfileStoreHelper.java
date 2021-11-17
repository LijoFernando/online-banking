package com.myWeb.model.logicLayer;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.pojo.Customer;

public class UserProfileStoreHelper {

	public static int updateUserProfileInfo(Customer customerProfileInfo) throws CustomizedException {
		int submitStatus = 0;
		int cusid = customerProfileInfo.getCusID();
		String fullname = customerProfileInfo.getName();
		String dofBirth = customerProfileInfo.getDofBirth();
		String address = customerProfileInfo.getLocation();
		boolean isSbmtted;
		isSbmtted = DataHandler.getPersistenceManager().updateUserProfileInfo(cusid, fullname, dofBirth, address);
		if (isSbmtted) {
			submitStatus = 1;
		}
		return submitStatus;
	}
}
