package myApp.client.org.model;

import java.util.Date;
import java.util.List;

import myApp.frame.ui.AbstractDataModel;

public class OrgInfoModel extends AbstractDataModel {
	private Long 	infoId;
	private Date	modDate ;
	private String 	modReason;
	private String 	modDetail ;
	private Long 	parentCodeId ;
	private Long 	codeId;
	private String 	korName;
	private String 	shortName;
	private String 	engName;
	private String 	levelCode;
	private String 	levelName;
	private String 	sortOrder;
	private String 	note;
	
	private OrgCodeModel orgCodeModel = new OrgCodeModel() ;

	private List<AbstractDataModel> childList;
	
	@Override
	public void setKeyId(Long id) {
		this.setInfoId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getInfoId();
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public String getModReason() {
		return modReason;
	}

	public void setModReason(String modReason) {
		this.modReason = modReason;
	}

	public String getModDetail() {
		return modDetail;
	}

	public void setModDetail(String modDetail) {
		this.modDetail = modDetail;
	}

	public Long getParentCodeId() {
		return parentCodeId;
	}

	public void setParentCodeId(Long parentCodeId) {
		this.parentCodeId = parentCodeId;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getKorName() {
		return korName;
	}

	public void setKorName(String korName) {
		this.korName = korName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public OrgCodeModel getOrgCodeModel() {
		return orgCodeModel;
	}

	public void setOrgCodeModel(OrgCodeModel orgCodeModel) {
		this.orgCodeModel = orgCodeModel;
	}

	public List<AbstractDataModel> getChildList() {
		return childList;
	}

	public void setChildList(List<AbstractDataModel> childList) {
		this.childList = childList;
	}
	
	// orgCodeModel get/set methods 
	public String getOrgCode() {
		return orgCodeModel.getOrgCode();
	}

	public void setOrgCode(String orgCode) {
		orgCodeModel.setOrgCode(orgCode);
	}

	public Date getOpenDate() {
		return orgCodeModel.getOpenDate();
	}

	public void setOpenDate(Date openDate) {
		orgCodeModel.setOpenDate(openDate);
	}

	public Date getCloseDate() {
		return orgCodeModel.getCloseDate();
	}

	public void setCloseDate(Date closeDate) {
		orgCodeModel.setCloseDate(closeDate);
	}

	public String getOpenReason() {
		return orgCodeModel.getOpenReason(); 
	}

	public void setOpenReason(String openReason) {
		orgCodeModel.setOpenReason(openReason);
	}

	public String getCloseReason() {
		
		return orgCodeModel.getCloseReason(); 
	}

	public void setCloseReason(String closeReason) {
		orgCodeModel.setCloseReason(closeReason);
	}

//	public String getCodeNote() {
//		return orgCodeModel.getNote(); 
//	}
//
//	public void setCodeNote(String note) {
//		orgCodeModel.setNote(note);
//	}
}
