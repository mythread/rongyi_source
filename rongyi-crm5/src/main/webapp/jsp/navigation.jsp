<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<div id="shortcuts" class="headerList">
  <b style="white-space: nowrap; color: #444;"><s:text
      name="title.navigation" />:&nbsp;&nbsp;</b> <span> <span
    style="white-space: nowrap;"> <s:iterator
        value="#session.navigation">
        <span><s:property escape="false" />&nbsp;|&nbsp;</span>
      </s:iterator>
  </span>
  </span>
</div>
