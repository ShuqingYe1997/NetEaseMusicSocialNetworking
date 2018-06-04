package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.User;
import netEaseMusic.connect.Follow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class FFFTest1 {

    // 添加关注
    //目前有500+用户
    int rootId=78553816;
    String rootNickname="叶叶bai";
    int count=2;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();

        User root = new User(rootId, rootNickname,2,"http://p1.music.126.net/ZYpAshtgcH7o4WEry1_E2Q==/109951163217532115.jpg");

        recursive_find(root,count);
    }

    private void recursive_find(User root , int count ){
        if(count>0){

            List<User> follow =new Follow(root.getUserId()).getFollows();

            for (int i = 0; i <follow.size() ; i++) {
                User sub_root = follow.get(i);
                User old=userRepository.findUserByUserId(sub_root.getUserId());

                if (!sub_root.getNickname().equals("帐号已注销")) {
                    if (old!=null) {
                        sub_root=old;   //很重要的一步，把老节点拉出来
                    }
                    int c = count - 1;
                    recursive_find(sub_root, c);
                    root.follow(sub_root);
                    userRepository.save(root);
                }
            }
        }
    }

    @Test
    public void testTotal(){
        User user=userRepository.findByNickname("RDJSVIP");
        assertEquals(87565717,user.getUserId());
    }
}
