package netEaseMusic.connect;

import musicSocialNetwork.domain.Tag;
import netEaseMusic.secret.JSSecret;
import netEaseMusic.util.Api;
import netEaseMusic.util.Parse;
import netEaseMusic.util.UrlParamPair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.*;

public class UserTag {
    private int rootId;
    private Map<String,Integer> tagMap;

    public UserTag(){ }
    public UserTag(int id){
        this.rootId=id;
    }

    public String getRootId() {
        return rootId+"";
    }

    public List<Map.Entry<String,Integer>> getTags(){
        List<Map.Entry<String, Integer>> top=new ArrayList<>();

        getTagMap();
        if(tagMap!=null) {
            List<Map.Entry<String, Integer>> sort = new ArrayList<>(tagMap.entrySet());
            Collections.sort(sort, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            int endIndex = 3;
            if (sort.size() < endIndex) endIndex = sort.size();
            top = sort.subList(0, endIndex);
        }
        return top;
    }

    private void getTagMap(){
        List<String> tagList=getTag();
        System.out.println(tagList);
        for(int i=0;i<tagList.size();i++){
            String tag=tagList.get(i);
            if(tagMap==null)
                tagMap=new HashMap<>();
            if(!tagMap.containsKey(tag)){
                tagMap.put(tag,1);
            }
            else
            {
               int count= tagMap.get(tag);
               tagMap.remove(tag);
               tagMap.put(tag,count+1);
            }
        }
    }

    private List<String> getTag(){
        List<String> tagList=new ArrayList<>();
        try {
            UrlParamPair upp = Api.getPlaylist(getRootId());
            String req_str = upp.getParas().toJSONString();
            System.out.println("req_str:"+req_str);
            JSSecret.getDatas(req_str);

            Connection.Response
                    response = Jsoup.connect("http://music.163.com/weapi/user/playlist?csrf_token=")
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
            Parse parse=new Parse(list);
            tagList=parse.getTags();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tagList;
    }

}
