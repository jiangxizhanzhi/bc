package com.cyberpeace.oj.ojbase.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.controller.req.ReportAddReq;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;
import com.cyberpeace.oj.ojbase.service.bo.NoteListBo;
import com.cyberpeace.oj.ojbase.service.context.AdminNoteListQuery;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;

import java.util.List;

/**
 * 注意Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `NoteService` 的接口，该接口继承了 `com.baomidou.mybatisplus.extension.service.IService<Note>`。这个接口提供了处理笔记（Note）相关操作的服务方法。以下是每个方法的简要分析： 
 * 1. **`NoteBo selectByUnique(NoteUniqueContext uniqueContext)`** 
 * - **功能**: 根据唯一条件查询笔记信息。 
 * - **参数**: `uniqueContext` - 唯一条件上下文对象。 
 * - **返回值**: 笔记业务对象 `NoteBo`。 
 * 2. **`Note addNote(NoteAddContext noteAddContext)`** 
 * - **功能**: 新增笔记信息。 
 * - **参数**: `noteAddContext` - 笔记添加上下文对象。 
 * - **返回值**: 添加后的笔记对象 `Note`。 
 * 3. **`IPage<NoteListBo> pageByUserNameAndContent(Page page, AdminNoteListQuery noteListQuery, Boolean isWeb)`** 
 * - **功能**: 分页查询笔记信息。 
 * - **参数**: 
 * - `page` - 分页对象。 
 * - `noteListQuery` - 管理员笔记列表查询对象。 
 * - `isWeb` - 是否是 Web 查询。 
 * - **返回值**: 符合条件的分页笔记列表 `IPage<NoteListBo>`。 
 * 4. **`Note addAndUpdateReport(ReportAddReq reportAddReq)`** 
 * - **功能**: 保存和更新实验报告。 
 * - **参数**: `reportAddReq` - 报告添加请求对象。 
 * - **返回值**: 更新后的笔记对象 `Note`。 
 * 5. **`Integer getCount(String userId, Integer noteType, List<String> resourceType)`** 
 * - **功能**: 获取指定用户、类型和资源类型的笔记数量。 
 * - **参数**: 
 * - `userId` - 用户ID。 
 * - `noteType` - 笔记类型。 
 * - `resourceType` - 资源类型列表。 
 * - **返回值**: 笔记数量 `Integer`。 
 * 6. **`void deleteByBusiness(String businessId, Integer noteType, Integer businessType)`** 
 * - **功能**: 根据业务ID、注释类型和业务类型删除笔记。 
 * - **参数**: 
 * - `businessId` - 业务ID。 
 * - `noteType` - 注释类型。 
 * - `businessType` - 业务类型。 
 * - **返回值**: 无。 
 * ### 总结 
 * - `NoteService` 接口提供了多种操作笔记的方法，包括查询、新增、分页查询、保存更新实验报告、获取笔记数量以及根据业务ID删除笔记。 
 * - 这些方法使用了多个自定义的上下文对象和请求对象来传递参数，以确保操作的灵活性和可扩展性。 
 * - 接口继承了 `IService<Note>`，这表明它利用了 MyBatis-Plus 框架提供的基础服务功能，并在此基础上进行了扩展。 
**/
public interface NoteService extends IService<Note> {

    /**
     * 根据唯一条件查询笔记信息
     *
     * @param uniqueContext
     * @return
     * @throws
     * @see NoteUniqueContext
     * @see NoteBo
     */
    NoteBo selectByUnique(NoteUniqueContext uniqueContext);

    /**
     * 笔记信息新增
     *
     * @param noteAddContext
     * @return
     * @throws
     * @see NoteAddContext
     * @see Note
     */
    Note addNote(NoteAddContext noteAddContext);

    /**
     * 分页查询笔记信息
     *
     * @param page
     * @param noteListQuery
     * @param isWeb         是 Web
     * @return
     * @throws
     * @see Page
     * @see AdminNoteListQuery
     * @see Boolean
     * @see IPage<NoteListBo>
     */
    IPage<NoteListBo> pageByUserNameAndContent(Page page, AdminNoteListQuery noteListQuery, Boolean isWeb);

    /**
     * 实验报告保存和更新
     *
     * @param reportAddReq
     * @return
     * @throws
     * @see ReportAddReq
     * @see Note
     */
    Note addAndUpdateReport(ReportAddReq reportAddReq);

    /**
     * 获取计数
     *
     * @param userId       用户id
     * @param noteType     注释类型
     * @param resourceType 资源类型
     * @return {@code Integer }
     * @throws
     * @see String
     * @see Integer
     * @see List<String>
     * @see Integer
     */
    Integer getCount(String userId, Integer noteType, List<String> resourceType);

    /**
     * 删除由业务
     *
     * @param businessId   业务id
     * @param noteType     注释类型
     * @param businessType 业务类型
     * @throws
     * @see String
     * @see Integer
     * @see Integer
     */
    void deleteByBusiness(String businessId, Integer noteType, Integer businessType);
}

