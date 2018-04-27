package myApp.service.org;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;

import myApp.client.org.model.OrgCodeModel;
import myApp.client.org.model.OrgInfoModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class OrgInfo { 
	
	String mapperName = "org02_info"; 
	
	Long companyId ;	// 재조회시 사용된다. 
	Date baseDate ; 	// 재조회시 사용된다. 
	
	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		this.companyId = request.getLong("companyId"); 
		this.baseDate = request.getDate("baseDate"); 
		
		System.out.println("companyId " + companyId);
		System.out.println("baseDate " + baseDate);
		
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("baseDate", baseDate);
		param.put("parentCodeId", Long.parseLong("0"));
		
		List<AbstractDataModel> orgList
			= sqlSession.selectList(mapperName + ".selectByParentId", param);

		for(AbstractDataModel child : orgList){
			OrgInfoModel orgInfoModel = (OrgInfoModel)child;
			List<AbstractDataModel> childList = getChildItem(sqlSession, companyId, baseDate, orgInfoModel.getCodeId());  
			orgInfoModel.setChildList(childList); 	
		}
		
		result.setRetrieveResult(orgList.size(), "조직정보 조회", orgList);
	}
	
	private List<AbstractDataModel> getChildItem(SqlSession sqlSession, Long companyId, Date baseDate, Long parentId){

		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("companyId", companyId); 
		param.put("baseDate", baseDate);
		param.put("parentCodeId", parentId);
		
		List<AbstractDataModel> orgList = sqlSession.selectList(mapperName + ".selectByParentId", param);

		for(AbstractDataModel child : orgList){
			OrgInfoModel orgInfoModel = (OrgInfoModel)child;
//			System.out.println("child menu is " + menuModel.getMenuName());
			List<AbstractDataModel> childList = getChildItem(sqlSession, companyId, baseDate, orgInfoModel.getCodeId());  
			orgInfoModel.setChildList(childList); 	
		}

		return orgList ; 
	}
	
	public void insertRoot(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		this.companyId = request.getLong("companyId"); 
		this.baseDate = request.getDate("baseDate"); 
		
		// Client로부터 하나의 OrgInfoModel만 받는다. 
		OrgInfoModel orgInfoModel = (OrgInfoModel)request.getModel("orgInfoModel");
		
		// OrgInfoModel에 있는 OrgCodeModel를 꺼낸다.
		OrgCodeModel orgCodeModel = orgInfoModel.getOrgCodeModel();  
		
		// List에 담아 넘겨준다.  
		List<AbstractDataModel> orgCodeList = new ArrayList<AbstractDataModel>();
		orgCodeList.add(orgCodeModel); 
		
		UpdateDataModel<OrgCodeModel> updateCodeModel = new UpdateDataModel<OrgCodeModel>();
		
		try {
			updateCodeModel.updateModel(sqlSession, orgCodeList, "org01_code", result);	
		}catch(SqlSessionException  e) {
			System.out.println("error database error"); 
		}
		
		if(result.getStatus() < 0 ) {
			// 오류가 나면 튕긴다. 
			return ; 
		}
		
		// orgInfoModel을 처리하자. 
		List<AbstractDataModel> orgInfoList = new ArrayList<AbstractDataModel>();
		orgInfoList.add(orgInfoModel); 
		
		UpdateDataModel<OrgInfoModel> updateInfoModel = new UpdateDataModel<OrgInfoModel>();
		updateInfoModel.updateModel(sqlSession, orgInfoList, mapperName, result);
				
	}
	
	public void insertChild(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		this.companyId = request.getLong("companyId"); 
		this.baseDate = request.getDate("baseDate"); 
		
		// Client로부터 하나의 OrgInfoModel만 받는다. 
		OrgInfoModel orgInfoModel = (OrgInfoModel)request.getModel("orgInfoModel");
		
		// OrgInfoModel에 있는 OrgCodeModel를 꺼낸다.
		OrgCodeModel orgCodeModel = orgInfoModel.getOrgCodeModel();  
		
		// List에 담아 넘겨준다.  
		List<AbstractDataModel> orgCodeList = new ArrayList<AbstractDataModel>();
		orgCodeList.add(orgCodeModel); 
		
		UpdateDataModel<OrgCodeModel> updateCodeModel = new UpdateDataModel<OrgCodeModel>();
		
		try {
			updateCodeModel.updateModel(sqlSession, orgCodeList, "org01_code", result);	
		}catch(SqlSessionException  e) {
			System.out.println("error database error"); 
		}
		
		if(result.getStatus() < 0 ) {
			// 오류가 나면 튕긴다. 
			return ; 
		}
		
		// orgInfoModel을 처리하자. 
		List<AbstractDataModel> orgInfoList = new ArrayList<AbstractDataModel>();
		orgInfoList.add(orgInfoModel); 
		
		UpdateDataModel<OrgInfoModel> updateInfoModel = new UpdateDataModel<OrgInfoModel>();
		updateInfoModel.updateModel(sqlSession, orgInfoList, mapperName, result);

		
		if(result.getStatus() < 0 ) {
			return ; 
		}
		else {
			
			List<AbstractDataModel> orgList = new ArrayList<AbstractDataModel>();
			
			for(AbstractDataModel data : result.getResult()) {
				OrgInfoModel updateOrgInfo = (OrgInfoModel)data; 
				orgList = getChildItem(sqlSession, companyId, baseDate, updateOrgInfo.getParentCodeId());
			}
 
			result.setRetrieveResult(orgList.size(), "조직정보 재조회", orgList);
		}
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		this.companyId = request.getLong("companyId"); 
		this.baseDate = request.getDate("baseDate"); 

		OrgInfoModel orgInfoModel = (OrgInfoModel)request.getModel("orgInfoModel");
		
		
		// insert인지 update인지는 날자를 비교하여 확인한다. 
		OrgInfoModel targetData = sqlSession.selectOne(mapperName + ".selectById", orgInfoModel.getCodeId());   
		
		if(!targetData.getModDate().equals(orgInfoModel.getModDate())){
			// 일자가 바뀌었다. 신규데이터를 생성한다.  
			Long seq = sqlSession.selectOne("dbConfig.getSeq"); 
			orgInfoModel.setInfoId(seq);
		}
		
		List<AbstractDataModel> orgInfoList = new ArrayList<AbstractDataModel>();
		orgInfoList.add(orgInfoModel); 
		
		UpdateDataModel<OrgInfoModel> updateModel = new UpdateDataModel<OrgInfoModel>(); 
		updateModel.updateModel(sqlSession, orgInfoList, mapperName, result);
		
		if(result.getStatus() < 0 ) {
			return ; 
		}
		else {
			
			List<AbstractDataModel> orgList = new ArrayList<AbstractDataModel>();
			
			for(AbstractDataModel data : result.getResult()) {
				OrgInfoModel updateOrgInfo = (OrgInfoModel)data; 
				orgList = getChildItem(sqlSession, companyId, baseDate, updateOrgInfo.getParentCodeId());
			}
 
			result.setRetrieveResult(orgList.size(), "조직정보 조회", orgList);
		}
	}
	
	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		System.out.println("delete start \r\r") ; 
		
		this.companyId = request.getLong("companyId"); 
		this.baseDate = request.getDate("baseDate"); 
		 
		OrgInfoModel orgInfoModel = (OrgInfoModel)request.getModel("orgInfoModel");
		OrgCodeModel orgCodeModel = orgInfoModel.getOrgCodeModel(); // 삭제용.
		
		Long parentCodeId = orgInfoModel.getParentCodeId(); // Refresh용  
		Long codeId = orgInfoModel.getCodeId(); // 잔여갯수 확인용. 
		 
		
		Long count = Long.parseLong("0") ;
		
		try {
			count = sqlSession.selectOne(mapperName + ".selectByCodeId", codeId);	
		}
		catch(RuntimeSqlException e) {
			result.setStatus(-1);
			result.setMessage("조직코드 확인 시 오류발생(ORG CODE Table)(1)"); 
			return; 
		}
		
		
		if(count > Long.parseLong("1") ) { // 2건 이상이다, 여유가 있다. 삭제해도 된다.  
			// 조직정보 삭제 
			List<AbstractDataModel> orgInfoList = new ArrayList<AbstractDataModel>();
			orgInfoList.add(orgInfoModel); 
			
			UpdateDataModel<OrgInfoModel> updateModel = new UpdateDataModel<OrgInfoModel>(); 
			updateModel.deleteModel(sqlSession, orgInfoList, mapperName, result); // delete 조직정보 
			
			System.out.println("delete data " + result.getStatus() + "\r\r") ;
			
			if(result.getStatus() < 0 ) {
				return ; 
			}
		}
		else {
			// 조직정보가 하나밖에 없다. 
			Long childCount = Long.parseLong("0") ;

			try {
				childCount = sqlSession.selectOne(mapperName + ".selectByChildCount", codeId);	
			}
			catch(RuntimeSqlException e) {
				result.setStatus(-100);
				result.setMessage("조직코드 삭제확인 시 오류발생(ORG CODE Table)(2)");
				System.out.println("조직코드 삭제확인 시 오류발생(ORG CODE Table)(2)") ;
				return; 
			}
			
			if(childCount > Long.parseLong("0") ) { // 하위조직이 있어 삭제가 불가능하다. 
				result.setStatus(-100); 
				result.setMessage("하위조직이 있어 삭제가 불가능합니다. ");
				System.out.println("하위조직이 있어 삭제가 불가능합니다. \"") ;
				return ; 
			}
			else { // 하위 조직도 없다. 삭제해도 된다.   

				// 조직정보 삭제 
				List<AbstractDataModel> orgInfoList = new ArrayList<AbstractDataModel>();
				orgInfoList.add(orgInfoModel); 
				
				UpdateDataModel<OrgInfoModel> updateModel = new UpdateDataModel<OrgInfoModel>(); 
				updateModel.deleteModel(sqlSession, orgInfoList, mapperName, result); // delete 조직정보 
				
				if(result.getStatus() < 0 ) {
					return ; 
				}

				// 조직코드 삭제 
				List<AbstractDataModel> orgCodeList = new ArrayList<AbstractDataModel>();
				orgCodeList.add(orgCodeModel);  
				
				UpdateDataModel<OrgCodeModel> updateCodeModel = new UpdateDataModel<OrgCodeModel>(); 
				updateCodeModel.deleteModel(sqlSession, orgCodeList, mapperName, result); // delete 조직정보
	
				if(result.getStatus() < 0 ) {
					return ;
				}
			}
		}
		
		// 삭제 후 Parent 조직의 ChildList를 넘겨준다. 
		List<AbstractDataModel> orgList = new ArrayList<AbstractDataModel>();
		orgList = getChildItem(sqlSession, companyId, baseDate, parentCodeId);
		
		System.out.println("\r\r\r delete and return list \r"); 
		
		for(AbstractDataModel data : orgList) {
			OrgInfoModel orgInfo = (OrgInfoModel)data; 
			System.out.println(orgInfo.getKorName()); 
			
		}
		result.setRetrieveResult(orgList.size(), "하위 조직정보 조회", orgList);
	}
}
