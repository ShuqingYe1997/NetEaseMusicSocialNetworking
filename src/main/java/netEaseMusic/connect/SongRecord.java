 package netEaseMusic.connect;

 import musicSocialNetwork.domain.Record;
import musicSocialNetwork.domain.User;
 import netEaseMusic.secret.JSSecret;
 import netEaseMusic.util.Api;
 import netEaseMusic.util.Parse;
 import netEaseMusic.util.UrlParamPair;
 import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
 import java.util.Set;

 public class SongRecord {
    private int userId;
    private List<Record> yearRecords;
    private List<Record> weekRecords;

    public SongRecord() {
    }
    public SongRecord(int id){
        this.userId=id;
    }

    public String getUserId() {
        return userId+"";
    }

    public List<Record> getYearRecords() {
        String year = "0";
        this.yearRecords =  getRecords(year);
        return yearRecords;
    }

    public List<Record> getWeekRecords() {
        String week = "1";
        this.weekRecords = getRecords(week);
        return weekRecords;
    }

    private List<Record> getRecords(String type){
        List<Record> records =new ArrayList<>();

        try {
            UrlParamPair upp = Api.getPlayRecord(getUserId(), type);
            String req_str = upp.getParas().toJSONString();
            System.out.println("req_str:" + req_str);
            Connection.Response
                    response = Jsoup.connect("http://music.163.com/weapi/v1/play/record?csrf_token=")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Referer", " http://music.163.com/user/home/")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
            String list = response.body();

            Parse parse = new Parse(list);
            records =parse.getRecords(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;

    }
}
