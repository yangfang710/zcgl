package com.cqupt.product;


public class Product {
	private String id;
	private String departmentId;
	private String departmentName;
	private String productCode;
	private String productName;
	private String classCode;
	private String productVersion;
	private String specification;
	private String price;
	private String manufacturers;
	private String productUse;
	private String receiveUser;
	private String productStatus;
	private String manageUser;
	private String useUser;
	private String address;
	private String lastOutTime;
	private String inTime;
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
	}

	public String getProductUse() {
		return productUse;
	}

	public void setProductUse(String productUse) {
		this.productUse = productUse;
	}

	public String getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getManageUser() {
		return manageUser;
	}

	public void setManageUser(String manageUser) {
		this.manageUser = manageUser;
	}

	public String getUseUser() {
		return useUser;
	}

	public void setUseUser(String useUser) {
		this.useUser = useUser;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLastOutTime() {
		return lastOutTime;
	}

	public void setLastOutTime(String lastOutTime) {
		this.lastOutTime = lastOutTime;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", departmentId=" + departmentId
				+ ", departmentName=" + departmentName + ", productCode="
				+ productCode + ", productName=" + productName + ", classCode="
				+ classCode + ", productVersion=" + productVersion
				+ ", specification=" + specification + ", price=" + price
				+ ", manufacturers=" + manufacturers + ", productUse="
				+ productUse + ", receiveUser=" + receiveUser
				+ ", productStatus=" + productStatus + ", manageUser="
				+ manageUser + ", useUser=" + useUser + ", address=" + address
				+ ", lastOutTime=" + lastOutTime + ", remark=" + remark + "]";
	}

}
