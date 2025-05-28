/**
 * 这段代码是一个单元测试类，用于测试 `SpaceAlgorithmCommonController` 控制器中的 `getStatistics` 方法。以下是代码的主要分析： 
 * 1. **测试框架**： 
 * - 使用了 JUnit 5 的 `@Test` 注解来定义测试方法。 
 * - `@WebMvcTest` 注解用于加载仅包含 Web 层的 Spring 上下文，专注于测试控制器。 
 * 2. **依赖注入**： 
 * - `@Autowired` 注入了 `MockMvc` 对象，用于模拟 HTTP 请求。 
 * - `@MockBean` 创建了服务层的模拟对象（mock），包括 `SpaceAlgorithmExpertiseService`、`SpaceAlgorithmRoleService`、`SpaceAlgorithmIndividualService` 和 `SpaceAlgorithmTeamService`。 
 * 3. **测试方法**： 
 * - 测试方法名为 `testGetStatistics`。 
 * - 使用 `when(...).thenReturn(...)` 配置模拟服务的行为，返回预设的统计值（均为 0）。 
 * - 调用 `mockMvc.perform()` 模拟 GET 请求 `/space/algorithm/common/statistics`，并验证响应状态码为 `200 OK`，以及 JSON 响应内容是否符合预期。 
 * 4. **断言**： 
 * - 使用 `content().json()` 验证返回的 JSON 数据结构和内容是否正确。 
 * - 预期返回的 JSON 包含统计数据（均为 0）、固定的状态码 `"OJ-000000"`，以及其他字段为空。 
 * 总结：该测试通过模拟服务层行为，验证了 `getStatistics` 方法在特定输入下的输出是否符合预期。 
**/
package com.cyberpeace.space.controller;

import com.cyberpeace.space.service.SpaceAlgorithmExpertiseService;
import com.cyberpeace.space.service.SpaceAlgorithmIndividualService;
import com.cyberpeace.space.service.SpaceAlgorithmRoleService;
import com.cyberpeace.space.service.SpaceAlgorithmTeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceAlgorithmCommonController.class)
class SpaceAlgorithmCommonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceAlgorithmExpertiseService mockExpertiseService;
    @MockBean
    private SpaceAlgorithmRoleService mockRoleService;
    @MockBean
    private SpaceAlgorithmIndividualService mockIndividualService;
    @MockBean
    private SpaceAlgorithmTeamService mockTeamService;

    @Test
    void testGetStatistics() throws Exception {
        // Setup
        when(mockExpertiseService.count()).thenReturn(0);
        when(mockRoleService.count()).thenReturn(0);
        when(mockIndividualService.count()).thenReturn(0);
        when(mockTeamService.count()).thenReturn(0);

        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/common/statistics")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"expertise\":0,\"role\":0,\"individual\":0,\"team\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
