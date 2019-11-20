package com.jay.wechat.bean;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Transient;
import java.util.Date;

@Alias("ProductCategory")
@Data
public class ProductCategory {
    private int category_id;

    //类目名字
    private String category_name;

    //类目编号
    private int category_type;

    @Transient
    private Date create_time;

    @Transient
    private Date update_time;

    public ProductCategory(String category_name, int category_type) {
        this.category_name = category_name;
        this.category_type = category_type;
    }

    public ProductCategory() {
    }
}

