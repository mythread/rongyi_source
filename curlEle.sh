#!/bin/bash                                                                                                                                                                        
                                                                                                                                                                                   
function randnum()                                                                                                                                                                 
{                                                                                                                                                                                  
    di=(0 1 2 3 4 5 6 7 8 9 \                                                                                                                                                      
        a b c d e f g h i j k l m n o p q r s t u v w x y z \                                                                                                                      
        A B C D E F G H I G K L M N O P Q R S T U V W X Y Z \                                                                                                                      
        ~ ! ^ _)                                                                                                                                                                   
    for(( i=0; i<$1; i++))                                                                                                                                                         
    do                                                                                                                                                                             
        num=$num`echo -n ${di[$RANDOM % ${#di[*]}]}`                                                                                                                               
    done                                                                                                                                                                           
    echo $num                                                                                                                                                                      
}                                                                                                                                                                                  
                                                                                                                                                                                   
while(true)                                                                                                                                                                        
do                                                                                                                                                                                                                                
  


done 


curl 'https://ele.me/register' 
-L 'https://ele.me/login' 
-H 'Cookie: eleme__ele_me=445c3f56cf4c271da83d4214468a370b:b201b3e0d9887cff52278cd986d42b6b80a065c6; _ga=GA1.2.1165069720.1401023748; Hm_lvt_4f06ea17a2c10a25b2f39bb33b432b16=1401023753; Hm_lpvt_4f06ea17a2c10a25b2f39bb33b432b16=1401024530; __utma=1.242851379.1401023780.1401023780.1401023780.1; __utmb=1.77.7.1401024529859; __utmc=1; __utmz=1.1401023780.1.1.utmcsr=v5.ele.me|utmccn=(referral)|utmcmd=referral|utmcct=/' 
-H 'Origin: https://ele.me' -H 'Accept-Encoding: gzip,deflate,sdch' -H 'Accept-Language: en-US,en;q=0.8,ko;q=0.6' 
-H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36' 
-H 'Content-Type: application/x-www-form-urlencoded' 
-H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' 
-H 'Cache-Control: max-age=0' -H 'Referer: https://ele.me/register' -H 'Connection: keep-alive' 
--data 'sf_guard_user%5Busername%5D=laolunshi1234&sf_guard_user%5Bpassword%5D=xing1234567&sf_guard_user%5BrepeatPassword%5D=xing1234567&sf_guard_user%5Bemail%5D=laolunshi123%40163.com&agreement=on&token=5381f82b7f45a' 
--compressed
