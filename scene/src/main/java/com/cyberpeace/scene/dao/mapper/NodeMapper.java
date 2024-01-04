package com.cyberpeace.scene.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/28 11:20
 * @desc :
 **/
@Mapper
public interface NodeMapper extends BaseMapper<NodeEntity> {

    List<NodeEntity> selectBySid(@Param("sid") String sid);
}
