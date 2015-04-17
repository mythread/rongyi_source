package com.rongyi.nasdaq.biz.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rongyi.nasdaq.biz.domain.ArticleDO;
import com.rongyi.nasdaq.biz.mapper.ArticleMapper;

/**
 * @author jiejie 2014年5月21日 下午6:25:49
 */
@Repository
public class ArticleDao extends BaseDao<ArticleDO, ArticleMapper> {

    public List<ArticleDO> list(Integer limitSize, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitSize", limitSize);
        map.put("type", type);
        return e.list(map);
    }

    public List<ArticleDO> listPagination(Integer startRecordIndex, Integer pageSize, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startRecordIndex", startRecordIndex);
        map.put("pageSize", pageSize);
        map.put("type", type);
        return e.listPagination(map);
    }

    /**
     * count总数
     */
    public Integer getTotalNum(String type) {
        return e.getTotalNum(type);
    };

    public boolean updateReadCount(Integer id) {
        return e.updateReadCount(id);
    }

    public ArticleDO getPrevArticle(Integer id) {
        return e.getPrevArticle(id);
    }

    public ArticleDO getNextArticle(Integer id) {
        return e.getNextArticle(id);
    }
}
