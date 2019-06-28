///**
// * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
// * This software is the confidential and proprietary information of
// * ZHONGHENG, Inc. You shall not disclose such Confidential
// * Information and shall use it only in accordance with the terms of the
// * license agreement you entered into with ZHONGHENG.
// */
//package com.zhkj.nettyserver.weixin.controller;
//
//import com.zhkj.qywebapi.common.base.controller.BaseController;
//import com.zhkj.qywebapi.common.base.request.Request;
//import com.zhkj.qywebapi.common.base.respone.Response;
//import com.zhkj.qywebapi.common.base.respone.ResponseFactory;
//import com.zhkj.qywebapi.common.utils.AESUtil;
//import com.zhkj.qywebapi.common.utils.BeanUtil;
//import com.zhkj.qywebapi.common.utils.CollectionUtil;
//import com.zhkj.qywebapi.common.utils.StringUtil;
//import com.zhkj.qywebapi.weixin.domain.EnterpriseWeixin;
//import com.zhkj.qywebapi.weixin.domain.WeixinTemplateMsg;
//import com.zhkj.qywebapi.weixin.domain.request.AddWeixinTemplateMsgParams;
//import com.zhkj.qywebapi.weixin.domain.request.EditEnterpriseWeixinParams;
//import com.zhkj.qywebapi.weixin.domain.request.EditWeixinTemplateMsgParams;
//import com.zhkj.qywebapi.weixin.domain.respone.DesEnterpriseWeixinVO;
//import com.zhkj.qywebapi.weixin.domain.respone.DesWeixinTemplateMsgVO;
//import com.zhkj.qywebapi.weixin.domain.respone.ListWeixinTemplateMsgVO;
//import com.zhkj.qywebapi.weixin.service.WeixinService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Des:
// * ClassName: WeixinController
// * Author: biqiang2017@163.com
// * Date: 2019/6/24
// * Time: 17:27
// */
//@Api(value = "微信Controller", tags = "微信控制器")
//@Controller
//@RequestMapping("/admin/api/weixin")
//public class WeixinController extends BaseController{
//    @Autowired
//    private WeixinService weixinService;
//
//    /**
//     * 修改公司微信账号
//     * @param reqOb
//     * @return
//     */
//    @ApiOperation(value = "修改公司微信账号")
//    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Response edit(@RequestBody @Valid Request<EditEnterpriseWeixinParams> reqOb) {
//        EditEnterpriseWeixinParams params = reqOb.getParams();
//        if (this.lawful(params.getEwxiEnteUuid())){
//            if (0 < this.weixinService.updateByEnteUuid(params)){
//                return ResponseFactory.createOk("修改公司微信账号成功");
//            }else{
//                return ResponseFactory.createBad("修改公司微信账号失败");
//            }
//        }else{
//            return ResponseFactory.createBad("修改公司微信账号失败，用户操作非法");
//        }
//    }
//
//    /**
//     * 公司微信账号详情
//     * @param reqOb
//     * @return
//     */
//    @ApiOperation(value = "公司微信账号详情",response = DesEnterpriseWeixinVO.class)
//    @RequestMapping(value = "/des", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Response des(@RequestBody @Valid Request<Long> reqOb) {
//        Long enteUuid = reqOb.getParams();
//        EnterpriseWeixin ewei = this.weixinService.selectOneByEnteUuid(enteUuid);
//        DesEnterpriseWeixinVO vo = new DesEnterpriseWeixinVO();
//        vo.setEwxiEnteUuid(enteUuid);
//        if (ewei == null){
//            return ResponseFactory.createOk(vo);
//        }else if (this.lawful(ewei.getEwxiEnteUuid())){
//            vo.setEwxiUuid(ewei.getEwxiUuid());
//            if (StringUtil.isNotBlank(ewei.getEwxiAccessToken())){
//                vo.setEwxiAccessToken(AESUtil.decryptor(ewei.getEwxiAccessToken()));
//            }
//            if (StringUtil.isNotBlank(ewei.getEwxiAppid())){
//                vo.setEwxiAppid(AESUtil.decryptor(ewei.getEwxiAppid()));
//            }
//            vo.setEwxiAccessToken(ewei.getEwxiAccessToken());
//            vo.setEwxiWtmsAllow(ewei.getEwxiWtmsAllow());
//            return ResponseFactory.createOk(vo);
//        }else{
//            return ResponseFactory.createBad("获取公司微信账号详情失败，用户操作非法");
//        }
//    }
//
//
//    /**
//     * 微信模板消息列表
//     * @param reqOb
//     * @return
//     */
//    @ApiOperation(value = "微信模板消息列表",response = ListWeixinTemplateMsgVO.class)
//    @RequestMapping(value = "/template/msg/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Response listTemplateMsg(@RequestBody @Valid Request<Long> reqOb) {
//        Long ewxiUuid = reqOb.getParams();
//        List<WeixinTemplateMsg> wtmsList = this.weixinService.selectTmsgByEwxiUuid(ewxiUuid);
//        List<ListWeixinTemplateMsgVO> voList = new ArrayList<ListWeixinTemplateMsgVO>();
//        if (CollectionUtil.isNotEmpty(wtmsList)){
//            for (WeixinTemplateMsg item :wtmsList){
//                ListWeixinTemplateMsgVO vo = new ListWeixinTemplateMsgVO();
//                vo.setWtmsEwxiUuid(item.getWtmsEwxiUuid());
//                vo.setWtmsTemplateId(item.getWtmsTemplateId());
//                vo.setWtmsUuid(item.getWtmsUuid());
//                if (StringUtil.isNotBlank(item.getWtmsToUser())){
//                    vo.setWtmsToUser(Arrays.asList(item.getWtmsToUser().split("#")));
//                }
//                vo.setWtmsType(item.getWtmsType());
//                voList.add(vo);
//            }
//        }
//        return ResponseFactory.createOk(voList);
//    }
//
//    /**
//     * 微信模板消息详情
//     * @param reqOb
//     * @return
//     */
//    @ApiOperation(value = "微信模板消息详情",response = DesEnterpriseWeixinVO.class)
//    @RequestMapping(value = "/template/msg/des", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Response desTemplateMsg(@RequestBody @Valid Request<Long> reqOb) {
//        Long wtmsUuid = reqOb.getParams();
//        WeixinTemplateMsg wtms = this.weixinService.selectTmsgOne(wtmsUuid);
//        DesWeixinTemplateMsgVO vo = new DesWeixinTemplateMsgVO();
//        if (wtms == null){
//            return ResponseFactory.createOk(vo);
//        }else {
//            BeanUtil.copyProperties(wtms,vo);
//            return ResponseFactory.createOk(vo);
//        }
//    }
//
//    /**
//     * 修改微信模板消息
//     * @param reqOb
//     * @return
//     */
//    @ApiOperation(value = "修改微信模板消息")
//    @RequestMapping(value = "/template/msg/edit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Response editTemplateMsg(@RequestBody @Valid Request<EditWeixinTemplateMsgParams> reqOb) {
//        EditWeixinTemplateMsgParams params = reqOb.getParams();
//        if (0 < this.weixinService.updateWtms(params)){
//            return ResponseFactory.createOk("修改微信模板消息成功");
//        }else{
//            return ResponseFactory.createBad("修改微信模板消息失败");
//        }
//    }
//
//    /**
//     * 增加微信模板消息
//     * @param reqOb
//     * @return
//     */
//    @ApiOperation(value = "增加微信模板消息")
//    @RequestMapping(value = "/template/msg/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Response addTemplateMsg(@RequestBody @Valid Request<AddWeixinTemplateMsgParams> reqOb) {
//        AddWeixinTemplateMsgParams params = reqOb.getParams();
//        if (0 < this.weixinService.insertWtms(params)){
//            return ResponseFactory.createOk("增加微信模板消息成功");
//        }else{
//            return ResponseFactory.createBad("增加微信模板消息失败");
//        }
//    }
//}
