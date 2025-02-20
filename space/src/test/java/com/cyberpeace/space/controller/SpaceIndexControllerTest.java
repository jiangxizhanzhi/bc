
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
