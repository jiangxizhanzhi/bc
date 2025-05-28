/**
 * 这段代码是一个测试类 `TheoryKnowledgeControllerTest` 的实现，用于测试 `TheoryKnowledgeController` 的功能。以下是对代码的简要分析： 
 * 1. **测试框架**： 
 * - 使用了 JUnit 5 (`org.junit.jupiter`) 作为测试框架。 
 * - `@WebMvcTest` 注解表明这是一个针对 Spring MVC 控制器的单元测试。 
 * 2. **依赖注入**： 
 * - 使用 `@Autowired` 注入 `MockMvc` 对象，用于模拟 HTTP 请求。 
 * - 使用 `@MockBean` 和 `@SpyBean` 注入被测试类的依赖服务（如 `CourseService`, `CourseLessonService`, `TheoryKService` 等），以便在测试中进行行为模拟。 
 * 3. **测试方法**： 
 * - 测试了多个接口的功能，包括获取课程列表、删除课程、获取课程详情、添加课程和修改课程。 
 * - 每个测试方法都通过 `mockMvc.perform()` 模拟 HTTP 请求，并使用 `andExpect()` 验证响应状态码和 JSON 内容是否符合预期。 
 * 4. **具体测试内容**： 
 * - **`testGetLessonList`**：测试获取课程列表接口，验证返回的 JSON 数据是否正确。 
 * - **`testDelete`**：测试删除课程接口，验证删除操作是否成功。 
 * - **`testGetDetail`**：测试获取课程详情接口，验证返回的课程信息是否正确。 
 * - **`testAddK`**：测试添加课程接口，验证课程是否成功添加。 
 * - **`testModify`**：测试修改课程接口，验证课程信息是否成功更新。 
 * 5. **Mock 行为**： 
 * - 使用 Mockito 的 `when()` 方法定义依赖服务的行为，例如 `mockTheoryKService.pageK()` 和 `mockCourseLessonService.addLesson()` 等。 
 * 6. **JSON 校验**： 
 * - 使用 `content().json()` 验证返回的 JSON 响应是否与预期一致，确保接口返回的数据格式正确。 
 * 总体来说，这段代码展示了如何使用 Spring Boot 提供的测试工具和 Mockito 进行控制器层的功能测试，确保各个接口的行为符合预期。 
**/
package com.cyberpeace.space.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.course.controller.req.LessonAddReq;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import com.cyberpeace.space.service.TheoryKService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.ListKReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TheoryKnowledgeController.class)
class TheoryKnowledgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService mockCourseService;
    @MockBean
    private CourseLessonService mockCourseLessonService;
    @MockBean
    private TheoryKService mockTheoryKService;
    @MockBean
    private UtilService mockUtilService;
    @SpyBean
    private TheoryKnowledgeController theoryKnowledgeController;

    @BeforeEach
    void setUp() {
        doReturn(new CourseLesson()).when(theoryKnowledgeController).addReqToEntity(any());
    }

    @Test
    void testGetLessonList() throws Exception {
        // Setup
        // Configure TheoryKService.pageK(...).
        final AdminLessonListBo adminLessonListBo = new AdminLessonListBo();
        adminLessonListBo.setResourceId("resourceId");
        adminLessonListBo.setCourseId("courseId");
        adminLessonListBo.setCourseName("courseName");
        adminLessonListBo.setType(0);
        adminLessonListBo.setName("name");
        final ListRsp<AdminLessonListBo> adminLessonListBoListRsp = new ListRsp<>(Arrays.asList(adminLessonListBo), 0L,
                0L, 0L);
        final ListKReq req = new ListKReq();
        req.setJob(0);
        req.setHandicap(0);
        when(mockTheoryKService.pageK(req)).thenReturn(adminLessonListBoListRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/space/theory/page")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"resourceId\",\"courseId\":\"courseId\",\"courseName\":\"courseName\",\"type\":0,\"name\":\"name\",\"handoutType\":null,\"handoutUrl\":null,\"videoUrl\":null,\"videoDuration\":null,\"difficulty\":null,\"isPublic\":null,\"compulsory\":null,\"duration\":null,\"topoId\":null,\"createUserId\":null,\"createUserName\":null,\"practiceCount\":null,\"paperPublic\":null,\"hasReport\":null,\"lessonFile\":null,\"sceneShareMode\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq req = new IdListReq();
        req.setIds(Arrays.asList("value"));
        mockMvc.perform(delete("/space/theory/")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockTheoryKService).deleteBatch(Arrays.asList("value"));
    }

    @Test
    void testGetDetail() throws Exception {
        // Setup
        // Configure TheoryKService.getDetail(...).
        final AdminLessonBo adminLessonBo = new AdminLessonBo();
        adminLessonBo.setResourceId("resourceId");
        adminLessonBo.setCourseId("courseId");
        adminLessonBo.setName("name");
        adminLessonBo.setType(0);
        adminLessonBo.setHandoutType(0);
        when(mockTheoryKService.getDetail("resource_id")).thenReturn(adminLessonBo);

        // Run the test and verify the results
        mockMvc.perform(get("/space/theory")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"courseId\":\"courseId\",\"name\":\"name\",\"type\":0,\"handoutType\":0,\"handoutUrl\":null,\"handoutName\":null,\"originMarkdownFile\":null,\"videoUrl\":null,\"difficulty\":null,\"isPublic\":null,\"compulsory\":null,\"destroyDelay\":null,\"duration\":null,\"topoId\":null,\"sceneShareMode\":null,\"sceneConfig\":null,\"practiceCount\":null,\"paperId\":null,\"knowledge\":null,\"videoDuration\":null,\"lessonFile\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddK() throws Exception {
        // Setup
        // Configure CourseLessonService.addLesson(...).
        final CourseLesson courseLesson = new CourseLesson();
        courseLesson.setCourseId("default");
        courseLesson.setHandoutUrl("");
        courseLesson.setVideoUrl("");
        courseLesson.setKnowledge("knowledge");
        courseLesson.setCreateUserId("createUserId");
        final CourseLesson courseLesson1 = new CourseLesson();
        courseLesson1.setCourseId("default");
        courseLesson1.setHandoutUrl("");
        courseLesson1.setVideoUrl("");
        courseLesson1.setKnowledge("knowledge");
        courseLesson1.setCreateUserId("createUserId");
        final PaperPracticeContext paperPracticeContext = new PaperPracticeContext();
        paperPracticeContext.setResourceId("resourceId");
        paperPracticeContext.setRefId("refId");
        paperPracticeContext.setPracticeType(0);
        paperPracticeContext.setScore(0.0);
        paperPracticeContext.setOptionRandom(false);
        final List<PaperPracticeContext> practiceList = Arrays.asList(paperPracticeContext);
        when(mockCourseLessonService.addLesson(courseLesson1, new JSONObject(0, false), practiceList))
                .thenReturn(courseLesson);

        LessonAddReq lessonAddReq = new LessonAddReq();
        lessonAddReq.setKnowledge("123432q");

        // Run the test and verify the results
        mockMvc.perform(post("/space/theory")
                        .content(JsonUtil.toJsonString(lessonAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));

    }

    @Test
    void testModify() throws Exception {
        // Setup
        when(mockUtilService.replaceFileUrl("videoUrl")).thenReturn("");

        // Configure CourseLessonService.updateLesson(...).
        final CourseLesson courseLesson = new CourseLesson();
        courseLesson.setCourseId("default");
        courseLesson.setHandoutUrl("");
        courseLesson.setVideoUrl("");
        courseLesson.setKnowledge("knowledge");
        courseLesson.setCreateUserId("createUserId");
        final CourseLesson courseLesson1 = new CourseLesson();
        courseLesson1.setCourseId("default");
        courseLesson1.setHandoutUrl("");
        courseLesson1.setVideoUrl("");
        courseLesson1.setKnowledge("knowledge");
        courseLesson1.setCreateUserId("createUserId");
        final PaperPracticeContext paperPracticeContext = new PaperPracticeContext();
        paperPracticeContext.setResourceId("resourceId");
        paperPracticeContext.setRefId("refId");
        paperPracticeContext.setPracticeType(0);
        paperPracticeContext.setScore(0.0);
        paperPracticeContext.setOptionRandom(false);
        final List<PaperPracticeContext> practiceList = Arrays.asList(paperPracticeContext);
        when(mockCourseLessonService.updateLesson(courseLesson1, new JSONObject(0, false), practiceList))
                .thenReturn(courseLesson);

        LessonAddReq lessonAddReq = new LessonAddReq();
        lessonAddReq.setKnowledge("123432q");
        // Run the test and verify the results
        mockMvc.perform(patch("/space/theory")
                        .content(JsonUtil.toJsonString(lessonAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));

    }
}
