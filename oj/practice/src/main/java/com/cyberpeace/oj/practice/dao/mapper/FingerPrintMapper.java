package com.cyberpeace.oj.practice.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.FingerPrint;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FingerPrintMapper extends BaseMapper<FingerPrint> {
}
