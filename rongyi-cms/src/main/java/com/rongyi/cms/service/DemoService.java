package com.rongyi.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.dao.DemoDao;

@Service
@Transactional
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public void addDemo() {
        try {
            demoDao.addDemo();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
