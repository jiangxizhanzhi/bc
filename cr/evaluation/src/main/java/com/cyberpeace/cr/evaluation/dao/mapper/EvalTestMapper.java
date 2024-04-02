package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestListReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EvalTestMapper extends BaseMapper<EvalTest> {

	IPage<EvalTest> testPage(Page<EvalTest> page, @Param("listQuery") EvalTestListReq evalTestListReq, @Param("userId") String userId);

	String selectTopoIdByTestId(@Param("testId") String testId);

}