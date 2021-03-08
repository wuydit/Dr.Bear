package com.bear.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuyd
 * 2021/2/25 12:15
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {

    private int port = 9000;

}
