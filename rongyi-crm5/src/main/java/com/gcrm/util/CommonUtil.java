/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gcrm.util;

import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import com.gcrm.domain.OptionBase;
import com.opensymphony.xwork2.ActionContext;

/**
 * Common util
 */
public class CommonUtil {

    /**
     * Confirms if the string is null or empty
     * 
     * @param str
     *            input string
     * @return the flat to identify if the string is null or empty
     */
    public static boolean isNullOrEmpty(String str) {

        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * Converts string from null to empty
     * 
     * @param str
     *            input string
     * @return the converted string
     */
    public static String fromNullToEmpty(String str) {

        if (str == null) {
            return "";
        } else {
            return str;
        }
    }
    
    /**
     * 对象返回对应的string
     *
     */
    public static String convert2String(Object obj){
        if(obj == null){
            return "";
        }
        return obj.toString();
    }

    public static ResourceBundle getResourceBundle() {
        Locale local = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("MessageResources", local);
        return rb;
    }

    public static String lowerCaseString(String value) {
        char[] chars = new char[1];
        chars[0] = value.charAt(0);
        String temp = new String(chars);
        value = value.replaceFirst(temp, temp.toLowerCase());
        return value;
    }

    public static String getOptionLabel(OptionBase option) {
        if (option == null) {
            return "";
        }
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        String local = (String) session.get("locale");
        String label = null;
        if ("en_US".equals(local)) {
            label = option.getLabel_en_US();
        } else {
            label = option.getLabel_zh_CN();
        }
        return CommonUtil.fromNullToEmpty(label);
    }

    public static final String randomString(int length) {
        Random randGen = null;
        char[] numbersAndLetters = null;
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
                    + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
    
    public static String stringToJson(String s) {    
        StringBuffer sb = new StringBuffer ();     
        for (int i=0; i<s.length(); i++) {     
      
            char c = s.charAt(i);     
            switch (c) {     
            case '\"':     
                sb.append("  ");     
                break;     
//            case '\\':   //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
//                sb.append("\\\\");     
//                break;     
            case '/':     
                sb.append("  ");     
                break;     
            case '\b':      //退格
                sb.append("  ");     
                break;     
            case '\f':      //走纸换页
                sb.append("  ");     
                break;         
            case '\n':     
            	sb.append("  "); //换行
                break;     
            case '\r':      //回车
                sb.append("  ");     
                break;     
            case '\t':      //横向跳格
                sb.append("  ");     
                break;   
            default:     
                sb.append(c);    
            }}
        return sb.toString();     
     }
   
}
