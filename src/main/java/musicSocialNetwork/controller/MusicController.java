package musicSocialNetwork.controller;

import musicSocialNetwork.domain.HasRecord;
import musicSocialNetwork.domain.HasTag;
import musicSocialNetwork.domain.Song;
import musicSocialNetwork.domain.User;
import musicSocialNetwork.services.MusicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * Created by smcsvip on 2018/6/5.
 */
@Controller
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService){
        this.musicService = musicService;
    }


    /**
     * 获取用户输入的userId
     */
    @GetMapping("/get")
    public String getUserId(Model model) {
        model.addAttribute("user",new User());   //同名属性
        return "signin";
    }

    /**
     * 根据用户输入的userId获取该user
     */
    @PostMapping("/get")
    public String printByuserId(@ModelAttribute User user,Model model) {
        User tmp = musicService.findByuserId(user.getUserId());
        model.addAttribute("user",tmp);
//todo:删除之后关注者的tag出不来？？
//        if(tmp.getFollow()!=null){
//            for (User user1 : tmp.getFollow()) {
//                int userId = user1.getUserId();
//                User follow = musicService.findByuserId(userId);
//                System.out.println(follow.getHasTags());
//            }
//        }
        List<User> userFollowed = musicService.findFolloweds(musicService,tmp);
        model.addAttribute("userFollowed",userFollowed);

        return "hello";
    }

    @RequestMapping("/return/{id}")
    public String returnToHello(@PathVariable int id, Model model){
        User tmp = musicService.findByuserId(id);
        model.addAttribute("user",tmp);

        List<User> userFollowed = musicService.findFolloweds(musicService,tmp);
        model.addAttribute("userFollowed",userFollowed);

        return "hello";
    }

    /**
     * 根据tag相似度推荐新朋友
     */
    @RequestMapping(value = "/user/{id}")
    public String getUserList(@PathVariable int id, Model model){
        User tmp = musicService.findByuserId(id);
        model.addAttribute("user",tmp);

        List<HasTag> tmpHasTags = musicService.findTagsByUserId(musicService,id);  //我
        List<User> allUser = musicService.getAllUser();

        //相同品味的人
        model.addAttribute("tagFriends",musicService.getNewFriends(musicService,tmp,allUser,tmpHasTags));

        //可能认识的人
        model.addAttribute("skipFriends",musicService.updateNewFriendByF(tmp.getUserId()));

        //听过同样歌曲的人
        model.addAttribute("songFriends",musicService.updateNewFriendBySong(tmp.getUserId()));

        return "helloNewFriends";
    }

    @RequestMapping(value = "/update")
    public String update(Model model, int userId, int followId){
        User me = musicService.findByuserId(userId);
        User friend = musicService.findByuserId(followId);

        musicService.updateNewFriendByTag(me.getUserId(),friend.getUserId());
        model.addAttribute("user",me);

        return "success";
    }
}
