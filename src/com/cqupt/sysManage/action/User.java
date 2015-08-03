package com.cqupt.sysManage.action;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = -7206311738589257883L;
	private String txtUserId;
	private String txtName;
	private String txtPsw;
	private String deptId;
	
	private String deptName;
	private String teamName;
	private String groupId;
	private String txtUserGroup;
	private String txtPhone;
	
	private String txtEmail;
	private String isUseable;
	private String dataAuthId;
	private String dataAuthName;
	public String getTxtUserId() {
		return txtUserId;
	}
	public void setTxtUserId(String txtUserId) {
		this.txtUserId = txtUserId;
	}
	public String getTxtName() {
		return txtName;
	}
	public void setTxtName(String txtName) {
		this.txtName = txtName;
	}
	public String getTxtPsw() {
		return txtPsw;
	}
	public void setTxtPsw(String txtPsw) {
		this.txtPsw = txtPsw;
	}
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
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTxtUserGroup() {
		return txtUserGroup;
	}
	public void setTxtUserGroup(String txtUserGroup) {
		this.txtUserGroup = txtUserGroup;
	}
	public String getTxtPhone() {
		return txtPhone;
	}
	public void setTxtPhone(String txtPhone) {
		this.txtPhone = txtPhone;
	}
	public String getTxtEmail() {
		return txtEmail;
	}
	public void setTxtEmail(String txtEmail) {
		this.txtEmail = txtEmail;
	}
	public String getIsUseable() {
		return isUseable;
	}
	public void setIsUseable(String isUseable) {
		this.isUseable = isUseable;
	}
	
	public String getDataAuthId() {
		return dataAuthId;
	}
	public void setDataAuthId(String dataAuthId) {
		this.dataAuthId = dataAuthId;
	}
	

	public String getDataAuthName() {
		return dataAuthName;
	}
	public void setDataAuthName(String dataAuthName) {
		this.dataAuthName = dataAuthName;
	}
	@Override
	public String toString() {
		return "User [txtUserId=" + txtUserId + ", txtName=" + txtName
				+ ", txtPsw=" + txtPsw + ", deptId=" + deptId + ", deptName="
				+ deptName + ", groupId=" + groupId + ", txtUserGroup="
				+ txtUserGroup + ", txtPhone=" + txtPhone + ", txtEmail="
				+ txtEmail + ", isUseable=" + isUseable + "]";
	}
	
	
}
