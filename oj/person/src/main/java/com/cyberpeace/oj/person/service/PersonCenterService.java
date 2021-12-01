package com.cyberpeace.oj.person.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.person.dao.entity.*;

import java.util.Date;

public interface PersonCenterService {

    /**
     * 分页查询用户所有的评论信息
     * @param page
     * @param userId 用户ID
     * @return
     */
    IPage<PersonComment> pageComment(Page page, String userId);


    /**
     * 分页查询用户所有的回复
     * @param page
     * @param userId 用户ID
     * @return
     */
    IPage<PersonComment> pageReplies(Page page, String userId);

    /**
     * 分页查询用户所有笔记
     * @param page
     * @param userId 用户ID
     * @return
     */
    IPage<PersonNote> pageNotes(Page page, String userId);

    /**
     * 分页查询用户所有收藏
     * @param page
     * @param userId 用户ID
     * @return
     */
    IPage<PersonCollection> pageCollections(Page page, String userId, String type);

    /**
     * 查询用户的学习课程与训练的记录
     * @param userId 用户ID
     * @return
     */
    BaseRsp<StudyRecord> studyRecords(String userId);

    /**
     * 查询30天内用户的学习课程与训练的记录
     * @param userId 用户ID
     * @param startTime
     * @param endTime
     * @return
     */
    BaseRsp<BrokenLine> studyPeriodRecords(String userId, Date startTime, Date endTime);
}
