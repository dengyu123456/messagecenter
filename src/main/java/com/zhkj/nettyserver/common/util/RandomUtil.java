package com.zhkj.nettyserver.common.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {


    public static String getRandStr(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 26) + 'a');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

    public static String getUuid(){
       return  UUID.randomUUID().toString().replace("-","");
    }


    public static void main(String [] args){
        System.out.println(getUuid());
    }
}
