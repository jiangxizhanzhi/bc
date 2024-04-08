package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;
import com.cyberpeace.cr.evaluation.service.context.EvalTestCategoryCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalTestInstanceMapper extends BaseMapper<EvalTestInstance> {

	List<String> selectIdsByTestIdAndStatus(@Param("testId") String testId, @Param("status") Integer status);

	List<EvalTestCategoryCount> countByCategoryAndUserId(@Param("userId") String userId);
}