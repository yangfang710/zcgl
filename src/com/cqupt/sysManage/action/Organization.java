package com.cqupt.sysManage.action;

import java.io.Serializable;
import java.util.Date;


public class Organization implements Serializable{
	private static final long serialVersionUID = 7159594091166305885L;
	private String deptId;
	private String deptName;
	private String hidParentDeptId;
	private String address;
	private String phoneNum;	
	private String deptState;
	private String remark;
	
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getHidParentDeptId() {
		return hidParentDeptId;
	}
	public void setHidParentDeptId(String hidParentDeptId) {
		this.hidParentDeptId = hidParentDeptId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getDeptState() {
		return deptState;
	}
	public void setDeptState(String deptState) {
		this.deptState = deptState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Department [deptId=" + deptId + ", deptName=" + deptName
				+ ", hidParentDeptId=" + hidParentDeptId + ", address="
				+ address + ", phoneNum=" + phoneNum + ", deptState="
				+ deptState + ", remark=" + remark + "]";
	}

	
	
	
}
