package com.bili.service;

import com.bili.entity.*;
import com.bili.entity.outEntity.UploadMgImg;
import com.bili.entity.outEntity.UploadMgInfo;
import com.bili.mapper.MgMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Slf4j
@Service
public class MgService {
    @Value("${files.mgPath}")
    private String mgPath;
    @Value("${files.mgNet}")
    private String mgNet;
    @Autowired
    private MgMapper mgMapper;
    public List<Mg> getByTitle(String keyword) {
        List<Mg> res = mgMapper.getByTitle(keyword);
        return res;
    }

    public Integer uploadMgInfo(UploadMgInfo uploadMgInfo) throws IOException {
        UUID uuid = UUID.randomUUID();
        String covername = "cover/" + uuid + ".png";
        uploadMgInfo.setCover(mgNet + covername);    // 网络地址
        uploadMgInfo.setChapters(1);
        String[] taglist = uploadMgInfo.getTaglist();
        String tagstemp = "";
        for (int i = 0; i < taglist.length; i++) {
            if (i < taglist.length - 1) {
                tagstemp += taglist[i] + " ";
            } else {
                tagstemp += taglist[i];
            }
        }
        uploadMgInfo.setTags(tagstemp);
        mgMapper.uploadMgInfo(uploadMgInfo);
        Integer mid = uploadMgInfo.getMid();

        // 生成图片
        // 封面存放地址
        String coverpath = mgPath + covername;   // 存放位置
        // 解密
        Base64.Decoder decoder = Base64.getDecoder();
        // 去掉base64前缀
        String b64file = uploadMgInfo.getCoverfile();
        // b64file = b64file.substring(b64file.indexOf(",", 1) + 1, b64file.length());
        byte[] b = decoder.decode(b64file);
        // 处理数据
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        // 保存图片
        OutputStream out = new FileOutputStream(coverpath);
        out.write(b);
        out.flush();
        out.close();

        // 生成图片存放文件夹（首次上传默认章节时第一章）
        Integer cpindex = uploadMgInfo.getNumber() == null ? 1 : uploadMgInfo.getNumber();
        String cpname = Objects.equals(uploadMgInfo.getName(), "") ? "第" + cpindex +"章" : uploadMgInfo.getName();
        String filepath = "resource/" + uploadMgInfo.getTitle() + "_" + mid + "/" + cpindex + "_" + cpname;
        File fp = new File(mgPath + filepath);
        if (!fp.exists()) {
            // 文件夹不存在
            boolean boo = fp.mkdirs();
        }
        // 生成章节信息
        mgMapper.addChapter(mid, cpname, cpindex);
        return mid;
    }

    public void uploadimgs(UploadMgImg uploadMgImg) throws IOException {
        Integer mid = uploadMgImg.getMid();
        UUID uuid = UUID.randomUUID();
        Integer cpindex = uploadMgImg.getNumber() == null ? 1 : uploadMgImg.getNumber();
        String cpname = Objects.equals(uploadMgImg.getName(), "") ? "第" + cpindex +"章" : uploadMgImg.getName();
        String filepath = "resource/" + uploadMgImg.getTitle() + "_" + mid + "/" + cpindex + "_" + cpname;
        File imgfile = new File(mgPath + filepath);
        if (!imgfile.exists()) {
            // 文件夹不存在
            imgfile.mkdirs();
        }
        String imgname = mgPath + filepath + "/"+ uploadMgImg.getInd() + "_" + uuid + "." + uploadMgImg.getType();
        String netname = mgNet + filepath + "/" + uploadMgImg.getInd() + "_" + uuid + "." + uploadMgImg.getType();
        File mgfile = new File(imgname);
        uploadMgImg.getImgfile().transferTo(mgfile);
        uploadMgImg.setPath(netname);
        mgMapper.uploadimgs(uploadMgImg);
    }

    public void updateMg(UploadMgInfo uploadMgInfo) {
        Integer mid = uploadMgInfo.getMid();
        Integer cpindex = uploadMgInfo.getNumber() == null ? 1 : uploadMgInfo.getNumber();
        String cpname = Objects.equals(uploadMgInfo.getName(), "") ? "第" + cpindex +"章" : uploadMgInfo.getName();
        String filepath = "/resource/" + uploadMgInfo.getTitle() + "_" + mid + "/" + cpindex + "_" + cpname;
        File fp = new File(mgPath + filepath);
        if (!fp.exists()) {
            // 文件夹不存在
            boolean boo = fp.mkdirs();
        }
        mgMapper.addChapter(mid, cpname, cpindex);
        mgMapper.updateMgChapter(mid, 1);
    }

