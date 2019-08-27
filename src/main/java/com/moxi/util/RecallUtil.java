package com.moxi.util;

import java.text.NumberFormat;
import java.util.Random;

/**
 * 回调率处理工具类
 * @author
 */
public class RecallUtil {

    /**
     * 通知回调率计算是否可进行回调通知
     * @return
     */
    public static boolean isCallback(int callbackRatio){
        //概率
        int ratioNum = (int) (Math.random()*100);
        if(ratioNum <= callbackRatio){
            return true;
        }
        return false;
    }
}
