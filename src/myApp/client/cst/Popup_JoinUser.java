package myApp.client.cst;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.sys.model.UserModel;
import myApp.frame.Main_Login;
import myApp.frame.ui.img.ResourceIcon;

public class Popup_JoinUser extends Window implements Editor<UserModel>{

	interface EditDriver extends SimpleBeanEditorDriver<UserModel, Popup_JoinUser> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	UserModel userModel;
	Main_Login main_Login;

	TextField	eMail		= new TextField();			//ID(E-Mail)
//	TextButton	eMailChk	= new TextButton("중복확인");	//중복확인버튼
	TextField	userName	= new TextField();			//이름
	//핸드폰번호1
	//핸드폰번호2
	//핸드폰번호3
	//우편번호
	//우편번호버튼
	//주소1
	//주소2
	//증권사
	//계좌번호
	//계좌명(별칭)
	//지점명

//	public void editData(Main_Login main_Login, UserModel userModel, String actionCode, String titleName){	
	public void editData(Main_Login main_Login, String actionCode, String titleName){	

		this.setModal(true);
		this.setHeading(titleName);
		this.setBorders(false);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		
		String formHeight = "";
		
		this.main_Login = main_Login;
		this.userModel = userModel;
		
		editDriver.initialize(this);
		editDriver.edit(this.userModel);
		
		HorizontalLayoutData rowLayout = new HorizontalLayoutData(200,-1);	//한컬럼의 크리(라벨+필드)
		rowLayout.setMargins(new Margins(0,10,0,10));	//컬럼간의 간격조정
		
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();

		VerticalLayoutContainer layout = new VerticalLayoutContainer();	//합치기
		
		row01.add(new FieldLabel(eMail, "ID(E-MAil)"), rowLayout);
//		row01.add(new textButton(eMail, "ID(E-MAil)"), rowLayout);
		
		row02.add(new FieldLabel(userName, "이름"), new HorizontalLayoutData(1,-1,new Margins(0,10,0,10)));
		
		
		layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
		
		formHeight = "200";
		
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(60);
		form.setSize("560", formHeight);
		this.add(form);
		
		this.show(); 
	}
		
//
//	container.add(formPanel); //, new MarginData(30));
//	viewport.add(container);
//
//	RootPanel.get().add(viewport);

	
//	TextButton closeButton = new TextButton("닫기");

//	closeButton.setWidth(60);
//	closeButton.addSelectHandler(new SelectHandler(){
//		@Override
//		public void onSelect(SelectEvent event) {
//			hide(); 
//		}
//	}); 
//	this.getButtonBar().add(closeButton);
//	
//	this.setButtonAlign(BoxLayoutPack.CENTER);
//	
//	this.show(); 
    
	
}
