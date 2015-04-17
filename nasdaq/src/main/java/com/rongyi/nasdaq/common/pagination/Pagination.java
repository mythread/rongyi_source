package com.rongyi.nasdaq.common.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页组件
 * 
 * @author jiejie 2014年5月21日 下午4:31:40
 */
public class Pagination {

    // 最多显示多少条页面按钮
    public final static int DEFAULT_PAGE_LENGTH = 5;
    // 定义默认每页显示记录数
    public final static int DEFAULT_PAGESIZE    = 15;
    // 所有的记录数
    private int             allRecordNum;
    // 所有的页面数量
    private int             allPageNum;
    // 当前页面处于第几页，页面从0开始计数，在页面上显示则从1开始
    private int             nowPageIndex        = 0;
    // 每页的记录数
    private int             pageSize            = DEFAULT_PAGESIZE;
    // 当前页的第一条记录在所有记录中的下标，从1开始
    private int             startRecordIndex    = 1;
    // 当前页的最后一条记录在所有记录中的下标
    private int             endRecordIndex      = Integer.MAX_VALUE;
    // 定义从当前所处页面开始能够跳转到的<strong>最开始</strong>页面
    private int             firstPageIndex;

    // 定义从当前所处页面开始能够跳转到的<strong>最末尾</strong>页面
    private int             lastPageIndex;
    private List<Integer>   skipPageIndex       = new ArrayList<Integer>(DEFAULT_PAGE_LENGTH + 1);
    private int             pageLength          = DEFAULT_PAGE_LENGTH;

    /**
     * 返回数据库分页的起始位置,Mysql从0开始
     * 
     * @return
     */
    public int getOffset() {
        return 0;
    }

    /**
     * 根据所有的记录数计算处总共有多少页
     * 
     * @param allRecordNum
     */
    public void init(int allRecordNum) {
        this.allRecordNum = allRecordNum;
        allPageNum = (allRecordNum + pageSize - 1) / pageSize;
        startRecordIndex = nowPageIndex * pageSize + getOffset();
        endRecordIndex = startRecordIndex + pageSize - 1;
        firstPageIndex = nowPageIndex - pageLength / 2;
        if (firstPageIndex < 0) {
            firstPageIndex = 0;
        }
        lastPageIndex = nowPageIndex + (pageLength - 1) / 2;
        if (lastPageIndex >= allPageNum) {
            lastPageIndex = allPageNum - 1;
        }
        if (nowPageIndex - firstPageIndex < pageLength / 2) {
            while (lastPageIndex + 1 < allPageNum && lastPageIndex + 1 - firstPageIndex <= pageLength - 1) {
                lastPageIndex++;
            }
        }
        if (lastPageIndex - nowPageIndex < (pageLength - 1) / 2) {
            while (firstPageIndex - 1 >= 0 && lastPageIndex - (firstPageIndex - 1) <= pageLength - 1) {
                firstPageIndex--;
            }
        }
        int i, j;
        if (lastPageIndex - 2 >= firstPageIndex) {
            j = lastPageIndex - 2;
        } else {
            j = lastPageIndex;
        }
        for (i = firstPageIndex; i <= j; i++) {
            skipPageIndex.add(i);
        }

        // 显示的最后2页
        Integer laset2 = allPageNum - 2;
        if (laset2 > 0 && !skipPageIndex.contains(laset2)) {
            skipPageIndex.add(laset2);
        }
        Integer laset1 = allPageNum - 1;
        if (!skipPageIndex.contains(laset1)) {
            skipPageIndex.add(laset1);
        }
    }

    /**
     * 返回所有的记录数
     * 
     * @return
     */
    public int getAllRecordNum() {
        return allRecordNum;
    }

    /**
     * 返回所有的页码数
     * 
     * @return
     */
    public int getAllPageNum() {
        return allPageNum;
    }

    /**
     * 返回当前页码
     * 
     * @return
     */
    public int getNowPageIndex() {
        return nowPageIndex;
    }

    /**
     * 首页页码
     * 
     * @return
     */
    public int getFirstPageIndex() {
        return 0;
    }

