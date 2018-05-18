package myApp.service.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;

import myApp.client.cst.model.AccModel;
import myApp.client.cst.model.IcamAccModel;
import myApp.client.sys.model.UserModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class User {
	
	private String mapperName = "sys02_user";
	
	public void getLoginInfo(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		try{
			UserModel userInfo = sqlSession.selectOne(mapperName + ".selectByLoginInfo", request.getParam()) ;
			
			if(userInfo == null){
				result.fail(-1, "등록된 사용자 정보가 아닙니다. 아이디와 패스워드를 확인하여 주십시요!");
				return ; 
			}

//			if(userInfo.getCloseYnBoolean()){ 
//				result.fail(-1, "로그인 제한 사용자입니다. 로그인 할 수 없습니다. : " +  userInfo.getCloseYnBoolean());
//				return ; 
//			} 
			
// if 관리자인지 여부 
// 관리자가 라면 관리하는 회사목록을 보여준다. 
// else 
// 관리자가 아니라면 본인이 속한 personInfo와 companyId를 찾아 줘야 한다. -> LoginUserModel이 있어야 함.
// 로그인 사용자가 이미 퇴사한 사용자인 경우 로그인은 제한된다. 
			
			result.setModel(1, "login OK", userInfo);
			System.out.println(userInfo.getCloseYnBoolean().toString());
			return ; 
		}
		catch(Exception e) {
			result.fail(-1, "login fail by service");
			e.printStackTrace();
		}
	}
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByAll");
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectAdminUserByUserName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String userName = request.getString("userName");
		if(userName != null){
			userName = "%" + userName + "%"; 
		}
		else {
			userName = "%"; 
		}
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectAdminUserByUserName", userName);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long clientId = request.getLong("companyId"); 
		System.out.println("company id is " + clientId); 
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByCompanyId", clientId);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		
		String userName = request.getString("userName") ; 
		if(userName != null){
			userName = "%" + userName + "%"; 
		}
		else {
			userName = "%"; 
		}
		
		param.put("companyId", request.getLong("companyId"));
		param.put("korName", userName);
		
		
		if(request.getLong("companyId") != null){
			System.out.println("company id is not null"); 
		}
		else {
			System.out.println("company id is null");
		}
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByName", param);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void updateAdminUser(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Long companyId = Long.parseLong("2000001"); 
		
		UpdateDataModel<UserModel> updateModel = new UpdateDataModel<UserModel>();
		List<AbstractDataModel> updateList = new ArrayList<AbstractDataModel>(); 
		
		for(AbstractDataModel model : request.getList()){
			UserModel userModel = (UserModel)model; 
			if(userModel.getCompanyId() == null){
				userModel.setCompanyId(companyId);
			}
			updateList.add(userModel);
		}
		updateModel.updateModel(sqlSession, updateList, mapperName, result);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<UserModel> updateModel = new UpdateDataModel<UserModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<UserModel> updateModel = new UpdateDataModel<UserModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}

	public void insert(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		String message = "회원가입 처리중 오류발생.고객지원실에 문의바랍니다.";
		result.setMessage(message);
		
		//---------------------------------------------
		//	펀드코드SET
		//---------------------------------------------
		List<AbstractDataModel> list = request.getList(); 
		List<AbstractDataModel> updateList = new ArrayList<AbstractDataModel>(); 
		
		for(AbstractDataModel data : list) {

			AccModel accModel = (AccModel)data ; 

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("mgCode", accModel.getMgCode()); 
			param.put("accNo" , accModel.getAccNo()); 

			String fundCode = sqlSession.selectOne("cst03_icam_acc.getFundCode", param);

			if(fundCode == null) {
				result.setMessage("계좌정보 가져오기 실패. 증권사와 계좌번호를 확인하여 주십시오.");
				result.setStatus(-1); 
				return; 
			} else {
				System.out.println("fundCode ====>" + fundCode);
				accModel.setFundCode(fundCode);
			}
			updateList.add((AbstractDataModel)accModel); 
		}

		//---------------------------------------------
		//	User 정보 Insert
		//---------------------------------------------
		AbstractDataModel data = request.getModel("userModel"); 
		UserModel userModel = (UserModel)data;

		try {
			sqlSession.insert(mapperName + ".insert", userModel);
		} catch (RuntimeSqlException e) {
			result.setMessage(message + e.getMessage());
			result.setStatus(-1); 
			return;
		}

		//---------------------------------------------
		//	Account 정보 Insert
		//---------------------------------------------
		UpdateDataModel<AccModel> updater = new UpdateDataModel<AccModel>();
		try {
			updater.updateModel(sqlSession, updateList, "cst02_acc", result);
		} catch (RuntimeSqlException e) {
			result.setMessage(message + e.getMessage());
			result.setStatus(-1); 
			return;
		}

		result.setMessage("회원가입이 완료되었습니다.");
		result.setStatus(1); 
		return; 
	}
}
