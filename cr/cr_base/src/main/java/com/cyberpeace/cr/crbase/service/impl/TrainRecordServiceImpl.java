package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TrainRecordServiceImpl extends ServiceImpl<TrainRecordMapper, TrainRecord> implements TrainRecordService {

    @Autowired
    private TrainRecordMapper trainRecordMapper;

    @Override
    public void saveRecord(String businessTemplateId, String userId, Integer businessType) {

        TrainRecord trainRecord = new TrainRecord();
        trainRecord.setBusinessTemplateId(businessTemplateId);
        trainRecord.setUserId(userId);
        trainRecord.setBusinessType(businessType);
        trainRecord.setStartTime(new Date());

        save(trainRecord);
    }

    @Override
    public void updateEndDate(String businessTemplateId, String userId, Integer businessType) {
        trainRecordMapper.setEndTime(businessTemplateId, userId, businessType, new Date());
    }

}
