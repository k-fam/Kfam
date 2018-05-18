package myApp.client.cst.model;

import myApp.frame.ui.AbstractDataModel;

public class IcamAccModel extends AbstractDataModel {

	private	Long 	mgId;
	private	String	mgCode;
	private String	mgCodeName;
	private	String	fundCode;

	@Override
	public void setKeyId(Long id) {
		this.setMgId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getMgId(); 
	}

	public Long getMgId() {
		return mgId;
	}

	public void setMgId(Long mgId) {
		this.mgId = mgId;
	}

	public String getMgCode() {
		return mgCode;
	}

	public void setMgCode(String mgCode) {
		this.mgCode = mgCode;
	}

	public String getMgCodeName() {
		return mgCodeName;
	}

	public void setMgCodeName(String mgCodeName) {
		this.mgCodeName = mgCodeName;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
}
