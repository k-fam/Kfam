package myApp.client.cst;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.cst.model.AccModel;
import myApp.client.sys.model.UserModel;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.AbstractLookupWindow;
import myApp.frame.ui.builder.InterfaceGridOperate;

public class Lookup_JoinUser extends AbstractLookupWindow implements Editor<UserModel>, InterfaceGridOperate, InterfaceServiceCall {

	interface EditDriver extends SimpleBeanEditorDriver<UserModel, Lookup_JoinUser> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	UserModel userModel;

	private String actionName = ""; 
	private Long userId; 
	private InterfaceLookupResult lookupParent;
	private Lookup_JoinUserAccount grid = new Lookup_JoinUserAccount();  

	TextField email 		= new TextField();
	TextField korName 		= new TextField();
	TextField telNo			= new TextField();
	TextField tel1			= new TextField();
	TextField tel2			= new TextField();
	TextField tel3			= new TextField();
	TextField zipCode		= new TextField();
	TextField zipAddress	= new TextField();
	TextField zipDetail		= new TextField();
	
	public Lookup_JoinUser(InterfaceLookupResult lookupParent, String actionName) {
		
		this.lookupParent = lookupParent;
		this.actionName = actionName;

		if("addUser".equals(actionName)) {
			this.setInit(":: 회원가입 ::", 700, 500);
			ServiceRequest request = new ServiceRequest("getSeq");
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
		else {
			this.setInit(":: 회원정보수정::", 700, 500);
		}

		this.editDriver.initialize(this);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(this.getForm(), new VerticalLayoutData(-1, 205, new Margins(0, 0, 0, 0)));
		vlc.add(this.grid, new VerticalLayoutData(1, 1, new Margins(0, 0, 0, 0)));
		vlc.setBorders(true);
		this.add(vlc); 
	}
	
	private FormPanel getForm() {
		
		VerticalLayoutContainer formLayout = new VerticalLayoutContainer();

		//--------------------------------
		//	ID(E-Mail) ADD
		//--------------------------------
		FieldLabel emailFieldLabel = new FieldLabel(email, "▶ ID (E-Mail) ");
		emailFieldLabel.setWidth(290);
		emailFieldLabel.setLabelWidth(100);
		emailFieldLabel.setLabelSeparator(" ");

		//--------------------------------
		//	중복확인버튼 ADD
		//--------------------------------
		TextButton emailCheckButton = new TextButton("중복확인"); 
		emailCheckButton.setWidth(80);
		emailCheckButton.setHeight(25);
		emailCheckButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				checkEmail(); 
			}
		});

		HBoxLayoutContainer emailBoxLayout = new HBoxLayoutContainer(); 
		emailBoxLayout.add(emailFieldLabel , new BoxLayoutData(new Margins(0, 5, 0, 0))); 
		emailBoxLayout.add(emailCheckButton, new BoxLayoutData(new Margins(0, 0, 0, 0)));
		formLayout.add(emailBoxLayout, new VerticalLayoutData(-1, -1, new Margins(15, 0, 0, 10)));

		//--------------------------------
		//	이름 ADD
		//--------------------------------
		FieldLabel korNameFieldLabel = new FieldLabel(korName, "▶ 이름 ");
		korNameFieldLabel.setLabelWidth(100);
		korNameFieldLabel.setLabelSeparator(" ");
		formLayout.add(korNameFieldLabel, new VerticalLayoutData(300, -1, new Margins(5, 0, 0, 10)));

		//--------------------------------
		//	휴대폰번호 ADD
		//--------------------------------
		FieldLabel tel1FieldLabel = new FieldLabel(tel1, "▶ 핸드폰번호 ");
		tel1FieldLabel.setWidth(160);
		tel1FieldLabel.setLabelWidth(100);
		tel1FieldLabel.setLabelSeparator(" ");

		FieldLabel tel2FieldLabel = new FieldLabel(tel2, "-");
		tel2FieldLabel.setWidth(60);
		tel2FieldLabel.setLabelWidth(5);
		tel2FieldLabel.setLabelSeparator(" ");

		FieldLabel tel3FieldLabel = new FieldLabel(tel3, "-");
		tel3FieldLabel.setWidth(60);
		tel3FieldLabel.setLabelWidth(5);
		tel3FieldLabel.setLabelSeparator(" ");

		HBoxLayoutContainer telBoxLayout = new HBoxLayoutContainer(); 
		telBoxLayout.add(tel1FieldLabel , new BoxLayoutData(new Margins(0, 0, 0, 0))); 
		telBoxLayout.add(tel2FieldLabel , new BoxLayoutData(new Margins(0, 0, 0, 5))); 
		telBoxLayout.add(tel3FieldLabel , new BoxLayoutData(new Margins(0, 0, 0, 5))); 
		formLayout.add(telBoxLayout, new VerticalLayoutData(-1, -1, new Margins(5, 0, 0, 10)));

		//--------------------------------
		//	우편번호 & 우편번호버튼 ADD
		//--------------------------------
		FieldLabel zipCodeFieldLabel = new FieldLabel(zipCode, "▶ 주소 ");
		zipCodeFieldLabel.setWidth(205);
		zipCodeFieldLabel.setLabelWidth(100);
		zipCodeFieldLabel.setLabelSeparator(" ");

		TextButton zipCodeButton = new TextButton("우펀번호"); 
		zipCodeButton.setWidth(80);
		zipCodeButton.setHeight(25);
		zipCodeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				//getService(); // 함수로 빼서 호출한다. 
			}
		});

		HBoxLayoutContainer zipCodeBoxLayout = new HBoxLayoutContainer(); 
		zipCodeBoxLayout.add(zipCodeFieldLabel , new BoxLayoutData(new Margins(0, 5, 0, 0))); 
		zipCodeBoxLayout.add(zipCodeButton     , new BoxLayoutData(new Margins(0, 0, 0, 0)));
		formLayout.add(zipCodeBoxLayout, new VerticalLayoutData(-1, -1, new Margins(5, 0, 0, 10)));

		//--------------------------------
		//	주소 ADD
		//--------------------------------
		FieldLabel zipAddressFieldLabel = new FieldLabel(zipAddress, "  ");
		zipAddressFieldLabel.setWidth(500);
		zipAddressFieldLabel.setLabelWidth(100);
		zipAddressFieldLabel.setLabelSeparator(" ");

		FieldLabel zipDetailFieldLabel = new FieldLabel(zipDetail, "  ");
		zipDetailFieldLabel.setWidth(500);
		zipDetailFieldLabel.setLabelWidth(100);
		zipDetailFieldLabel.setLabelSeparator(" ");

		formLayout.add(zipAddressFieldLabel, new VerticalLayoutData(-1, -1, new Margins(0, 0, 0, 10)));
		formLayout.add(zipDetailFieldLabel , new VerticalLayoutData(-1, -1, new Margins(0, 0, 0, 10)));

		FormPanel form = new FormPanel(); 
	    form.setWidget(formLayout);
