package myApp.client.cst.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface IcamAccModelProperties extends PropertyAccess<IcamAccModel> {
	
	ModelKeyProvider<IcamAccModel> keyId();
	ValueProvider<IcamAccModel, Long>	mgId();
	ValueProvider<IcamAccModel, String>	mgCode();
	ValueProvider<IcamAccModel, String>	mgCodeName();
	ValueProvider<IcamAccModel, String>	fundCode();
}
