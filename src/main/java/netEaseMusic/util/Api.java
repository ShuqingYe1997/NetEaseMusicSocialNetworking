package netEaseMusic.util;

public class Api {
    private final static String BaseURL = "http://music.163.com/";

    /**
     * 获取用户收藏歌单
     *
     * @param uid
     * @return
     */
    public static UrlParamPair getPlaylist(String uid) {
        UrlParamPair upp = new UrlParamPair();
        upp.setUrl(BaseURL + "weapi/user/playlist?csrf_token=");
        upp.addPara("offset", 0);
        upp.addPara("uid", uid);
        upp.addPara("limit",50);  //返回数量
        upp.addPara("csrf_token", "nothing");
        return upp;
    }

    /**
     * 获取歌曲详情
     *
     * @param music_id
     * @return
     */
    public static UrlParamPair getDetailOfMusic(String music_id) {
        UrlParamPair upp = new UrlParamPair();
        upp.addPara("id", music_id);
        upp.addPara("offset", 0);
        upp.addPara("total", "True");
        upp.addPara("limit", 100);
        upp.addPara("n", 1000);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }

    /**
     * 搜索歌曲
     *
     * @param s,type;
     * @return
     */
    public static UrlParamPair Search(String s,String type) {
        UrlParamPair upp = new UrlParamPair();
        upp.addPara("s", s);
        upp.addPara("type",type);
        upp.addPara("offset", 0);
        upp.addPara("total", "True");
        upp.addPara("limit", 20);
        upp.addPara("n", 1000);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }


    /**
     * 获取指定用户的粉丝
     * @param uid
     * @return
     */
    public static UrlParamPair getFollowers(String uid) {
        UrlParamPair upp = new UrlParamPair();
        upp.addPara("userId", uid); // 马鹿，网易云就不能把他们的参数名统一一下吗
        upp.addPara("offset", 0);
        upp.addPara("total", "True");
        upp.addPara("limit", 100);
        //upp.addPara("Refer")
        upp.addPara("csrf_token", "nothing");
        return upp;
    }

    /**
     * 获取用户听歌记录
     * @param uid
     * @param type  0:year  1:week
     * @return
     */

    public static UrlParamPair getPlayRecord(String uid,String type) {
        UrlParamPair upp = new UrlParamPair();
        upp.addPara("uid", uid);
        upp.addPara("type",type);
        upp.addPara("offset", 0);
        upp.addPara("total", "True");
        upp.addPara("limit", 20);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }

}
