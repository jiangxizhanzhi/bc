package com.cyberpeace.cr.report_score.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.req.ScoreRecordListReq;
import com.cyberpeace.cr.report_score.controller.req.WebReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.res.*;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import com.cyberpeace.cr.report_score.dao.mapper.ReportScoreMapper;
import com.cyberpeace.cr.report_score.service.ReportScoreConfigService;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import com.cyberpeace.cr.report_score.service.bo.UserSubjectGroupBo;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportScoreServiceImpl extends ServiceImpl<ReportScoreMapper, ReportScore> implements ReportScoreService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private ReportScoreConfigService reportScoreConfigService;

    private final String savePath = "report" + File.separator + "report" + File.separator;

    private String uploadPath;

    @PostConstruct
    public void init() {
        uploadPath = ConfigUtil.getContextProperty("cr.local.upload.path") + File.separator
                + savePath;
        ensureDir(uploadPath);
    }

    private void ensureDir(String uploadPath) {
        File f = new File(uploadPath);

        if (!f.exists() || !f.isDirectory()) {
            f.mkdirs();
        }
    }


    @Autowired
    private ReportScoreMapper reportScoreMapper;

    @Override
    public IPage<ReportScoreRsp> selectPage(Page page, ReportScoreListReq reportScoreListReq) {
        IPage<ReportScoreRsp> result = reportScoreMapper.list(page, reportScoreListReq);
        return result;
    }

    @Override
    public ReportScoreDetailRsp selectById(String resourceId) {
        ReportScore reportScore = getById(resourceId);
        ReportScoreDetailRsp copy = BeanUtils.copy(reportScore, ReportScoreDetailRsp.class);
        return copy;
    }

    @Override
    public IPage<ScoreRecordRsp> selectRecordPage(Page page, ScoreRecordListReq scoreRecordListReq) {
        IPage<ScoreRecordRsp> result = reportScoreMapper.listRecord(page, scoreRecordListReq);
        return result;
    }

    @Override
    public List<HashMap<String, String>> selectCountBySubject() {
        List<HashMap<String, String>> result = reportScoreMapper.countSubject();
        return result;
    }

    @Override
    public List<HashMap<String, String>> selectCountByStatus() {
        List<HashMap<String, String>> result = reportScoreMapper.countStatus();
        return result;
    }

    @Override
    public IPage<WebReportScoreRsp> selectWebPage(Page page, WebReportScoreListReq webReportScoreListReq, String userId) {
        IPage<WebReportScoreRsp> result = reportScoreMapper.ListWeb(page, webReportScoreListReq, userId);
        return result;
    }

    @Override
    public WebReportScoreDetailRsp selectWebById(String resourceId) {
        ReportScore reportScore = getById(resourceId);
        WebReportScoreDetailRsp copy = BeanUtils.copy(reportScore, WebReportScoreDetailRsp.class);
        return copy;
    }

    @Override
    public String saveFile(MultipartFile multipartFile) throws IOException {
        File file = FileUtil.saveFile(multipartFile, uploadPath);
        return file.getAbsolutePath();
    }

    @Override
    public List<HashMap<String, String>> selectWebCountByStatus(String userId) {
        List<HashMap<String, String>> result = reportScoreMapper.countWebStatus(userId);
        return result;
    }

    @Override
    public RankingRep getRankScore(String userId) {
        List<RankingRep> rankingRepList = reportScoreMapper.getRankScore();
        for (RankingRep rankingRep : rankingRepList) {
            if(rankingRep.getUserId().equals(userId)) {
                return rankingRep;
            }
        }
        return null;
    }

    @Override
    public void validateReportScoreNameExist(ReportScore reportScore) {
        // 名称重复性校验
        ReportScore reportScoreObject = reportScoreMapper.selectByName(reportScore.getReportName(), reportScore.getUserId());
        if (reportScoreObject != null) {
            throw new BusinessException(CrBusinessErrorCode.DUPLICATED_REPORT_SCORE_NAME);
        }
    }

    @Override
    public List<RankingRep> rankingStatistics() {
        List<RankingRep> rankScoreList = reportScoreMapper.getRankScore();
        Map<String, RankingRep> rankScoreMap = rankScoreList.stream()
                .collect(Collectors.toMap(RankingRep::getUserId, item -> item));


        List<UserInfo> userInfoList = getUsers();
        List<RankingRep> rankingRepList = Lists.newArrayList();
        for (UserInfo userInfo : userInfoList) {

            RankingRep rankingRep = new RankingRep();
            rankingRep.setLogo(userInfo.getAvatarUrl());
            rankingRep.setSumScore("0");
            rankingRep.setUserName(userInfo.getName());
            rankingRep.setUserId(userInfo.getUserId());
            rankingRep.setRank("0");

            RankingRep userRankRep = rankScoreMap.get(userInfo.getUserId());
            if (userRankRep != null) {
                // 该排名为数据库中部分排名，不是最终排名
                // rankingRep.setRank(userRankRep.getRank());
                rankingRep.setSumScore(userRankRep.getSumScore());
            }
            rankingRepList.add(rankingRep);
        }
        if(rankingRepList.stream().allMatch(rankingRep -> rankingRep.getSumScore().equals("0"))) {
            // 初始状态分数为0分，排名全部为0
            log.info("all team source 0 , rank init is 0 ");
            return rankingRepList;
        }
        return amountRank(rankingRepList);
    }

    @Override
    public List<SubjectCompleteRep> subjectCompleteSt() {
        List<UserSubjectGroupBo> userSubjectGroupList = reportScoreMapper.getUserSubjectGroup();

        Map<String, List<UserSubjectGroupBo>> subjectMap = Maps.newHashMap();
        for (UserSubjectGroupBo userSubjectGroupBo : userSubjectGroupList) {
            subjectMap.putIfAbsent(userSubjectGroupBo.getUserId(), Lists.newArrayList());
            subjectMap.get(userSubjectGroupBo.getUserId()).add(userSubjectGroupBo);
        }

        // 获取全部用户作为队伍， 除了admin
        List<UserInfo> userInfoList = getUsers();
        List<SubjectCompleteRep> subjectCompleteRepList = Lists.newArrayList();

        for (UserInfo userInfo : userInfoList) {

            SubjectCompleteRep subjectCompleteRep = new SubjectCompleteRep();
            subjectCompleteRep.setUserId(userInfo.getUserId());
            subjectCompleteRep.setUserName(userInfo.getName());

            List<UserSubjectGroupBo> userSubjectGroupBos = subjectMap.get(userInfo.getUserId());
            if (userSubjectGroupBos != null) {
                // 按顺序获取1-5的科目分数，组成数组
                List<String> subjectScoreList = getSubjectScoreList(userSubjectGroupBos);
                subjectCompleteRep.setSubjectScore(subjectScoreList);
            }
            subjectCompleteRepList.add(subjectCompleteRep);
        }
        return subjectCompleteRepList;
    }

    private List<UserInfo> getUsers() {
        // 获取全部用户作为队伍， 除了admin
        // List<UserInfo> userInfoList = remoteUserService.getList();

        // 获取数据库指定配置用户
        List<String> userIds = reportScoreConfigService.getUserIds();
        if (userIds == null || userIds.isEmpty()) {
            log.error("Not found user config");
            throw new BusinessException("Not found user config");
        }
        List<UserInfo> userByIdList = remoteUserService.getByIdList(userIds);

        if (userByIdList.size() != 8) {
            // 8个队伍进行统计
            log.warn("Leaderboard users are not 8");
        }

        if (userByIdList.size() != userIds.size()) {
            log.error("Configure user data not always {}:{}", userByIdList.size(), userIds.size());
            throw new BusinessException("Configure user data not always");
        }

        return userByIdList;
    }

    private Boolean isSkipUser(String name) {
        boolean isSkip = name.equals("admin");
        if (isSkip) {
            log.info("skip admin user");
        }
        return isSkip;
    }

    private static List<String> getSubjectScoreList(List<UserSubjectGroupBo> userSubjectGroupBos) {
        List<String> subjectScoreList = Lists.newArrayList();

        Map<Integer, String> collect = userSubjectGroupBos.stream()
                .collect(Collectors.toMap(UserSubjectGroupBo::getSubject,
                        UserSubjectGroupBo::getSubjectScore));

        List<Integer> listCode = EnumUtils.getListCode(ReportScore.SubjectCode.class);
        for (Integer code : listCode) {
            String subjectScore = collect.get(code);
            subjectScoreList.add(subjectScore != null ? subjectScore: "0");
        }

        return subjectScoreList;
    }

    private static List<RankingRep> amountRank(List<RankingRep> rankingRepList) {
        rankingRepList.sort((s1, s2) -> -Long.compare(Long.parseLong(s1.getSumScore()), Long.parseLong(s2.getSumScore())));

        int index = 0;
        int tmpSize = 0;
        long lastSumScore = -1L;

        List<RankingRep> tmpAmountRankList = new ArrayList<>();
        for (RankingRep rankingRep : rankingRepList) {

            long sumScoreLong = Long.parseLong(rankingRep.getSumScore());

            if (lastSumScore != sumScoreLong) {
                lastSumScore = sumScoreLong;
                index = index + 1;
            }
            rankingRep.setRank(String.valueOf(index));
            //相同并列，不同则跳过，序号一次递增
            if (tmpSize > 0) {
                if (sumScoreLong < Long.parseLong(rankingRepList.get(tmpSize - 1).getSumScore())) {
                    index = tmpSize + 1;
                    rankingRep.setRank(String.valueOf(index));
                }
            }
            tmpAmountRankList.add(rankingRep);
            tmpSize = tmpAmountRankList.size();
        }

        return tmpAmountRankList;
    }
}
