package base.page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class PaginationTag extends TagSupport {

	private static final Logger logger = Logger.getLogger(PaginationTag.class);

	private Pagination pagination;

	private String searchFormId;

	private boolean ajaxForm = false;

	public int doStartTag() throws JspException {
		try {
			// 参数存session中
			JspWriter out = pageContext.getOut();
			if (pagination == null) {
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

		pageBuffer.append("<div id='pager' class='page pull-right compact-theme simple-pagination'>");
		// pageBuffer.append("<ul>");
		pageBuffer.append(getPrevPageInfo(pagination));
		pageBuffer.append(getPageInfo(pagination));
		pageBuffer.append(getNextPageInfo(pagination));
		// pageBuffer.append("</ul>");
		pageBuffer.append("</div>");
		return pageBuffer.toString();
	}

	/**
	 * 获取页码信息 1,2,3,4...7,8,9,10
	 * 
	 * @param pagination
	 * @return
	 */
	private String getPageInfo(Pagination pagination) {
		StringBuffer str = new StringBuffer();
		if (pagination.getCurrentPage() >= 1) {
			if (pagination.getTotalPage() <= 10) {
				for (int i = 1; i <= pagination.getTotalPage(); i++) {
					if (i != pagination.getCurrentPage()) {
						str.append(getPageInfo(i));
					} else {
						str.append("<a class='default-btn-style selected'>" + pagination.getCurrentPage() + "</a> ");
					}

				}
			} else {
				if (pagination.getCurrentPage() >= 4) {
					str.append(getPageInfo(1));
					str.append("... ");
				}
				if (pagination.getCurrentPage() >= 3) {
					str.append(getPageInfo(pagination.getCurrentPage() - 2));
				}
				if (pagination.getCurrentPage() >= 2) {
					str.append(getPageInfo(pagination.getCurrentPage() - 1));
				}
				str.append("<a class='default-btn-style selected'>" + pagination.getCurrentPage() + "</a> ");
				if (pagination.getTotalPage() >= 4 && pagination.getTotalPage() - pagination.getCurrentPage() >= 1) {
					str.append(getPageInfo(pagination.getCurrentPage() + 1));
				}
				if (pagination.getTotalPage() >= 5 && pagination.getTotalPage() - pagination.getCurrentPage() >= 2) {
					str.append(getPageInfo(pagination.getCurrentPage() + 2));
				}
				if (pagination.getTotalPage() - pagination.getCurrentPage() > 2) {
					str.append("... ");
					str.append(getPageInfo(pagination.getTotalPage()));
				}
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
		str.append("<a href='javascript:;' class='default-btn-style' onclick='goPage(" + page + ",\"" + getSearchFormId() + "\"," + isAjaxForm() + ")'>" + page + "</a> ");
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
		// str.append("<li");
		// if (pagination.getCurrentPage() <= 1) {
		// str.append(" class='disabled'");
		// }
		// str.append(">");
		str.append("<a class='default-btn-style' href='javascript:;' ");
		if (pagination.getCurrentPage() > 1) {
			str.append(" onclick='goPage(" + (pagination.getCurrentPage() - 1) + ",\"" + getSearchFormId() + "\"," + isAjaxForm() + ")' ");
		}
		str.append(">&lt;").append("</a> ");
		// str.append("</li>");
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
		// str.append("<li ");
		// if (pagination.getCurrentPage() >= pagination.getTotalPage()) {
		// str.append(" class='disabled'");
		// }
		// str.append(">");
		str.append("<a class='default-btn-style' href='javascript:;' ");
		if (pagination.getCurrentPage() <= pagination.getTotalPage()) {
			str.append(" onclick='goPage(" + (pagination.getCurrentPage() + 1) + ",\"" + getSearchFormId() + "\"," + isAjaxForm() + ")' ");
		}
		str.append(">&gt; ").append("</a> ");
		// str.append("</li>");
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
