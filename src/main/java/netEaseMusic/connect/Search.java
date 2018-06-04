package netEaseMusic.connect;

import musicSocialNetwork.domain.User;
import netEaseMusic.secret.JSSecret;
import netEaseMusic.util.Api;
import netEaseMusic.util.Parse;
import netEaseMusic.util.UrlParamPair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

//我也不知道这个Class放着什么用，就先放着吧，应该会用得上的……
public class Search {

    private String name;

    public Search() {
    }

    public Search(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User searchUser() {
        User user=new User();

        UrlParamPair upp = Api.Search(name, "1002");
             /*
                type=1		单曲
                type=10		专辑
                type=100	歌手
                type=1000	歌单
                type=1002	用户
                type=1004	MV
                type=1006	歌词
                type=1009	主播电台
*/
        String req_str = upp.getParas().toJSONString();
        System.out.println("req_str:" + req_str);
        try {
            Connection.Response
                    response = Jsoup.connect("http://music.163.com/weapi/cloudsearch/get/web?csrf_token=")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();

            String list = response.body();
            Parse parse = new Parse(list);
            user=parse.getUser(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
