package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryInfoMapper {

    List<CategoryInfo> selectByType(Integer type);

}
