package com.bili.service;

import com.bili.entity.Comment;
import com.bili.entity.LikeInfo;
import com.bili.entity.User;
import com.bili.mapper.CommentMapper;
import com.bili.mapper.UserMapper;
import com.bili.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                res.get(i).setLiked(false);
                List<Comment> second = commentMapper.findSecond(id);
                if (second.size() > 0) {
                    for (int j = 0; j < second.size(); j++) {
                        Integer uid2 = second.get(j).getUid();
                        User user2 = userMapper.findUid(uid2);
                        second.get(j).setName(user2.getName());
                        second.get(j).setAvatar(user2.getAvatar());
                        second.get(j).setFname(fname);
                        second.get(j).setLiked(false);
                    }

                    res.get(i).setLists(second);
                    res.get(i).setListslength(second.size());
                } else {
                    res.get(i).setLists(new ArrayList<>());
                    res.get(i).setListslength(0);
                }
            }
        }
    }

    // 添加点赞信息
    private void addLikeinfos(List<Comment> res, Integer uid, Integer vid) {
        List<Integer> cids = commentMapper.getLikeinfo(uid, vid);   // 点过赞的评论
        for (Comment i : res) {
            if (cids.contains(i.getId())) {
                i.setLiked(true);
            }
            if (i.getLists().size() > 0) {
                for (Comment j : i.getLists()) {
                    if (cids.contains(j.getId())) {
                        j.setLiked(true);
                    }
                }
            }
        }
    }
    public List<Comment> getAllComment(Integer vid, Integer uid, Integer type) {
        String order = "likes";
        if (type == 1) {
            order = "time";
        }
        List<Comment> res = commentMapper.getAllComment(vid, order);
        loadinfos(res);
        if (uid != -1) {
            addLikeinfos(res, uid, vid);
        }
        return res;
    }

    public Integer addComment(Comment comment) {
        // 更新video中的infos
        Integer vid = comment.getVid();
        videoMapper.chnageCommentNum(1, vid);   // video表中， 评论数 + 1
        commentMapper.addComment(comment);
        Integer comment_id = comment.getId();         // 新插入数据获得的id
        return comment_id;
    }

    public void deleteComment(Integer id, Integer vid) {
        Integer num = -1;
        videoMapper.chnageCommentNum(num, vid);
        commentMapper.deleteComment(id);
    }

    public void addLikeinfo(LikeInfo likeInfo) {
        commentMapper.addLikeinfo(likeInfo);
        commentMapper.updataCommentLikes(likeInfo.getCid(), 1);
    }

    public void deletelikeinfo(Integer cid, Integer uid) {
        commentMapper.deletelikeinfo(cid, uid);
        commentMapper.updataCommentLikes(cid, -1);
    }
}
