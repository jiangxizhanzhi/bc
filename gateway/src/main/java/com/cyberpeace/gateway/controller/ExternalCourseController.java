/**
 * 这段代码定义了一个名为 `ExternalCourseController` 的 Spring MVC 控制器类，用于处理与课程相关的 HTTP GET 请求。以下是代码的详细分析： 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.gateway.controller; 
 * ``` 
 * 该控制器位于 `com.cyberpeace.gateway.controller` 包中。 
 * 2. **导入语句**： 
 * ```java 
 * import com.cyberpeace.common.model.BaseRsp; 
 * import io.swagger.annotations.Api; 
 * import io.swagger.annotations.ApiOperation; 
 * import lombok.extern.slf4j.Slf4j; 
 * import org.springframework.web.bind.annotation.GetMapping; 
 * import org.springframework.web.bind.annotation.RequestMapping; 
 * import org.springframework.web.bind.annotation.RestController; 
 * ``` 
 * 这些导入语句引入了所需的类和注解： 
 * - `BaseRsp`：用于封装响应数据。 
 * - `Api` 和 `ApiOperation`：用于 Swagger 文档生成。 
 * - `Slf4j`：用于日志记录。 
 * - `GetMapping`、`RequestMapping` 和 `RestController`：用于定义 RESTful API。 
 * 3. **类声明**： 
 * ```java 
 * @Api(tags = "外部接口-课程课时api") 
 * @Slf4j 
 * @RestController 
 * @RequestMapping("/gateway/course") 
 * public class ExternalCourseController { 
 * ``` 
 * - `@Api` 注解用于描述这个控制器的用途。 
 * - `@Slf4j` 注解用于自动创建一个 SLF4J 日志记录器。 
 * - `@RestController` 注解表明这是一个 RESTful 控制器。 
 * - `@RequestMapping` 注解指定了所有请求的公共路径前缀 `/gateway/course`。 
 * 4. **方法定义**： 
 * - `getCourseList()` 方法： 
 * ```java 
 * @ApiOperation("课程列表") 
 * @GetMapping("/page") 
 * public BaseRsp getCourseList(){ 
 * return new BaseRsp(); 
 * } 
 * ``` 
 * - 该方法用于获取课程列表。 
 * - `@GetMapping("/page")` 指定了请求路径为 `/gateway/course/page`。 
 * - 返回一个新的 `BaseRsp` 对象。 
 * - `getCourseDetail()` 方法： 
 * ```java 
 * @ApiOperation("课程详情") 
 * @GetMapping("/") 
 * public BaseRsp getCourseDetail(){ 
 * return new BaseRsp(); 
 * } 
 * ``` 
 * - 该方法用于获取课程详情。 
 * - `@GetMapping("/")` 指定了请求路径为 `/gateway/course/`。 
 * - 返回一个新的 `BaseRsp` 对象。 
 * - `getLessonDetail()` 方法： 
 * ```java 
 * @ApiOperation("小节详情") 
 * @GetMapping("/lesson") 
 * public BaseRsp getLessonDetail(){ 
 * return new BaseRsp(); 
 * } 
 * ``` 
 * - 该方法用于获取小节详情。 
 * - `@GetMapping("/lesson")` 指定了请求路径为 `/gateway/course/lesson`。 
 * - 返回一个新的 `BaseRsp` 对象。 
 * ### 总结 
 * - 该控制器主要用于处理与课程相关的 GET 请求。 
 * - 控制器定义了三个方法，分别用于获取课程列表、课程详情和小节详情。 
 * - 每个方法都返回一个 `BaseRsp` 对象，但目前这些方法只是简单地返回一个空的 `BaseRsp` 对象。 
 * - 使用 Swagger 注解来生成 API 文档。 
 * - 日志记录功能通过 Lombok 的 `@Slf4j` 注解实现。 
**/
package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.model.BaseRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "外部接口-课程课时api")
@Slf4j
@RestController
@RequestMapping("/gateway/course")
public class ExternalCourseController {

    @ApiOperation("课程列表")
    @GetMapping("/page")
    public BaseRsp getCourseList(){

        return new BaseRsp();
    }

    @ApiOperation("课程详情")
    @GetMapping("/")
    public BaseRsp getCourseDetail(){

        return new BaseRsp();
    }

    @ApiOperation("小节详情")
    @GetMapping("/lesson")
    public BaseRsp getLessonDetail(){

        return new BaseRsp();
    }
}
