package com.cyberpeace.cr.singletrain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomInfoBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomListBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SingleRoomServiceImpl extends ServiceImpl<SingleRoomMapper, SingleRoom> implements SingleRoomService {

    @Autowired
    private SingleRoomMapper singleRoomMapper;

    @Autowired
    private SingleTemplateService singleTemplateService;

    @Autowired
    private TrainRecordService trainRecordService;

    @Autowired
    private BusinessTemplateService businessTemplateService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private RemoteTopoService remoteTopoService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 创建单兵训练
     *
     * @param businessTemplateId 训练模板id
     * @return 单兵训练房间信息
     */
    @Override
    public SingleRoomBo createRoom(String businessTemplateId) {
        // todo 重复创建
        BusinessTemplate businessTemplateObject = businessTemplateService.getById(businessTemplateId);

        // new topology
        String newTopology = remoteTopoService.copyTopo(businessTemplateObject.getTopoId());

        // copied topology start
        // todo 场景启动 localSceneService.createLocalScene(BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode(), newTopology);

        // save single room data
        String user_id = UserInfoContext.getUserContext().getUserId();
        SingleRoom room = new SingleRoom();
        room.setSingleTemplateId(businessTemplateId);
        room.setStartTime(new Date());
        room.setUserId(user_id);
        room.setCopyedTopoId(newTopology);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                save(room);
                trainRecordService.saveRecord(businessTemplateId, user_id, BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
            }
        });

        return getRoomInfo(room.getSingleTemplateId());
    }

    /**
     * 获取单兵训练房间信息
     *
     * @param businessTemplateId 训练业务模板id
     * @return SingleRoomBo
     */
    @Override
    public SingleRoomBo getRoomInfo(String businessTemplateId) {
        SingleTemplateBo singleTemplateBo = singleTemplateService.get(businessTemplateId);
        SingleRoomBo singleRoomBo = new SingleRoomBo();
        BeanUtils.copyProperties(singleTemplateBo, singleRoomBo);

        // 当前用户使用运行中单兵训练, 无运行直接返回
        SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId, UserInfoContext.getUserContext().getUserId());
        if (singleRoom == null) {
            return singleRoomBo;
        }
        SingleRoomInfoBo singleRoomInfoBo = new SingleRoomInfoBo();
        singleRoomInfoBo.setStartTime(singleRoom.getStartTime());
        singleRoomInfoBo.setTraining_record_data(JsonUtil.toJsonObject(singleRoom.getTrainingRecord(), JSONObject.class));
        singleRoomBo.setRoomInfo(singleRoomInfoBo);

        return singleRoomBo;
    }

    /**
     * 关闭单兵训练房间
     *
     * @param businessTemplateId 训练业务模板id
     */
    @Override
    public Boolean deleteRoom(String businessTemplateId) {
        String userId = UserInfoContext.getUserContext().getUserId();
        SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId, userId);
        if (singleRoom == null) {
            return false;
        }

        // todo 场景关闭

        // 删除单兵训练房间, 记录结束时间
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                singleRoomMapper.deleteById(singleRoom);
                // todo 无效
                trainRecordService.updateEndDate(businessTemplateId, userId, BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
            }
        });
        return true;
    }

    /**
     * 前台单兵训练页面
     *
     * @param queryParams 查询条件
     * @return 单兵训练列表
     */
    @Override
    public IPage<SingleRoomListBo> getSingleTrainPageList(SingleTrainListReq queryParams) {

        // 向前7天日期
        List<String> dateRange = Lists.newArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
            dateRange.add(0, dateFormat.format(calendar.getTime()));
        }
        // train_record
        return null;
    }
}
