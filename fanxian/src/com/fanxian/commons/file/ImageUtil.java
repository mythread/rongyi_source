/*
 * Copyright 2011-2016 YueJi.com All right reserved. This software is the confidential and proprietary information of
 * YueJi.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with YueJi.com.
 */
package com.fanxian.commons.file;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.Info;
import org.im4java.core.InfoException;
import org.im4java.process.ArrayListOutputConsumer;
import org.slf4j.Logger;

import com.yue.commons.lang.Assert;
import com.yue.commons.log.LoggerFactoryWrapper;

/**
 * @author 轰天雷 2012-4-25 上午11:58:16
 */
public class ImageUtil {

    private static Logger       logger        = LoggerFactoryWrapper.getLogger(ImageUtil.class);
    /**
     * 图片质量
     */
    public static final String  IMAGE_QUALITY = "Quality";
    /**
     * 图片高度
     */
    public static final String  IMAGE_HEIGHT  = "Height";
    /**
     * 图片宽度
     */
    public static final String  IMAGE_WIDTH   = "Width";
    /**
     * 图片格式
     */
    public static final String  IMAGE_FORMAT  = "Format";

    /**
     * 
     */
    public static final boolean isDebug       = false;

    // 是否为调试模式
    public static boolean isDebugMode() {
        return isDebug;
    }

    /**
     * @param imgFile
     * @return
     * @throws InfoException
     */
    public static Info getImageInfo(String imgFile) throws InfoException {
        Info imageInfo = new Info(imgFile);
        return imageInfo;
    }

    /**
     * 获取图片属性
     * 
     * <pre>
     * identify -verbose 图片名
     * </pre>
     * 
     * @param imageInfo
     * @param propertyName
     * @return
     */
    public static String getProperty(Info imageInfo, String propertyName) {
        String propertyValue = imageInfo.getProperty(propertyName);
        if (propertyValue == null) {
            Enumeration<String> propertyNames = imageInfo.getPropertyNames();
            String value = null;
            while (propertyNames.hasMoreElements()) {
                value = propertyNames.nextElement();
                if (value.indexOf(propertyName) != -1) {
                    propertyValue = imageInfo.getProperty(value);
                    break;
                }
            }
        }
        return propertyValue;
    }

    // /////////////////////////////////////////////////////////////////////
    //
    // 图片处理方法
    //
    // /////////////////////////////////////////////////////////////////////
    /**
     * 查询图片的基本信息:格式,质量，宽度，高度
     * 
     * <pre>
     *    %b   file size of image read in
     *    %c   comment property
     *    %d   directory component of path
     *    %e   filename extension or suffix
     *    %f   filename (including suffix)
     *    %g   layer canvas page geometry   ( = %Wx%H%X%Y )
     *    %h   current image height in pixels
     *    %i   image filename (note: becomes output filename for "info:")
     *    %k   number of unique colors
     *    %l   label property
     *    %m   image file format (file magic)
     *    %n   exact number of images in current image sequence
     *    %o   output filename  (used for delegates)
     *    %p   index of image in current image list
     *    %q   quantum depth (compile-time constant)
     *    %r   image class and colorspace
     *    %s   scene number (from input unless re-assigned)
     *    %t   filename without directory or extension (suffix)
     *    %u   unique temporary filename (used for delegates)
     *    %w   current width in pixels
     *    %x   x resolution (density)
     *    %y   y resolution (density)
     *    %z   image depth (as read in unless modified, image save depth)
     *    %A   image transparency channel enabled (true/false)
     *    %C   image compression type
     *    %D   image dispose method
     *    %G   image size ( = %wx%h )
     *    %H   page (canvas) height
     *    %M   Magick filename (original file exactly as given,  including read mods)
     *    %O   page (canvas) offset ( = %X%Y )
     *    %P   page (canvas) size ( = %Wx%H )
     *    %Q   image compression quality ( 0 = default )
     *    %S   ?? scenes ??
     *    %T   image time delay
     *    %W   page (canvas) width
     *    %X   page (canvas) x offset (including sign)
     *    %Y   page (canvas) y offset (including sign)
     *    %Z   unique filename (used for delegates)
     *    %@   bounding box
     *    %#   signature
     *    %%   a percent sign
     *    \n   newline
     *    \r   carriage return
     * </pre>
     * 
     * @param imageFilePath 图片完整路径
     * @return 返回图片的属性Map信息,如果出错，则返回空Map对象
     */
    public static Map<String, String> getImageBasicInfo(String imageFilePath) {
        // create operation
        IMOperation op = new IMOperation();
        op.ping();
        // op.format("%m\n%w\n%h\n%g\n%W\n%H\n%G\n%z\n%r\n%Q");
        op.format("%m\n%w\n%h\n%Q");
        op.addImage(imageFilePath);

        try {
            // execute ...
            IdentifyCmd identify = new IdentifyCmd();
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            identify.setOutputConsumer(output);
            identify.run(op);

            // ... and parse result
            ArrayList<String> cmdOutput = output.getOutput();
            Iterator<String> iter = cmdOutput.iterator();
            Map<String, String> map = new Hashtable<String, String>();
            map.put(IMAGE_FORMAT, iter.next());
            map.put(IMAGE_WIDTH, iter.next());
            map.put(IMAGE_HEIGHT, iter.next());
            map.put(IMAGE_QUALITY, iter.next());
            return map;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return Collections.emptyMap();
        }
    }

