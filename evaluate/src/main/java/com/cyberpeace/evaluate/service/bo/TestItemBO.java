package com.cyberpeace.evaluate.service.bo;

import java.util.List;

import com.cyberpeace.common.model.evaluation.ToolActionModel;
import com.cyberpeace.evaluate.dao.entity.TestItem;
import com.cyberpeace.evaluate.dao.entity.TestItemInput;

import com.cyberpeace.evaluate.service.bo.TestIndexBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestItemBO extends TestItem {

    /**
     * 有运行中的测试实例，填充此项
     */
    private TestItemInput itemData;

    @ApiModelProperty("指标项集合")
    private List<TestIndexBO> indices;

    private ToolActionModel actionModel;
}
