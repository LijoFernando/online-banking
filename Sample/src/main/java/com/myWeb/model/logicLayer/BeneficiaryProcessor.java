package com.myWeb.model.logicLayer;

import java.util.List;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.pojo.BeneficiaryInfo;

public class BeneficiaryProcessor {

	public int submitBeneficiaryRecord(BeneficiaryInfo benefInfo) {
		int processState = 0;
		long beneficiaryAccNo = benefInfo.getBenefAccNo();

		boolean isValidAccount;
		try {

			isValidAccount = DataHandler.getPersistenceManager().validateAccNo(beneficiaryAccNo);

			if (isValidAccount) {

				DataHandler.getPersistenceManager().insertBeneficiaryInfo(benefInfo);
				processState = 200;
			} else {

				processState = 1;
			}
		} catch (CustomizedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return processState;

	}

	public static List<BeneficiaryInfo> getBeneficiaryListForCusId(int Cusid) {
		
		List<BeneficiaryInfo> beneficeryList = null;
		
		try {
			beneficeryList = DataHandler.getPersistenceManager().getBeneficiariesforCusID(Cusid);

		} catch (CustomizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beneficeryList;
	}
	
	/*
	 * public static boolean beneficiaryAlreadyAdded(int cusId) {
	 * 
	 * }
	 */

}
