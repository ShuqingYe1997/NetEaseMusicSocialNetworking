package musicSocialNetwork.domain;

import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private int userId;
    private String nickname;
    private int gender;//1=male,2=female,0=unknow
    private String icon;
    private String signature;
    private int eventCount;
    private int followeds;  //粉丝
    private int follows;  //关注

    @Relationship(type = "FOLLOWS")
    private Set<User> follow;

    @Relationship(type = "LISTENED")
    private Set<HasRecord> hasRecords;

    @Relationship(type="INTERESTED_IN")
    private Set<HasTag> hasTags;


    public User() { }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public User(int userId, String nickname, int gender, String icon) {
        this.gender = gender;
        this.nickname = nickname;
        this.userId = userId;
        this.icon = icon;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        if (signature == null)
            this.signature = "";
        else
            this.signature = signature;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public int getFolloweds() {
        return followeds;
    }

    public int getFollows() {
        return follows;
    }

    public void setFolloweds(int followeds) {
        this.followeds = followeds;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    @Override
    public String toString() {
        String result = getUserId() + " " + nickname + " " + gender + " " + icon + " " + signature + "\n";
        result += "eventCount: " + eventCount + " follows: " + follows + " followeds: " + followeds + "\n";
        return result;
    }


    public void follow(User user) {
        if (this.follow == null) {
            this.follow = new HashSet<>();
        }
        this.follow.add(user);
    }

    public Set<User> getFollow() {
        return follow;
    }

    public void addRecord(HasRecord hasRecord){
        if (this.hasRecords ==null)
            this.hasRecords =new HashSet<>();
        this.hasRecords.add(hasRecord);
    }

    public Set<HasRecord> getHasRecords() {
        return hasRecords;
    }

    public void addTag (HasTag hasTag){
        if(this.hasTags==null){
            this.hasTags=new HashSet<>();
        }
        this.hasTags.add(hasTag);
    }

    public Set<HasTag> getHasTags() {
        return hasTags;
    }
}
