package myApp.client.org.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface OrgCodeModelProperties extends PropertyAccess<OrgInfoModel> {
	
	ModelKeyProvider<OrgCodeModel> 		keyId();	
	ValueProvider<OrgCodeModel, Long> 	codeId();
	ValueProvider<OrgCodeModel, Long> 	companyId();
	ValueProvider<OrgCodeModel, String> orgCode();
	ValueProvider<OrgCodeModel, Date> 	openDate();
	ValueProvider<OrgCodeModel, Date> 	closeDate();
	ValueProvider<OrgCodeModel, String> openReason();
	ValueProvider<OrgCodeModel, String> closeReason();
	ValueProvider<OrgCodeModel, String> note();
} 