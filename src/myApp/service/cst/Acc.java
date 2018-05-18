package myApp.service.cst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.cst.model.AccModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class Acc {
	
	String	mapperName = "cst02_acc";
	
	public void selectByAccId (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String accId = request.getString("accId");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("accId", accId);
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByAccId", param);
		result.setRetrieveResult(1, "select OK",  list);
	}
	
	public void selectByUserId (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String userId = request.getString("userId");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByUserId", param);
		result.setRetrieveResult(1, "select OK",  list);
	}

	public void update (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
//		System.out.println("여기오나???");
		UpdateDataModel<AccModel> updateModel = new UpdateDataModel<AccModel>();
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<AccModel> updateModel = new UpdateDataModel<AccModel>();
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

}
