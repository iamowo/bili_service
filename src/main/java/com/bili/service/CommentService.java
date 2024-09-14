package com.bili.service;

import com.bili.entity.Comment;
import com.bili.entity.User;
import com.bili.mapper.CommentMapper;
import com.bili.mapper.UserMapper;
import com.bili.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;
    private void loadinfos (List<Comment> res) {
        for (int i = 0; i < res.size(); i++) {
            Integer id = res.get(i).getId();
            Integer uid = res.get(i).getUid();
            User user = userMapper.findUid(uid);
            String fname = user.getName();
            res.get(i).setName(user.getName());
            res.get(i).setAvatar(user.getAvatar());
            // 一级评论
            if (res.get(i).getTopid() == 0) {
                // 二级评论
                List<Comment> second = commentMapper.findSecond(id);
                if (second.size() > 0) {
                    for (int j = 0; j < second.size(); j++) {
                        Integer uid2 = second.get(j).getUid();
                        User user2 = userMapper.findUid(uid2);
                        second.get(j).setName(user2.getName());
                        second.get(j).setAvatar(user2.getAvatar());
                        second.get(j).setFname(fname);
                    }

                    res.get(i).setLists(second);
                    res.get(i).setListslength(second.size());
                }
            }
        }
    }
    public List<Comment> getAllComment(Integer vid) {
        List<Comment> res = commentMapper.getAllComment(vid);
        loadinfos(res);
        return res;
    }

    public void addComment(Comment comment) {
        // 更新video中的infos
        Integer vid = comment.getVid();
        videoMapper.chnageCommentNum(1, vid);   // video表中， 评论数 + 1
        commentMapper.addComment(comment);
    }

    public void deleteComment(Integer id, Integer vid) {
        Integer num = -1;
        videoMapper.chnageCommentNum(num, vid);
        commentMapper.deleteComment(id);
    }
}
