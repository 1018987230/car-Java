package com.example.nft.utils;

/**
 * @Author wangyixiong
 * @Date 2023/1/31 下午10:03
 * @PackageName:com.example.nft.utils
 * @ClassName: RandomUtil
 * @Description: TODO
 * @Version 1.0
 */
public class RandomUtil {
    public static String generateRandom(Integer num){

        String random = ""+Math.random();

        return random.substring(random.length()-num);
    }
}
