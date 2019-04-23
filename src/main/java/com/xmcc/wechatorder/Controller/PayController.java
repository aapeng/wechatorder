package com.xmcc.wechatorder.Controller;

import com.lly835.bestpay.model.PayResponse;
import com.xmcc.wechatorder.entity.OrderMaster;
import com.xmcc.wechatorder.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map map){
        //根据id查询订单
        OrderMaster orderMaster = payService.findById(orderId);
        //根据订单创建支付
        PayResponse response = payService.create(orderMaster);
        //将参数设置到map中
        map.put("payResponse",response);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("weixin/pay",map);
    }

    @RequestMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        log.info("回调");
        //调用业务层处理回调验证，修改订单
        payService.notify(notifyData);
        //返回到页面，页面的内容会被微信读取，告诉微信，我们这边ok了，不然会一致发送异步回调
        return new ModelAndView("weixin/success");
    }
}
