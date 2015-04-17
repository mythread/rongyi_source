package com.fanxian.biz.common.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CommonBizTools {

    /**
     * 检查id并去重
     * 
     * @param ids
     * @return
     */
    public static Collection<Integer> positiveCollection(Integer... ids) {
        if (ids == null || ids.length == 0) {
            return Collections.emptySet();
        }
        List<Integer> idList = new ArrayList<Integer>(ids.length);
        for (Integer integer : ids) {
            if (integer != null && integer.intValue() > 0) {
                if (!idList.contains(integer)) {
                    idList.add(integer);
                }
            }
        }
        return idList;
    }

    /**
     * 检查id并去重
     * 
     * @param ids
     * @return
     */
    public static Collection<Integer> positiveCollection(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptySet();
        }
        List<Integer> idList = new ArrayList<Integer>(ids.size());
        for (Integer integer : ids) {
            if (integer != null && integer.intValue() > 0) {
                if (!idList.contains(integer)) {
                    idList.add(integer);
                }
            }
        }
        return idList;
    }
}
