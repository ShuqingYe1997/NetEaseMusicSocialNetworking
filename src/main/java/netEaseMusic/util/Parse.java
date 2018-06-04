package netEaseMusic.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import musicSocialNetwork.domain.*;
import musicSocialNetwork.domain.Record;
import musicSocialNetwork.domain.Song;

import java.util.*;

public class Parse {

    private String data;

    public Parse() {
    }

    public Parse(String s) {
        if(s!=null)
            data = s;
    }

    public void setData(String data) {
        if(data!=null)
            this.data = data;
    }

    /**
     * 获取用户的粉丝/用户的关注
     * demo4,5
     * @param type
     * fans=0,following=1
     */
    public List<User> getFollow(int type) {
        List<User> myFollow=new ArrayList<>();

        JSONObject jsonObject = JSONObject.parseObject(data);
        String followType="";
        if(type==0)
            followType="followeds";
        if(type==1)
            followType="follow";
        JSONArray follow = (JSONArray) jsonObject.get(followType);

        for (Object o : follow) {
            JSONObject jo = (JSONObject) o;  //for loop里不能强制类型转换
//            System.out.println(jo);
//            Object userId=jo.get("userId");
//            Object nickname=jo.get("nickname");
//            Object gender=jo.get("gender");

            int userId = (int) jo.get("userId");
            String nickname = (String) jo.get("nickname");
            int gender = (int) jo.get("gender");
            String icon = (String) jo.get("avatarUrl");
            String signature = (String) jo.get("signature");
            int eventCount=(int)jo.get("eventCount");
            int followeds=(int)jo.get("followeds");
            int follows=(int)jo.get("follows");

            User user = new User(userId, nickname, gender, icon);
            user.setSignature(signature);
            user.setEventCount(eventCount);
            user.setFolloweds(followeds);
            user.setFollows(follows);

            //System.out.println(user.toString());
            myFollow.add(user);
        }
        return myFollow;
    }

    /**
     * 获取用户的歌单
     * demo
     */
    public List<String> getTags(){
        List<String>myTag=new ArrayList<>();

        JSONObject jsonObject=JSONObject.parseObject(data);
        JSONArray playlists=(JSONArray)jsonObject.get("playlist");

        for(Object o1:playlists){
            JSONObject playlist=(JSONObject)o1;

//            Object id=playlist.get("id");
//            String name=(String)playlist.get("name");
//            String description=(String)playlist.get("description");
//            int playCount=(int)playlist.get("playCount");
//            int subscribedCount=(int)playlist.get("subscribedCount");
//            int userId=(int)playlist.get("userId");
//
//            Playlist myList=new Playlist(id,name,playCount,subscribedCount,userId);
//            myList.setDescription(description);

//            Object o2=playlist.get("tags");
//            tags belongs to class JSONArray
            JSONArray tags=(JSONArray)playlist.get("tags");
            new ArrayList<>();
            for(int i=0;i<tags.size();i++){
                String tag=(String)tags.get(i);
                myTag.add(tag);
//                System.out.println(tag);
            }
        }
        return myTag;
    }

    public List<Record> getRecords(String type){

        List<Record> records =new ArrayList<>();

        JSONObject recordsObject = JSONObject.parseObject(data);
        String s="allData";
        int recordNum=10;   //限制数据

        if(type.equals("1")) {
            s = "weekData";
            recordNum=5;
        }

        JSONArray recordArray=(JSONArray)recordsObject.get(s);
        if (recordArray==null)
            return new ArrayList<>();
        if(recordArray.size()<recordNum)
            recordNum=recordArray.size();

        for(int i=0;i<recordNum;i++) {

            JSONObject recordObject=(JSONObject)recordArray.get(i);
            //获取热度
            int score = (int) recordObject.get("score");

            //获取歌曲信息
            JSONObject songObject = (JSONObject) recordObject.get("song");
            int id = (int) songObject.get("id");
            String name = (String) songObject.get("name");

            //获取专辑信息
            JSONObject albumObject = (JSONObject) songObject.get("al");
            int albumId = (int) albumObject.get("id");
            String albumName = (String) albumObject.get("name");
            String albumPic = (String) albumObject.get("picUrl");


            //获取歌手信息
            List<String> artists=new ArrayList<>();
            JSONArray artistArray = (JSONArray) songObject.get("ar");
            for (Object o : artistArray) {
                JSONObject jo = (JSONObject) o;
                int aid=(int)jo.get("id");
                String artistName = (String) jo.get("name");
                artists.add(albumName);
            }

            Song song = new Song(id, name, albumName,albumPic,artists);
            Record record = new Record(song,score,type);
            records.add(record);
        } //end for loop
        return records;
    }

    /*
    * 通过用户名获得指定用户
    * 只在Search里用到，目前没用到Search……
    * */
    public User getUser(String name){

        JSONObject recordsObject = JSONObject.parseObject(data);
        JSONArray userArray=(JSONArray)recordsObject.get("userprofiles");

        //todo: 这里要传一个$进来，现在默认数组的第一个
        JSONObject userObject=userArray.getJSONObject(0);
        int userId = (int) userObject.get("userId");
        String nickname = (String) userObject.get("nickname");
        int gender = (int) userObject.get("gender");
        String icon = (String) userObject.get("avatarUrl");
        String signature = (String) userObject.get("signature");
        int eventCount=(int)userObject.get("eventCount");
        int followeds=(int)userObject.get("followeds");
        int follows=(int)userObject.get("follows");

        User user = new User(userId, nickname, gender, icon);
        user.setSignature(signature);
        user.setEventCount(eventCount);
        user.setFolloweds(followeds);
        user.setFollows(follows);

        return user;
    }


}


