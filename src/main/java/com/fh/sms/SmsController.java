package com.fh.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fh.common.ServerResponse;
import com.fh.redis.RedisUtil;
import com.fh.util.MessageVerifyUtils;
import com.fh.util.SystemConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
public class SmsController {
    @RequestMapping("sendMsg")
    public ServerResponse sendMsg(String phone){
        String code = MessageVerifyUtils.getNewcode();
        try {
            SendSmsResponse sendSmsResponse = MessageVerifyUtils.sendSms(phone, code);
            if(sendSmsResponse.getCode()!= null && sendSmsResponse.getCode().equals("OK")){
                //RedisUtil.set(phone,code);
                RedisUtil.set(phone,code);
                return ServerResponse.success();
            }else {
                System.out.println("短信发送失败！");
                return ServerResponse.error("短信发送失败！");
            }

        } catch (ClientException e) {
            e.printStackTrace();
            return ServerResponse.error("短信发送失败！");
        }

    }
}