    private void dealTaglist (List<Mg> res) {
        for (int i = 0; i < res.size(); i++) {
            List<String> n = new ArrayList<>();
            if (res.get(i).getTags().length() == 0) {
                res.get(i).setTaglist(n);
            }
            String[] r = res.get(i).getTags().trim().split(" ");
            res.get(i).setTaglist(Arrays.asList(r));
        }
    }
    private void dealOne (Mg res) {
        String[] r = res.getTags().trim().split(" ");
        res.setTaglist(Arrays.asList(r));
    }

    public List<Mg> getMgs(Integer num, Integer type) {
        String kw = "";
        switch (type) {
            case 0:
                kw="time";
                break;
            case 1:
                kw="time";
                break;
            case 2:
                kw="time";
                break;
        }
        List<Mg> res = mgMapper.getMgs(num, kw);
        dealTaglist(res);
        return res;
    }

    public Mg getOneMg(Integer mid, Integer uid) {
        Mg res = mgMapper.getOneMg(mid);
        Integer hadecolected = mgMapper.judgeCollected(mid, uid, 0, 0);
        if (hadecolected == 1) {
            res.setCollected(true);
        } else {
            res.setCollected(false);
        }
        dealOne(res);
        return res;
    }

    public List<MgChapters> getChapters(Integer mid, Integer page, Integer num) {
        if (page == 0 && num == 0) {
            return mgMapper.getAllChapter(mid);
        }
        page = page * num;
        return mgMapper.getChapters(mid, page, num);
    }

    public List<MgImgs> getMgImgs(Integer mid, Integer number) {
        return mgMapper.getMgImgs(mid, number);
    }

    public List<Mg> getClassify(String type1, Integer type2, Integer type3) {
        if (Objects.equals(type1, "null")) {
            type1 = null;
        }
        List<Mg> res = mgMapper.getClassify(type1, type2, type3);
        dealTaglist(res);
        return res;
    }

    public List<Mg> getMgRanking(Integer type) {
        String t = "";
        if (type == 0) {
            t = "reads";
        } else if (type == 1) {
            t = "favorited";
        }
        List<Mg> res = mgMapper.getMgRanking(t);
        dealTaglist(res);
        return res;
    }

    public List<Mg> searchMg(String keyword) {
        List<Mg> res = mgMapper.searchMg(keyword);
        dealTaglist(res);
        return res;
    }

    public List<Mg> getMgList(Integer uid, Integer type) {
        List<Mg> res = new ArrayList<>();
        List<Integer> mids = mgMapper.getMgList(uid, type);
        for (int i = 0; i < mids.size(); i++) {
            Mg one = mgMapper.getOneMg(mids.get(i));
            res.add(one);
        }
        dealTaglist(res);
        return res;
    }

    public void addMgList(MgList mgList) {
        if (mgList.getType() == 0) {
            Integer r = mgMapper.judgeCollected(mgList.getMid(), mgList.getUid(),0, 1);
            if (r == 1) {
                // 取消收藏
                mgList.setDeleted(0);
                mgMapper.updateMgStatus(mgList);
            } else {
                mgMapper.addMgList(mgList);
            }
        } else {
            // 观看历史
            Integer r = mgMapper.judgeCollected(mgList.getMid(), mgList.getUid(), 1, 1);
            Integer r2 = mgMapper.judgeCollected(mgList.getMid(), mgList.getUid(), 1, 0);
            if (r + r2 == 0) {
                mgMapper.addMgList(mgList);
            } else {
                mgList.setDeleted(0);
                mgMapper.updateMgStatus(mgList);
            }
        }
    }

    public void updateMgStatus(MgList mgList) {
        // type 0 收藏 1 历史
        if (mgList.getType() == 1 && mgList.getNum() == 0) {
            // 删除全部观看历史
            mgMapper.changeAllList(mgList);
            return;
        }
        if (mgList.getType() == 0) {
            // 删除收藏一个
            mgMapper.updateMgStatus(mgList);
            for (int i = 0; i < mgList.getMidlist().length; i++) {
                MgList temp = new MgList();
                temp.setMid(mgList.getMidlist()[i]);
                temp.setDeleted(1);
                temp.setUid(mgList.getUid());
                temp.setType(0);
                mgMapper.updateMgStatus(temp);
            }
//            mgMapper.updateMgStatus(temp);
        }
    }

    public List<MgImgs> getMgImgsRandom(Integer mid, Integer number, Integer num) {
        List<MgImgs> res = mgMapper.getMgImgsRandom(mid, number, num);
        return res;
    }

    public List<Mg> getUploadMg(Integer uid) {
        List<Mg> res = mgMapper.getUploadMg(uid);
        dealTaglist(res);
        return res;
    }

    public void updateMgInfo(Mg mg) {
        mgMapper.updateMgInfo(mg);
    }

    public List<Mg> getMgsss(Integer type) {
        List<Mg> res = mgMapper.getMgsss(type);
        dealTaglist(res);
        return res;
    }

    public MgList getLastWatch(Integer mid, Integer uid) {
        return mgMapper.getLastWatch(mid, uid);
    }
}
