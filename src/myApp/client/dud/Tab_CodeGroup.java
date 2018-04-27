package myApp.client.dud;

import java.util.List;

import myApp.client.dud.model.CodeGroupModel;
import myApp.client.dud.model.CodeGroupModelProperties;
import myApp.client.sys.model.CodeKindModel;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;


//public class Tab_CodeGroup extends BorderLayoutContainer implements InterfaceGridOperate {
public class Tab_CodeGroup extends VerticalLayoutContainer implements InterfaceGridOperate {

	private CodeGroupModelProperties properties = GWT.create(CodeGroupModelProperties.class);
	private Grid<CodeGroupModel> grid = this.buildGrid();
	private TextField codeGroupName = new TextField();
	
	public Tab_CodeGroup() {

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(codeGroupName, "코드구분명", 200, 70, true);
		searchBarBuilder.addRetrieveButton();
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1,40));
		this.add(grid, new VerticalLayoutData(1,1));

		//VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		//vlc.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		//vlc.add(this.grid, new VerticalLayoutData(1, 1));

		//BorderLayoutData westLayoutData = new BorderLayoutData(800);
		//westLayoutData.setMargins(new Margins(0,4,0,0));
		//westLayoutData.setSplit(true);
		//westLayoutData.setMaxSize(1000);
		//this.setWestWidget(vlc, westLayoutData);

		/*grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<CodeGroupModel>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<CodeGroupModel> event) {
				CodeGroupModel codeGroup = grid.getSelectionModel().getSelectedItem();
			}
		});*/
	}

	private Grid<CodeGroupModel> buildGrid() {
		
		GridBuilder<CodeGroupModel> gridBuilder = new GridBuilder<CodeGroupModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText		(properties.groupCode(), 200 , "구분코드"	, new TextField());
		gridBuilder.addText		(properties.groupName(), 200 , "코드구분명", new TextField());
		gridBuilder.addBoolean	(properties.sysYnFlag(), 60  , "시스템"					 );
		gridBuilder.addText		(properties.note()     , 400 , "비 고"	, new TextField());

		//gridBuilder.addText(properties.accountName()	,	200	,	"계정과목"		,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField());

		return gridBuilder.getGrid();
	}

	@Override
	public void retrieve() {
		
		//String name = codeGroupName.getValue();
		String name = codeGroupName.getText();
		Info.display("name", "["+name+"]");

		GridRetrieveData<CodeGroupModel> service = new GridRetrieveData<CodeGroupModel>(grid.getStore());
		//service.retrieve("dud.CodeGroup.selectByAll");
		service.addParam("groupName", name);
		service.retrieve("dud.CodeGroup.selectByName");
	}

	@Override
	public void update() {
		GridUpdateData<CodeGroupModel> service = new GridUpdateData<CodeGroupModel>();
		service.update(grid.getStore(), "dud.CodeGroup.update");
	}

	@Override
	public void insertRow() {
		GridInsertRow<CodeGroupModel> service = new GridInsertRow<CodeGroupModel>();
		service.insertRow(grid, new CodeGroupModel());
	}

	@Override
	public void deleteRow() {
		GridDeleteData<CodeGroupModel> service = new GridDeleteData<CodeGroupModel>();
		List<CodeGroupModel> checkedList = grid.getSelectionModel().getSelectedItems();
		service.deleteRow(grid.getStore(), checkedList, "dud.CodeGroup.delete");
	}
}
