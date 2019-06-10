//package com.zhkj.purchase.netty;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.util.Attribute;
//
///**
// * Created by root on 2019/6/9.
// */
//public class VerificationHandler extends SimpleChannelInboundHandler<Object> {
//
//    @Override
//    protected void messageReceived(ChannelHandlerContext channel, Object o) throws Exception {
//
//        if(!this.verification(channel,o)){
//            channel.channel().close();
//        }else {
//            channel.attr(Attributes.LOGIN).set(true);
//            //如果登陆认证成功，删掉连接上的登陆认证模块
//            channel.pipeline().remove(this);
//        }
//
//    }
//
//    private Boolean verification(ChannelHandlerContext channel, Object o){
//        //登陆验证 复制tocken的验证方式 不通过直接关闭连接
//        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
//
//        return loginAttr.get() != null;
//    }
//}
