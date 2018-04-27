package myApp.client.org;

import java.util.Date;
import java.util.List;

import myApp.client.org.model.OrgInfoModel;
import myApp.client.org.model.OrgInfoModelProperties;
import myApp.frame.LoginUser;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.GridBuilder;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent.CellClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

public class Tab_OrgInfo extends VerticalLayoutContainer implements InterfaceServiceCall {
	
	private TreeGrid<OrgInfoModel> treeGrid = this.buildTreeGrid();
	private DateField dateField = new DateField();
	 
//	private OrgInfoModel parentModel ; 
	
	private String actionCode = "insertRoot";
	
	public Tab_OrgInfo(){

		ButtonBar buttonBar = new ButtonBar();
		
		dateField.setValue(new Date()); //오늘일자로 설정한다. 

		FieldLabel dateFiledLabel = new FieldLabel(dateField, "기준일");
		dateFiledLabel.setWidth(160);
		dateFiledLabel.setLabelWidth(50);
		
		buttonBar.add(dateFiledLabel);
		
		//dateField.set
		TextButton retrieveButton = new TextButton("조회"); 
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve(); 
			}
		}); 
		retrieveButton.setWidth(70);
		buttonBar.add(retrieveButton);
		
		TextButton createRoot = new TextButton("회사등록"); 
		createRoot.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertRoot(); 
			}
		}); 
		createRoot.setWidth(70);
		buttonBar.add(createRoot);

		TextButton addSubMenu = new TextButton("조직등록");
		addSubMenu.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertChild(); 
			}
		}); 
		buttonBar.add(addSubMenu);

		TextButton updateButton = new TextButton("조직패쇄");
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				//deleteCode()
				setActionCode("closeOrgCode");
//				update();  
			}
		}); 
		buttonBar.add(updateButton);

