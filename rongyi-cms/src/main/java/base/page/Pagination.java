package base.page;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


/**
 * @author fuxw
 * 
 */
@Component
public class Pagination{

	/**
	 * 每页显示行数
	 */

	private int pageSize=10;

	/**
	 * 总记录数
	 */
	private int totalResult;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 当前页数
	 */
	private int currentPage;
	/**
	 *当前记录起始索引
	 */
	private int currentResult;
	/**
	 * true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	 * 
	 * */	
	private boolean entityOrField;	
	
	
	public Pagination(){
		
	}
	
	/**
	 * 实例化分页对象
	 * 
	 * @param request
	 *            当前request
	 * @param pageSize
	 *            每页行数
	 * @param totalRecord
	 *            总记录数
	 */
	public Pagination(HttpServletRequest request, int pageSize, int totalRecord) {
		init(request, pageSize, totalRecord);
	}

	public Pagination(HttpServletRequest request, int pageSize) {
		this.pageSize = pageSize;

		this.currentPage = 1;
		String currentPageStr = request.getParameter("pageNum");
		if (StringUtils.isNumeric(currentPageStr)) {
			this.currentPage = Integer.valueOf(currentPageStr).intValue();
		}

	}
	
	public Pagination(HttpServletRequest request) {
		this.currentPage = 1;
		String currentPageStr = request.getParameter("pageNum");
		if (StringUtils.isNumeric(currentPageStr)) {
			this.currentPage = Integer.valueOf(currentPageStr).intValue();
		}

	}

	private void init(HttpServletRequest request, int pageSize, int totalRecord) {
		this.pageSize = pageSize;
		this.totalResult = totalRecord;

		this.currentPage = 1;
		String currentPageStr = request.getParameter("pageNum");
		if (StringUtils.isNumeric(currentPageStr)) {
			this.currentPage = Integer.valueOf(currentPageStr).intValue();
		}

		if (this.pageSize > 0 && this.totalResult != 0) {
			this.totalPage = totalRecord / this.pageSize;
			if (totalRecord % this.pageSize > 0) {
				this.totalPage += 1;
			}
		}

		if ((this.currentPage - 1) * this.pageSize + this.pageSize > this.totalResult) {
			this.currentPage = this.totalPage;
		}
	}

	/**
	 * @return 当前页
	 */
	public int getCurrentPage() {
		if(currentPage<=0)
			currentPage = 1;
		if(currentPage>getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return 每页显示数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 总页数
	 */
	public int getTotalPage() {
		if(totalResult%pageSize==0)
			totalPage = totalResult/pageSize;
		else
			totalPage = totalResult/pageSize+1;
		return totalPage;
	}

	/**
	 * 总页数
	 * 
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public int getTotalRecord() {
		return totalResult;
	}

	/**
	 * 总记录数
	 * 
	 * @param totalRecord
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalResult = totalRecord;
		if (this.pageSize != 0 && this.totalResult != 0) {
			if (totalRecord % this.pageSize > 0) {
				this.totalPage = totalRecord / this.pageSize + 1;
			} else {
				this.totalPage = totalRecord / this.pageSize;
			}
		}
		if ((this.currentPage - 1) * this.pageSize + this.pageSize > this.totalResult) {
			this.currentPage = this.totalPage;
		}
	}

	/**
	 * 返回下一页数据的起始行
	 * 
	 * @return
	 */
	public int getCurrentResult() {
		currentResult = (getCurrentPage()-1)*getPageSize();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}

	/**
	 * 返回下一页取多少行数据
	 * 
	 * @return
	 */
	public int getMaxRow() {
		return this.pageSize;
	}


	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}


	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}
	
	
	
}
