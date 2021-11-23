package com.bear.server.control.tcp;

import com.google.protobuf.GeneratedMessageV3;

/**
 * @author wuyd
 * 2021/3/9 15:06
 */
public interface BaseSocketControl<T>{

    GeneratedMessageV3 execute(T t);
}
