package com.fanxian.commons.file;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.fanxian.commons.lang.Argument;
import com.yue.commons.log.LoggerFactoryWrapper;

/**
 * 负责处理图片的压缩等
 */
public class ImageService {

    private static Logger logger = LoggerFactoryWrapper.getLogger(ImageService.class);

    /**
     * 等比例压缩图片
     * 
     * @param url
     * @param quality
     * @param size
     * @param savePath
     * @return
     */
    public boolean reduceImage4Proportional(String source, Double quality, Integer size, String savePath) {
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(savePath) || Argument.isNotPositive(size)) {
            return false;
        }
        try {
            Map<String, Integer> imageBasicInfo = ImageUtil.getImageWH(source);
            Integer imageWidth = imageBasicInfo.get(ImageUtil.IMAGE_WIDTH);
            Integer imageHeight = imageBasicInfo.get(ImageUtil.IMAGE_HEIGHT);
            if (imageWidth == null || imageHeight == null) {
                return false;
            }
            int originalImageWidth = imageWidth.intValue();
            int originalImageHeight = imageHeight.intValue();
            double widthBo = (double) size / originalImageWidth;
            int height = (int) Math.ceil(widthBo * originalImageHeight);
            return ImageUtil.scaleImage(source, size, height, savePath, quality);
        } catch (Exception e) {
            logger.error("图片缩放错误：", e);
            return false;
        }
    }

    /**
     * 转换图片格式，生成的图片格式根据dest的后缀来决定
     * 
     * @param source
     * @param dest
     * @return
     */
    public boolean converImage(String source, String dest) {
        return ImageUtil.converImage(source, dest);
    }
}
