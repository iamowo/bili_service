package com.bili.service;

import com.bili.entity.At;
import com.bili.entity.Comment;
import com.bili.entity.LikeInfo;
import com.bili.entity.User;
import com.bili.mapper.CommentMapper;
import com.bili.mapper.UserMapper;
import com.bili.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private void addLikeinfos(List<Comment> res, Integer uid, Integer id, Integer type) {
        List<Integer> cids = commentMapper.getLikeinfo(uid, id, type);   // 点过赞的评论
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
    public List<Comment> getAllComment(Integer id, Integer uid, Integer sort, Integer type) {
        String order = "likes";
        if (sort == 1) {
            order = "time";
        }
        List<Comment> res = new ArrayList<>();
        if (type == 0) {
            res.addAll(commentMapper.getAllComment(id, order));
        } else if (type == 1){
            res.addAll(commentMapper.getAllComment2(id, order));
        } else if (type == 2) {
            res.addAll(commentMapper.getAllComment3(id, order));
        }
        loadinfos(res);
        if (uid != -1) {
            addLikeinfos(res, uid, id, type);
        }
        return res;
    }

    public Integer addComment(Comment comment) {
        Integer atid = -1;
        if (comment.getAtuid() != -1) {
            // @某人
            At at = new At();
            at.setType(comment.getType());
            at.setUid1(comment.getUid());
            at.setUid2(comment.getAtuid());
            at.setAtname(comment.getAtname());
            at.setType(comment.getType());
            if (comment.getType() == 0) {
                at.setVid(comment.getVid());
            } else if (comment.getType() == 1) {
                at.setDid(comment.getDid());
            } else if (comment.getType() == 2) {
                // 评论
                at.setVid(comment.getVid());
                at.setCid(comment.getReplaycid());
                at.setAttitle(comment.getReplaycontent());        // 视频和动态不用设置
            }
            commentMapper.addAt(at);
            atid = at.getAtid();
        }
        // 更新video中的infos
        comment.setAtid(atid);
        if (comment.getType() == 0) {
            // 视频
            Integer vid = comment.getVid();
            videoMapper.chnageCommentNum(1, vid);   // video表中， 评论数 + 1
            commentMapper.addComment(comment);
        } else if (comment.getType() == 1) {
            // 动态
            Integer did = comment.getDid();
            videoMapper.chnageCommentNumDynamic(1, did);
            commentMapper.addComment(comment);
        } else if (comment.getType() == 2) {
            // 漫画
            commentMapper.addComment(comment);
        }
        Integer comment_id = comment.getId();         // 新插入数据获得的id
        return comment_id;
    }

    public void deleteComment(Integer id, Integer deletedid, Integer type) {
        if (type == 0) {
            // 视频评论
            videoMapper.chnageCommentNum(-1, deletedid);
        } else if (type == 1) {
            // 动态评论
            videoMapper.chnageCommentNumDynamic(-1, deletedid);
        }
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

    public List<Map> getReplayComment(Integer uid) {
        List<Map> res=  new ArrayList<>();
        List<Comment> comments = commentMapper.getReplayComment(uid);
        for (int i = 0; i < comments.size(); i++) {
            Map<String, Object> temp = new HashMap<>();
            User userinfo = userMapper.getByUid(comments.get(i).getUid());
            temp.put("avatar", userinfo.getAvatar());
            temp.put("name", userinfo.getName());
            temp.put("content", comments.get(i).getContent());
            temp.put("time", comments.get(i).getTime());
            temp.put("type", comments.get(i).getType());
            res.add(temp);
        }
        return res;
    }
}
