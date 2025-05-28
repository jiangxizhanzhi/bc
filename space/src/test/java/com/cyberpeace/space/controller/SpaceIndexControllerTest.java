/**
 * 这段代码是一个单元测试类，用于测试 `SpaceIndexController` 控制器的功能。以下是代码的主要内容和功能分析： 
 * 1. **测试类结构**： 
 * - 类名为 `SpaceIndexControllerTest`，继承自 `WebMvcTest(SpaceIndexController.class)`。 
 * - 使用了 Spring Boot 的测试框架，专注于 Web 层的测试。 
 * 2. **依赖注入**： 
 * - `@Autowired` 注入了 `MockMvc` 对象，用于模拟 HTTP 请求。 
 * - `@MockBean` 模拟了 `SpaceIndexService` 服务层对象，用于替代实际的服务实现。 
 * 3. **测试方法**： 
 * - 测试了四个不同的 API 接口： 
 * - `/space/index/knowledge`：测试获取知识库统计信息。 
 * - `/space/index/tool`：测试获取工具统计信息。 
 * - `/space/index/frame`：测试获取框架统计信息。 
 * - `/space/index/ai`：测试获取 AI 相关的 URL。 
 * 4. **测试逻辑**： 
 * - 每个测试方法都设置了服务层的返回值（使用 `when` 方法）。 
 * - 使用 `mockMvc.perform` 模拟 HTTP GET 请求，并验证响应状态码、内容和 JSON 格式是否符合预期。 
 * 5. **JSON 验证**： 
 * - 使用 `content().json()` 方法验证返回的 JSON 数据是否与预期一致。 
 * - 参数 `true` 表示允许 JSON 的顺序不完全匹配。 
 * 6. **总结**： 
 * - 这段代码通过 Mock 技术实现了对控制器层的单元测试，确保每个接口的返回数据和状态码符合预期。 
 * - 测试覆盖了多个业务场景，包括知识库、工具、框架和 AI 相关的功能。 
**/

package com.cyberpeace.space.controller;

import com.cyberpeace.space.service.SpaceIndexService;
import com.cyberpeace.space.service.context.index.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpaceIndexController.class)
class SpaceIndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SpaceIndexService mockIndexService;

	@Test
	void testGetKRepository() throws Exception {
		// Setup
		// Configure SpaceIndexService.getKnowledgeRepositoryStatistics(...).
		final IndexKnowledge indexKnowledge = new IndexKnowledge();
		final IndexTheory theory = new IndexTheory();
		theory.setTheoryCourse(0);
		theory.setOnlineExp(0);
		theory.setTheoryExam(0);
		theory.setExpExam(0);
		theory.calSum();
		indexKnowledge.setTheory(theory);
		indexKnowledge.setAlgorithm(new IndexAlgorithm());
		indexKnowledge.setRule(new IndexRule());
		indexKnowledge.setTactic(new IndexTactic());
		indexKnowledge.setStrategy(new IndexStrategy());
		when(mockIndexService.getKnowledgeRepositoryStatistics()).thenReturn(indexKnowledge);

		// Run the test and verify the results
		mockMvc.perform(get("/space/index/knowledge").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(
						"{\"data\":{\"theory\":{\"theoryCourse\":0,\"onlineExp\":0,\"theoryExam\":0,\"expExam\":0,\"use\":0,\"sum\":0},\"strategy\":{\"os\":0,\"database\":0,\"middleware\":0,\"switchboard\":0,\"router\":0,\"bigData\":0,\"antiVirus\":0,\"terminal\":0,\"border\":0,\"invasionCheck\":0,\"use\":0,\"sum\":0},\"tactic\":{\"attack\":0,\"defense\":0,\"use\":0,\"sum\":0},\"rule\":{\"base\":0,\"warning\":0,\"threat\":0,\"race\":0,\"sum\":0},\"traffic\":{\"protocol\":0,\"behavior\":0,\"attack\":0,\"sum\":0},\"algorithm\":{\"base\":0,\"ability\":0,\"equipment\":0,\"efficiency\":0,\"sum\":0},\"scene\":{\"ai\":0,\"geek\":0,\"rvb\":0,\"simulation\":0,\"sum\":0},\"sum\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}",
						true));
	}

	@Test
	void testGetTRepository() throws Exception {
		// Setup
		// Configure SpaceIndexService.getToolStatistics(...).
		final IndexTool tool = new IndexTool();
		tool.setProbe(0L);
		tool.setAttack(0L);
		tool.setControl(0L);
		tool.setDefense(0L);
		tool.setSum(0);
		when(mockIndexService.getToolStatistics()).thenReturn(tool);

		// Run the test and verify the results
		mockMvc.perform(get("/space/index/tool").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"probe\":0,\"attack\":0,\"control\":0,\"defense\":0,\"sum\":0,\"platform\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testGetF() throws Exception {
		// Setup
		// Configure SpaceIndexService.getFrameIndex(...).
		final IndexFrame frame = new IndexFrame();
		final IndexFrameCount frameCount = new IndexFrameCount();
		frameCount.setProcess(0);
		frameCount.setTarget(0);
		frameCount.setSum(0);
		frame.setFrameCount(frameCount);
		final IndexActionFrameChart trending = new IndexActionFrameChart();
		frame.setTrending(trending);
		when(mockIndexService.getFrameIndex()).thenReturn(frame);

		// Run the test and verify the results
		mockMvc.perform(get("/space/index/frame").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"frameCount\":{\"process\":0,\"target\":0,\"sum\":0},\"trending\":{\"process\":null,\"target\":[],\"xdates\":[]}},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testGetAiUrl() throws Exception {
		// Setup
		when(mockIndexService.getAiUrl()).thenReturn("data");

		// Run the test and verify the results
		mockMvc.perform(get("/space/index/ai").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":\"data\",\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}
