package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.HasTag;
import musicSocialNetwork.domain.Tag;
import musicSocialNetwork.domain.User;
import musicSocialNetwork.repositories.UserRepository;
import netEaseMusic.connect.UserTag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class TagTest {

    //最底下有2度关系的Tag代码

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    private int userId = 78553816;
    private String nickname="叶叶bai";
    private User root = new User(userId, nickname,2,"http://p1.music.126.net/ZYpAshtgcH7o4WEry1_E2Q==/109951163217532115.jpg");

    @Before
    public void setUp(){

            userRepository.deleteAll();
            tagRepository.deleteAll();

            int userId1 = 78553816;
            String nickname1 = "叶叶bai";
            User user1 = new User(userId1, nickname1, 2, "");
            userRepository.save(user1);
            UserTag userTag1 = new UserTag(userId1);
            List<Map.Entry<String, Integer>> tags1 = userTag1.getTags();

        int userId2= 87565717;
        String nickname2="RDJSVIP";
        User user2=new User(userId2,nickname2,2,"");
        userRepository.save(user2);
        UserTag userTag2=new UserTag(userId2);
        List<Map.Entry<String, Integer>> tags2 = userTag2.getTags();

        User user=user2;
        List<Map.Entry<String, Integer>> tags=tags2;

        for (int i = 0; i < tags.size(); i++) {
            Map.Entry<String, Integer> entry1 = tags.get(i);
            Tag tag=new Tag(entry1.getKey());
            Integer count=entry1.getValue();

            Tag old=tagRepository.findTagByTitle(tag.getTitle());
            if(old!=null)
                tag=old;
            user.addTag(new HasTag(user,tag,count));
        }
        userRepository.save(user);
    }
    @Test
    public void testPrint(){
        Set<HasTag> hasTags=root.getHasTags();
        Iterator iterator=hasTags.iterator();
        while(iterator.hasNext())
            System.out.println(iterator.next().toString());
    }

    //combine with FFF

//    int rootId = 78553816;
//    String rootNickname = "叶叶bai";
//    int n = 2;
//    User root = new User(rootId, rootNickname, 2, "http://p1.music.126.net/ZYpAshtgcH7o4WEry1_E2Q==/109951163217532115.jpg");
//    recursive_find(root, n, userRepository);
//
//    Iterable<User> all = userRepository.findAll();
//    Iterator<User> allUser = all.iterator();
//
//            while (allUser.hasNext()) {
//        User user = allUser.next();
//        UserTag userTag = new UserTag(user.getUserId());
//        List<Map.Entry<String, Integer>> tags = userTag.getTags();  //got here
//        for (int i = 0; i < tags.size(); i++) {
//            Map.Entry<String, Integer> entry1 = tags.get(i);
//            Tag tag = new Tag(entry1.getKey());
//            Integer count = entry1.getValue();
////                    System.out.println(tag.getTitle()+" "+count);
//            Tag old = tagRepository.findTagByTitle(tag.getTitle());
//            if (old != null)
//                tag = old;
//            user.addTag(new HasTag(user, tag, count));
//        }
//        userRepository.save(user);
//    }
//};
//    }
//
//private void recursive_find(User root , int count ,UserRepository userRepository){
//        if(count>0){
//
//        List<User> follow =new Follow(root.getUserId()).getFollows();
//
//        for (int i = 0; i <follow.size() ; i++) {
//        User sub_root = follow.get(i);
//        User old=userRepository.findUserByUserId(sub_root.getUserId());
//
//        if (!sub_root.getNickname().equals("帐号已注销")) {
//        if (old!=null) {
//        sub_root=old;   //很重要的一步，把老节点拉出来
//        }
//        int c = count - 1;
//        recursive_find(sub_root, c,userRepository);
//        root.follow(sub_root);
//        userRepository.save(root);
//        }
//        }
//        }
//        }

}