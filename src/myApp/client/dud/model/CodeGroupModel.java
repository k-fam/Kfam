package myApp.client.dud.model;

import myApp.frame.ui.AbstractDataModel;

public class CodeGroupModel extends AbstractDataModel {
	private Long 	codeGroupId;
	private String	groupCode;
	private String 	groupName;
	private String 	sysYn;
	private Boolean sysYnFlag;
	private String	note;

	@Override
	public void setKeyId(Long id) {
		this.setCodeGroupId(id);
	}

	@Override
	public Long getKeyId() {
		return getCodeGroupId();
	}

	public Long getCodeGroupId() {
		return codeGroupId;
	}

	public void setCodeGroupId(Long codeGroupId) {
		this.codeGroupId = codeGroupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSysYn() {
		return sysYn;
	}

	public void setSysYn(String sysYn) {
		this.sysYn = sysYn;
	}

	public Boolean getSysYnFlag() {
		this.sysYnFlag = "true".equals(this.sysYn);
		return sysYnFlag;
	}

	public void setSysYnFlag(Boolean sysYnFlag) {
		this.sysYnFlag = sysYnFlag;
		this.sysYn = sysYnFlag.toString();
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	
	
}
