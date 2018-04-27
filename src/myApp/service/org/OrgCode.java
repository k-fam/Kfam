package myApp.service.org;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.MenuModel;
import myApp.client.sys.model.RoleModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class OrgCode {

	String mapperName = "org01_code"; 
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		List<AbstractDataModel> orgCodeList = sqlSession.selectList(mapperName + ".selectByAll") ; 
		result.setRetrieveResult(orgCodeList.size(), "role list select ok", orgCodeList);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<MenuModel> updateModel = new UpdateDataModel<MenuModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<RoleModel> updateModel = new UpdateDataModel<RoleModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

}
