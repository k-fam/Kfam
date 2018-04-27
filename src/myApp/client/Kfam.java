package myApp.client;

import com.google.gwt.core.client.EntryPoint;
import myApp.frame.Main_Login;

public class Kfam implements EntryPoint {

	@Override
	public void onModuleLoad() {
		myApp.frame.Main_Login login = new Main_Login();
		login.open();  
	}
}
