package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.HasRecord;
import musicSocialNetwork.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class distinctFriendTest {

    @Autowired
    private UserRepository userRepository;

    private int userId = 87565717;

    @Before
    public void setUp() {
        List<HasRecord> tmp = userRepository.updateBySong(userId);
         Map<User, String> distinctFriends = new HashMap<>();

            if (tmp!=null) {
                for (int i = 0; i < tmp.size(); i++) {
                    HasRecord hasRecord = tmp.get(i);
                    User user = hasRecord.getUser();
                    String info = hasRecord.getSong().toString();

                    if (!distinctFriends.containsKey(user)) {
                        distinctFriends.put(user, info);
                        System.out.println(info);
                    } else {
                        String oldInfo = distinctFriends.get(user);
                        distinctFriends.remove(user);
                        distinctFriends.put(user, oldInfo + " , " + info);
                        System.out.println(oldInfo + " , " + info);
                    }
                }
            }
            System.out.println("=========================================");
            //distinctFriends.size()==0 ???
            for (Map.Entry<User, String> entry : distinctFriends.entrySet())
                System.out.println(entry.getKey().toString() + " " + entry.getValue());
        }

        @Test
    public void test(){

        }
    }