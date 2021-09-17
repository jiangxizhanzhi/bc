package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestIndex;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestIndexMapper {

    void insert(TestIndex testIndex);

    void update(TestIndex testIndex);

    List<TestIndex> selectByItemId(String itemId);

    void batchDelete(List<String> ids);

    TestIndex selectByIndexId(String indexId);
}
