package com.bili.mapper;

import com.bili.entity.Comment;
import com.bili.entity.LikeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> getAllComment(Integer vid, String order);

    void addComment(Comment comment);

    void deleteComment(Integer id);

    List<Comment> findSecond(Integer id);

    void addLikeinfo(LikeInfo likeInfo);

    void deletelikeinfo(Integer cid, Integer uid);

    List<Integer> getLikeinfo(Integer uid, Integer vid);

    void updataCommentLikes(Integer cid, Integer num);
}
