package com.example.nft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author wangyixiong
 * @Date 2023/4/11 下午10:39
 * @PackageName:com.example.nft.config
 * @ClassName: WebSocketConfig
 * @Description: TODO
 * @Version 1.0
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}