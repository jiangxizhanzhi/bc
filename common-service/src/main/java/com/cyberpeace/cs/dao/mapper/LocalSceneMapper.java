package com.cyberpeace.cs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface LocalSceneMapper extends BaseMapper<LocalScene> {

	LocalScene getByBusinessInstanceId(String businessInstanceId);

	/**
	 * 修改销毁时间 空数据也修改
	 * @param localScene
	 */
	void updateEndTime(LocalScene localScene);

	/**
	 * 查询到关闭时间的场景
	 * @param closeTime
	 * @return
	 */
	List<LocalScene> selectByCloseTime(Date closeTime);
}
