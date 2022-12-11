package com.cyberpeace.integratation.model.situation.res;

import com.cyberpeace.common.model.ListRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LgPageResBase implements Serializable {

    @ApiModelProperty(value = "当前页数")
    private Integer pageNum;

    @ApiModelProperty(value = "最后页数")
    private Integer pages;

    @ApiModelProperty(value = "总数据量")
    private Integer total;

    public ListRsp convert(){
        ListRsp rsp = new ListRsp<>();
        rsp.setCurrentPage(this.pageNum);
        rsp.setTotal(this.total);
        rsp.setLastPage(this.pages);
        return rsp;
    }
}
