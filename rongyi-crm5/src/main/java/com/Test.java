package com;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.methods.GetMethod;

public class Test {

    public static void main(String[] args) throws Exception {
        String picUrl = "http://ww4.sinaimg.cn/thumbnail/94bec68bjw1efcsjci531j20hs0nsjvc.jpg";
        GetMethod getMethod = new GetMethod(picUrl);
        // byte[] bytes = HttpClientUtils.getResponseBodyAsByte(getMethod);
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH));
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int period = 2;
        final Runnable sentMsg = new Runnable() {

            @Override
            public void run() {
                System.out.println("11111111");
            }
        };
        executor.scheduleAtFixedRate(sentMsg, 0, period, TimeUnit.SECONDS);
    }
}
