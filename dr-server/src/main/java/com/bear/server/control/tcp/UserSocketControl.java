package com.bear.server.control.tcp;

import com.bear.common.dto.ActionProto;
import com.bear.common.dto.UserProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.bear.server.config.SocketConfig.BEAN_PREFIX;

/**
 * @author wuyd
 * 2021/3/9 12:28
 */
@Slf4j
@Service(BEAN_PREFIX + ActionProto.Action.getUserInfo_VALUE)
public class UserSocketControl implements BaseSocketControl<UserProto.User>{

    @Override
    public UserProto.UserResult execute(UserProto.User user){
        log.info(user.toString());
        return UserProto.UserResult.newBuilder().setResult(344).build();
    }
}
