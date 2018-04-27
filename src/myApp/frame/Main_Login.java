package myApp.frame;

import myApp.client.sys.model.UserModel;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.SimpleMessage;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class Main_Login implements InterfaceServiceCall {
	
//	private final Dialog loginDialog = new Dialog();
	private TextField firstName = new TextField();
	private PasswordField password= new PasswordField();
    private CenterLayoutContainer container = new CenterLayoutContainer();
    Viewport viewport = new Viewport();
     
	public void open() {
		
		FieldLabel loginFieldLabel = new FieldLabel(firstName, "로그인ID ");
		loginFieldLabel.setLabelWidth(60);
		firstName.setText("alignfactory@gmail.com");
		
		FieldLabel passwdFieldLabel = new FieldLabel(password, "패스워드 ");  
		passwdFieldLabel.setLabelWidth(60);
		passwdFieldLabel.setWidth(264);
		password.setText("1234");

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		HTML image = new HTML("<center><div><img src='img/Login.jpg' width='423' height='103'></center></div>"); 
		vlc.add(image, new VerticalLayoutData(300, -1, new Margins(0, 0, 10, 0)));
		
		vlc.add(loginFieldLabel, new VerticalLayoutData(280, -1, new Margins(0, 0, 5, 15)));
		
		HBoxLayoutContainer hBoxLayout = new HBoxLayoutContainer(); 
		hBoxLayout.add(passwdFieldLabel, new BoxLayoutData(new Margins(0, 0, 0, 0))); 
		
		TextButton okButton = new TextButton("OK"); 
		okButton.setWidth(40);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getService(); // 함수로 빼서 호출한다. 
			}
		});

		hBoxLayout.add(okButton, new BoxLayoutData(new Margins(0, 0, 0, 6)));
		vlc.add(hBoxLayout, new VerticalLayoutData(350, -1, new Margins(0, 0, 0, 15)));
		
		Label loginDesc = new HTML("<font size='2'>※ Login ID는 등록된 E-Mail ID를 사용 바랍니다. <br>※ 오류 발생시 담당자에게 문의 바랍니다.<br></font>");
		vlc.add(loginDesc, new VerticalLayoutData(350, -1, new Margins(20, 0, 0, 15)));
		 
		FormPanel formPanel = new FormPanel();
    	formPanel.setBorders(false);
//		formPanel.setLabelWidth(60); // 모든 field 적용 후 설정한다.
    	formPanel.setWidth(423);
    	formPanel.setHeight(300);
		
	    formPanel.setWidget(vlc);
	    //formPanel.setBorders(true);
	    
		container.add(formPanel); //, new MarginData(30));
		viewport.add(container);

		RootPanel.get().add(viewport);
	}
	


	public void getService(){
		ServiceRequest request = new ServiceRequest("sys.User.getLoginInfo");
		request.add("loginId", firstName.getText());
		request.add("passwd", password.getText());

		ServiceCall service = new ServiceCall(); 
		service.execute(request, this);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() > 0){
			
			UserModel user = (UserModel) result.getResult(0); 
			LoginUser.setLoginUser(user); 
			openFrame(); 
		}
		else {
			new SimpleMessage("로그인 정보 확인", result.getMessage()); 
		}
	}
	
	private void openFrame(){
		// 일반 사용자이다. 회사 선택없이 로드인한다. 
		this.viewport.remove(container);
		
		Main_Frame window = new Main_Frame(); 
		viewport.add(window.getMainWindow());
		RootPanel.get().add(viewport);
	}
}

