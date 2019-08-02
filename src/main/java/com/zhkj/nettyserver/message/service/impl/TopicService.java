package com.zhkj.nettyserver.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.util.CollectionUtil;
import com.zhkj.nettyserver.message.domain.MessageTopic.ToWebScoketParams;
import com.zhkj.nettyserver.message.domain.MessageTopic.Topic;
import com.zhkj.nettyserver.netty.ChannelUtil;
import com.zhkj.nettyserver.weixin.WeiXin;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Des:消息处理服务
 * ClassName: TopicService
 * Author: dengyi
 * Date: 2019-06-28 08:48
 */
@Service
@Async("taskExecutor")
public class TopicService {
    @Autowired
    private WeiXin weiXin;

    public void msgToWebScoket(Topic topic) {
        System.out.println(topic.getParams());
        ToWebScoketParams twsp = JSON.parseObject(topic.getParams(), ToWebScoketParams.class);

        List<String> userList = topic.getUserList();
        List<String> enteList = topic.getEnteList();
        if ((userList == null || userList.size() < 1) && (enteList == null || enteList.size() < 1)) {
            //发送给所有人
//            ResponseStomp vo = ResponseStompFactory.createOk(topic.getParams(), "schat");
            List<Channel> channelList = ChannelUtil.getInstance().getAllChannel();
            for (Channel item : channelList) {
//                item.writeAndFlush(vo);
                item.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(twsp)));
            }
        }
        if (CollectionUtil.isNotEmpty(userList)) {
            for (String item : userList) {
                //发送给需要接收的人
                Channel otherChannel = ChannelUtil.getInstance().getChannel(Long.valueOf(item));
                if (otherChannel != null && otherChannel.isActive()) {
//                    otherChannel.writeAndFlush(ResponseStompFactory.createOk(topic.getParams(), "schat"));
//                    otherChannel.writeAndFlush(twsp);
                    otherChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(twsp)));
                }
            }
        } else if (CollectionUtil.isNotEmpty(enteList)) {
            //发送给公司
//            ResponseStomp vo = ResponseStompFactory.createOk(topic.getParams(), "schat");
            for (String enteUuid : enteList) {
                ConcurrentHashMap<Long, Channel> chalMap = ChannelUtil.getInstance().getChalMap(Long.valueOf(enteUuid));
                if (chalMap == null || chalMap.isEmpty()) {
                    continue;
                }
                for (Channel channel : chalMap.values()) {
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(twsp)));
//                    channel.writeAndFlush(vo);
                }
            }
        }


    }

    //    //发送消息到微信
//    public void MsgToWX(Topic topic) {
//        ToWeiXinParams twxp = JSON.parseObject(topic.getParams(), ToWeiXinParams.class);
//        weiXin.sendTemplate(twxp.getEnteUuid(),twxp.getWtmsType(),twxp.getData());
//    }

}
