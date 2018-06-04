package netEaseMusic.connect;

import musicSocialNetwork.domain.User;
import netEaseMusic.secret.JSSecret;
import netEaseMusic.util.Api;
import netEaseMusic.util.Parse;
import netEaseMusic.util.UrlParamPair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

public class Followed {
    private int rootId;
    public Followed(){}

    public Followed(int id){
        rootId=id;
    }

    public String getRootId() {
        return rootId+"";
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public List<User> getFolloweds() {
        List<User> followeds=new ArrayList<>();

        try {
            UrlParamPair upp = Api.getFollowers(getRootId());
            String req_str = upp.getParas().toJSONString();
            System.out.println("req_str:"+req_str);
            Connection.Response
                    response = Jsoup.connect("http://music.163.com/weapi/user/getfolloweds?csrf_token=")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Refer","http://music.163.com/user/fans/")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
            String list = response.body();

            Parse parse = new Parse(list);
            followeds=parse.getFollow(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return followeds;
    }
}
