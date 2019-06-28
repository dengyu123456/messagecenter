package com.zhkj.nettyserver.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.respone.ResponseStomp;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.message.domain.MessageTopic.ToWebScoketParams;
import com.zhkj.nettyserver.message.domain.MessageTopic.ToWeiXinParams;
import com.zhkj.nettyserver.message.domain.MessageTopic.Topic;
import com.zhkj.nettyserver.netty.ChannelUtil;
import com.zhkj.nettyserver.weixin.WeiXin;
import io.netty.channel.Channel;
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

    public void MsgToWebScoket(Topic topic) {
        ToWebScoketParams twsp = JSON.parseObject(topic.getParams(), ToWebScoketParams.class);

        List<Long> userList = twsp.getUserList();

        List<Long> enteList = twsp.getEnteList();
        if ((userList == null || userList.size() <= 0) && (enteList == null || enteList.size() <= 0)) {
            //发送给所有人
            ResponseStomp vo = ResponseStompFactory.createOk(twsp.getContent(), topic.getAction());
            List<Channel> channelList = ChannelUtil.getInstance().getAllChannel();
            for (Channel item : channelList) {
                item.writeAndFlush(vo);
            }
        }
        if (userList != null && userList.size() > 0) {
            for (Long item : userList) {
                //发送给需要接收的人
                Channel otherChannel = ChannelUtil.getInstance().getChannel(item);
                if (otherChannel != null && otherChannel.isActive()) {
                    otherChannel.writeAndFlush(ResponseStompFactory.createOk(twsp.getContent(), topic.getAction()));
                }
            }

        } else if (enteList != null && enteList.size() > 0) {
            //发送给公司
            ResponseStomp vo = ResponseStompFactory.createOk(twsp.getContent(), "schat");
            for (Long enteUuid : enteList) {
                ConcurrentHashMap<Long, Channel> chalMap = ChannelUtil.getInstance().getChalMap(enteUuid);
                if (chalMap == null || chalMap.isEmpty()) {
                    continue;
                }
                for (Channel channel : chalMap.values()) {
                    channel.writeAndFlush(vo);
                }
            }
        }


    }

    //发送消息到微信
    public void MsgToWX(Topic topic) {
        ToWeiXinParams twxp = JSON.parseObject(topic.getParams(), ToWeiXinParams.class);
        weiXin.sendTemplate(twxp.getEnteUuid(),twxp.getWtmsType(),twxp.getData());
    }
}
