package com.rongyi.mina.bean;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类BaseTaskImpl.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月9日 上午11:00:22
 */
public abstract class BaseTaskImpl<T> implements TaskInterface<T> {

    private static final Logger     LOG = LoggerFactory.getLogger(BaseTaskImpl.class);
    private HashMap<String, Object> map;
    private Class<T>                clazz;

    public BaseTaskImpl(HashMap<String, Object> map, Class<T> clazz) {
        this.map = map;
        this.clazz = clazz;
    }

    @Override
    public T getTask() {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            T t = clazz.newInstance();
            PropertyDescriptor[] props = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
            for (int i = 0; i < props.length; i++) {
                Object obj = map.get(props[i].getName());
                if (obj != null) {
                    LOG.info(props[i].getName() + " obj >>> " + obj);
                    Class<?> propType = props[i].getPropertyType();
                    if (propType.isInstance(new Date())) {
                        props[i].getWriteMethod().invoke(t, new Date((Long) obj));
                    } else if (propType.isInstance(new String())) {
                        props[i].getWriteMethod().invoke(t, String.valueOf(obj));
                    } else if (propType.isInstance(new Integer(0))) {
                        props[i].getWriteMethod().invoke(t, obj);
                    } else {
                        props[i].getWriteMethod().invoke(t, obj);
                    }
                }

            }
            if (t instanceof Advertisements) {
                ((Advertisements) t).setCmsOuterId(map.get(CMS_ID).toString());
            } else if (t instanceof Shops) {
                LOG.info("map.get(CMS_ID)>>>" + map.get(CMS_ID));
                ((Shops) t).setCmsOuterId(map.get(CMS_ID).toString());
            } else if (t instanceof Brands) {
                ((Brands) t).setCmsOuterId(map.get(CMS_ID).toString());
                ((Brands) t).setMallId((String) map.get(MALL_ID));
                ((Brands) t).setCategoryIds((String) map.get(CATEGORY_ID_LIST));
            }
            return t;
        } catch (IllegalArgumentException iea) {
            LOG.info("参数错误。");
            iea.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取传递过来的消息map
     * 
     * @return
     */
    public HashMap<String, Object> getMap() {
        return map;
    }

    @Override
    public List<Photos> getPhotosList() {
        if (map.get("minaPicId_1") == null) {
            return Collections.emptyList();
        }
        List<Photos> result = new ArrayList<Photos>();
        for (int i = 1;; i++) {
            String key1 = "minaPicId_" + i;
            String key2 = "minaPicFileUrl_" + i;
            String key3 = "minaPicFile_" + i;
            String key4 = "minaPicOwnerId_" + i;
            String key5 = "minaPicOwnerType_" + i;
            String key6 = "minaPicPosition_" + i;
            String key7 = "minaPicOwnerMongoId_" + i;
            String key8 = "minaPicMongoId_" + i;
            String key9 = "minaPicGmtCreate_" + i;
            String key10 = "minaPicDeleteStatus_" + i;
            Photos p = new Photos();
            Integer photoId = (Integer) map.get(key1);
            if (photoId == null) {
                break;
            }
            p.setCmsPhotoId(photoId);
            p.setStatus(0);
            p.setFileUrl((String) map.get(key2));
            p.setMallId((String) map.get(MALL_ID));
            p.setOwnerId((Integer) map.get(key4));
            p.setFile((String) map.get(key3));
            p.setOwnerType((String) map.get(key5));
            p.setPosition((Integer) map.get(key6));
            p.setDeleteStatus((Byte) map.get(key10));
            Long gmtCreate = (Long) map.get(key9);
            if (gmtCreate != null) {
                p.setCreatedAt(new Date(gmtCreate));
            }
            Object owobj = map.get(key7);
            if (owobj != null) {
                p.setOwnerMongoId((String) owobj);
            }
            Object mnobj = map.get(key8);
            if (mnobj != null) {
                p.setMongoId((String) mnobj);
            }
            result.add(p);
        }
        return result;
    }

    public abstract CmsTask getCmsTask();

}
