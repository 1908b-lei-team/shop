package com.fh.wxpay.controller;

import com.fh.common.ServerResponse;
import com.fh.wxpay.sdk.HttpClient;
import com.fh.wxpay.sdk.WXPayUtil;
import freemarker.template.utility.DateUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RequestMapping("wx")
@RestController
public class WxController {
    @RequestMapping("getUrl")
    public ServerResponse getUrl(String orderNo, BigDecimal totalPrice) {
        //1.调用远程接口
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        //设置传输协议
        client.setHttps(true);
        //设置传递的参数
        Map<String,String> map = new HashMap<>();
        //微信公众账号
        map.put("appid","wxa1e44e130a9a8eee");
        //商户号
        map.put("mch_id","1507758211");
        //微信工具类提供的生成随机字符串
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        //页面显示的名称，自己的公司名
        map.put("body","西西小铺");
        //加密方式，默认是MD5
        map.put("sign_type","MD5");
        //out_trade_no为 自己生成的商品订单号
        map.put("out_trade_no",orderNo);
        //微信支付是以分为单位,不能有小数点
        map.put("total_fee","1");
        //IP地址
        map.put("spbill_create_ip","127.0.0.1");
        //回调地址，随便配
        map.put("notify_url","https://www.baidu.com");
        //交易类型
        map.put("trade_type", "NATIVE");
        Date date = DateUtils.addMinutes(new Date(), 2);
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
        map.put("time_expire", sim.format(date));

        //将map转成带有签名的XML,微信工具类提供
        try {
            //api密钥 partnerkey
            String xmlParam = WXPayUtil.generateSignedXml(map,"feihujiaoyu12345678yuxiaoyang123");
            //发送数据
            //System.out.println("发送数据"+xmlParam);
            client.setXmlParam(xmlParam);
            client.post();
            //获取返回结果
            String content = client.getContent();
            //System.out.println("返回结果"+content);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            if(resultMap.get("return_code").equalsIgnoreCase("FAIL")){
                return ServerResponse.error(resultMap.get("return_msg"));
            }
            if(resultMap.get("result_code").equalsIgnoreCase("FAIL")){
                return ServerResponse.error(resultMap.get("return_msg")+resultMap.get("err_code_des"));
            }

            //返回数据生成二维码
            return ServerResponse.success(resultMap.get("code_url"));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ServerResponse.error();
        }


    }
    @RequestMapping("getPayStatus")
    public ServerResponse getPayStatus(String orderNo) {
        //1.调用远程接口
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        //设置传输协议
        client.setHttps(true);
        //设置传递的参数
        Map<String,String> map = new HashMap<>();
        //微信公众账号
        map.put("appid","wxa1e44e130a9a8eee");
        //商户号
        map.put("mch_id","1507758211");
        //微信工具类提供的生成随机字符串
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        //加密方式，默认是MD5
        map.put("sign_type","MD5");
        //out_trade_no为 自己生成的商品订单号
        map.put("out_trade_no",orderNo);
        //微信支付是以分为单位,不能有小数点
        //将map转成带有签名的XML,微信工具类提供
        try {
            //api密钥 partnerkey
            String xmlParam = WXPayUtil.generateSignedXml(map,"feihujiaoyu12345678yuxiaoyang123");
            //发送数据
            System.out.println("发送数据"+xmlParam);
            client.setXmlParam(xmlParam);
            client.post();
            //获取返回结果
            String content = client.getContent();
            System.out.println("返回结果"+content);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            if(resultMap.get("return_code").equalsIgnoreCase("FAIL")){
                return ServerResponse.error(resultMap.get("return_msg"));
            }
            if(resultMap.get("result_code").equalsIgnoreCase("FAIL")){
                return ServerResponse.error(resultMap.get("return_msg"));
            }
            if(!resultMap.get("trade_state").equalsIgnoreCase("SUCCESS")){
                return ServerResponse.error("未支付");
            }


            return ServerResponse.success();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return ServerResponse.error();
        }


    }
}
