package com.cyberpeace.cs.service;

import com.cyberpeace.cs.controller.res.MarkScore;
import com.cyberpeace.cs.dao.entity.Marking;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface MarkingService extends IService<Marking> {

    void mark(String userId, String courseId, Integer score);

    MarkScore getMarkScoreByUserIdAndBusinessId(String userId, String resourceId);
}
