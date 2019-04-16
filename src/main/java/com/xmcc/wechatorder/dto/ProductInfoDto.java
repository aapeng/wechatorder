package com.xmcc.wechatorder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xmcc.wechatorder.entity.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDto implements Serializable {

    @JsonProperty("id")//返回json的属性名跟属性名不一致 我又希望见名知意 用这个注解转换
    private String productId;

    /** 名字. */
    @JsonProperty("name")
    private String productName;

    /** 单价. */
    @JsonProperty("price")
    private BigDecimal productPrice;


    /** 描述. */
    @JsonProperty("description")
    private String productDescription;

    /** 小图. */
    @JsonProperty("icon")
    private String productIcon;

    //一般情况都是根据数据库查询到productInfo来构建这个类
    public static ProductInfoDto build(ProductInfo productInfo){
        ProductInfoDto productInfoDto = new ProductInfoDto();
        BeanUtils.copyProperties(productInfo,productInfoDto);//复制属性
        return productInfoDto;
    }
}
