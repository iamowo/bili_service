package com.bili.service;

import com.bili.entity.PO.*;
import com.bili.entity.outEntity.Audit;
import com.bili.entity.outEntity.OneChunk;
import com.bili.entity.outEntity.UploadVideoInfos;
import com.bili.entity.outEntity.VideoInfos;
import com.bili.entity.temp.TagAndNums;
import com.bili.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class VideoService {
    @Value("${files.videoPath}")
    private String videoPath;
    @Value("${files.videoNet}")
    private String videoNet;
    @Value("${url2}")
    private String neturl;
    @Value("${files.animationPath}")
    private String animationPath;
    @Value("${files.animationNet}")
    private String animationNet;

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysInfoMapper sysInfoMapper;
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private SearchMapper searchMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagService tagService;

    public List<Video> getAll() {
        List<Video> videos = videoMapper.getAll();
        for (int i = 0; i < videos.size(); i++) {
            // 加载tag信息
            String s =  videos.get(i).getOthertags();
            String[] ss = s.split(" ");
            videos.get(i).setTags(ss);
        }
        adduserinfo(videos);
        return videos;
    }

    public List<Video> getVideos(Integer type) {
        // 获取pass = 1（审核通过）
        List<Video> res = videoMapper.getVideos(type);

        adduserinfo(res);
        return res;
    }

    public List<Video> getRecommend() {
        List<Video> videos = videoMapper.getRecommend();
        adduserinfo(videos);
        return videos;
    }

    public List<Video> getRandom(Integer num) {
        List<Video> videos = videoMapper.getRandom(null, num);
        adduserinfo(videos);
        return videos;
    }

    // 加工视频信息
    private void adduserinfo(List<Video> video) {
        for (int i = 0; i < video.size(); i++) {
            Integer uid = video.get(i).getUid();
            User user = userMapper.findUid(uid);
            video.get(i).setName(user.getName());
            video.get(i).setAvatar(user.getAvatar());
            video.get(i).setUserintro(user.getIntro());
        }
    }
    private void adduserinfoone(Video video) {
        Integer uid = video.getUid();
        User user = userMapper.findUid(uid);
        video.setName(user.getName());
        video.setAvatar(user.getAvatar());
        video.setUserintro(user.getIntro());
    }
    public Integer uploadVideoInfos(UploadVideoInfos uploadVideoInfos) throws IOException {
        Video video = new Video();
        video.setType(uploadVideoInfos.getType());
        video.setPass(0);
        Integer aid = -1;
        Integer animation_list_id = -1;
        // 添加animation表信息
        Animation animation = new Animation();

        // base64文件(生成cover)
        // 封面存放地址
        String videopath = "";
        UUID uuid = UUID.randomUUID();
        if (uploadVideoInfos.getType() == 0) {
            String path = uploadVideoInfos.getUid() + "/cover/" + uuid + ".png";
            video.setCover(videoNet + path);       // 网络地址
            videopath = videoPath + path;   // 存放位置
        } else {
            String path = "cover/" + uuid + ".png";
            video.setCover(animationNet + path);       // 网络地址
            animation.setCover(animationNet + path);  // 另一个
            videopath = animationPath + path;   // 存放位置
        }
        // 解密==========================================
        Base64.Decoder decoder = Base64.getDecoder();
        // 去掉base64前缀
        String b64file = uploadVideoInfos.getCover();
        b64file = b64file.substring(b64file.indexOf(",", 1) + 1, b64file.length());
        byte[] b = decoder.decode(b64file);
        // 处理数据
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        // 保存图片===================================================
        OutputStream out = new FileOutputStream(videopath);
        out.write(b);
        out.flush();
        out.close();

        if (uploadVideoInfos.getType() == 1) {
            video.setPass(1);                                      // 连续剧某人通过， 不用审核
            // 插入 animation_list 信息
            AnimationList animationList = new AnimationList();
            if (uploadVideoInfos.getAid() == -1) {
                // 第一次上传
                animation.setTitle(uploadVideoInfos.getTitle());  // 标题
                animation.setIntro(uploadVideoInfos.getIntro());
                animation.setChapters(1);
                animation.setUid(uploadVideoInfos.getUid());
                videoMapper.insertAnimation(animation);
                aid = animation.getAid();
                animationList.setAid(aid);
                video.setAid(aid);                                    // 是连续剧， 不是连续剧的话aid = -1
            } else {
                // animation 中 chapters + 1
                videoMapper.updateAnimationChpaters(uploadVideoInfos.getAid(), 1);
                animationList.setAid(uploadVideoInfos.getAid());
                video.setAid(uploadVideoInfos.getAid());
            }
            animationList.setUid(uploadVideoInfos.getUid());
            animationList.setSeason(uploadVideoInfos.getSeason());
            animationList.setChapter(uploadVideoInfos.getChapter());
            animationList.setChaptertitle(uploadVideoInfos.getChaptertitle());
            videoMapper.insertAnimationList(animationList);
            animation_list_id = animationList.getId();
        }
        video.setUid(uploadVideoInfos.getUid());
        video.setTitle(uploadVideoInfos.getTitle());
        if (uploadVideoInfos.getType() == 1) {
            video.setTitle(uploadVideoInfos.getChaptertitle());
        }
        video.setIntro(uploadVideoInfos.getIntro());
        video.setHashValue(uploadVideoInfos.getHashValue());
        video.setDuration(uploadVideoInfos.getDuration());
        video.setMaintag(uploadVideoInfos.getMaintag());
        video.setOthertags(uploadVideoInfos.getOthertags());
        video.setListid(uploadVideoInfos.getListid());
        Integer duration = uploadVideoInfos.getDuration();
        String ss = duration % 60 < 10 ? "0" + duration % 60 : "" +  duration % 60;
        String mm = duration / 60 < 10 ? "0" + duration / 60 : "" +  duration / 60;
        String hh = duration / 3600 < 10 ? "0" + duration / 3600 : "" +  duration / 3600;
        String vidlong = duration >= 3600 ? hh + ":" + mm + ":" + ss : mm + ":" + ss;
        video.setVidlong(vidlong);

        videoMapper.addInfos(video);    // 插入数据

        // 上传文件形式的封面
//        UUID uuid = UUID.randomUUID();
//        String path = uploadVideoInfos.getUid() + "/cover/" + uuid + "." + uploadVideoInfos.getCovertype();
//        String netPath = videoNet + path;
//        video.setCover(netPath);
//        videoMapper.addInfos(video);
//        // 封面存放地址
//        File coverPath = new File(videoPath + path);
//        uploadVideoInfos.getCover().transferTo(coverPath);
//        // 返回生成的vid

        Integer vid = video.getVid();
        if (uploadVideoInfos.getType() == 1) {
            // animation_list  更新vid
            AnimationList animationList = new AnimationList();
            animationList.setId(animation_list_id);
            animationList.setVid(vid);
            videoMapper.uploadAnimationList(animationList);
        }
        if (uploadVideoInfos.getListid() != -1) {
            // 添加到视频列表
            VideoListInfo videoListInfo = new VideoListInfo();
            videoListInfo.setVid(vid);
            videoListInfo.setListid(uploadVideoInfos.getListid());
            videoListInfo.setUid(uploadVideoInfos.getUid());
            videoMapper.addVideoToList(videoListInfo);
        }

        // 插入tag
        String[] tags = uploadVideoInfos.getOthertags().split(" ");
        for (int i = 0; i < tags.length; i++) {
            tagService.insertTag(tags[i], 0, vid, uploadVideoInfos.getMaintagId());
        }
        return vid;
    }

    public void uploadChunks(OneChunk oneChunk) throws IOException {
        Integer uid = oneChunk.getUid();
        String path = videoPath + uid + "/temp/" + oneChunk.getFilename();
        File newFile = new File(path);
        oneChunk.getFile().transferTo(newFile);
        // 最后一个切片上传后，合并
//        if (Objects.equals(oneChunk.getChunsnum() - 1, oneChunk.getIndex())) {
//            String netvideo = mergeChunks(uid);
//            if (!Objects.equals(netvideo, "error")) {
//                Video video = new Video();
//                video.setVid(oneChunk.getVid());
//                video.setPath(netvideo);
//                videoMapper.updateinfo(video);
//                log.info("视频上传success");
//            } else {
//                log.info("视频上传失败");
//            }
//        }
    }
    public Map<String, Object> getAlready(String hashValue, Integer uid) {
        List<String> list = new ArrayList<>();
        Video video = videoMapper.getVideoByHash(hashValue);
        Integer thisvid = video != null ?  video.getVid() : -1;
        Map<String, Object> res = new HashMap<>();
        File tempFile = new File(videoPath + uid + "/temp/");
        File[] files = tempFile.listFiles();
        for (File f : files) {
            String filename = f.getName();
            String thisHash = f.getName().split("_")[0];
            if (Objects.equals(thisHash, hashValue)) {
                list.add(filename);
            }
        }
        res.put("already", list);
        res.put("vid", thisvid);
        return res;
    }
    public void mergeChunks(Integer uid, Integer vid, Integer type) throws IOException {
        String rPath = videoPath + uid + "/temp/";
        File rFile = new File(rPath);
        File[] files = rFile.listFiles();
        // 对要合并的文件排序
        sortList(files);

        UUID uuid = UUID.randomUUID();
        String videotype = files[0].toString().split("\\.")[1];
        String resPath = "";                // 存储地址
        String netVideoPath = "";           // 网络地址
        if (type == 0) {
            resPath = videoPath + uid + "/video/" + uuid + "." + videotype;
            netVideoPath = videoNet + uid + "/video/" + uuid + "." + videotype;
        } else {
            resPath = animationPath + "resource/" + uuid + "." + videotype;
            netVideoPath = animationNet + "resource/" + uuid + "." + videotype;
        }
        File resFile = new File(resPath);         // 合并后的文件
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(resFile));
        // 向合并文件写的流
        RandomAccessFile raf_rw = new RandomAccessFile(resFile, "rw");
        // 缓冲区
        byte[] bytes = new byte[1024];

        for (File file : files) {
            // 读分块的流
//            System.out.println(file);
            RandomAccessFile raf_r = new RandomAccessFile(file, "r");
            int len = -1;
            // 从分块中读取数据到缓冲区
            while ((len = raf_r.read(bytes)) != -1) {
                // 将缓冲区的数据写入合并文件
                raf_rw.write(bytes, 0, len);
            }
            raf_r.close();
            // 删除原来的切片文件
            file.delete();
        }
        raf_rw.close();
        out.close();

        Video video = new Video();
        video.setVid(vid);
        video.setPath(netVideoPath);
        videoMapper.updateinfo(video);
    }


    // 检查视频完整性，是否能够播放
    public static boolean checkVideo(String filePath) {
        long total = 0;
        try (InputStream is = new FileInputStream(filePath)) {
            long realSize = new File(filePath).length();

            boolean readLarge = false;
            long size;
            byte[] buff = new byte[8];
            while (is.read(buff,0,buff.length) != -1) {
                ByteBuffer wrap = ByteBuffer.wrap(buff);
                if (readLarge) {
                    size = wrap.getLong();
                }else {
                    size = Integer.toUnsignedLong(wrap.getInt());
                }

                if (size == 0) {
                    break;
                }
                if (size == 1) {
                    //读取largeSize
                    readLarge = true;
                }else {
                    total += size;
                    long skip;
                    if (readLarge) {
                        skip = size - 16;//跳过size + type + largeSize
                    }else {
                        skip = size - 8;
                    }
                    if (skip > 0) {
                        is.skip(skip);
                    }
                    readLarge = false;
                }
            }
            return realSize == total;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sortList(File[] files) {
        for (int i = 0; i < files.length; i++) {
            Integer min = i;
            Integer ind1 = Integer.parseInt(files[i].getName().split("\\.")[0].split("_")[1]);
            for (int j = i + 1; j < files.length; j++) {
                Integer ind2 = Integer.parseInt(files[j].getName().split("\\.")[0].split("_")[1]);
                if (ind2 < ind1) {
                    min = j;
                    ind1 = ind2;
                }
            }
            if (min > i) {
                File temp = files[i];
                files[i] = files[min];
                files[min] = temp;
            }
        }
    }

    // 获取视频完整信息(视频页面)
    public Video getByVid(Integer vid, Integer uid) {
        Video res = videoMapper.getByVid(vid);
        adduserinfoone(res);          // 加载作者相关信息
        // 加载tag信息
        String s = res.getMaintag() + " " + res.getOthertags();
        String[] ss = s.split(" ");
        res.setTags(ss);
        
        if (uid == -1) { // 未登录
            return res;
        } else{
            // 获取用户与视频有关信息， 是否已经观看过，是否已经点赞，投币。。。
            Watchinfo watchinfo = videoMapper.getLastWatch(vid, uid);
            if (watchinfo != null) {
                res.setLastweatched(watchinfo.getLastwatched());
                res.setDone(watchinfo.getDone());
            } else {
                res.setLastweatched(0);
                res.setDone(0);
            }

            Integer like = videoMapper.getUserLikes(vid, uid);
            if (like == 1) res.setLiked(true);
            else res.setLiked(false);

            Integer icon = videoMapper.getUserIcon(vid, uid);
            if (icon >= 1) res.setIconed(true);
            else res.setIconed(false);

            Integer fav = videoMapper.getUserFav(vid, uid);
            if (fav >= 1) res.setFaved(true);
            else res.setFaved(false);

            return res;
        }
    }

    // 修改数据
    public void updateVideoInfo(VideoInfos videoInfos) {
        Integer uid = videoInfos.getUid();   // 视频up的uid
        Integer vid = videoInfos.getVid();
        Integer fid = videoInfos.getFid();
        Integer typestyle = videoInfos.getType();

        Video video = new Video();
        video.setVid(videoInfos.getVid());
        video.setUid(videoInfos.getUid());
        if (typestyle == 0) {
            // 观看信息
            Watchinfo watchinfo = videoMapper.getLastWatch(vid, uid);
            Integer lastwatched = videoInfos.getLastwatched();
            Integer done = videoInfos.getDone();
            if (watchinfo != null) {
                Integer id = watchinfo.getId();
                //2
                Date now1 = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(now1);
                // 未删除，直接更新
                if (watchinfo.getDeleted() == 0) {
                    videoMapper.updateWatchinfo(id, done, lastwatched, time, null);
                } else {   // deleted = 1 ，后更新时间
                    videoMapper.updateWatchinfo(id, done, lastwatched, time, 0);
                }
            }
            else {
                // 新建记录
                videoMapper.addWatch(uid, vid, lastwatched, done);
                Integer playsnum = 1;
                video.setPlays(playsnum);
            }
        } else if (typestyle == 1) {
            // likes
            // 先检查是否已有了
            Integer res = videoMapper.exist(uid, vid);
            Integer likes = 0;
            if (res == 0) {
                Integer hisuid = videoInfos.getHisuid();
                videoMapper.addLike(uid, vid, 0, hisuid);
                likes = 1;
            } else {
                videoMapper.deletedLike(uid, vid);
                likes = -1;
            }
            video.setLikes(likes);
        } else if (typestyle == 2) {
            // iocns  投币不能撤销
            Integer res = videoMapper.existedIcon(uid, vid);
            if (res == 0) {
                videoMapper.addIcon(uid, vid);
                Integer icons = videoInfos.getIcons();
                video.setIcons(icons);
                userMapper.touseIocn(uid, icons);   // 减少icon
            }
        }
        // 单独处理
//        else if (typestyle == 3) {
//            // favorite
//            videoMapper.addFav(uid, vid, fid);
//            Integer favorites = videoMapper.getFavorites(vid);
//            video.setFavorites(favorites);
//        }
        else if (typestyle == 4){
            //share   分享可以无限分享
            videoMapper.addShare(uid,vid);
            Integer shares = 1;
            video.setShares(shares);
        } else if (typestyle == 5){
            // danmu
            videoMapper.addShare(uid,vid);
            Integer shares = 1;
            video.setShares(shares);
        } else if (typestyle == 6){
            // comments
            videoMapper.addShare(uid,vid);
            Integer shares = 1;
            video.setShares(shares);
        } else if (typestyle == 7) {
            Integer comments = videoMapper.getComments(vid);
            video.setComments(comments);
        } else if (typestyle == 9) {
            Integer danmus = videoMapper.getDanmus(vid);
            video.setDanmus(danmus);
        } else if (typestyle == 10) {
            video.setListid(videoInfos.getListid());
        }

        videoMapper.updateVideoInfo(video);
    }

    public List<Video> getVideoLikely(Integer vid) {
//        List<Video> res = videoMapper.getVideoLikely(vid);
        List<Video> res = videoMapper.getRecommend();
        adduserinfo(res);
        return res;
    }

    public List<Watchinfo> getHistory(Integer uid) {
        List<Watchinfo> res = videoMapper.getHistory(uid);
        hisinfos(res);
        return res;
    }

    public List<Watchinfo> getHomHistory(Integer uid, Integer page, Integer num, Integer size) {
        Integer start = size * page;
        List<Watchinfo> res = videoMapper.getHomHistory(uid, start, num);
        hisinfos(res);
        return res;
    }

    // 处理观看信息
    private void hisinfos (List<Watchinfo> res) {
        for (int i = 0; i < res.size(); i++) {
            Integer vid = res.get(i).getVid();
            Video video = videoMapper.getByVid(vid);

            res.get(i).setUpuid((video.getUid()));
            res.get(i).setTitle(video.getTitle());
            res.get(i).setCover(video.getCover());
            res.get(i).setTag(video.getMaintag());

            Integer upuid = video.getUid();
            User user = userMapper.getByUid(upuid);
            res.get(i).setUpname(user.getName());
            res.get(i).setUpavatar(user.getAvatar());
            res.get(i).setUpuid(upuid);

            // 设置格式化时间
            Integer watchnum = res.get(i).getLastwatched();
            int h = watchnum / 3600;
            int m = watchnum / 60;
            int s = watchnum % 60;
            String hh = h >= 10 ? "" + h : "0" + h;
            String mm = m >= 10 ? "" + m : "0" + m;
            String ss = s >= 10 ? "" + s : "0" + s;
            String restime = mm + ":" + ss;
            if (h > 0) restime = hh + ":" + restime;
            res.get(i).setWatchtype(restime);
        }
    }
    public void deleteHistory(Integer uid, Integer vid) {
        videoMapper.deleteHistory(uid, vid);
    }

    public void deleteAll(Integer uid) {
        videoMapper.deleteAll(uid);
    }

    public List<Video> getVideoByUid(Integer uid, Integer nums) {
        List<Video> res = new ArrayList<>();
        if (nums == 0) {
            res.addAll(videoMapper.getVideoByUid(uid));
        } else {
            res.addAll(videoMapper.getVideoByUidNums(uid, nums));
        }
        adduserinfo(res);
        return res;
    }

    public List<Video> getRank(Integer type) {
        List<Video> res = new ArrayList<>();
        if (type == 0) {
            // 综合热门
            List<Video> temp = videoMapper.getRank1();
            res.addAll(temp);
        } else if (type == 1) {
            // 必刷榜
            List<Video> temp = videoMapper.getRank2();
            res.addAll(temp);
        } else if (type == 2) {
            // 总排行
            List<Video> temp = videoMapper.getRank3();
            res.addAll(temp);
        }
        adduserinfo(res);
        return res;
    }

    public List<Video> getByKeywordAll(String keyword, Integer sort1, Integer sort2, Integer sort3) {
        List<Video> res = new ArrayList<>();
        if (sort2 == 0) {
            res.addAll(videoMapper.getByKeyword(keyword));  // 标题结果
        } else {
            // < 10 min
            if (sort2 == 1) {
                res.addAll(videoMapper.getByDuration1(keyword, 0, 600));
            } else if (sort2 == 2) {  // 10min~30min
                res.addAll(videoMapper.getByDuration1(keyword, 601, 1800));
            } else if (sort2 == 3) {
                res.addAll(videoMapper.getByDuration1(keyword, 1801, 3600));
            } else if (sort2 == 4) {
                res.addAll(videoMapper.getByDuration1(keyword, 3600, 36000));
            }
        }
        // 用户名结果
        List<Integer> res2 = videoMapper.searchUserName(keyword);
        for (int i = 0; i < res2.size(); i++) {
            List<Video> temp = videoMapper.getVideoByUid(res2.get(i));
            if (sort1 == 0) {
                res.addAll(temp);
            }
            if (sort2 == 1) {
                for (int j = 0; j < temp.size(); j++) {
                    if (temp.get(j).getDuration() >= 0 && temp.get(j).getDuration() <= 600) {
                        res.add(temp.get(j));
                    }
                }
            } else if (sort2 == 2) {  // 10min~30min
                for (int j = 0; j < temp.size(); j++) {
                    if (temp.get(j).getDuration() >= 601 && temp.get(j).getDuration() <= 1800) {
                        res.add(temp.get(j));
                    }
                }
            } else if (sort2 == 3) {
                for (int j = 0; j < temp.size(); j++) {
                    if (temp.get(j).getDuration() >= 1801 && temp.get(j).getDuration() <= 3600) {
                        res.add(temp.get(j));
                    }
                }
            } else if (sort2 == 4) {
                for (int j = 0; j < temp.size(); j++) {
                    if (temp.get(j).getDuration() >= 3601 && temp.get(j).getDuration() <= 36000) {
                        res.add(temp.get(j));
                    }
                }
            }
        }

        adduserinfo(res);
        if (sort1 == 0) {
            Collections.sort(res, new Comparator<Video>() {
                @Override
                public int compare(Video o1, Video o2) {
                    return o1.getPlays() - o2.getPlays();
                }
            });
        } else if (sort1 == 1) {
            Collections.sort(res, new Comparator<Video>() {
                @Override
                public int compare(Video o1, Video o2) {
                    return o1.getPlays() - o2.getPlays();
                }
            });
        } else if (sort1 == 2) {
            Collections.sort(res, new Comparator<Video>() {
                @Override
                public int compare(Video o1, Video o2) {
                    return o1.getVid() - o2.getVid();
                }
            });
        } else if (sort1 == 3) {
            Collections.sort(res, new Comparator<Video>() {
                @Override
                public int compare(Video o1, Video o2) {
                    return o1.getFavorites() - o2.getFavorites();
                }
            });
        } else if (sort1 == 4) {
            Collections.sort(res, new Comparator<Video>() {
                @Override
                public int compare(Video o1, Video o2) {
                    return o1.getDanmus() - o2.getDanmus();
                }
            });
        }
        return res;
    }
    public List<Video> getByKeywordName(String keyword) {
        List<Integer> uids = userMapper.getUidByKeyword(keyword);
        for (int i = 0; i < uids.size(); i++) {
            
        }
        List<Video> res = new ArrayList<>();
        return res;
    }

    public List<Video> getHomeDynamicVideo(Integer uid, Integer page, Integer num, Integer size) {
        Integer start = page * num;
        List<Video> res = videoMapper.getHomeDynamicVideo(uid, start, num);
        adduserinfo(res);
        return res;
    }

    public void sendDm(Dm dm) {
        if (dm.getColor() == null) {
            dm.setColor("#fff");
        }
        videoMapper.sendDm(dm);
        videoMapper.updateVideoDm(dm.getVid());
    }

    public List<Dm> getDm(Integer vid) {
        List<Dm> res = videoMapper.getDm(vid);
        for (int i = 0; i < res.size(); i++) {
            Integer t = res.get(i).getSendtime();
            String hh = t / 3600 >= 10 ?  "" + t / 3600 : "0" + t / 3600;
            String mm = t / 60 >= 10 ? "" + t / 60 : "0" + t / 60;
            String ss = t % 60 >= 10 ? "" + t % 60 : "0" + t % 60;
            String type = t >= 3600 ? hh+":"+mm+":"+ss  : mm+":"+ss;
            res.get(i).setTypetime(type);
        }
        return res;
    }

    public List<String> searchKw(String keyword) {
        List<String> res1 = videoMapper.searchName(keyword);
        List<String> res2 = videoMapper.searchTilte(keyword);
        res1.addAll(res2);
        // 添加hot_keyword
//        searchMapper.addKeyword(uid, keyword);
        return res2;
    }

    public List<Video> getFamous(Integer uid) {
        List<Video> res = videoMapper.getFamous(uid);
        return res;
    }

    public void changeFamous(Integer uid, Integer[] vids) {
        List<Video> last = videoMapper.getFamous((uid));
        for (int i = 0; i < last.size(); i++) {
            userMapper.updateFamous(last.get(i).getVid(), 0);
        }
        for (int i = 0; i < vids.length; i++) {
            userMapper.updateFamous(vids[i], 1);
        }
    }

    public List<Video> getUnfamous(Integer uid, Integer type) {
        String t = type == 0 ? "time" : "plays";
        List<Video> res = videoMapper.getUnfamous(uid, t);
        return res;
    }

    public List<User> getFollowedUser(Integer uid) {
        List<User> res = new ArrayList<>();
        List<Integer> follows = userMapper.finfAllFollows(uid, 0, 0, "");
        for (int i = 0; i < follows.size(); i++) {
            res.add(userMapper.getByUid(follows.get(i)));
        }
        return res;
    }
    public List<Video> getHomeDynamic(Integer uid) {
        List<Video> res = new ArrayList<>();
        List<Integer> follows = userMapper.finfAllFollows(uid, 0, 0, "");
        for (int i = 0; i < follows.size(); i++) {
            List<Video> temp = videoMapper.getVideoByUid(follows.get(i));
            res.addAll(temp);
        }
        Collections.sort(res, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                return o2.getVid() - o1.getVid();
            }
        });
        if (res.size() > 50) {
            res.subList(0, 50);
        }
        adduserinfo(res);
        return res;
    }

    public List<Video> getAuditList() {
        List<Video> res = videoMapper.getAuditVideo();
        adduserinfo(res);
        return res;
    }

    public void oneResult(Audit audit) {
        videoMapper.updatePass(audit.getVid(), audit.getPass());
        Integer uid = audit.getUid();
        String content = audit.getContent();
        String vidtitle = audit.getTitle();
        String ititle = "";
        if (audit.getPass() == 1) {
            ititle = "稿件" + vidtitle + "通过体通知";
            // 审核通过后在发送动态==========
            Dynamic dynamic = new Dynamic();
            dynamic.setVordid(audit.getVid());
            dynamic.setUid(uid);
            dynamic.setType(3);
            dynamicMapper.sendDynamic(dynamic);
        } else {
            ititle = "稿件" + vidtitle + "未通过体通知";
        }
        sysInfoMapper.addInfo(uid, ititle, content);
    }

    public List<Video> getAllv(Integer uid) {
        List<Video> res = videoMapper.getAllv(uid);
        adduserinfo(res);
        return res;
    }

    public void userdeletevideo(Integer vid) {
        userMapper.userdeletevideo(vid);
    }

    public void userChnageInfo(Video video) {
        userMapper.userChnageInfo(video);
    }

    public List<VideoList> getUserVideoList(Integer uid) {
        List<VideoList> res = videoMapper.getUserVideoList(uid);
        for (int i = 0; i < res.size(); i++) {
            Integer listid = res.get(i).getListid();
            Integer nums = videoMapper.getNumsOfVideoList(listid);
            res.get(i).setNums(nums);
            if (nums == 0) {
                // 为空时默认
                res.get(i).setCover(neturl + "/sys/playlistbg.png");
            } else {
                List<Integer> vids = getAllListVideo(res.get(i).getListid());
                Video video = videoMapper.getByVid(vids.getFirst());
                if (video.getPass() == 0) {
                    res.get(i).setCover(neturl + "/sys/playlistbg.png");
                } else {
                    String cover = video.getCover();
                    res.get(i).setCover(cover);
                }
            }
        }
        return res;
    }

    public List<Integer> getAllListVideo(Integer listid) {
        List<Integer> res = videoMapper.getAllListVideo(listid);
        return res;
    }
    public Integer addVideoList(VideoList videoList) {
        videoMapper.addVideoList(videoList);
        return videoList.getListid();
    }

    public void addVideoToList(Integer listid, Integer uid, List<Integer> vids) {
        for (int i = 0; i < vids.size(); i++) {
            Integer vid = vids.get(i);
            Video video = new Video();
            video.setVid(vid);
            video.setListid(listid);
            VideoListInfo videoListInfo = new VideoListInfo();
            videoListInfo.setVid(vid);
            videoListInfo.setListid(listid);
            videoListInfo.setUid(uid);
            videoMapper.addVideoToList(videoListInfo);
            videoMapper.updateinfo(video);
        }
    }

    // 得到一个列表中的视频(已经添加过的)
    public List<Video> getVideoFormList(Integer listid) {
        List<Video> res = videoMapper.getVideoFormList(listid);
        return res;
    }

    public List<Video> getUnaddVideo( Integer uid) {
        List<Video> res =  videoMapper.getUnExitList(uid);
        return res;
    }

    public List<Video> getByMaintag(String maintag) {
        List<Video> res = videoMapper.getByMaintag(maintag);
        adduserinfo(res);
        return res;
    }

    public List<TagAndNums> getAllMainTag() {
//        List<String> tags = videoMapper.getAllMainTag();
//        List<Object> res = new ArrayList<>();
//        for (int i = 0; i < tags.size(); i++) {
//            String maintag = tags.get(i);
//            Map<String, Integer> tag = new HashMap<>();
//            Integer videos = videoMapper.videoNumsMainTag(maintag);
//            tag.put(maintag, videos);
//            res.add(tag);
//        }
        List<TagAndNums> res = videoMapper.getTandN();
        return res;
    }


    public VideoList getUserListOne(Integer listid) {
        VideoList res = videoMapper.getUserListOne(listid);
        return res;
    }

    public void deleteVideoList(Integer listid) {
        // 删除列表
        videoMapper.deleteVideoList(listid);
        // 更改列表中的视频信息
        videoMapper.updateVideoInfoListid(listid);
    }

    public void chanegListInfo(VideoList videoList) {
        videoMapper.chanegListInfo(videoList);
    }

    public List<Video> getVideoByKeyword(Integer uid, String keyword) {
        List<Video> res = videoMapper.getVideoByKeyword(uid, keyword);
        adduserinfo(res);
        return res;
    }

    public List<Video> getSomeVideos(Map<String, Object> map) {
        List<Integer> vids = (List<Integer>)map.get("vids");
        Integer num = (Integer)map.get("num");
        List<Video> res = videoMapper.getRandom(vids, num);
        adduserinfo(res);
        return res;
    }

    public List<VideoClassify> getAllClassify() {
        return videoMapper.getAllClassify();
    }

    public void addClassify(String value) {
        videoMapper.addClassify(value);
    }

}
