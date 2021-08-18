package com.cyberpeace.oj.common.model;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

@ApiModel
public class PageReqBase2<T> implements Serializable {

    @ApiModelProperty(name = "per_page", value = "每页大小", required = true)
    @Getter
    private Integer perPage;

    @ApiModelProperty(value = "页号，从1开始", required = true)
    @Getter
    @Setter
    private Integer page;

    @ApiModelProperty(value = "排序参数")
    @Getter
    @Setter
    private String sorter;

    public Page<T> toPageParam() {
        boolean asc = true;
        if (StringUtils.startsWith(sorter, "-")) {
            sorter = StringUtils.substring(sorter, 1);
            asc = false;
        }

        Page<T> page = new Page<T>(this.page, this.perPage);

        // 设置排序字段
        OrderItem item = new OrderItem(sorter, asc);
        List<OrderItem> orders = Lists.newArrayList();
        orders.add(item);
        page.setOrders(orders);

        return page;
    }

    public void setPer_page(Integer perPage) {
        this.perPage = perPage;
    }
}
