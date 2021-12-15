package com.cyberpeace.oj.course.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassroomGroupInfoServiceImpl extends ServiceImpl<ClassroomGroupInfoMapper, ClassroomGroupInfo> implements ClassroomGroupInfoService {

    @Override
    public LocalScene selectByLessonAndUser(String lessonId, AuthContext authContext) {
        return null;
    }
}
