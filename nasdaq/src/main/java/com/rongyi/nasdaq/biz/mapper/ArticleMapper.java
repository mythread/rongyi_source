package com.rongyi.nasdaq.biz.mapper;

import com.rongyi.nasdaq.biz.domain.ArticleDO;

/**
 * @author jiejie 2014年5月21日 下午3:26:36
 */
public interface ArticleMapper extends BaseMapper<ArticleDO> {

    /**
     * count总数
     */
    public Integer getTotalNum(String type);

    /**
     * 更新浏览次数
     */
    public boolean updateReadCount(Integer id);

    /**
     * 获得上一条数据
     */
    public ArticleDO getPrevArticle(Integer id);

    /**
     * 获得下一条数据
     */
    public ArticleDO getNextArticle(Integer id);

}
