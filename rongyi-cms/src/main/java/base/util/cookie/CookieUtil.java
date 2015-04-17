package base.util.cookie;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil {

    public static Map<String,String> readCookie(HttpServletRequest request,String key){
       
        Cookie cookies[] = request.getCookies();
        Map<String ,String> map = null;
        if (cookies != null) {
        	 map = new HashMap<String, String>();
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                	map.put(key, cookie.getValue());
                }
            }
        }
        return map;
    }
    

    public static void writeCookie(Map<String, String> cookieMap,HttpServletResponse response,int cookieDay){
        for (Iterator<String> it = cookieMap.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            Cookie cookie = new Cookie(key, cookieMap.get(key));
            cookie.setMaxAge(cookieDay* 24 * 60 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
    
    

    public static void writeCookie(Map<String, String> cookieMap,HttpServletResponse response){
        for (Iterator<String> it = cookieMap.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            Cookie cookie = new Cookie(key, cookieMap.get(key));
            response.addCookie(cookie);
        }
       
    }
    
    public static String readCookieOne(HttpServletRequest request,String key){
        
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                	return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    
    

}
