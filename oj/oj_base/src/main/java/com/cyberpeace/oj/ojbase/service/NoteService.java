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
 * @date 2024/11/08
 */
public interface NoteService extends IService<Note> {

    /**
     * 根据唯一条件查询笔记信息
     *
     * @param uniqueContext
     * @return
     */
    NoteBo selectByUnique(NoteUniqueContext uniqueContext);

    /**
     * 笔记信息新增
     *
     * @param noteAddContext
     * @return
     */
    Note addNote(NoteAddContext noteAddContext);

    /**
     * 分页查询笔记信息
     *
     * @param page
     * @param noteListQuery
     * @param isWeb         是 Web
     * @return
     */
    IPage<NoteListBo> pageByUserNameAndContent(Page page, AdminNoteListQuery noteListQuery, Boolean isWeb);

    /**
     * 实验报告保存和更新
     *
     * @param reportAddReq
     * @return
     */
    Note addAndUpdateReport(ReportAddReq reportAddReq);

    /**
     * 获取计数
     *
     * @param userId       用户id
     * @param noteType     注释类型
     * @param resourceType 资源类型
     * @return {@code Integer }
     */
    Integer getCount(String userId, Integer noteType, List<String> resourceType);

    /**
     * 删除由业务
     *
     * @param businessId   业务id
     * @param noteType     注释类型
     * @param businessType 业务类型
     */
    void deleteByBusiness(String businessId, Integer noteType, Integer businessType);
}