    /**
     * 得到宽高属性
     */
    public static Map<String, Integer> getImageWH(String imageFilePath) {
        // create operation
        IMOperation op = new IMOperation();
        op.ping();
        // op.format("%m\n%w\n%h\n%g\n%W\n%H\n%G\n%z\n%r\n%Q");
        op.format("%w\n%h");
        op.addImage(imageFilePath);

        try {
            // execute ...
            IdentifyCmd identify = new IdentifyCmd();
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            identify.setOutputConsumer(output);
            identify.run(op);

            // ... and parse result
            ArrayList<String> cmdOutput = output.getOutput();
            Iterator<String> iter = cmdOutput.iterator();
            Map<String, Integer> map = new Hashtable<String, Integer>();

            map.put(IMAGE_WIDTH, parseInt(iter.next()));
            map.put(IMAGE_HEIGHT, parseInt(iter.next()));
            return map;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return Collections.emptyMap();
        }
    }

    private static Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 切割图片
     * 
     * @param xOffset 截点横坐标 (从左开始计数)
     * @param yOffset 截点纵坐标 (从上开始计数)
     * @param width 期望截取的图片的宽度
     * @param height 期望截取的图片长度
     * @param sourceFilePath 原始图片位置的完整路径，包括后缀名
     * @param destFilePath 新生成的图片位置的完整路径，包括后缀名
     * @return 成功返回true，否则返回false
     */
    public static boolean cutImage(String sourceFilePath, String destFilePath, Double quality, int xOffset,
                                   int yOffset, int width, int height) {
        Assert.assertPositive(width, "图片裁剪的期望宽度不能小于0");
        Assert.assertPositive(height, "图片裁剪的期望高度不能小于0");
        ConvertCmd convert = new ConvertCmd();
        IMOperation op = new IMOperation();
        // 图片质量缺省设置为80
        if (quality != null && quality > 0) {
            op.quality(quality);
        }
        op.addImage(sourceFilePath);
        op.crop(width, height, xOffset, yOffset);
        op.addImage(destFilePath);
        try {
            convert.run(op);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 图片缩放
     * 
     * @param sourceImage 原始图片
     * @param width 宽 图片期望缩放或的宽度
     * @param height 高 图片期望缩放后的高度
     * @param destFilePath 图片缩放后存储的完整路径--包括后缀名
     * @return 成功返回true，否则返回false
     */
    public static boolean scaleImage(BufferedImage sourceImage, int width, int height, String destFilePath,
                                     Double quality) {
        Assert.assertNotNull(sourceImage, "图片缩放的图片源不能为空");
        Assert.assertNotBlank(destFilePath, "图片缩放的目标存储位置不能为空");
        Assert.assertPositive(width, "图片缩放的期望宽度不能小于0");
        Assert.assertPositive(height, "图片缩放的期望高度不能小于0");
        ConvertCmd convert = new ConvertCmd();
        IMOperation op = new IMOperation();
        if (quality != null && quality > 0) {
            op.quality(quality);
        }
        op.addImage();
        op.resize(width, height);
        op.addImage();
        try {
            convert.run(op, sourceImage, destFilePath);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 图片缩放
     * 
     * @param sourceFilePath 原始图片完整文件名
     * @param width 宽 图片期望缩放或的宽度
     * @param height 高 图片期望缩放后的高度
     * @param destFilePath 图片缩放后存储的完整路径--包括后缀名
     * @return 成功返回true，否则返回false
     */
    public static boolean scaleImage(String sourceFilePath, int width, int height, String destFilePath, Double quality) {
        Assert.assertNotBlank(sourceFilePath, "图片缩放的图片源不能为空");
        Assert.assertNotBlank(destFilePath, "图片缩放的目标存储位置不能为空");
        Assert.assertPositive(width, "图片缩放的期望宽度不能小于0");
        Assert.assertPositive(height, "图片缩放的期望高度不能小于0");
        ConvertCmd convert = new ConvertCmd();
        IMOperation op = new IMOperation();
        if (quality != null && quality > 0) {
            op.quality(quality);
        }
        op.addImage(sourceFilePath);
        op.resize(width, height);
        op.addImage(destFilePath);
        try {
            convert.run(op);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static boolean converImage(String source, String dest) {
        ConvertCmd convert = new ConvertCmd();
        IMOperation op = new IMOperation();
        op.addImage(source);
        op.addImage(dest);
        try {
            convert.run(op);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

}
