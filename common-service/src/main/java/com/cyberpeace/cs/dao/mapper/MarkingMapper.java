package com.cyberpeace.cs.dao.mapper;

import com.cyberpeace.cs.dao.entity.Marking;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity com.cyberpeace.cs.dao.entity.Marking
 */
@Mapper
public interface MarkingMapper extends BaseMapper<Marking> {

    void deleteByUserIdAndBusinessId(String userId, String businessId);

    List<Marking> getByBusinessId(String businessId);
}




