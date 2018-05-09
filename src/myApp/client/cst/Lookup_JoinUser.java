package myApp.client.cst;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.cst.model.AccModel;
import myApp.client.cst.model.AccModelProperties;
import myApp.client.sys.model.UserModel;
import myApp.client.sys.model.UserModelProperties;
import myApp.frame.service.GridInsertRow;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.AbstractLookupWindow;
import myApp.frame.ui.builder.ComboBoxField;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;

public class Lookup_JoinUser extends AbstractLookupWindow implements Editor<UserModel>, InterfaceGridOperate {

	interface EditDriver extends SimpleBeanEditorDriver<UserModel, Lookup_JoinUser> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	Grid<UserModel> UserGird;
	UserModel userModel;

	private AccModelProperties properties = GWT.create(AccModelProperties.class);
	private Grid<AccModel> accGrid = this.buildGrid();
	private InterfaceLookupResult lookupParent;

	TextField 		email 		= new TextField();
	TextField 		korName 	= new TextField();
	TextField    	tel1		= new TextField();
	TextField		tel2		= new TextField();
	TextField		tel3		= new TextField();
	TextField		zipCode		= new TextField();
	TextField		zipAddress	= new TextField();
	TextField		zipDetail	= new TextField();

	public Lookup_JoinUser(InterfaceLookupResult lookupParent) {
		this.lookupParent = lookupParent;
		this.setInit(":: 회원가입 ::", 700, 500);

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

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
				//getService(); // 함수로 빼서 호출한다. 
			}
		});

		HBoxLayoutContainer emailBoxLayout = new HBoxLayoutContainer(); 
		emailBoxLayout.add(emailFieldLabel , new BoxLayoutData(new Margins(0, 5, 0, 0))); 
		emailBoxLayout.add(emailCheckButton, new BoxLayoutData(new Margins(0, 0, 0, 0)));
		vlc.add(emailBoxLayout, new VerticalLayoutData(-1, -1, new Margins(15, 0, 0, 10)));

		//--------------------------------
		//	이름 ADD
		//--------------------------------
		FieldLabel korNameFieldLabel = new FieldLabel(korName, "▶ 이름 ");
		korNameFieldLabel.setLabelWidth(100);
		korNameFieldLabel.setLabelSeparator(" ");
		vlc.add(korNameFieldLabel, new VerticalLayoutData(300, -1, new Margins(5, 0, 0, 10)));

		//--------------------------------
		//	휴대폰번호 ADD
		//--------------------------------
		FieldLabel tel1FieldLabel = new FieldLabel(tel1, "▶ 핸드폰번호 ");
		tel1FieldLabel.setWidth(160);
		tel1FieldLabel.setLabelWidth(100);
		tel1FieldLabel.setLabelSeparator(" ");
		tel1.setText("010");

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
		vlc.add(telBoxLayout, new VerticalLayoutData(-1, -1, new Margins(5, 0, 0, 10)));

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
		vlc.add(zipCodeBoxLayout, new VerticalLayoutData(-1, -1, new Margins(5, 0, 0, 10)));

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

		vlc.add(zipAddressFieldLabel, new VerticalLayoutData(-1, -1, new Margins(0, 0, 0, 10)));
		vlc.add(zipDetailFieldLabel , new VerticalLayoutData(-1, -1, new Margins(0, 0, 0, 10)));

		//--------------------------------
		//	계좌정보Grid ADD
		//--------------------------------
		Label accLabel = new Label();
		accLabel.setText("▶ 계좌정보");
		accLabel.setStyleName("etc_label");

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addInsertButton("계좌추가", 80);

		HBoxLayoutContainer accAddBoxLayout = new HBoxLayoutContainer(); 
		accAddBoxLayout.add(accLabel                        , new BoxLayoutData(new Margins(12, 0, 0, 0))); 
		accAddBoxLayout.add(searchBarBuilder.getSearchBar() , new BoxLayoutData(new Margins(0, 0, 0, 15)));
		vlc.add(accAddBoxLayout, new VerticalLayoutData(-1, -1, new Margins(0, 0, 0, 10)));
		vlc.add(accGrid  , new VerticalLayoutData(1,1, new Margins(0, 0, 0, 0)));

		vlc.setBorders(true);
		this.add(vlc);
	}
	
	private Grid<AccModel> buildGrid() {

		GridBuilder<AccModel> gridBuilder = new GridBuilder<AccModel>(properties.keyId());

		final ComboBoxField mgNameComboBox = new ComboBoxField("mgName");
		mgNameComboBox.addCollapseHandler(new CollapseHandler() {
			@Override
			public void onCollapse(CollapseEvent event) {
				AccModel data = accGrid.getSelectionModel().getSelectedItem();
				accGrid.getStore().getRecord(data).addChange(properties.mgName(), mgNameComboBox.getCode());
			}
		});
		
		gridBuilder.addText(properties.mgName()	, 120, "증권사"	, mgNameComboBox);
		gridBuilder.addText(properties.accNo() 	, 150, "계좌번호"	, new TextField());

		ActionCell<String> accCheckCell = new ActionCell<String>("계좌확인", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String arg0) {
//				accCheck();
			}
		});
		gridBuilder.addCell(properties.actionCell() , 80, "계좌확인"		, accCheckCell);

		gridBuilder.addText(properties.accName()	, 100, "계좌명(별칭)"	, new TextField());
		gridBuilder.addText(properties.accBranch()	, 100, "지점명"		, new TextField());

		ActionCell<String> delCell = new ActionCell<String>("삭제", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String arg0) {
//				accDelete();
			}
		});
		gridBuilder.addCell(properties.actionCell() , 70, "계좌삭제", delCell);

		return gridBuilder.getGrid();
	}

	@Override
	public void cancel() {
		this.hide();
	}

	@Override
	public void confirm() {
		UserModel userModel = UserGird.getSelectionModel().getSelectedItem();
		if(userModel != null) {
			lookupParent.setLookupResult(userModel);
			this.hide();
		} else {
			new SimpleMessage("입력된 회원정보가 없습니다.");
		}
	}
	
	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertRow() {
		GridInsertRow<AccModel> service = new GridInsertRow<AccModel>();
		service.insertRow(accGrid, new AccModel());
	}

	@Override
	public void deleteRow() {
		// TODO Auto-generated method stub
		
	}

}