//    	form.setBorders(true);
//	    form.setLabelWidth(80); // 모든 field 적용 후 설정한다.

	    return form ; 
	}

	protected void checkEmail() {

		this.actionName = "checkEmail";
		this.userModel  = this.editDriver.flush();
		
		if (this.userModel.getEmail().length() == 0) {
			new SimpleMessage("입력확인","ID(E-Mail) 을(를) 입력하여 주십시오.");
			return;
		}

		ServiceRequest request = new ServiceRequest("cst.IcamAcc.checkEmail");
		request.add("email", this.userModel.getEmail());
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	@Override
	public void confirm() {

		this.actionName = "updateUser";
		this.userModel  = this.editDriver.flush(); 

		if (this.userModel.getEmail().length() == 0) {
			new SimpleMessage("입력확인","E-Mail 을(를) 입력하여 주십시오.");
			return;
		}
		if (this.userModel.getKorName().length() == 0) {
			new SimpleMessage("입력확인","이름 을(를) 입력하여 주십시오.");
			return;
		}
		if (this.userModel.getTel1().length() == 0) {
			new SimpleMessage("입력확인","핸드폰번호를 정확하게 입력하여 주십시오.");
			return;
		}
		if (this.userModel.getTel2().length() == 0) {
			new SimpleMessage("입력확인","핸드폰번호를 정확하게 입력하여 주십시오.");
			return;
		}
		if (this.userModel.getTel3().length() == 0) {
			new SimpleMessage("입력확인","핸드폰번호를 정확하게 입력하여 주십시오.");
			return;
		}
		if (grid.checkPreUpdate() == 1) {

			this.userModel.setTelNo(this.userModel.getTel1()+this.userModel.getTel2()+this.userModel.getTel3());

			ServiceRequest request = new ServiceRequest("sys.User.insert");

			request.add("userModel", this.userModel);
			
			List<AbstractDataModel> list = new ArrayList<AbstractDataModel>() ;
			for(AccModel accModel : grid.getGrid().getStore().getAll()) {
				list.add((AbstractDataModel)accModel); 
			};
			
			request.setList(list);
			
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
	}

	@Override
	public void cancel() {
		this.hide();
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		
		if (result.getStatus() > -1) {	
			
			if(this.actionName.equals("addUser")) {
				
				this.userModel = new UserModel();
				
				// user id를 가져온다. 
				this.userId = result.getSeq() ; 
				this.userModel.setUserId(this.userId);
				
				this.userModel.setCompanyId(Long.parseLong("1701"));
				this.userModel.setLoginYn("false");
				this.userModel.setRefreshTime("60");
				this.userModel.setRoleId(Long.parseLong("2005540"));

				this.userModel.setEmail("");
				this.userModel.setKorName("");
				this.userModel.setTel1("010");
				this.userModel.setTel2("");
				this.userModel.setTel3("");
				
				this.editDriver.edit(userModel);
				grid.setUserId(this.userId);
			}
			else if (this.actionName.equals("updateUser")) {
				new SimpleMessage("확인", result.getMessage());
				lookupParent.setLookupResult(userModel);
				this.hide();
			}
			else if (this.actionName.equals("checkEmail")) {
				new SimpleMessage("확인", result.getMessage());
			}
		}
		else {
			new SimpleMessage("오류", result.getMessage()); 
		}
	}
	
	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
	}
	@Override
	public void update() {
	}
	@Override
	public void deleteRow() {
		// TODO Auto-generated method stub
	}
	@Override
	public void insertRow() {
		// TODO Auto-generated method stub
	}

}
