package musicSocialNetwork.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "LISTENED")
public class HasRecord{

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;

    @EndNode
    private Song song;

    private int score;
    private String type;

    public HasRecord(){};
    public HasRecord(User user,Song song,int score,String type){
        this.user=user;
        this.score=score;
        this.song=song;
        this.type=type;
    }

    public int getScore() {
        return score;
    }

    public Song getSong() {
        return song;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
