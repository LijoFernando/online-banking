package com.myWeb.model.comparatore;

import java.util.Comparator;

import com.myWeb.model.pojo.TransactionHistory;

public class TransactionDateTime implements Comparator<TransactionHistory> {
	@Override
	public int compare(TransactionHistory o1, TransactionHistory o2) {
		// TODO Auto-generated method stub
		
		return o1.getTimeOfTransaction().compareTo(o2.getTimeOfTransaction());
	}
}
