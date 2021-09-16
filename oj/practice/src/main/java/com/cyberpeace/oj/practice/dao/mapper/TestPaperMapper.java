package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.dao.entity.TestPaper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestPaperMapper extends BaseMapper<TestPaper> {

    ListRsp<TestPaper> getList();

    Integer batchDelete();
}
