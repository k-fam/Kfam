package myApp.client.sys.model;

import java.util.Date;

import myApp.frame.ui.AbstractDataModel;

public class UserModel extends AbstractDataModel {

	private Long userId ;
	private Long companyId;
	private String korName; 
	private String email; 
	private String telNo; 
	private String tel1; 
	private String tel2; 
	private String tel3; 
	private String zipCode; 
	private String zipAddress; 
	private String zipDetail; 

	private String loginYn; 
	private String loginId; 
	private String passwd; 

	private String refreshTime;
	private Long   roleId;
	
	private String managerYn;
	private String closeYn;
	private String adminYn; 
	
	private Date startDate; 
	private Date endDate; 
	
	private CompanyModel companyModel = new CompanyModel(); 
	
	public String getAdminYn() {
		return adminYn;
	}

	public void setAdminYn(String adminYn) {
		this.adminYn = adminYn;
	}

	public UserModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setUserId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getUserId();
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getKorName() {
		return korName;
	}
	public void setKorName(String korName) {
		this.korName = korName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
//		this.telNo = getTel1() + getTel2() + getTel3();
	}
	
	public String getTel1() {
//		String telNo = getTelNo();
//		return telNo.substring(0,2);
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
//		String telNo = getTelNo();
//		return telNo.substring(3,telNo.length()-4);
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getTel3() {
//		String telNo = getTelNo();
//		return telNo.substring(telNo.length()-4, telNo.length());
		return tel3;
	}

	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}

	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getZipAddress() {
		return zipAddress;
	}
	public void setZipAddress(String zipAddress) {
		this.zipAddress = zipAddress;
	}
	public String getZipDetail() {
		return zipDetail;
	}
	public void setZipDetail(String zipDetail) {
		this.zipDetail = zipDetail;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getManagerYn() {
		return managerYn;
	}
	public void setManagerYn(String managerYn) {
		this.managerYn = managerYn;
	}

	public String getCloseYn() {
		return closeYn;
	}

	public void setCloseYn(String closeYn) {
		this.closeYn = closeYn;
	}
	
	public Boolean getCloseYnBoolean() {
		return "true".equals(this.closeYn); 
	}
	
	public void setCloseYnBoolean(Boolean closeYnBoolean) {
		this.closeYn = closeYnBoolean.toString(); 
	}
	
	public String getLoginYn() {
		return loginYn;
	}

	public void setLoginYn(String loginYn) {
		this.loginYn = loginYn;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public CompanyModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}

}

