package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.TrainComment;

import java.util.List;

public interface TrainCommentService extends IService<TrainComment> {
    /**
     * 提交评论
     * @param templateId 训练模板id {@link TrainComment#templateId}
     * @param businessType 业务类型 {@link TrainComment#businessType}
     * @param content 评论内容 {@link TrainComment#content}
     * @return entity object
     */
    TrainComment postComment(String templateId, Integer businessType, String content);

    List<TrainComment> fullList(String templateId);
}
