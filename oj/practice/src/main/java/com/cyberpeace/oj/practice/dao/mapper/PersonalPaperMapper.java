package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PersonalPaper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PersonalPaperMapper extends BaseMapper<PersonalPaper> {

	PersonalPaper getLastPaperByUserId(String userId);

}
