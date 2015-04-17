package base.page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;



@SuppressWarnings("serial")
public class PaginationTag1 extends TagSupport {

	private static final Logger logger = Logger.getLogger(PaginationTag1.class);

	private Pagination pagination;

	private String searchFormId;

	private boolean ajaxForm = false;

	public int doStartTag() throws JspException {
		try {
			// 参数存session中
			JspWriter out = pageContext.getOut();
			if(pagination==null){
				this.pagination = new Pagination();
			}
			String outValue = getPaginationHTML(pagination);
			out.println(outValue);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
		return super.doStartTag();
	}

	private String getPaginationHTML(Pagination pagination) {
		
		StringBuffer pageBuffer = new StringBuffer();

		pageBuffer.append("<div id='paging' class='page pull-right compact-theme simple-pagination'>");
		pageBuffer.append("<ul>");
		pageBuffer.append(getPrevPageInfo(pagination));
		pageBuffer.append(getPageInfo(pagination));
		pageBuffer.append(getNextPageInfo(pagination));
		pageBuffer.append("</ul>");
		pageBuffer.append("</div>");
		return pageBuffer.toString();
	}

	/**
	 * 获取页码信息
	 * 
	 * @param pagination
	 * @return
	 */
	private String getPageInfo(Pagination pagination) {
		StringBuffer str = new StringBuffer();
		if (pagination.getCurrentPage() >= 1) {
			if (pagination.getCurrentPage() >= 4) {
				str.append(getPageInfo(1));
				str.append("<li><a href='javascript:;'>...</a></li>");
			}
			if (pagination.getCurrentPage() >= 3) {
				str.append(getPageInfo(pagination.getCurrentPage() - 2));
			}
			if (pagination.getCurrentPage() >= 2) {
				str.append(getPageInfo(pagination.getCurrentPage() - 1));
			}
			str.append("<li><span class='current'>" + pagination.getCurrentPage() + "</span></li>");
			if (pagination.getTotalPage() >= 4 && pagination.getTotalPage() - pagination.getCurrentPage() >= 1) {
				str.append(getPageInfo(pagination.getCurrentPage() + 1));
			}
			if (pagination.getTotalPage() >= 5 && pagination.getTotalPage() - pagination.getCurrentPage() >= 2) {
				str.append(getPageInfo(pagination.getCurrentPage() + 2));
			}
			if (pagination.getTotalPage() - pagination.getCurrentPage() > 2) {
				str.append("<li><a href='javascript:;'>...</a></li>");
				str.append(getPageInfo(pagination.getTotalPage()));
			}
		}
		return str.toString();
	}

	/**
	 * 根据页码写跳转按钮
	 * 
	 * @param page
	 * @return
	 */
	private String getPageInfo(Integer page) {
		StringBuffer str = new StringBuffer();
		str.append("<li><a href='javascript:;' class='page-link' onclick='goPage(" + page + ",\"" + getSearchFormId() + "\"," + isAjaxForm() + ")'>" + page + "</a></li>");
		return str.toString();
	}

	/**
	 * 获取上一页内容
	 * 
	 * @param pagination
	 * @return
	 */
	private String getPrevPageInfo(Pagination pagination) {
		StringBuffer str = new StringBuffer();
		str.append("<li");
		if (pagination.getCurrentPage() <= 1) {
			str.append(" class='disabled'");
		}
		str.append(">");
		str.append("<a href='javascript:;'  class='page-link prev' ");
		if (pagination.getCurrentPage() > 1) {
			str.append(" onclick='goPage(" + (pagination.getCurrentPage() - 1) + ",\"" + getSearchFormId() + "\"," + isAjaxForm() + ")' ");
		}
		str.append(">上一页").append("</a>");
		str.append("</li>");
		return str.toString();
	}

	/**
	 * 获取下一页内容
	 * 
	 * @param pagination
	 * @return
	 */
	private String getNextPageInfo(Pagination pagination) {
		StringBuffer str = new StringBuffer();
		str.append("<li ");
		if (pagination.getCurrentPage() >= pagination.getTotalPage()) {
			str.append(" class='disabled'");
		}
		str.append(">");
		str.append("<a href='javascript:;' class='page-link next' ");
		if (pagination.getCurrentPage() <= pagination.getTotalPage()) {
			str.append(" onclick='goPage(" + (pagination.getCurrentPage() + 1) + ",\"" + getSearchFormId() + "\"," + isAjaxForm() + ")' ");
		}
		str.append(">下一页 ").append("</a>");
		str.append("</li>");
		return str.toString();
	}

	

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getSearchFormId() {
		return searchFormId;
	}

	public void setSearchFormId(String searchFormId) {
		this.searchFormId = searchFormId;
	}

	public boolean isAjaxForm() {
		return ajaxForm;
	}

	public void setAjaxForm(boolean ajaxForm) {
		this.ajaxForm = ajaxForm;
	}
}
