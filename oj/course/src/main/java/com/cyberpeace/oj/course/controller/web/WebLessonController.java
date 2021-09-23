package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.CollectionReq;
import com.cyberpeace.oj.course.dao.mapper.LessonMapper;
import com.cyberpeace.oj.course.dao.oj_entity.Lesson;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteReq;
import com.cyberpeace.oj.ojbase.controller.res.CommentRes;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "oj-前台课时相关api")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/lesson")
public class WebLessonController {

    @Autowired
    private CourseLessonService courseLessonService;

    @Autowired
    private LessonMapper lessonMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NoteService noteService;

    @ApiOperation("课时收藏")
    @PostMapping("/collection")
    public BaseRsp<String> collectionLesson(@ApiParam(name = "req", value = "课时收藏信息") @RequestBody CollectionReq req) {

        String userId = UserInfoContext.getUserContext().getUserId();
        String lesson = courseLessonService.collectionLesson(userId, req.getResource(),
                req.getIsCollection());
        return new BaseRsp<>(lesson);
    }

    @ApiOperation("课时详情查询")
    @GetMapping("")
    @ApiImplicitParams({ @ApiImplicitParam(name = "lesson_id", value = "课时编号"),
            @ApiImplicitParam(name = "course_id", value = "课程编号") })
    public BaseRsp<LessonInfoBo> lessonDetail(@RequestParam("course_id") String courseId,
                                              @RequestParam("lesson_id") String lessonId) {

        String userId = UserInfoContext.getUserContext().getUserId();
        LessonInfoBo infoBo = courseLessonService.getLessonDetail(userId, courseId, lessonId);
        return new BaseRsp<>(infoBo);
    }

    @ApiOperation("课时评论")
    @PostMapping("/comment")
    public BaseRsp<CommentRes> lessonComment(@RequestBody @Valid CommentReq commentReq) {

        Lesson lesson = lessonMapper.selectById(commentReq.getResource());
        if (null == lesson) {
            throw new BusinessException(OjBusinessErrorCode.LESSON_NOT_FOUND);
        }

        Comment comment = BeanUtils.copy(commentReq, Comment.class);
        comment.setCommentType(Comment.LESSON);
        comment.setUser(UserInfoContext.getUserContext().getUserId());

        Comment newComment = commentService.addNewComment(comment);
        CommentRes commentRes = BeanUtils.copy(newComment, CommentRes.class);
        return new BaseRsp<>(commentRes);
    }

    @ApiOperation("课时笔记新增")
    @PostMapping("/note")
    public BaseRsp<NoteAddContext> saveNote(@ApiParam @RequestBody @Valid NoteReq noteReq) {

        Lesson lesson = lessonMapper.selectById(noteReq.getResource());
        if (null == lesson) {
            throw new ResourceNotFoundException("未检索到对应课时信息！");
        }

        String userId = UserInfoContext.getUserContext().getUserId();
        NoteAddContext addContext = BeanUtils.copy(noteReq, NoteAddContext.class);
        addContext.setUser(userId);
        addContext.setResourceType(Note.LESSON);

        NoteAddContext noteAddContext = noteService.updateOrSave(addContext);
        return new BaseRsp<>(noteAddContext);
    }


}
