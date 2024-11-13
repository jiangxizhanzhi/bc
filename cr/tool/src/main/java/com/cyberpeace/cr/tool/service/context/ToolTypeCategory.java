package com.cyberpeace.cr.tool.service.context;

import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 工具类型类别
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class ToolTypeCategory {

    /**
     * 类型
     */
    private String type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类别
     */
    private List<WebToolCategoryRsp> categories = Lists.newArrayList();
}
