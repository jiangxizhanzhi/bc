package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.dao.entity.TrainComment;
import com.cyberpeace.cr.crbase.dao.mapper.TrainCommentMapper;
import com.cyberpeace.cr.crbase.service.TrainCommentService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TrainCommentServiceImpl extends ServiceImpl<TrainCommentMapper, TrainComment> implements TrainCommentService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public TrainComment postComment(String trainId, Integer businessType, String content) {
        TrainComment trainComment = TrainComment.builder()
                .trainId(trainId)
                .businessType(businessType)
                .content(content)
                .userId(UserInfoContext.getUserContext().getUserId()).build();

        return save(trainComment) ? getById(trainComment.getResourceId()) : null;
    }

    @Override
    public List<TrainComment> fullList(String trainId) {
        List<TrainComment> trainComments = lambdaQuery().eq(TrainComment::getTrainId, trainId).orderByDesc(BaseTable::getCreateTime).list();
        Map<String, String> usernameMap = remoteUserService.getIdNameMap(trainComments.stream().map(TrainComment::getUserId).collect(Collectors.toList()));

        trainComments.forEach(trainComment -> trainComment.setUsername(usernameMap.get(trainComment.getUserId())));
        return trainComments;
    }
}
