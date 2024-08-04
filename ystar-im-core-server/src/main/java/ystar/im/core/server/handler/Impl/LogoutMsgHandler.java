package ystar.im.core.server.handler.Impl;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ystar.im.Domain.Dto.ImMsgBody;
import ystar.im.constant.ImConstants;
import ystar.im.constant.ImMsgCodeEnum;
import ystar.im.core.server.common.ChannelHandlerContextCache;
import ystar.im.core.server.common.ImContextUtils;
import ystar.im.core.server.common.ImMsg;
import ystar.im.core.server.constants.ImCoreServerConstants;
import ystar.im.core.server.handler.SimpleHandler;

import java.util.concurrent.TimeUnit;

/**
 * 登出消息处理器
 */
@Component
public class LogoutMsgHandler implements SimpleHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutMsgHandler.class);

    @Resource
    private RedisTemplate<String , String> redisTemplate;

    @Override
    public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
        Long userId = ImContextUtils.getUserId(ctx);
        Integer appId = ImContextUtils.getAppId(ctx);
        if(userId == null || appId == null){
            LOGGER.error("attr error, imMsg is {}", imMsg);
            //有可能是错误的消息包导致，直接放弃连接
            ctx.close();
            throw new IllegalArgumentException("attr error");
        }

        //将IM消息回写给客户端
        ImMsgBody respBody = new ImMsgBody();
        respBody.setUserId(userId);
        respBody.setAppId(appId);
        respBody.setData("登出信息");
        ctx.writeAndFlush(ImMsg.build(ImMsgCodeEnum.IM_LOGOUT_MSG.getCode(), JSON.toJSONString(respBody)));
        LOGGER.info("[登出信息] logout success, userId is {}, appId is {}", userId, appId);

        /**
         * 退出登录，删除 记录
         */
        redisTemplate.delete(ImCoreServerConstants.IM_BIND_IP_KEY + appId + userId);

        //理想情况下：客户端断线线的时候发送短线消息包
        ChannelHandlerContextCache.remove(userId);
        ImContextUtils.removeUserId(ctx);
        ImContextUtils.removeAppId(ctx);

        ctx.close();
    }
}