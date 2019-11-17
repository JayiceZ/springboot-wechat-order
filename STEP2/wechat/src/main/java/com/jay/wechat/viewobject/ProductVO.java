package com.jay.wechat.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

//商品（包含类目）
@Data
public class ProductVO {
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private int categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> list;
}
