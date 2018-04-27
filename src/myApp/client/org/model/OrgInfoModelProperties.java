package myApp.client.org.model;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface OrgInfoModelProperties extends PropertyAccess<OrgInfoModel> {
	
	ModelKeyProvider<OrgInfoModel> 		keyId();	
	ValueProvider<OrgInfoModel, Long> 	infoId();
	ValueProvider<OrgInfoModel, Date> 	modDate();
	ValueProvider<OrgInfoModel, String> modReason();
	ValueProvider<OrgInfoModel, String> modDetail();
	ValueProvider<OrgInfoModel, Long> 	parentCodeId();
	ValueProvider<OrgInfoModel, Long> 	codeId();
	ValueProvider<OrgInfoModel, String> korName();
	ValueProvider<OrgInfoModel, String> shortName();
	ValueProvider<OrgInfoModel, String> engName();
	ValueProvider<OrgInfoModel, String> levelCode();
	ValueProvider<OrgInfoModel, String> levelName();
	ValueProvider<OrgInfoModel, String> sortOrder();
	ValueProvider<OrgInfoModel, String> note();
	
	
	// treeDataStore에서 사용하기 위하여 선언한다.
	// eidtDriver 에서 사용하기 위해서는 orgInfoModel에 orgCodeModel의 get/set을 별도로 선언하였다. 
	@Path("orgCodeModel.orgCode")  
	ValueProvider<OrgInfoModel, String> orgCode();
	
	@Path("orgCodeModel.openDate")
	ValueProvider<OrgInfoModel, Date> openDate();
	
	@Path("orgCodeModel.openReason")
	ValueProvider<OrgInfoModel, String> openReason();
	
	@Path("orgCodeModel.closeDate")
	ValueProvider<OrgInfoModel, Date> closeDate();

	@Path("orgCodeModel.closeReason")
	ValueProvider<OrgInfoModel, String> closeReason();

	ValueProvider<OrgInfoModel, String> actionCell();	// 그리드에 버튼을 넣기 위한 선언이다. 
	
}