    /**
     * 尾页页码
     * 
     * @return
     */
    public int getLastPageIndex() {
        return allPageNum - 1;
    }

    /**
     * 返回上一页页码，若当前页已是首页则返回首页
     * 
     * @return
     */
    public int getPrevPageIndex() {
        return nowPageIndex - 1 > 0 ? nowPageIndex - 1 : 0;
    }

    /**
     * 返回下一页页码，若当前页已是首页则返回首页
     * 
     * @return
     */
    public int getNextPageIndex() {
        return nowPageIndex == allPageNum - 1 ? allPageNum - 1 : nowPageIndex + 1;
    }

    /**
     * 当前页是否是首页
     * 
     * @return
     */
    public boolean isFirstPage() {
        return nowPageIndex == 0;
    }

    /**
     * 当前页是否是尾页
     * 
     * @return
     */
    public boolean isLastPage() {
        return nowPageIndex == allPageNum - 1;
    }

    /**
     * 设置当前页码
     * 
     * @param nowPageIndex
     */
    public void setNowPageIndex(int nowPageIndex) {
        this.nowPageIndex = nowPageIndex;
    }

    /**
     * 返回起始记录
     * 
     * @return
     */
    public int getStartRecordIndex() {
        return startRecordIndex;
    }

    /**
     * 返回起始记录
     * 
     * @return
     */
    public int getMySqlStartRecordIndex() {
        return startRecordIndex - 1 >= 0 ? startRecordIndex - 1 : 0;
    }

    /**
     * 返回结束记录
     * 
     * @return
     */
    public int getEndRecordIndex() {
        return endRecordIndex;
    }

    /**
     * 到前一页，如果不能再前进的时候，不动
     */
    public boolean toPrePage() {
        if (nowPageIndex == 0) {
            return false;
        }
        nowPageIndex--;
        startRecordIndex -= pageSize;
        return true;
    }

    /**
     * 到后一页，如果不能再前进，则不动
     * 
     * @return
     */
    public boolean toNextPage() {
        if (nowPageIndex == allPageNum - 1) {
            return false;
        }
        nowPageIndex++;
        startRecordIndex += pageSize;
        return true;
    }

    /**
     * 直接跳转到nextPage页
     */
    public boolean toSpecialPage(int nextPage) {
        if (nextPage < 0 || nextPage >= allPageNum || nextPage == nowPageIndex) {
            return false;
        }
        startRecordIndex += pageSize * (nextPage - nowPageIndex);
        nowPageIndex = nextPage;
        return true;
    }

    /**
     * 返回最多显示多少条页面按钮
     * 
     * @return
     */
    public int getPageLength() {
        return pageLength;
    }

    /**
     * @param pageLength the pageLength to set
     */
    public void setPageLength(int pageLength) {
        if (pageLength <= 0) {
            return;
        }
        this.pageLength = pageLength;
    }

    /**
     * 设置每页的记录数
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 返回每页记录数
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @return
     */
    public int getFirstGotoPageIndex() {
        return firstPageIndex;
    }

    /**
     * @param firstPageIndex
     */
    public void setFirstGotoPageIndex(int firstPageIndex) {
        this.firstPageIndex = firstPageIndex;
    }

    /**
     * @return
     */
    public int getLastGotoPageIndex() {
        return lastPageIndex;
    }

    public List<Integer> getSkipPageIndex() {
        return skipPageIndex;
    }

    /**
     * @param lastPageIndex
     */
    public void setLastGotoPageIndex(int lastPageIndex) {
        this.lastPageIndex = lastPageIndex;
    }

    /**
     * 返回该页面的起始记录数
     * 
     * @return
     */
    public Integer getPageStart() {
        return startRecordIndex;
    }

    /**
     * 返回该页面的结束记录数
     * 
     * @return
     */
    public Integer getPageEnd() {
        return endRecordIndex;
    }

    public void setStartRecordIndex(int startRecordIndex) {
        this.startRecordIndex = startRecordIndex;
    }

    public void setEndRecordIndex(int endRecordIndex) {
        this.endRecordIndex = endRecordIndex;
    }
}
