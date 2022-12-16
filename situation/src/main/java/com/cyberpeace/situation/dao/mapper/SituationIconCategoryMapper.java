package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author xuxz
 * @Date 15/12/2022 下午12:00
 */
@Mapper
public interface SituationIconCategoryMapper extends BaseMapper<SituationIconCategory> {
    /**
     * 获取全部图标
     * @return
     */
    List<SituationIconCategory> selectAll();
}
