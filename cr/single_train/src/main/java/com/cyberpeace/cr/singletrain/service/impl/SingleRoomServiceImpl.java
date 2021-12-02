package com.cyberpeace.cr.singletrain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionBase;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoomList;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTrainStats;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
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
    private SingleTemplateMapper singleTemplateMapper;

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

    @Autowired
    private MissionService missionService;

    /**
     * 创建单兵训练
     *
     * @param businessTemplateId 训练模板id
     * @return 单兵训练房间信息
     */
    @Override
    public SingleRoomBo createRoom(String businessTemplateId) {
        // 已存在运行中训练, 返回
        SingleRoomBo singleRoomBo = getRoomInfo(businessTemplateId);
        if (singleRoomBo != null) {
            return singleRoomBo;
        }

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
        room.setTrainingRecord("{}");

        // todo 任务拷贝
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
        // 无运行中训练, 返回null
        SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId, UserInfoContext.getUserContext().getUserId());
        if (singleRoom == null) {
            return null;
        }

        // 获取当前训练模板数据
        SingleTemplateBo singleTemplateBo = singleTemplateService.get(businessTemplateId);
        SingleRoomBo singleRoomBo = new SingleRoomBo();
        BeanUtils.copyProperties(singleTemplateBo, singleRoomBo);

        // 生成数据
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
                userId,
                queryParams.getPattern(),
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

    /**
     * 训练任务提交
     *
     * @param businessTemplateId 训练业务模板id
     * @param requestData        提交任务内容
     * @return bool
     */
    @Override
    public Boolean submitMission(String businessTemplateId, SubmitMissionReq requestData) {
        SingleRoom roomObject = singleRoomMapper.getSingleRoom(businessTemplateId, UserInfoContext.getUserContext().getUserId());
        if (roomObject == null) {
            return false;
        }

        // 写入任务记录
        JSONObject missionsRecord = JsonUtil.toJsonObject(roomObject.getTrainingRecord(), JSONObject.class);

        JSONObject record = new JSONObject();
        record.put("time", new Date());
        record.put("record", requestData.getTrainingRecord());
        missionsRecord.put(requestData.getMissionResourceId(), record);

        roomObject.setTrainingRecord(JsonUtil.toJsonString(missionsRecord));
        updateById(roomObject);

        return true;
    }

    /**
     * 当前用户此次单兵训练任务记录
     *
     * @param businessTemplateId 训练业务模板id
     * @return 任务方案与任务训练记录
     */
    @Override
    public List<TrainRecordBo> getTrainRecord(String businessTemplateId) {
        // 运行中单兵训练
        SingleRoom roomObject = singleRoomMapper.getSingleRoom(businessTemplateId, UserInfoContext.getUserContext().getUserId());
        if (roomObject == null) {
            return null;
        }

        SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);

        // 获取任务方案
        JSONObject missionScheme = JsonUtil.toJsonObject(singleTemplate.getMissionScheme(), JSONObject.class);
        JSONArray schemeData = missionScheme.getJSONArray("scheme_data");
        if (schemeData == null) {
            return null;
        }

        // 构建数据
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TrainRecordBo> trainRecordBos = Lists.newArrayList();
        JSONObject trainRecord = JsonUtil.toJsonObject(roomObject.getTrainingRecord(), JSONObject.class);

        for (JSONObject stage : schemeData.toJavaList(JSONObject.class)) {
            TrainRecordBo trainRecordBo = new TrainRecordBo();
            trainRecordBo.setMissions(Lists.newArrayList());
            trainRecordBo.setStage(stage.getString("stage"));

            for (JSONObject _m : stage.getJSONArray("missions").toJavaList(JSONObject.class)) {
                MissionRecordBo missionRecordBo = new MissionRecordBo();
                missionRecordBo.setResourceId(_m.getString("resource_id"));
                missionRecordBo.setTitle(_m.getString("title"));
                missionRecordBo.setDescription(_m.getString("description"));
                JSONObject missionRecord = trainRecord.getJSONObject(_m.getString("resource_id"));

                // 时间戳 -> 日期字符串
                if (!missionRecord.isEmpty()) {
                    missionRecord.put("time", simpleDateFormat.format(new Date(missionRecord.getLong("time"))));
                }
                missionRecordBo.setTrainingRecord(missionRecord);

                trainRecordBo.getMissions().add(missionRecordBo);
            }

            trainRecordBos.add(trainRecordBo);
        }

        return trainRecordBos;
    }

    /**
     * 根据任务方案、训练记录生成富文本
     *
     * @param businessTemplateId 训练业务模板id
     * @return String
     */
    @Override
    public String getTrainManual(String businessTemplateId) {
        // 获取训练方案
        SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);
        JSONObject missionScheme = JsonUtil.toJsonObject(singleTemplate.getMissionScheme(), JSONObject.class);
        JSONArray schemeData = missionScheme.getJSONArray("scheme_data");

        // 生成富文本训练手册
        StringBuilder writeup = new StringBuilder();
        // todo ? -> loop index
        schemeData.toJavaList(JSONObject.class).forEach(item -> {
            writeup.append("## ").append("阶段?: ").append(item.getString("stage")).append("\n\n");
            item.getJSONArray("missions").toJavaList(JSONObject.class).forEach(_mission -> {
                writeup.append("#### ").append("任务?: ").append(_mission.getString("title")).append("\n\n");
                writeup.append("题解: \n").append(_mission.getString("write_up")).append("\n\n");
            });
        });

        return writeup.toString();
    }

    /**
     * 获取训练时间线数据
     *
     * @param businessTemplateId 训练业务模板id
     * @return TrainTimeLineBo
     */
    @Override
    public TrainTimeLineBo getTimeLineData(String businessTemplateId) {
        String userId = UserInfoContext.getUserContext().getUserId();
        SingleRoom singleRoom = singleRoomMapper.getSingleRoom(businessTemplateId, userId);
        if (singleRoom == null) {
            return null;
        }

        SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);
        TrainTimeLineBo trainTimeLineBo = new TrainTimeLineBo();

        trainTimeLineBo.setCurrentTime(new Date());
        trainTimeLineBo.setStartTime(singleRoom.getStartTime());

        // 预计结束时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(singleRoom.getStartTime());
        calendar.add(Calendar.HOUR, singleTemplate.getDurationTime());
        trainTimeLineBo.setEndTime(calendar.getTime());

        // 任务完成时间点
        List<JSONObject> missionRecord = Lists.newArrayList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Mission> missionMap = getMissionMapByScheme(singleTemplate.getMissionScheme());

        JSONObject trainRecord = JsonUtil.toJsonObject(singleRoom.getTrainingRecord(), JSONObject.class);
        for (Map.Entry<String, Object> stringObjectEntry : trainRecord.entrySet()) {

            JSONObject missionEvent = new JSONObject();
            JSONObject val = (JSONObject) stringObjectEntry.getValue();
            missionEvent.put("time", simpleDateFormat.format(new Date(val.getLong("time"))));
            missionEvent.put("title", missionMap.get(stringObjectEntry.getKey()).getTitle());

            missionRecord.add(missionEvent);
        }
        trainTimeLineBo.setData(missionRecord);

        return trainTimeLineBo;
    }


    // todo 待迁移
    private List<String> getMissionIdsByScheme(String missionScheme) {
        JSONArray schemeData = JsonUtil.toJsonObject(missionScheme, JSONObject.class).getJSONArray("scheme_data");
        List<String> missionIds = Lists.newArrayList();
        schemeData.toJavaList(JSONObject.class).forEach(
                stage -> missionIds.addAll(
                        stage.getJSONArray("missions").toJavaList(JSONObject.class).stream().map(
                                mission -> mission.getString("resource_id")
                        ).collect(Collectors.toList())
                )
        );
        return missionIds;
    }

    private Map<String, Mission> getMissionMapByScheme(String missionScheme) {
        List<Mission> missions = missionService.batchGet(getMissionIdsByScheme(missionScheme));
        return missions.stream().collect(Collectors.toMap(MissionBase::getResourceId, mission -> mission));
    }
}
