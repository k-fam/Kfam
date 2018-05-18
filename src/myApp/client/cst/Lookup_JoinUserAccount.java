package myApp.client.cst;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.cst.model.AccModel;
import myApp.client.cst.model.AccModelProperties;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;

public class Lookup_JoinUserAccount extends VerticalLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall {

	private AccModelProperties  accProperties  = GWT.create(AccModelProperties.class);
	private Grid<AccModel>  accGrid  = this.accBuildGrid();
	private Long userId;
//	private String actionName = "";

	public Grid<AccModel> getGrid(){
		return accGrid; 
	}
	
	public Lookup_JoinUserAccount() {
		//--------------------------------
		//	계좌정보Grid ADD
		//--------------------------------
		Label accLabel = new Label();
		accLabel.setText("▶ 계좌정보");
		accLabel.setStyleName("etc_label");

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addInsertButton("계좌추가", 80);

		HBoxLayoutContainer accAddBoxLayout = new HBoxLayoutContainer(); 
		accAddBoxLayout.add(accLabel                        , new BoxLayoutData(new Margins(11, 0, 0, 0))); 
		accAddBoxLayout.add(searchBarBuilder.getSearchBar() , new BoxLayoutData(new Margins(0, 0, 0, 15)));

		this.add(accAddBoxLayout, new VerticalLayoutData(-1, -1, new Margins(0, 0, 0, 10)));
		this.add(accGrid  , new VerticalLayoutData(1,1, new Margins(0, 0, 0, 0)));

//		this.setBorders(true);
	}

	private Grid<AccModel> accBuildGrid() {

		GridBuilder<AccModel> gridBuilder = new GridBuilder<AccModel>(accProperties.keyId());

//		gridBuilder.addText(accProperties.mgCode(), 100, "증권사", new TextField());

		final MgComboBoxField mgComboBox = new MgComboBoxField();
		mgComboBox.addCollapseHandler(new CollapseHandler() {
			@Override
			public void onCollapse(CollapseEvent event) {
//				AccModel data = accGrid.getSelectionModel().getSelectedItem();
//				accGrid.getStore().getRecord(data).addChange(accProperties.mgCode(), mgComboBox.getCode());	//증권사코드SET
				accGrid.getSelectionModel().getSelectedItem().setMgcode(mgComboBox.getCode());	//증권사코드SET
				accGrid.getView().refresh(true);
			}
		});

		gridBuilder.addText(accProperties.mgCodeName(), 120, "증권사"	  , mgComboBox);
		gridBuilder.addText(accProperties.accNo() 	  , 150, "계좌번호"  , new TextField());

//		ActionCell<String> accCheckCell = new ActionCell<String>("계좌확인", new ActionCell.Delegate<String>() {
//			@Override
//			public void execute(String arg0) {
//				accCheck();
//			}
//		});

//		gridBuilder.addCell(accProperties.actionCell() , 80, "계좌확인", accCheckCell);
		gridBuilder.addText(accProperties.accName()	 , 150, "계좌명(별칭)"	, new TextField());
		gridBuilder.addText(accProperties.accBranch(), 100, "지점명"      , new TextField());

		ActionCell<String> delCell = new ActionCell<String>("삭제", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String arg0) {
				deleteRow();
			}
		});
		gridBuilder.addCell(accProperties.actionCell() , 70, "계좌삭제", delCell);
		
		return gridBuilder.getGrid();
	}

//	protected void accCheck() {
//		
//		this.actionName = "accCheck";
//
//		accGrid.getStore().commitChanges();
//
//		AccModel data = accGrid.getSelectionModel().getSelectedItem();
//		String mgCode = accGrid.getStore().getRecord(data).getValue(accProperties.mgCode());
//		String accNo  = accGrid.getStore().getRecord(data).getValue(accProperties.accNo());
//		
//		ServiceRequest request = new ServiceRequest("cst.IcamAcc.selectByFundCode");
//		request.add("mgCode", mgCode);
//		request.add("accNo" , accNo);
//
//		ServiceCall service = new ServiceCall(); 
//		service.execute(request, this);
//	}

	public void setUserId(Long userId) {
		this.userId = userId; 
	}

	public int checkPreUpdate() {

		accGrid.getStore().commitChanges();

		for (int i=0; i<accGrid.getStore().size(); i++) {

			AccModel chkModel = accGrid.getStore().get(i);

			if ((chkModel.getMgCode() == null) || (chkModel.getMgCode().equals(""))) {
				new SimpleMessage("입력확인","증권사를 선택하여 주십시오.");
				return 0;
			}

			if ((chkModel.getAccNo() == null) || (chkModel.getAccNo().equals(""))) {
				new SimpleMessage("입력확인","계좌번호를 입력하여 주십시오.");
				return 0;
			}

			if ((chkModel.getAccName() == null) || (chkModel.getAccName().equals(""))) {
				new SimpleMessage("입력확인","계좌명(별칭)을 입력하여 주십시오.");
				return 0;
			}
		}
		return 1;
	}

	@Override
	public void retrieve() {
	}

	@Override
	public void insertRow() {
		
		AccModel accModel = new AccModel(); 
		accModel.setUserId(this.userId);
		
		GridInsertRow<AccModel> service = new GridInsertRow<AccModel>();
		service.insertRow(accGrid, accModel);
	}

	@Override
	public void deleteRow() {
		GridDeleteData<AccModel> service = new GridDeleteData<AccModel>();
		List<AccModel> delList = accGrid.getSelectionModel().getSelectedItems();
		service.deleteRow(accGrid.getStore(), delList, "cst.Acc.delete");
	}

	@Override
	public void update() {
//		GridUpdateData<AccModel> service = new GridUpdateData<AccModel>();
//		service.update(accGrid.getStore(), "cst.Acc.update");
	}

	@Override
	public void getServiceResult(ServiceResult result) {
//		if (result.getStatus() == 1) {
//			if("accCheck".equals(this.actionName)) {
//				List dataList = result.getResult();
//				if (dataList.size() == 0) {
//					Info.display("[계좌확인실패]","증권사와 계좌번호를 확인하여 주십시오.");
//				} else {
//					IcamAccModel icamAccModel = (IcamAccModel) dataList.get(0);
////					AccModel data = accGrid.getSelectionModel().getSelectedItem();
////					accGrid.getStore().getRecord(data).addChange(accProperties.fundCode(), icamAccModel.getFundCode());	//펀드코드SET
//					accGrid.getSelectionModel().getSelectedItem().setFundCode(icamAccModel.getFundCode());	//펀드코드SET
//					accGrid.getView().refresh(true);
//					Info.display("[계좌확인실패]","펀드코드SET완료!!!");
////					Info.display("[계좌확인]","계좌확인이 완료되었습니다.");
//				}
//			}
//		}
	}
}
