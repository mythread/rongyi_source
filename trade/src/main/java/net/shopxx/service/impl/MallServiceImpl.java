package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.MallDao;
import net.shopxx.dao.MemberAttributeDao;
import net.shopxx.entity.Admin;
import net.shopxx.entity.Mall;
import net.shopxx.service.MallService;

import org.springframework.stereotype.Service;

@Service
public class MallServiceImpl extends BaseServiceImpl<Mall, Integer> implements MallService{
    
    @Resource
    private MallDao mallDao;
    
    @Resource
    public void setBaseDao(MallDao mallDao) {
        super.setBaseDao(mallDao);
    }

    public boolean isExistName(String name) {
       return mallDao.isExistName(name);
    }


}
