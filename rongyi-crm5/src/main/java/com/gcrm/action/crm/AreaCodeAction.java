package com.gcrm.action.crm;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.AreaCode;
import com.gcrm.service.IBaseService;

public class AreaCodeAction extends BaseListAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7444274776657793565L;
	private IBaseService<AreaCode> areaCodeService;
	private String areacode ;

	
	
	public String province(){
		  String hql = "from AreaCode where superiorAreaCode = \'000000\'";
          List<AreaCode> areacodeList = areaCodeService.findByHQL(hql);
          
          String returnHtml = getSelectHtmlByAreaList(
        		  areacodeList, "areaProvince");
          HttpServletResponse response = ServletActionContext.getResponse();
  		response.setContentType("text/json;charset=utf-8");
  		try {
  			response.getWriter().write(returnHtml);
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
  		return null;
	}
	
	
	public String city(){
		
		String hql = "from AreaCode where superiorAreaCode = \'"+areacode+"\'";
        List<AreaCode> areacodeList = areaCodeService.findByHQL(hql);
        
        String returnHtml = getSelectHtmlByAreaList(
      		  areacodeList, "areaCity");
        HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		try {
			response.getWriter().write(returnHtml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String area(){
		
		String hql = "from AreaCode where superiorAreaCode = \'"+areacode+"\'";
        List<AreaCode> areacodeList = areaCodeService.findByHQL(hql);
        
        String returnHtml = getSelectHtmlByAreaList(
      		  areacodeList, "areas");
        HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		try {
			response.getWriter().write(returnHtml);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	private String getSelectHtmlByAreaList(List<AreaCode> areaInfos,
			String idname) {
		StringBuffer sbt = new StringBuffer();
		if(areaInfos.size()!=0){
			sbt.append("<select style='width: auto;margin-right: 10px' id='"
					+ idname + "' > ");
			sbt.append("<option value=''>请选择</option>");
			for (AreaCode areaInfo : areaInfos) {
				sbt.append("<option value='" + areaInfo.getAreaCode() + "'>"
						+ areaInfo.getAreaName() + "</option>");
			}
			sbt.append("</select>");
		}
		
		return sbt.toString();
	}


	public IBaseService<AreaCode> getAreaCodeService() {
		return areaCodeService;
	}



	public void setAreaCodeService(IBaseService<AreaCode> areaCodeService) {
		this.areaCodeService = areaCodeService;
	}


	public String getAreacode() {
		return areacode;
	}


	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	
	
	
}
