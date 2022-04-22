package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.TrainComment;

import java.util.List;

public interface TrainCommentService extends IService<TrainComment> {
    /**
     * 提交评论
     * @param trainId 训练模板id {@link TrainComment#trainId}
     * @param businessType 业务类型 {@link TrainComment#businessType}
     * @param content 评论内容 {@link TrainComment#content}
     * @return entity object
     */
    TrainComment postComment(String trainId, Integer businessType, String content);

    List<TrainComment> fullList(String trainId);
}
