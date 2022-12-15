package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:47
 */
@Api(tags = "图标类型")
@RestController
@RequestMapping("/situation/icon/category")
public class SituationIconCategoryController {

    public BaseRsp<List<SituationIconCategory>> getSituationCategoryList(){
        return new BaseRsp<>();
    }
}
