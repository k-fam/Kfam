package myApp.client.cst;

import java.util.HashMap;
import java.util.Map;

import myApp.client.cst.model.IcamAccModel;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

public class MgComboBoxField extends StringComboBox implements InterfaceServiceCall {

	private Map<String, IcamAccModel> mgCodeList = new HashMap<String, IcamAccModel>();
	
	public MgComboBoxField(){
		ServiceRequest request = new ServiceRequest("cst.IcamAcc.selectByMgCombo");
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		this.setTriggerAction(TriggerAction.ALL);
  	}  	

//	public void addAllString(String str){
//		this.add(str);
//	}
	
	public String getCode(){
		IcamAccModel code = mgCodeList.get(this.getCurrentValue()); 
  		if(code != null){
  			return code.getMgCode(); 
  		}
  		else {
  			return null; 
  		}
  	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}
		for (AbstractDataModel model: result.getResult()) {
			IcamAccModel mgCode = (IcamAccModel)model ;
			mgCodeList.put(mgCode.getMgCodeName(), mgCode);
			this.add(mgCode.getMgCodeName());
		}
	}
	
}


