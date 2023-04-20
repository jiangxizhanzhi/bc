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

public interface NoteService extends IService<Note> {

    /**
     * 根据唯一条件查询笔记信息
     * @param uniqueContext
     * @return
     */
    NoteBo selectByUnique(NoteUniqueContext uniqueContext);

    /**
     * 笔记信息新增
     * @param noteAddContext
     * @return
     */
    Note addNote(NoteAddContext noteAddContext);

    /**
     * 分页查询笔记信息
     * @param page
     * @param noteListQuery
     * @return
     */
    IPage<NoteListBo> pageByUserNameAndContent(Page page, AdminNoteListQuery noteListQuery, Boolean isWeb);

    Note addAndUpdateReport(ReportAddReq reportAddReq);
}
