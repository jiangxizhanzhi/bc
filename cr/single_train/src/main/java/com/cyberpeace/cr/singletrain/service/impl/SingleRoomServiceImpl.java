package com.cyberpeace.cr.singletrain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoomList;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTrainStats;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.singletrain.service.bo.*;
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
import java.util.Map;
import java.util.stream.Collectors;

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
    public ListRsp<SingleRoomListBo> getSingleTrainPageList(SingleTrainListReq queryParams) {

        // 向前7天日期
        List<String> dateRange = Lists.newArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
            dateRange.add(0, dateFormat.format(calendar.getTime()));
        }
        String userId = UserInfoContext.getUserContext().getUserId();

        // 分页查询结果
        IPage<SingleRoomList> selectResult = singleRoomMapper.singleTrainSearch(
                queryParams.toPageParam(),
                userId, queryParams.getPattern(),
                queryParams.getCategory(),
                queryParams.getDifficult()
        );

        // 查询训练使用统计
        List<String> template_ids = selectResult.getRecords().stream().map(
                singleRoomList -> singleRoomList.getBusinessTemplate().getResourceId()
        ).collect(Collectors.toList());
        List<SingleTrainStats> dateStats = singleRoomMapper.singleTrainStats(template_ids, dateRange);
        Map<String, List<Integer>> trainStats = dateStats.stream().collect(
                Collectors.toMap(
                        SingleTrainStats::getBusinessTemplateId,
                        singleTrainStats -> {
                            List<SingleTrainStats> currentTrainHistory = dateStats.stream().filter(
                                    singleTrainStats1 -> singleTrainStats1.getBusinessTemplateId().equals(singleTrainStats.getBusinessTemplateId())
                            ).collect(Collectors.toList());
                            return dateRange.stream().map(date -> {
                                Integer count = 0;
                                for (SingleTrainStats dailyTrainStats : currentTrainHistory) {
                                    if (dailyTrainStats.getDate().equals(date)) {
                                        count = dailyTrainStats.getCount();
                                        break;
                                    }
                                }
                                return count;
                            }).collect(Collectors.toList());
                        },
                        (key1, key2) -> key1
                )
        );

        // 拓展额外数据
        List<SingleRoomListBo> singleRoomListBoList = Lists.newArrayList();
        for (SingleRoomList item : selectResult.getRecords()) {
            SingleRoomListBo singleRoomListBo = new SingleRoomListBo();
            singleRoomListBo.setRoomInfo(new SingleRoomInfoBo());
            singleRoomListBo.setEventData(new EventDataBo());

            BeanUtils.copyProperties(item.getSingleTemplate(), singleRoomListBo.getEventData());
            BeanUtils.copyProperties(item.getBusinessTemplate(), singleRoomListBo.getEventData());
            if (item.getSingleRoom() != null) {
                BeanUtils.copyProperties(item.getSingleRoom(), singleRoomListBo.getRoomInfo());
            }

            singleRoomListBo.setResourceId(item.getBusinessTemplate().getResourceId());
            singleRoomListBo.getEventData().setPreSkill(JsonUtil.toJsonObjectArray(item.getSingleTemplate().getPreSkills(), String.class));
            singleRoomListBo.getEventData().setTrainSkill(JsonUtil.toJsonObjectArray(item.getSingleTemplate().getTrainSkills(), String.class));
            singleRoomListBo.setMissionScheme(JsonUtil.toJsonObject(item.getMissionScheme(), JSONObject.class));

            singleRoomListBo.setLastSevenDays(dateRange);
            singleRoomListBo.setTemplateUseCount(trainStats.getOrDefault(singleRoomListBo.getResourceId(), Lists.newArrayList(0, 0, 0, 0, 0, 0, 0)));

            singleRoomListBoList.add(singleRoomListBo);
        }

        // 生成分页结构
        ListRsp<SingleRoomListBo> result = new ListRsp<>();
        result.setList(singleRoomListBoList);
        result.setTotal(selectResult.getTotal());
        result.setLastPage(selectResult.getPages());
        result.setCurrentPage(selectResult.getCurrent());

        return result;
    }
}
