package com.myWeb.model.pojo;

public class BeneficiaryInfo {
	private int benefId;
	private String benefNickName;
	private String benefFullName;
	private Long benefAccNo;
	private String benefBankBranch;
	private int cusId;
	
	
	public int getBenefId() {
		return benefId;
	}
	public void setBenefId(int benefId) {
		this.benefId = benefId;
	}
	public String getBenefNickName() {
		return benefNickName;
	}
	public void setBenefNickName(String benefNickName) {
		this.benefNickName = benefNickName;	
	}
	public String getBenefFullName() {
		return benefFullName;
	}
	public void setBenefFullName(String benefFullName) {
		this.benefFullName = benefFullName;
	}
	public Long getBenefAccNo() {
		return benefAccNo;
	}
	public void setBenefAccNo(Long benefAccNo) {
		this.benefAccNo = benefAccNo;
	}
	public String getBenefBankBranch() {
		return benefBankBranch;
	}
	public void setBenefBankBranch(String benefBankBranch) {
		this.benefBankBranch = benefBankBranch;
	}
	public int getCusId() {
		return cusId;
	}
	public void setCusId(int cusId) {
		this.cusId = cusId;
	}

}
