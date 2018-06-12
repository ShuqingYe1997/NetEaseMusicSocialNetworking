package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.User;
import netEaseMusic.connect.Follow;
import netEaseMusic.connect.Followed;
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
public class FollowTest {

    int rootId=78553816;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();

        //todo:修改硬编码
        User root = new User(rootId, "叶叶bai",2,"http://p1.music.126.net/ZYpAshtgcH7o4WEry1_E2Q==/109951163217532115.jpg");
        root.setSignature("主业划水，副业养生");
        root.setFollows(10);
        root.setFolloweds(4);

        userRepository.save(root);

        List<User> follows =new Follow(rootId).getFollows();
        for(int i=0;i<follows.size();i++) {
            userRepository.save(follows.get(i));
            root.follow(follows.get(i));
        }

        List<User> followeds=new Followed(rootId).getFolloweds();
        for (int i = 0; i <followeds.size() ; i++) {
            User u=followeds.get(i);
            u.follow(root);
            userRepository.save(u);
        }

    }


//    @Test
//    public void testFindByNickname1() {
//
//        String nickname = "RDJSVIP";
//        User result = userRepository.findByNickname(nickname);
//        assertNotNull(result);
//        assertEquals(87565717, result.getUserId());
//    }//pass
//
//    @Test
//    public void testFindByFollow1(){
//        User root=userRepository.findUserByUserId(rootId);
//        assertEquals("网易UFO丁磊", root.getFollow().iterator().next().getNickname());
//    }//pass

    @Test
    public void testFindByNickname2(){
        String nickname="薛壕";
        User result = userRepository.findByNickname(nickname);
        assertNotNull(result);
    }

    @Test
    public void testFindByFollow2(){
        String nickname="薛壕";
        User result = userRepository.findByNickname(nickname);
        assertEquals("叶叶bai", result.getFollow().iterator().next().getNickname());

    }

}
