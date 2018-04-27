package myApp.client.dud.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface CodeGroupModelProperties extends PropertyAccess<CodeGroupModel> {
	
	ModelKeyProvider<CodeGroupModel> keyId();
	
	ValueProvider<CodeGroupModel, Long  >	codeGroupId();
	ValueProvider<CodeGroupModel, String>	groupCode();
	ValueProvider<CodeGroupModel, String>	groupName();
	ValueProvider<CodeGroupModel, Boolean>	sysYnFlag();
	ValueProvider<CodeGroupModel, String>	note();
}
