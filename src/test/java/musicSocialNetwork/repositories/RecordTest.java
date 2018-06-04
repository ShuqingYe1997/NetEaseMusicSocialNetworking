package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.HasRecord;
import musicSocialNetwork.domain.Record;
import musicSocialNetwork.domain.Song;
import musicSocialNetwork.domain.User;
import netEaseMusic.connect.Follow;
import netEaseMusic.connect.SongRecord;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class RecordTest {

    //2度关系的听歌记录

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  SongRepository songRepository;

    @Before
    public void setUp() {
        songRepository.deleteAll();
        userRepository.deleteAll();

        int count=2;

        int userId1 = 78553816;
        String nickname1 = "叶叶bai";
        User user1 = new User(userId1, nickname1, 2, "");
        userRepository.save(user1);

        recursive_find(user1,count,userRepository);

        Iterable<User> all = userRepository.findAll();
        Iterator<User> allUser = all.iterator();

        while (allUser.hasNext()) {
            User user = allUser.next();
            SongRecord songRecord=new SongRecord(user.getUserId());
            List<Record> yearRecords=songRecord.getYearRecords();
            List<Record> weekRecords=songRecord.getWeekRecords();
            saveRecords(user,yearRecords,songRepository);
            saveRecords(user,weekRecords,songRepository);
            userRepository.save(user);
        }
    }

    public void saveRecords(User user,List<Record> records,SongRepository songRepository){
        for(int i=0;i<records.size();i++){
            Record record=records.get(i);
            Song song=record.getSong();
            int score=record.getScore();
            String type=record.getType();

            List<Song> old=songRepository.findSongsBySongId(song.getSongId());  //为什么是list，我到现在都没搞懂
            if(old.size()!=0) {
                song = old.get(0);
            }
            else
                songRepository.save(song); //不加不行啊，user没把song存进去
            HasRecord hasRecord=new HasRecord(user,song,score,type);
            user.addRecord(hasRecord);
        }
    }

    private void recursive_find(User root , int count ,UserRepository userRepository){
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
                    recursive_find(sub_root, c,userRepository);
                    root.follow(sub_root);
                    userRepository.save(root);
                }
            }
        }
    }
}
