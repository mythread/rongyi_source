package com.rongyi.nasdaq.biz.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongyi.nasdaq.biz.dao.ArticleDao;
import com.rongyi.nasdaq.biz.dao.CaseDao;
import com.rongyi.nasdaq.biz.dao.HomePictureDao;
import com.rongyi.nasdaq.biz.dao.JobDao;
import com.rongyi.nasdaq.biz.dao.JobPlaceDao;
import com.rongyi.nasdaq.biz.dao.JobTypeDao;
import com.rongyi.nasdaq.biz.dao.PartnerDao;
import com.rongyi.nasdaq.biz.domain.ArticleDO;
import com.rongyi.nasdaq.biz.domain.CaseDO;
import com.rongyi.nasdaq.biz.domain.HomePictureDO;
import com.rongyi.nasdaq.biz.domain.JobDO;
import com.rongyi.nasdaq.biz.domain.JobPlaceDO;
import com.rongyi.nasdaq.biz.domain.JobTypeDO;
import com.rongyi.nasdaq.biz.domain.PartnerDO;
import com.rongyi.nasdaq.common.util.Argument;

/**
 * 类WebSiteService.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月22日 下午2:35:00
 */
@Service
public class WebSiteService {

    @Autowired
    private HomePictureDao homePicDao;

    @Autowired
    private ArticleDao     articleDao;

    @Autowired
    private CaseDao        caseDao;

    @Autowired
    private JobDao         jobDao;

    @Autowired
    private JobPlaceDao    jobPlaceDao;

    @Autowired
    private JobTypeDao     jobTypeDao;

    @Autowired
    private PartnerDao     partnerDao;

    // ////////////////////////// 首页轮播图 ////////////////////////
    public List<HomePictureDO> listHomePic() {
        return homePicDao.listHomePic();
    }

    // /////////////////////////媒体报道///////////////////////////////
    public ArticleDO getArticleById(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return articleDao.getById(id);
    }

    public boolean updateArticleRead(Integer id) {
        if (!Argument.isPositive(id)) {
            return false;
        }
        return articleDao.updateReadCount(id);
    }

    public ArticleDO getPrevArticle(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return articleDao.getPrevArticle(id);
    }

    public ArticleDO getNextArticle(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return articleDao.getNextArticle(id);
    }

    /**
     * 分页查询
     */
    public List<ArticleDO> listPageArticle(Integer startRecordIndex, Integer pageSize, String type) {
        if (startRecordIndex != null && startRecordIndex >= 0 && pageSize != null && pageSize >= 0) {
            return articleDao.listPagination(startRecordIndex, pageSize, type);
        } else {
            return Collections.emptyList();
        }
    }

    public Integer countArticle(String type) {
        return articleDao.getTotalNum(type);
    }

    public List<ArticleDO> listArticle(Integer limitSize, String type) {
        return articleDao.list(limitSize, type);
    }

    // ///////////////////////////案列///////////////////////////// /////
    public CaseDO getCaseById(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return caseDao.getById(id);
    }

    public List<CaseDO> listPageCase(Integer startRecordIndex, Integer pageSize) {
        if (startRecordIndex != null && startRecordIndex >= 0 && pageSize != null && pageSize >= 0) {
            return caseDao.listPagination(startRecordIndex, pageSize);
        } else {
            return Collections.emptyList();
        }
    }

    public CaseDO getPrevCase(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return caseDao.getPrevCase(id);
    }

    public CaseDO getNextCase(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return caseDao.getNextCase(id);
    }

    public Integer countCase() {
        return caseDao.getTotalNum();
    }

    public List<CaseDO> listCase(Integer limitSize) {
        return caseDao.list(limitSize);
    }

    // /////////////////////////////////合作伙伴//////////////////////////////
    public PartnerDO getPartnerById(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return partnerDao.getById(id);
    }

    public List<PartnerDO> listPagePartner(Integer startRecordIndex, Integer pageSize) {
        if (startRecordIndex != null && startRecordIndex >= 0 && pageSize != null && pageSize >= 0) {
            return partnerDao.listPagination(startRecordIndex, pageSize);
        } else {
            return Collections.emptyList();
        }
    }

    public Integer countPartner() {
        return partnerDao.getTotalNum();
    }

    public List<PartnerDO> listPartner(Integer limitSize) {
        return partnerDao.list(limitSize);
    }

    // //////////////////////////// 人才招聘 ////////////////////////////////

    public JobDO getJobById(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return jobDao.getById(id);
    }

    public List<JobDO> listPageJob(Integer startRecordIndex, Integer pageSize, Integer placeId, Integer typeId) {
        if (startRecordIndex != null && startRecordIndex >= 0 && pageSize != null && pageSize >= 0) {
            if (placeId <= 0) {
                placeId = null;
            }
            if (typeId <= 0) {
                typeId = null;
            }
            return jobDao.listPagination(startRecordIndex, pageSize, placeId, typeId);
        } else {
            return Collections.emptyList();
        }
    }

    public Integer countJob(Integer placeId, Integer typeId) {
        if (placeId <= 0) {
            placeId = null;
        }
        if (typeId <= 0) {
            typeId = null;
        }
        return jobDao.getTotalNum(placeId, typeId);
    }

    public List<JobTypeDO> listJobType(Integer limitSize) {
        return jobTypeDao.list(limitSize);
    }

    public List<JobPlaceDO> listJobPlace(Integer limitSize) {
        return jobPlaceDao.list(limitSize);
    }
}
