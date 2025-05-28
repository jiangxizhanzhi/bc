/**
 * 这段代码是一个单元测试类，用于测试 `SpaceViolationJudgeRuleController` 控制器的功能。以下是代码的主要分析： 
 * 1. **测试框架**： 
 * - 使用了 JUnit 5 (`@Test`, `@BeforeEach`) 和 Spring Boot 的测试注解 (`@WebMvcTest`, `@MockBean`, `@SpyBean`)。 
 * 2. **依赖注入**： 
 * - `mockMvc`：用于模拟 HTTP 请求。 
 * - `mockService`：模拟 `SpaceViolationJudgeRuleService` 服务层。 
 * - `spaceViolationJudgeRuleController`：被测试的控制器实例。 
 * 3. **初始化设置**： 
 * - 在 `setUp()` 方法中，通过 `when` 和 `doReturn` 配置了服务层的返回值，模拟了数据库操作的结果。 
 * - 初始化了 `TableInfoHelper`，用于处理表信息。 
 * 4. **测试方法**： 
 * - `testPage()`：测试分页功能，验证返回的 JSON 数据是否符合预期。 
 * - `testAddAttack()`：测试添加规则的功能。 
 * - `testUpdateA()`：测试更新规则的功能。 
 * - `testDeleteA()`：测试删除规则的功能。 
 * - `testGetADetail()`：测试获取单个规则详情的功能。 
 * 5. **断言**： 
 * - 使用 `andExpect` 验证 HTTP 状态码、响应内容和 JSON 格式。 
 * 6. **数据结构**： 
 * - 使用了 `SpaceViolationJudgeRuleBo`、`IdListReq` 等数据传输对象（DTO）来传递请求参数。 
 * 7. **工具类**： 
 * - 使用了 `JsonUtil` 来将对象转换为 JSON 字符串。 
 * 总结：该测试类全面覆盖了 `SpaceViolationJudgeRuleController` 的主要功能点，确保了控制器的行为与预期一致。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceViolationJudgeRule;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceViolationJudgeRuleService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.rule.SpaceViolationJudgeRuleBo;
import com.google.common.collect.Lists;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceViolationJudgeRuleController.class)
class SpaceViolationJudgeRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private SpaceViolationJudgeRuleController spaceViolationJudgeRuleController;
    @MockBean
    private SpaceViolationJudgeRuleService mockService;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceViolationJudgeRule());
        when(mockService.save(any(SpaceViolationJudgeRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceViolationJudgeRule.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceBaseTable.class);

        doReturn(true).when(spaceViolationJudgeRuleController).checkNameDuplication(any());

    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_violation_judge_rule/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceViolationJudgeRuleBo spaceViolationJudgeRuleBo = new SpaceViolationJudgeRuleBo();
        spaceViolationJudgeRuleBo.setResourceId("resource_id");

        mockMvc.perform(post("/space/rule/space_violation_judge_rule")
                        .content(JsonUtil.toJsonString(spaceViolationJudgeRuleBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceViolationJudgeRuleBo spaceViolationJudgeRuleBo = new SpaceViolationJudgeRuleBo();
        spaceViolationJudgeRuleBo.setResourceId("resource_id");
        mockMvc.perform(patch("/space/rule/space_violation_judge_rule")
                        .content(JsonUtil.toJsonString(spaceViolationJudgeRuleBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testDeleteA() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq req = new IdListReq();
        req.setIds(Arrays.asList("value"));
        mockMvc.perform(delete("/space/rule/space_violation_judge_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_violation_judge_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"interval\":null,\"portCheck\":null,\"targetCheck\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
