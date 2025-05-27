package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpaceTacticDefenseMapper extends BaseMapper<SpaceTacticDefense> {
    List<String> idsByCategoryids(List<String> ids);
}
