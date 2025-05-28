/**
 * 该代码定义了一个名为 `TheoryKService` 的接口，位于 `com.cyberpeace.space.service` 包中。以下是对其功能的简要分析： 
 * 1. **接口描述**： 
 * - 接口名为 `TheoryKService`，用于提供与理论K相关的服务。 
 * - 注释说明了其用途，并标注了作者、版本和日期。 
 * 2. **方法分析**： 
 * - **`pageK(ListKReq req)`**： 
 * - 功能：根据请求参数 `req` 分页查询管理课时列表。 
 * - 返回值：`ListRsp<AdminLessonListBo>`，即包含分页结果的响应对象。 
 * - 参数：`ListKReq`，表示请求体。 
 * - **`deleteBatch(List<String> ids)`**： 
 * - 功能：批量删除指定 ID 列表中的记录。 
 * - 参数：`List<String> ids`，表示需要删除的资源 ID 列表。 
 * - **`getDetail(String resourceId)`**： 
 * - 功能：获取指定资源 ID 的详细信息。 
 * - 返回值：`AdminLessonBo`，表示详细的课程信息。 
 * - 参数：`String resourceId`，表示资源 ID。 
 * 3. **依赖类**： 
 * - 使用了多个外部类和模型： 
 * - `ListRsp<T>`：通用的分页响应类。 
 * - `AdminLessonBo` 和 `AdminLessonListBo`：分别表示课程详情和课程列表的业务对象。 
 * - `ListKReq`：请求参数类。 
 * 4. **适用场景**： 
 * - 该接口适用于管理课时相关的操作，包括分页查询、批量删除和单条详情获取。 
 * 总结：`TheoryKService` 是一个面向理论 K 的服务接口，提供了课时管理的核心功能，包括分页查询、批量删除和详情获取，是课程管理系统的重要组成部分。 
**/
package com.cyberpeace.space.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.space.service.context.ListKReq;

import java.util.List;

/**
 * 理论KSERVICE 服务
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
public interface TheoryKService {
    /**
     * pagek
     *
     * @param req 请求体
     * @return 列表RSP<管理课时列表博>
     * @throws
     * @see ListKReq
     * @see ListRsp<AdminLessonListBo>
     */
    ListRsp<AdminLessonListBo> pageK(ListKReq req);

    /**
     * 删除批量
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    void deleteBatch(List<String> ids);

    /**
     * 获取详情
     *
     * @param resourceId 资源id
     * @return 管理员课程bo
     * @throws
     * @see String
     * @see AdminLessonBo
     */
    AdminLessonBo getDetail(String resourceId);
}
