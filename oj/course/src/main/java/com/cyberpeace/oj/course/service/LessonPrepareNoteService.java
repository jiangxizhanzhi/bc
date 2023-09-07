package com.cyberpeace.oj.course.service;

public interface LessonPrepareNoteService {
    /**
     * 提交备课笔记
     * @param lessonId
     * @param note
     */
    void submitNote(String lessonId, String note);
}
