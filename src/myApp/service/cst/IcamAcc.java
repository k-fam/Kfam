package myApp.service.cst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.cst.model.IcamAccModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

public class IcamAcc {
	
	String	mapperName = "cst03_icam_acc";
	
	public void selectByMgCombo (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByMgCombo");  
		result.setRetrieveResult(1, "select OK",  list);
	}

	public void checkEmail (SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String message = " ID(Email) 중복확인중 오류발생. 고객지원실에 문의바랍니다.";
		result.setMessage(message);

		System.out.println("email ====> " + request.getString("email")); 

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("email", request.getString("email"));

		Long count = sqlSession.selectOne("sys02_user.checkEmail", param);
		System.out.println("count is " + count); 

		if(count == 0) {
			result.setMessage("사용가능한 ID(Email)입니다.");
		} else {
			result.setMessage("이미 등록된 ID(Email)입니다.");
		}
		result.setStatus(1); 
	}
}
