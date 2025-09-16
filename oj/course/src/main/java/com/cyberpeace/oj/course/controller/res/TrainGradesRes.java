package com.cyberpeace.oj.course.controller.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainGradesRes {

    private static final int[] SCORE_LIST = {60, 70, 80, 90, 101};


    private Integer zeroToSixtyNums = 0;

    private Integer sixtyToSeventyNums = 0;

    private Integer seventyToEightyNums = 0;

    private Integer eightyToNinetyNums = 0;

    private Integer ninetyToOneHundredNums = 0;

    public TrainGradesRes(List<Integer> scores) {
        if (CollectionUtils.isNotEmpty(scores)) {
            int[] scoreCounts = {0, 0, 0, 0, 0};
            for (Integer score : scores) {
                for (int i = 0; i < SCORE_LIST.length; i++) {
                    if (score < SCORE_LIST[i]) {
                        scoreCounts[i]++;
                        break;
                    }
                }
            }
            zeroToSixtyNums = scoreCounts[0];
            sixtyToSeventyNums = scoreCounts[1];
            seventyToEightyNums = scoreCounts[2];
            eightyToNinetyNums = scoreCounts[3];
            ninetyToOneHundredNums = scoreCounts[4];
        }
    }



}
