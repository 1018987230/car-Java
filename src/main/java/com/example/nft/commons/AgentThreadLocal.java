package com.example.nft.commons;

/**
 * @Author wangyixiong
 * @Date 2023/3/3 下午12:11
 * @PackageName:com.example.nft.commons
 * @ClassName: AgentThreadLocal
 * @Description: 保存token解析出的登录uuid
 * @Version 1.0
 */
public class AgentThreadLocal {

    private AgentThreadLocal(){

    }

    private static final ThreadLocal<String> LOCAL = new ThreadLocal<String>();

    public static void set(String uuid){
        LOCAL.set(uuid);
    }

    public static String get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }


}
