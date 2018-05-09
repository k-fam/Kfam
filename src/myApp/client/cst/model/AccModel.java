package myApp.client.cst.model;

import myApp.client.sys.model.CompanyModel;
import myApp.frame.ui.AbstractDataModel;

public class AccModel extends AbstractDataModel {

	private Long	accID;
	private Long	userID;
	private	String	mgCd;
	private String	accNo;
	private	String	fundCode;
	private String	accName;
	private String	accBranch;
	private String	branchManager;
	private String	managerContact;
	
	private String	mgName;
	
	@Override
	public void setKeyId(Long id) {
		this.setAccID(id);
	}

	@Override
	public Long getKeyId() {
		return getAccID();
	}

	public Long getAccID() {
		return accID;
	}

	public void setAccID(Long accID) {
		this.accID = accID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getMgCd() {
		return mgCd;
	}

	public void setMgCd(String mgCd) {
		this.mgCd = mgCd;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccBranch() {
		return accBranch;
	}

	public void setAccBranch(String accBranch) {
		this.accBranch = accBranch;
	}

	public String getBranchManager() {
		return branchManager;
	}

	public void setBranchManager(String branchManager) {
		this.branchManager = branchManager;
	}

	public String getManagerContact() {
		return managerContact;
	}

	public void setManagerContact(String managerContact) {
		this.managerContact = managerContact;
	}

	public String getMgName() {
		return mgName;
	}

	public void setMgName(String mgName) {
		this.mgName = mgName;
	}

}
