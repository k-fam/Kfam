package myApp.client.cst.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface AccModelProperties extends PropertyAccess<AccModel> {
	
	ModelKeyProvider<AccModel> keyId();
	
	ValueProvider<AccModel, Long>	accID();
	ValueProvider<AccModel, Long>	userID();
	ValueProvider<AccModel, String>	mgCd();
	ValueProvider<AccModel, String>	accNo();
	ValueProvider<AccModel, String>	fundCode();
	ValueProvider<AccModel, String>	accName();
	ValueProvider<AccModel, String>	accBranch();
	ValueProvider<AccModel, String>	branchManager();
	ValueProvider<AccModel, String>	managerContact();
	ValueProvider<AccModel, String>	mgName();
	
	ValueProvider<AccModel, String> actionCell();	// 그리드에 버튼을 넣기 위한 선언이다.   

}