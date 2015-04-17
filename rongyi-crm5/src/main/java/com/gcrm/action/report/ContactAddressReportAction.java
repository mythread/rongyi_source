/**
 * Copyright (C) 2012-2013, Grass CRM Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gcrm.action.report;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.Contact;
import com.gcrm.domain.Role;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.ContactVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Manages the Contact Address Report
 * 
 */
public class ContactAddressReportAction extends ActionSupport {

    private static final long serialVersionUID = -2404576552417042445L;
    private IBaseService<Contact> baseService;
    private List<ContactVO> list;
    private Map<String, Object> map;
    private String report;

    /**
     * Generates html report
     * 
     */
    @Override
    public String execute() throws Exception {
        UserUtil.permissionCheck("view_contact");
        setList();
        HttpServletRequest request = ServletActionContext.getRequest();
        String path = request.getSession().getServletContext()
                .getRealPath("/reports/allContactHTML.jasper");
        File reportFile = new File(path);
        if (!reportFile.exists()) {
            throw new JRRuntimeException(
                    "File allContactHTML.jasper is not found");
        }
        JasperReport jasperReport = (JasperReport) JRLoader
                .loadObjectFromFile(reportFile.getPath());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                map, new JRBeanCollectionDataSource(list));
        JRHtmlExporter exporter = new JRHtmlExporter();
        StringBuffer buffer = new StringBuffer();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, buffer);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
                Constant.APP_PATH + "/servlets/image?image=");
        exporter.setParameter(
                JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
        exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,
                JRHtmlExporterParameter.SIZE_UNIT_POINT);
        exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO, 1.0f);
        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
        exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
        exporter.exportReport();
        report = buffer.toString();
        report = report.replaceAll(
                "<p style=\"overflow: hidden; text-indent: 0px; \">", "");
        report = report.replaceAll("</span></p></td>", "</span></td>");
        return SUCCESS;
    }

    /**
     * PDF report
     */
    public String pdf() throws Exception {
        UserUtil.permissionCheck("view_contact");
        setList();
        return SUCCESS;
    }

    /**
     * Excel report
     */
    public String excel() throws Exception {
        UserUtil.permissionCheck("view_contact");
        setList();
        return SUCCESS;
    }

    /**
     * CSV report
     */
    public String csv() throws Exception {
        UserUtil.permissionCheck("view_contact");
        setList();
        return SUCCESS;
    }

    /**
     * Sets report content
     * 
     */
    @SuppressWarnings("rawtypes")
    private void setList() {
        User loginUser = UserUtil.getLoginUser();
        int scope = loginUser.getScope_account();
        StringBuilder hqlBuilder = new StringBuilder(
                "select first_name,last_name,title,email,primary_street,primary_city,primary_state,primary_postal_code from Contact");

        if (scope == Role.OWNER_OR_DISABLED) {
            hqlBuilder.append(" where owner = ").append(loginUser.getId());
        }
        hqlBuilder.append(" order by created_on desc");
        List result = baseService.findVOByHQL(hqlBuilder.toString());
        Iterator it = result.iterator();
        list = new ArrayList<ContactVO>();
        ContactVO contactVO = null;
        String firstName = null;
        String lastName = null;
        String title = null;
        String email = null;
        String primaryStreet = null;
        String primaryCity = null;
        String primaryState = null;
        String primaryPostalCode = null;
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();
            contactVO = new ContactVO();
            firstName = (String) row[0];
            lastName = (String) row[1];
            title = (String) row[2];
            email = (String) row[3];
            primaryStreet = (String) row[4];
            primaryCity = (String) row[5];
            primaryState = (String) row[6];
            primaryPostalCode = (String) row[7];
            contactVO.setFirst_name(CommonUtil.fromNullToEmpty(firstName));
            contactVO.setLast_name(CommonUtil.fromNullToEmpty(lastName));
            contactVO.setTitle(CommonUtil.fromNullToEmpty(title));
            contactVO.setEmail(CommonUtil.fromNullToEmpty(email));
            contactVO.setPrimary_street(CommonUtil
                    .fromNullToEmpty(primaryStreet));
            contactVO.setPrimary_city(CommonUtil.fromNullToEmpty(primaryCity));
            contactVO
                    .setPrimary_state(CommonUtil.fromNullToEmpty(primaryState));
            contactVO.setPrimary_postal_code(CommonUtil
                    .fromNullToEmpty(primaryPostalCode));
            list.add(contactVO);
        }
        map = new HashMap<String, Object>();
    }

    /**
     * @return the baseService
     */
    public IBaseService<Contact> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Contact> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the list
     */
    public List<ContactVO> getList() {
        return list;
    }

    /**
     * @param list
     *            the list to set
     */
    public void setList(List<ContactVO> list) {
        this.list = list;
    }

    /**
     * @return the map
     */
    public Map<String, Object> getMap() {
        return map;
    }

    /**
     * @param map
     *            the map to set
     */
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * @return the report
     */
    public String getReport() {
        return report;
    }

    /**
     * @param report
     *            the report to set
     */
    public void setReport(String report) {
        this.report = report;
    }

}
