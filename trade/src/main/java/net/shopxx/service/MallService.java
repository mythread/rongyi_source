package net.shopxx.service;

import net.shopxx.entity.Mall;

public interface MallService extends BaseService<Mall, Integer>{
    
    /**
     * 根据商场名判断是否存在
     */
   public boolean  isExistName(String name);
   
}
