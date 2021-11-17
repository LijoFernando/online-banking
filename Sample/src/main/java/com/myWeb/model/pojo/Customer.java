package com.myWeb.model.pojo;

import java.util.List;

public class Customer {

	// Variable
	private int authId;
	private String userName;
	private String userPassword;
	private String userType;
	private String email;
	private Integer cusID;
	private String name;
	private String dofBirth;
	private String location;
	private String CusStatus;
	private List<AccountInfo> listofAccountInfo;

	// Getter and Setter for name and Date
	public Integer getCusID() {
		return cusID;
	}

	public void setCusID(Integer cusID) {
		this.cusID = cusID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDofBirth() {
		return dofBirth;
	}

	public void setDofBirth(String dofBirth) {
		this.dofBirth = dofBirth;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCusStatus() {
		return CusStatus;
	}

	public void setCusStatus(String cusStatus) {
		CusStatus = cusStatus;
	}
//
//    @Override
//    public String toString() {
//        return "Customer{" +
//                "cusID=" + cusID +
//                ", name='" + name + '\'' +
//                ", dofBirth=" + dofBirth +
//                ", location='" + location + '\'' +
//                ", CusStatus='" + CusStatus + '\'' +
//                '}';
//    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<AccountInfo> getListofAccountInfo() {
		return listofAccountInfo;
	}

	public void setListofAccountInfo(List<AccountInfo> listofAccountInfo) {
		this.listofAccountInfo = listofAccountInfo;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getAuthId() {
		return authId;
	}

	public void setAuthId(int authId) {
		this.authId = authId;
	}
}
