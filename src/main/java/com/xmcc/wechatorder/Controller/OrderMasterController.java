package com.xmcc.wechatorder.Controller;

import com.google.common.collect.Maps;
import com.xmcc.wechatorder.common.ResultResponse;
import com.xmcc.wechatorder.dto.OrderMasterDto;
import com.xmcc.wechatorder.service.OrderMasterService;
import com.xmcc.wechatorder.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/order")
@Api(value = "订单相关接口",description = "完成订单的增删改查")
public class OrderMasterController {

   @Autowired
    private OrderMasterService orderMasterService;

    /*
     * @Valid：配合DTO上的JSR303注解完成校验
     * BindingResult：配合@Valid，用于处理前台传入的参数信息
     * 注意：JSR303的注解默认是在Contorller层进行校验
     * 如果想在service层进行校验 需要使用javax.validation.Validator  也就是上个项目用到的工具
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建订单接口", httpMethod = "POST", response = ResultResponse.class)
    public ResultResponse create(@Valid @ApiParam(name="订单对象",value = "传入json格式",required = true)
                    OrderMasterDto orderMasterDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){//判断参数是否有误
            List<String> errList = bindingResult.getFieldErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
            Map<String,String> map = Maps.newHashMap();
            map.put("参数校验错误", JsonUtil.object2string(errList));
            return ResultResponse.fail(map);//将参数校验的错误信息返回给前台
       }
        return orderMasterService.create(orderMasterDto);
    }

    @GetMapping("/list")
    @ApiOperation(value = "订单列表接口", httpMethod = "GET", response = ResultResponse.class)
    public ResultResponse list(String openid,Integer page,Integer size){
        return orderMasterService.list(openid, page, size);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "订单详情接口", httpMethod = "GET", response = ResultResponse.class)
    public ResultResponse detail(String openid,String orderId){
        return orderMasterService.detail(openid,orderId);
    }

    @PostMapping("/cancel")
    @ApiOperation(value = "取消接口", httpMethod = "POST", response = ResultResponse.class)
    public ResultResponse cancel(String openid,String orderId){
        return orderMasterService.cancel(openid,orderId);
    }
}