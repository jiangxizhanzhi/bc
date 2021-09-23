package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.CommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {

    /**
     * 查询点赞列表
     * @param comment
     * @param isLike null 全部都查 true仅查点赞 false取消点赞
     * @return
     */
    List<CommentLike> listByComment(@Param("comment") String comment,
                                    @Param("isLike") Boolean isLike);

    CommentLike selectByCommentAndUser(@Param("comment") String comment,
                                       @Param("userId") String userId);
}
