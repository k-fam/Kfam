package myApp.service.dud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.dud.model.CodeGroupModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class CodeGroup {
	
	String mapperName = "sys20_code_group";
	
	public void selectByAll (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByAll");
		result.setRetrieveResult(1, "select ok", list);		
	}

	public void selectByName (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String groupName = request.getString("groupName");
		if(groupName != null) {
			groupName = "%" + groupName + "%";
		} else {
			groupName = "%";
		}
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("groupName", groupName);

		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByName", param);
		result.setRetrieveResult(1, "select ok", list);		
	}
	
	
	public void update (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<CodeGroupModel> updateModel = new UpdateDataModel<CodeGroupModel>();
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}
	
	public void delete (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<CodeGroupModel> updateModel = new UpdateDataModel<CodeGroupModel>();
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
