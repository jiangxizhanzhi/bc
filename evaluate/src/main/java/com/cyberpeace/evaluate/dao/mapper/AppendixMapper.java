package com.cyberpeace.evaluate.dao.mapper;

import com.cyberpeace.evaluate.dao.entity.Appendix;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppendixMapper {

    void insert(Appendix appendix);

    Appendix selectById(String appendixId);

}