//		TextButton deleteButton = new TextButton("삭제");  
//		deleteButton.setWidth(60);
//		deleteButton.addSelectHandler(new SelectHandler(){
//			@Override
//			public void onSelect(SelectEvent event) {
//				deleteRow();  
//			}
//		}); 
//		buttonBar.add(deleteButton);  
		
		this.add(buttonBar, new VerticalLayoutData(1, 40));
		this.add(this.treeGrid, new VerticalLayoutData(1, 1));
		this.treeGrid.addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				OrgInfoModel item = treeGrid.getSelectionModel().getSelectedItem() ;
				treeGrid.setExpanded(item, !treeGrid.isExpanded(item)); 
			}
		}); 
		
		this.retrieve();
		
		
	}
	
	public TreeGrid<OrgInfoModel> buildTreeGrid(){
		
		OrgInfoModelProperties properties = GWT.create(OrgInfoModelProperties.class);
		final GridBuilder<OrgInfoModel> gridBuilder = new GridBuilder<OrgInfoModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.korName(), 300, "조직명"); //, new TextField());
		gridBuilder.addText(properties.orgCode(), 80, "조직코드"); //, new TextField());
		gridBuilder.addText(properties.levelName(), 80, "조직구분"); //, new TextField()) ;
		
		ActionCell<String> editOrgInfoCell = new ActionCell<String>("수정", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				editItem(); 
			}
		});
		gridBuilder.addCell(properties.actionCell(), 50, "수정", editOrgInfoCell); //, new TextField()) ;
		gridBuilder.addText(properties.sortOrder(), 100, "조회순서"); //, new TextField()) ;
		gridBuilder.addDate(properties.openDate(), 120, "최초개설일"); //, new TextField()) ;
		gridBuilder.addDate(properties.modDate(), 120, "최근변경일"); //, new TextField()) ;
		gridBuilder.addText(properties.modReason(), 400, "변경사유"); //, new TextField());
		return gridBuilder.getTreeGrid(2);  
	}

	private void retrieve(){
		this.setActionCode("retrieve"); // 자료조회 

		ServiceRequest request = new ServiceRequest("org.OrgInfo.selectByCompanyId");
		request.add("companyId", LoginUser.getLoginUser().getCompanyId());
		request.add("baseDate", dateField.getValue());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void addTreeItem(OrgInfoModel parentItem) {
		// 조회 시 하위 노드 생성 함수. 
		if(parentItem.getChildList() != null){
			List<AbstractDataModel> childList = parentItem.getChildList(); 
			for(AbstractDataModel child : childList){
				OrgInfoModel childObject = (OrgInfoModel)child;
				this.treeGrid.getTreeStore().add(parentItem, childObject);
				this.addTreeItem(childObject);
			}
		}
	}

	private void insertRoot(){
		// 회사등록, 회사는 하나만 등록이 가능하다. 
		this.setActionCode("insertRoot"); 	// org code insert 

		ServiceRequest request = new ServiceRequest("getSeq"); // seq가져오기
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void insertChild(){
		
		OrgInfoModel parentModel = treeGrid.getSelectionModel().getSelectedItem(); // 선택된 Menu를 가져온다.
		
		if(parentModel == null){
			new SimpleMessage("하위조직이 등록될 상위 조직을 먼저 선택하여야 합니다. "); 
			return ; 
		}
		else { 
			// 하위조직을 생성
			this.setActionCode("insertChild");  	// org code insert
			
			// 닫혀있다면 일단 펼친다. 
			treeGrid.setExpanded(parentModel, true);
			ServiceRequest request = new ServiceRequest("getSeq"); // seq 가져오기 
			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
	}
	
	private void editItem(){
		this.setActionCode("edit");  	// org code insert
		OrgInfoModel editModel = treeGrid.getSelectionModel().getSelectedItem();
		Popup_OrgInfo popup_OrgInfo = new Popup_OrgInfo();
		popup_OrgInfo.editData(this, editModel, "edit", "조직정보수정");
	}
	
	
	public void insertCall(OrgInfoModel orgInfoModel){
		// Popup_OrgInfo에서 호출한다., 신규조직 생성시에 호출된다.  
		if(orgInfoModel.getParentCodeId() == Long.parseLong("0")) {

			// 회사코드 등록이다.하나의 정보만 받아서 tree에 넣어주면 된다. 
			this.setActionCode("insertRootCall");
			ServiceRequest request = new ServiceRequest("org.OrgInfo.insertRoot");
			request.add("orgInfoModel", orgInfoModel);
			request.add("companyId", LoginUser.getLoginUser().getCompanyId());
			request.add("baseDate", dateField.getValue());

			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
		else { 
			// 하위조직코드 등록이다. 
			// 하위조직은 등록 후 상위조직 아래의 모든 node를 refresh해야 하므로
			// 상위조직 아래의 조직 정보들을 다시 받아야 한다. 
			this.setActionCode("insertChildCall");
			ServiceRequest request = new ServiceRequest("org.OrgInfo.insertChild");
			request.add("orgInfoModel", orgInfoModel);
			request.add("companyId", LoginUser.getLoginUser().getCompanyId());
			request.add("baseDate", dateField.getValue());

			ServiceCall service = new ServiceCall();
			service.execute(request, this);
		}
	}

	public void updateCall(OrgInfoModel orgInfoModel){

		// Popup_OrgInfo에서 호출한다. 
		this.setActionCode("updateCall"); // action code를 update로 변경한다.  

		ServiceRequest request = new ServiceRequest("org.OrgInfo.update");
		request.add("orgInfoModel", orgInfoModel);
		request.add("companyId", LoginUser.getLoginUser().getCompanyId());
		request.add("baseDate", dateField.getValue());

		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	public void deleteCall(OrgInfoModel orgInfoModel){

		// Popup_OrgInfo에서 호출한다. 
		this.setActionCode("updateCall"); // action code를 update로 변경한다.  

		ServiceRequest request = new ServiceRequest("org.OrgInfo.delete");
		request.add("orgInfoModel", orgInfoModel);
		request.add("companyId", LoginUser.getLoginUser().getCompanyId());
		request.add("baseDate", dateField.getValue());

		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	
//	private void deleteRow(){
//		TreeGridDeleteRow<OrgInfoModel> service = new TreeGridDeleteRow<OrgInfoModel>();
//		List<OrgInfoModel> checkedList = treeGrid.getSelectionModel().getSelectedItems() ; 
//		service.deleteRow(treeGrid.getTreeStore(), checkedList, "sys.Menu.delete");
//	}

	@Override
	public void getServiceResult(ServiceResult result) {
		
		Info.display("return code", result.getStatus() + "");
		
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return; 
		}
		
		if("retrieve".equals(this.getActionCode()) ) {
			this.treeGrid.getTreeStore().clear(); // 깨끗이 비운다. 
			for (AbstractDataModel model: result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
				OrgInfoModel item = (OrgInfoModel)model;   
				this.treeGrid.getTreeStore().add(item);
				this.addTreeItem(item); // child menu & object setting  
			}
			this.treeGrid.expandAll();
		} 
		
		if("insertRoot".equals(this.getActionCode())) {
			
			OrgInfoModel orgInfoModel = new OrgInfoModel() ;
			Long parentCodeId = Long.parseLong("0");   
			String titleName = LoginUser.getLoginCompany().getCompanyName() + " 정보등록" ; 
 
			// orgCodeId & orgInfoId 값을 설정하기 위하여 서버에서 seq값을 받아온다. 
			orgInfoModel.getOrgCodeModel().setCompanyId(LoginUser.getLoginUser().getCompanyId()); // 회사ID
			orgInfoModel.getOrgCodeModel().setKeyId(result.getSeq()); // 조직코드 ID
			orgInfoModel.setKeyId(result.getSeq()); // 조직정보 ID, 조직코드와 같이 사용한다. 
			orgInfoModel.setCodeId(result.getSeq()); // 조직정보 ID, 조직코드와 같이 사용한다.
			orgInfoModel.setParentCodeId(parentCodeId); // 상위조직ID 설정, ROOT일경우에는 0 이다.  
			
			Popup_OrgInfo popup_OrgInfo = new Popup_OrgInfo();
			popup_OrgInfo.editData(this, orgInfoModel, "insert", titleName);
		}

		if("insertChild".equals(this.getActionCode())) {
			
			OrgInfoModel parentModel = treeGrid.getSelectionModel().getSelectedItem(); // 선택된 Menu를 가져온다.
			
			if(parentModel == null) {
				Info.display("상위조직 확인", "상위조직을 선택하여야 하위조직을 등록할 수 있습니다.");
				return; 
			}
			else { 
				OrgInfoModel orgInfoModel = new OrgInfoModel() ;
				
				// 코드ID를 가져가야 한다. 
				Long parentCodeId = parentModel.getCodeId() ;
				
				
				String titleName = "상위조직 : " + parentModel.getKorName() + "(" + parentModel.getOrgCode() + ")" ; 
			
				// orgCodeId & orgInfoId 값을 설정하기 위하여 서버에서 seq값을 받아온다. 
				orgInfoModel.getOrgCodeModel().setCompanyId(LoginUser.getLoginUser().getCompanyId()); // 회사ID
				orgInfoModel.getOrgCodeModel().setKeyId(result.getSeq()); // 조직코드 ID
				orgInfoModel.setKeyId(result.getSeq()); // 조직정보 ID, 조직코드와 같이 사용한다.
				orgInfoModel.setCodeId(result.getSeq()); // 조직정보 ID, 조직코드와 같이 사용한다.
				orgInfoModel.setParentCodeId(parentCodeId); // 상위조직ID 설정, ROOT일경우에는 0 이다.  
				
				Popup_OrgInfo popup_OrgInfo = new Popup_OrgInfo();
				popup_OrgInfo.editData(this, orgInfoModel, "insert", titleName);
			}
		}
		
		if("insertRootCall".equals(this.getActionCode())) {
			// 회사정보등록 처리, List로 받기는 하나 한건의 정보만 넘어온다.  
			for (AbstractDataModel model: result.getResult()) {
				OrgInfoModel orgInfoModel = (OrgInfoModel)model;
				treeGrid.getTreeStore().add(orgInfoModel);
			} 
		}
		
		if("insertChildCall".equals(this.getActionCode())) {

			// 상위조직에 딸린 모든 조직들을 refresh 해줘야 한다. 
			OrgInfoModel selectModel = treeGrid.getSelectionModel().getSelectedItem();
			// 하위조직 모두 삭제함. 
			treeGrid.getTreeStore().removeChildren(selectModel);

			for (AbstractDataModel model: result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
				OrgInfoModel childItem = (OrgInfoModel)model;   
				this.treeGrid.getTreeStore().add(selectModel, childItem);
				this.addTreeItem(childItem);
			}
				
			treeGrid.setExpanded(selectModel, true);
		}
		
		if("updateCall".equals(this.getActionCode())) {

			// 상위조직에 딸린 모든 조직들을 refresh 해줘야 한다. 
			OrgInfoModel selectModel = treeGrid.getSelectionModel().getSelectedItem();
			OrgInfoModel parentModel = treeGrid.getTreeStore().getParent(selectModel); 
			
			if(parentModel == null) {
				// 최상위 회사정보가 변경되면 모두 다시 조회한다.   
				this.treeGrid.getTreeStore().clear(); // 깨끗이 비운다. 

				for (AbstractDataModel model: result.getResult()) {
					OrgInfoModel rootItem = (OrgInfoModel)model;   
					this.treeGrid.getTreeStore().add(rootItem);
					this.addTreeItem(rootItem); // child menu & object setting  
				}
			}
			else {
				// 하위조직 모두 삭제함. 
				treeGrid.getTreeStore().removeChildren(parentModel);

				for (AbstractDataModel model: result.getResult()) {
					// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
					OrgInfoModel childItem = (OrgInfoModel)model;   
					this.treeGrid.getTreeStore().add(parentModel, childItem);
					this.addTreeItem(childItem);
					
				}
				
				treeGrid.setExpanded(parentModel, true);
			}
		}

		if("deleteCall".equals(this.getActionCode())) {

			// 상위조직에 딸린 모든 조직들을 refresh 해줘야 한다. 
			OrgInfoModel selectModel = treeGrid.getSelectionModel().getSelectedItem();
			OrgInfoModel parentModel = treeGrid.getTreeStore().getParent(selectModel); 
			
			if(parentModel == null) {
				// 최상위 회사정보가 변경되면 모두 다시 조회한다.   
				this.treeGrid.getTreeStore().clear(); // 깨끗이 비운다. 

				for (AbstractDataModel model: result.getResult()) {
					OrgInfoModel rootItem = (OrgInfoModel)model;   
					this.addTreeItem(rootItem); // child menu & object setting
					this.treeGrid.getTreeStore().add(rootItem);
				}
			}
			else {
				// 하위조직 모두 삭제함. 
				treeGrid.getTreeStore().removeChildren(parentModel);

				for (AbstractDataModel model: result.getResult()) {
					// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
					OrgInfoModel childItem = (OrgInfoModel)model;   
					this.treeGrid.getTreeStore().add(parentModel, childItem);
					this.addTreeItem(childItem);
				}
				treeGrid.setExpanded(parentModel, true);
			}
		}

	} 

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

//	public String getTargetItem() {
//		return targetItem;
//	}
//
//	public void setTargetItem(String targetItem) {
//		this.targetItem = targetItem;
//	}
}



/*		
TextButton expandAll = new TextButton("펼치기"); 
expandAll.setWidth(60);
expandAll.addSelectHandler(new SelectHandler(){
	@Override
	public void onSelect(SelectEvent event) {
		 treeGrid.expandAll();
	}
}); 
buttonBar.add(expandAll);

TextButton collapseAll = new TextButton("감추기");
collapseAll.setWidth(60);
collapseAll.addSelectHandler(new SelectHandler(){
	@Override
	public void onSelect(SelectEvent event) {
		 treeGrid.collapseAll();
	}
}); 
buttonBar.add(collapseAll);
*/ 


//if("updateCode".equals(this.getActionCode())) {
//
//OrgInfoModel orgInfoModel = new OrgInfoModel();
//
//// 초기값 설정. 
//// orgCodeId & orgInfoId 정보를 새로 등록하기 위한 SEQ 값을 서버에서 받아온다. 
//orgInfoModel.getOrgCodeModel().setCompanyId(LoginUser.getLoginUser().getCompanyId()); // 회사ID
//orgInfoModel.getOrgCodeModel().setKeyId(result.getSeq()); // 조직코드 ID
//orgInfoModel.setKeyId(result.getSeq()); // 조직정보 ID, 조직코드와 같이 사용한다. 
//orgInfoModel.setCodeId(result.getSeq()); // 조직코드 ID
//orgInfoModel.setParentCodeId(parentModel.getInfoId()); // 상위조직이 있다. 
// 
////Popup_OrgInfo popup_OrgInfo = new Popup_OrgInfo();
////popup_OrgInfo.addChildOrgCode(orgInfoModel, parentModel, this);
//}

