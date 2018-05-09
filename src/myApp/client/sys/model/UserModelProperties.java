package myApp.client.sys.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface UserModelProperties  extends PropertyAccess<UserModel> {
	ModelKeyProvider<UserModel> keyId();
		
	ValueProvider<UserModel, Long> 		userId();
	ValueProvider<UserModel, Long> 		companyId();
	ValueProvider<UserModel, String> 	korName(); 
	ValueProvider<UserModel, String> 	email(); 
	ValueProvider<UserModel, String> 	telNo(); 
	ValueProvider<UserModel, String> 	zipCode(); 
	ValueProvider<UserModel, String> 	zipAddress(); 
	ValueProvider<UserModel, String> 	zipDetail(); 
	ValueProvider<UserModel, String> 	loginId();

	ValueProvider<UserModel, String> 	loginYn();

	ValueProvider<UserModel, String> 	passwd();
	ValueProvider<UserModel, Boolean> 	closeYnBoolean();
	ValueProvider<UserModel, String> 	managerYn();
	
	ValueProvider<UserModel, Date> 		startDate();
	ValueProvider<UserModel, Date> 		closeDate();
	
	ValueProvider<UserModel, String>	refreshTime();
	ValueProvider<UserModel, String>	mrdRole();
	
	// ValueProvider<UserModel, Boolean> 	managerYnBoolean();
}
