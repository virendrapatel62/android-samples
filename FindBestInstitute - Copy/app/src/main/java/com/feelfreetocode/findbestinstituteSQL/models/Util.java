package com.feelfreetocode.findbestinstituteSQL.models;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Util {
    public static String getUrl(String url , Map<String , String> param){
        Set<String> keySet = param.keySet();
        Iterator<String> otr = keySet.iterator();
        String finalUrl = url+"?";
        while(otr.hasNext()){
            String key = otr.next();
            String value = param.get(key);
            finalUrl = finalUrl+key+"="+value+"&";
        }

        finalUrl = finalUrl.replace(" " , "%20");

        return finalUrl.substring(0 , finalUrl.length()-1);
    }
}
