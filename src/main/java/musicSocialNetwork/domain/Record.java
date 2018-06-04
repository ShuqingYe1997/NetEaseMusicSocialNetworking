package musicSocialNetwork.domain;


//注意，这个类和HasRecord没什么关系
public class Record {
    private Song song;

    private int score;
    private String type;
    public Record(){};

    public Record(Song song, int score, String type){
        this.score=score;
        this.song=song;
        this.type=type;
    }

    public int getScore() {
        return score;
    }

    public String getType() {
        return type;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
