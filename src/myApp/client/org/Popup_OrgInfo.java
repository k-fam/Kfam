package myApp.client.org;

import myApp.client.org.model.OrgInfoModel;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.ComboBoxField;
import myApp.frame.ui.img.ResourceIcon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class Popup_OrgInfo extends Window implements Editor<OrgInfoModel>{
	
	interface EditDriver extends SimpleBeanEditorDriver<OrgInfoModel, Popup_OrgInfo> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	OrgInfoModel orgInfoModel; 
	Tab_OrgInfo tab_OrgInfo; 
	
	//LongField parentCodeId 	= new LongField(); // 상위조직ID  

	TextField orgCode 		= new TextField(); // 조직코드 
	TextField korName 		= new TextField(); // 조직한글명 
	TextField levelCode 	= new TextField(); // 조직레벨코드 
	ComboBoxField levelName = new ComboBoxField("OrgLevelCode"); // 조직레벨명 & 콤보박스 
	TextField sortOrder 	= new TextField(); // 정렬순서 
	DateField openDate 		= new DateField(); // 개설일 
	TextField openReason 	= new TextField(); // 개설사유
	DateField closeDate 	= new DateField(); // 종료일
	TextField closeReason 	= new TextField(); // 종료사유
	TextField engName 		= new TextField(); // 영문명
	
	DateField modDate		= new DateField(); // 변경일
	TextField modReason 		= new TextField(); // 변경사유

	
	public void editData(Tab_OrgInfo tab_OrgInfo, OrgInfoModel orgInfoModel, String actionCode, String titleName){
		
		this.setModal(true);
		this.setHeading(titleName);
		this.setBorders(false);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		
		String formHeight = ""; 
		
		this.tab_OrgInfo = tab_OrgInfo;		
		this.orgInfoModel = orgInfoModel; 
		
		editDriver.initialize(this);
		editDriver.edit(this.orgInfoModel);

		HorizontalLayoutData rowLayout = new HorizontalLayoutData(200, -1); // 한컬럼의 크기(라벨 + 필드)
		rowLayout.setMargins(new Margins(0, 10, 0, 10)); // 컬럼간의 간격조정
		
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row05 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		HorizontalLayoutContainer row07 = new HorizontalLayoutContainer();
		
		VerticalLayoutContainer layout = new VerticalLayoutContainer(); // 합치기 
		
		// 첫번째 행
		row01.add(new FieldLabel(orgCode, "조직코드"), rowLayout);
		row01.add(new FieldLabel(korName, "조직명"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
		// 다섯째 행
		row02.add(new FieldLabel(engName, "영문명"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));

		
		// 두번째 행
		row03.add(new FieldLabel(levelName, "조직레벨"), rowLayout);
		row03.add(new FieldLabel(sortOrder, "정렬순서"), rowLayout);
		levelName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				levelCode.setValue(levelName.getCode());
			}
    	}); 

		
		if("insert".equals(actionCode))  {

			// 네번째 행
			row04.add(new FieldLabel(openDate, "개설일"), rowLayout);
			row04.add(new FieldLabel(openReason, "개설사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));

			// 다섯째 행
			row05.add(new FieldLabel(closeDate, "종료일"), rowLayout);
			row05.add(new FieldLabel(closeReason, "종료사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
			closeDate.setEnabled(false);
			closeReason.setEnabled(false);
			
//			// 여섯째 행
//			row06.add(new FieldLabel(modReason, "비고"), new HorizontalLayoutData(1, 100, new Margins(0, 10, 0, 10)));
			
			// 합치기 
			layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row03, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row04, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row05, new VerticalLayoutData(1, -1, new Margins(16)));
//			layout.add(row06, new VerticalLayoutData(1, -1, new Margins(16)));

			formHeight = "190"; 
		}
		else { // eidt mode 

			orgCode.setEnabled(false);


			// 네번째 행
			row04.add(new FieldLabel(modDate, "변경일"), rowLayout);
			
			// 다섯째 행
			row04.add(new FieldLabel(modReason, "변경사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));


			row05.add(new FieldLabel(openDate, "개설일"), rowLayout);
			row05.add(new FieldLabel(openReason, "개설사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));

			openDate.setEnabled(false);
			openReason.setEnabled(false);
			
			row06.add(new FieldLabel(closeDate, "종료일"), rowLayout);
			row06.add(new FieldLabel(closeReason, "종료사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));

			closeDate.setEnabled(false);
			closeReason.setEnabled(false);

//			// 일곱째 행
//			row07.add(new FieldLabel(modReason, "비고"), new HorizontalLayoutData(690, 80, new Margins(0, 50, 0, 0)));

			// 합치기 
			layout.add(row01, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row03, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row04, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row05, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row06, new VerticalLayoutData(1, -1, new Margins(16)));
			layout.add(row07, new VerticalLayoutData(1, -1, new Margins(16)));
			
			formHeight = "220";
		
		}
		
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(60); // 모든 field 적용 후 설정한다.
		form.setSize("560", formHeight);
		this.add(form);
		
		
		TextButton deleteButton = new TextButton("삭제");
		TextButton updateButton = new TextButton("저장");
		TextButton closeButton = new TextButton("닫기");
		
		if("insert".equals(actionCode))  {

 
			updateButton.setWidth(60);
			updateButton.addSelectHandler(new SelectHandler(){
				@Override
				public void onSelect(SelectEvent event) {
					insertData(); 
					hide();
				}
			}); 
			this.getButtonBar().add(updateButton);
		}
		else {
			deleteButton.setWidth(60);
			deleteButton.addSelectHandler(new SelectHandler(){
				@Override
				public void onSelect(SelectEvent event) {
					deleteData(); 
					hide();
				}
			}); 
			this.getButtonBar().add(deleteButton);
			
			updateButton.setWidth(60);
			updateButton.addSelectHandler(new SelectHandler(){
				@Override
				public void onSelect(SelectEvent event) {
					updateData(); 
					hide();
				}
			}); 
			this.getButtonBar().add(updateButton);
		}

		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		}); 
		this.getButtonBar().add(closeButton);
		
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show(); 
	}


	
	public void insertData() {
		// 신규 조직정보 생성 호출 
		OrgInfoModel data = editDriver.flush(); 
		
		if(data.getOrgCodeModel().getOpenDate() == null) {
			new SimpleMessage("개설일은 반드시 등록하여야 합니다");
			return ; 
		}
		else { 
			data.setModDate(data.getOrgCodeModel().getOpenDate());
		}
		
		if(data.getOrgCodeModel().getOpenReason() == null) {
			new SimpleMessage("개설사유는 반드시 등록하여야 합니다");
			return ; 
		}
		else { 
			data.setModReason(data.getOrgCodeModel().getOpenReason());
		}
		
		tab_OrgInfo.insertCall(data);
	}
	
	public void deleteData(){
		tab_OrgInfo.deleteCall(editDriver.flush());
	}

	public void updateData(){
		tab_OrgInfo.updateCall(editDriver.flush());
	}

	
	public void closeOrgCode() {
	}

	
//	private void updateOrgInfo() { 
//		// 상호참조가 된다. 
//		this.tab_OrgInfo.update(editDriver.flush()); 
//	}
	
}

/*
 	public void editInfo(OrgInfoModel orgInfoModel, Tab_OrgInfo tab_OrgInfo){
		
		this.tab_OrgInfo = tab_OrgInfo; 
		
		this.setModal(true);
		this.setHeading("조직정보 수정");
		this.setBorders(false);
		this.setResizable(false);
		this.getHeader().setIcon(ResourceIcon.INSTANCE.gearIcon());
		
		this.orgInfoModel = orgInfoModel; 
		
		editDriver.initialize(this);
		editDriver.edit(this.orgInfoModel);

		HorizontalLayoutData rowLayout = new HorizontalLayoutData(200, -1); // 한컬럼의 크기(라벨 + 필드)
		rowLayout.setMargins(new Margins(0, 10, 0, 10)); // 컬럼간의 간격조정
		
		// 두번째 행
		HorizontalLayoutContainer row02 = new HorizontalLayoutContainer();
		row02.add(new FieldLabel(orgCode, "조직코드"), rowLayout);
		row02.add(new FieldLabel(korName, "조직명"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
		orgCode.setEnabled(false);
		
		// 세번째 행
		HorizontalLayoutContainer row03 = new HorizontalLayoutContainer();
		row03.add(new FieldLabel(levelName, "조직레벨"), rowLayout);
		row03.add(new FieldLabel(sortOrder, "정렬순서"), rowLayout);
		levelName.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				levelCode.setValue(levelName.getCode());
			}
    	}); 

		// 네번째 행 
		HorizontalLayoutContainer row04 = new HorizontalLayoutContainer();
		row04.add(new FieldLabel(modDate, "변경일"), rowLayout);
		row04.add(new FieldLabel(modReason, "변경사유"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));

		// 여섯째 행
		HorizontalLayoutContainer row06 = new HorizontalLayoutContainer();
		row06.add(new FieldLabel(engName, "조직영문명"), new HorizontalLayoutData(1, -1, new Margins(0, 10, 0, 10)));
		
		// 합치기 
		VerticalLayoutContainer layout = new VerticalLayoutContainer();
		layout.add(row02, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row03, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row04, new VerticalLayoutData(1, -1, new Margins(16)));
		layout.add(row06, new VerticalLayoutData(1, -1, new Margins(16)));
		
		FormPanel form = new FormPanel();
		form.setWidget(layout);
		form.setLabelWidth(70); // 모든 field 적용 후 설정한다.
		form.setSize("560", "300");
		
		this.add(form);
		
		TextButton deleteButton = new TextButton("삭제"); 
		deleteButton.setWidth(60);
		deleteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				deleteData(); 
				hide();
			}
		}); 

		
		TextButton updateButton = new TextButton("저장"); 
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				updateData(); 
				hide();
			}
		}); 

		TextButton closeButton = new TextButton("닫기"); 
		closeButton.setWidth(60);
		closeButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		}); 

		this.getButtonBar().add(deleteButton);
		this.getButtonBar().add(updateButton);
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		
		this.show(); 
	} 
*/