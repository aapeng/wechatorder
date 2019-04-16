package com.xmcc.wechatorder;

import com.xmcc.wechatorder.common.ResultResponse;
import com.xmcc.wechatorder.dto.ProductCategoryDto;
import com.xmcc.wechatorder.service.ProductCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatorderApplicationTests {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void contextLoads() {

        ResultResponse<List<ProductCategoryDto>> all = productCategoryService.findAll();
        List<ProductCategoryDto> data = all.getData();
        data.stream().forEach(productCategoryDto -> System.out.println(productCategoryDto));
    }

}
