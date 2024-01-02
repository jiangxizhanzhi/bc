package com.cyberpeace.oj.person.controller.res;

import lombok.Data;

import java.util.List;

@Data
public class StudyCalendarRes {

        private String date;

        private List<StudyHistoryRes> historyList;
}
