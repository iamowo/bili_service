package com.bili.mapper;

import com.bili.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> getAllComment(Integer vid);

    void addComment(Comment comment);

    void deleteComment(Integer id);

    List<Comment> findSecond(Integer id);
}
