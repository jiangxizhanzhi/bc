package com.cyberpeace.cr.tool.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Web工具类别Controller
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个Spring MVC控制器类 `WebToolCategoryController`，用于处理与Web工具类别相关的HTTP请求。以下是详细的分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.cr.tool.controller.web` 
 * - **导入**： 
 * - `com.cyberpeace.common.constant.MenuConstant`：常量类，可能包含权限控制相关的常量。 
 * - `com.cyberpeace.common.model.BaseRsp`：基础响应类，用于封装API响应结果。 
 * - `com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp`：工具类别响应类，用于表示工具类别的响应数据。 
 * - `com.cyberpeace.cr.tool.service.ToolCategoryService`：工具类别服务接口，提供业务逻辑操作。 
 * - `io.swagger.annotations.Api`, `io.swagger.annotations.ApiOperation`, `io.swagger.annotations.ApiParam`：Swagger注解，用于生成API文档。 
 * - `org.apache.shiro.authz.annotation.RequiresAuthentication`, `org.apache.shiro.authz.annotation.RequiresPermissions`：Apache Shiro注解，用于权限控制。 
 * - `org.springframework.beans.factory.annotation.Autowired`：Spring注解，用于自动注入依赖。 
 * - `org.springframework.web.bind.annotation.GetMapping`, `org.springframework.web.bind.annotation.RequestMapping`, `org.springframework.web.bind.annotation.RestController`：Spring MVC注解，用于定义RESTful API。 
 * ### 类定义 
 * - **类名**：`WebToolCategoryController` 
 * - **注解**： 
 * - `@RestController`：表示这是一个RESTful风格的控制器。 
 * - `@RequestMapping("cr/tool/category")`：定义该控制器的基本路径。 
 * - `@RequiresAuthentication`：表示访问该控制器的方法需要用户已认证。 
 * ### 成员变量 
 * - **成员变量**：`private ToolCategoryService toolCategoryService;` 
 * - **注解**：`@Autowired`：自动注入`ToolCategoryService`实例。 
 * ### 方法 
 * - **方法名**：`getList` 
 * - **注解**： 
 * - `@GetMapping("/list")`：定义该方法处理GET请求，路径为`/cr/tool/category/list`。 
 * - `@ApiOperation("工具分类列表")`：描述该方法的用途。 
 * - `@RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)`：表示访问该方法需要特定权限。 
 * - **参数**： 
 * - `@ApiParam(name = "type", value = "工具类别") String type`：表示请求参数`type`，用于指定工具类别。 
 * - **返回值**：`BaseRsp<List<WebToolCategoryRsp>>`，表示返回一个包含工具类别列表的响应对象。 
 * - **功能**：调用`toolCategoryService.webSelectAll(type)`方法获取工具类别列表，并将其封装到`BaseRsp`对象中返回。 
 * ### 总结 
 * - **功能**：该控制器主要负责处理与Web工具类别相关的HTTP GET请求，返回工具类别列表。 
 * - **权限控制**：使用了Shiro的注解进行认证和权限控制。 
 * - **文档生成**：使用了Swagger注解来生成API文档。 
 * - **依赖注入**：使用Spring的`@Autowired`注解实现依赖注入。 
**/
@Api(tags = "前台-资源管理-工具库-分类管理")
@RestController
@RequestMapping("cr/tool/category")
@RequiresAuthentication
public class WebToolCategoryController {

    /**
     * 工具类别Service
     */
    @Autowired
    private ToolCategoryService toolCategoryService;

    /**
     * 获取列表
     *
     * @param type 类型
     * @return {@code BaseRsp<List<WebToolCategoryRsp>> }
     */
    @GetMapping("/list")
    @ApiOperation("工具分类列表")
    @RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
    public BaseRsp<List<WebToolCategoryRsp>> getList(
            @ApiParam(name = "type", value = "工具类别") String type) {
        List<WebToolCategoryRsp> webToolCategoryRsps = toolCategoryService.webSelectAll(type);

        return new BaseRsp<>(webToolCategoryRsps);
    }

}

