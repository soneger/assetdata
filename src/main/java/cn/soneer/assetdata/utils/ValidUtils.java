package cn.soneer.assetdata.utils;

import java.util.regex.Pattern;

/**
 * project：assetdata
 * class：ValidUtils
 * describe：TODO
 * time：2021/3/20 18:57
 * author：soneer
 * version:1.0
 */

public class ValidUtils {

    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if(str!=null&&!str.isEmpty()){
            return pattern.matcher(str).matches();
        }
        return false;
    }

}
