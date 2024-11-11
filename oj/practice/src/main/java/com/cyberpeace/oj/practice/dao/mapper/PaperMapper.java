package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 试卷Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface PaperMapper extends BaseMapper<Paper> {


}
