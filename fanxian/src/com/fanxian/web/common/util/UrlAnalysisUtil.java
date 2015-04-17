package com.fanxian.web.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.UrlValidator;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class UrlAnalysisUtil {

    private static final String IMG_NODE     = "img";
    private static final String HTTP         = "http://";
    private static UrlValidator urlValidator = new UrlValidator();

    /**
     * 获取域名
     * 
     * @param url
     * @return
     */
    public static String getHost(String url) {
        url = StringUtils.trim(url);
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        int index = url.indexOf(HTTP);
        if (index == -1) {
            return null;
        }
        url = url.substring(index + HTTP.length());
        index = url.indexOf('/');
        if (index == -1) {
            return url;
        }
        return url.substring(0, index);
    }

    /**
     * 获取URL中的URI部分（去掉了host和参数）
     * 
     * @param url
     * @return
     */
    public static String getURI(String url) {
        url = StringUtils.trim(url);
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        int index = url.indexOf(HTTP);
        if (index == -1) {
            return null;
        }
        url = url.substring(index + HTTP.length());
        index = url.indexOf('/');
        if (index == -1) {
            return null;
        }
        url = url.substring(index);
        if (StringUtils.isEmpty(url) || url.length() == 1) {
            return null;
        }
        index = url.indexOf('?');
        if (index == -1) {
            return url;
        }
        url = url.substring(0, index);
        return url;
    }

    /**
     * 解析url，获取参数<br>
     * 注意：没处理key对多个value的情况
     * 
     * @param url
     * @return
     */
    public static Map<String, String> getParamsMap(String url) {
        Map<String, String> map = new HashMap<String, String>();
        url = StringUtils.trim(url);
        if (StringUtils.isEmpty(url)) {
            return map;
        }
        int index = url.indexOf('?');
        if (index == -1) {
            return map;
        }
        map.put("reqUrl", url.substring(0, index));
        String paramsString = url.substring(index + 1);
        String[] keyvalueArray = StringUtils.split(paramsString, '&');
        for (String keyvalue : keyvalueArray) {
            String[] s = StringUtils.split(keyvalue, '=');
            if (s == null || s.length != 2) {
                continue;
            }
            map.put(s[0], s[1]);
        }
        return map;
    }

    /**
     * detail信息是html内容，通过html paser解析出所有的img节点
     * 
     * @param detail
     * @return
     */
    public static List<String> getAllImg(String detail) {
        if (StringUtils.isEmpty(detail) || detail.trim().length() == 0) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<String>();
        Parser parser = new Parser();
        try {
            parser.setInputHTML(detail);
            NodeFilter filterLink = new TagNameFilter(IMG_NODE);
            NodeList links = new NodeList();
            links = parser.extractAllNodesThatMatch(filterLink);
            Node[] imgNodes = links.toNodeArray();
            for (Node node : imgNodes) {
                if (node instanceof ImageTag) {
                    ImageTag it = (ImageTag) node;
                    String urlString = it.getImageURL();
                    if (StringUtils.isNotEmpty(urlString)) {
                        list.add(it.getImageURL());
                    }
                    // Attribute dataOrg = it.getAttributeEx("data-org");
                    // if (dataOrg == null) {
                    // continue;
                    // }
                    // String dataStr = dataOrg.getValue();
                    // list.add(dataStr);
                }
            }
            return list;
        } catch (ParserException e) {
            return Collections.emptyList();
        }
    }

}
