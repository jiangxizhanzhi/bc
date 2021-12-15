package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassroomGroupInfoMapper extends BaseMapper<ClassroomGroupInfo> {

    /**
     * 根据课堂编号和用户查询分组信息
     * @param roomId
     * @param userId
     * @return
     */
    ClassroomGroupInfo selectByRoomAndUser(String roomId, String userId);

}
