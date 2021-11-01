package com.cyberpeace.oj.ojbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;
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
     * 笔记信息修改
     * @param context
     * @return
     */
    Note updateNote(NoteAddContext context);
}
