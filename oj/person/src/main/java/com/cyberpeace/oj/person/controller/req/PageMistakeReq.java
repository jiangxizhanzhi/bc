package com.cyberpeace.oj.person.controller.req;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageMistakeReq extends PageReqBase {
    private Integer sortByIncorrect = 0;
    private Integer sortByCorrect = 0;

}
