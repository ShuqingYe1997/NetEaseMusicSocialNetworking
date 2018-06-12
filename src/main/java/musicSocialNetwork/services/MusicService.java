package musicSocialNetwork.services;

import musicSocialNetwork.domain.*;
import musicSocialNetwork.repositories.TagRepository;
import musicSocialNetwork.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.lang.Math.sqrt;


/**
 * Created by smcsvip on 2018/6/5.
 */
@Service
public class MusicService{
    private final static Logger LOG = LoggerFactory.getLogger(MusicService.class);

    private final UserRepository userRepository;
    //private final TagRepository tagRepository;

    public MusicService(UserRepository userRepository){
        this.userRepository = userRepository;
        //this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUser(){
        Iterable<User> get = userRepository.findAll();
        List<User> list = new ArrayList<>();
        get.forEach(list::add);
        return list;
    }

    @Transactional(readOnly = true)
    public User findByuserId(int userId){
        return userRepository.findUserByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<User> findFolloweds(MusicService musicService, User user){
        List<User> userFollowed = new ArrayList<>();
        for(User user2 : musicService.getAllUser()){
            //System.out.println(user2.getNickname());
            //System.out.println(user2.getFollow());
            if(user2.getFollow()!=null){
                for(User user3 : user2.getFollow()){
                    if(user3 == user)
                        userFollowed.add(user2);
                    //System.out.println(user2.getNickname());
                }
            }
        }
        return userFollowed;
    }

    @Transactional(readOnly = true)
    public List<HasTag> findTagsByUserId(MusicService musicService, int id){
        List<HasTag> userTags = new ArrayList<>();

        User tmp = musicService.findByuserId(id);
        Iterable<HasTag> hasTags = tmp.getHasTags();
        if(hasTags!=null){
            hasTags.forEach(userTags::add);

            userTags.sort(new Comparator<HasTag>() {
                @Override
                public int compare(HasTag o1, HasTag o2) {
                    return  o2.getCount()-o1.getCount();
                }
            });
        }

        return userTags;
    }

    @Transactional(readOnly = true)
    public double computeSquareSum(List<HasTag> list){
        double sum = 0;
        for(HasTag hasTag : list){
            sum += hasTag.getCount()*hasTag.getCount();
        }
        return sum;
    }

    @Transactional(readOnly = true)
    public double computeSimilarity(List<HasTag> list1, List<HasTag> list2){
        double sum1 = 0, sum2 = 0, sim = 0;
        for(HasTag hasTag1 : list1){
            for(HasTag hasTag2 : list2){
                if(hasTag1.getTag().getTitle().equals(hasTag2.getTag().getTitle())){
                    sum1 += hasTag1.getCount()*hasTag2.getCount();
                }
            }
        }
        sum2 = sqrt(computeSquareSum(list1)) * sqrt(computeSquareSum(list2));
        sim = sum1/sum2;
        return sim*1000;
    }

    public void updateNewFriendByTag(int userId1,int userId2){
        userRepository.update(userId1,userId2);
    }

    public List<User> updateNewFriendByF(int userId){
        return userRepository.findSkipFollowers(userId);
    }

    public Map<User,String> updateNewFriendBySong(int userId) {
        List<HasRecord> tmp = userRepository.updateBySong(userId);
        Map<User, String> distinctFriends = new HashMap<>();

        if (tmp != null) {
            for (int i = 0; i < tmp.size(); i++) {
                HasRecord hasRecord = tmp.get(i);
                User user = hasRecord.getUser();
                String info = hasRecord.getSong().toString();

                if (!distinctFriends.containsKey(user)) {
                    distinctFriends.put(user, info);
                } else {
                    String oldInfo = distinctFriends.get(user);
                    distinctFriends.remove(user);
                    distinctFriends.put(user, oldInfo + " , " + info);
                }
            }
        }
//        for(Map.Entry<User,String> entry:distinctFriends.entrySet())
//            System.out.println(entry.getKey().toString()+" "+entry.getValue());
        return distinctFriends;
    }

    public List<Map<User,Double>> getNewFriends(MusicService musicService,User tmp,List<User> allUser,List<HasTag> tmpHasTags){
        List<Map<User,Double>> newFriends = new ArrayList<>();
        for(User each : allUser){
            if(tmp.getFollow()!=null){
                if(each!=tmp && each.getHasTags()!=null && newFriends.size()<10 && !tmp.getFollow().contains(each)){
                    List<HasTag> eachHasTags = findTagsByUserId(musicService,each.getUserId());  //其他人
                    Map<User,Double> map = new HashMap<>();
                    map.put(each,computeSimilarity(tmpHasTags,eachHasTags));
                    newFriends.add(map);
                }
            }
        }
        Collections.sort(newFriends, new Comparator<Map<User, Double>>() {
            @Override
            public int compare(Map<User, Double> o1, Map<User, Double> o2) {
                double d1=0,d2=0;
                for (Map.Entry<User, Double> entry : o1.entrySet()) {
                    d1 = entry.getValue();
                }
                for (Map.Entry<User, Double> entry : o2.entrySet()) {
                    d2 = entry.getValue();
                }
                return (int)(d2-d1);
            }
        });
        return newFriends;
    }
}
