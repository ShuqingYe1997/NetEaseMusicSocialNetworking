package musicSocialNetwork.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;
import java.util.Set;

@NodeEntity
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    private int songId;
    private String name;
    private String album;
    private String picUrl;

    private List<String> Artists;

    public Song(){}
    public Song(int id,String name,String album, String pic,List<String> Artists){
        this.songId =id;
        this.name=name;
        this.album=album;
        this.picUrl=pic;
       this.Artists=Artists;
    }

    public Song(Song song){
        this.songId =song.getSongId();
        this.name=song.getName();
        this.album=song.getAlbum();
        this.Artists=song.getArtists();
        this.picUrl=song.getPicUrl();
    }

    public String getName() {
        return name;
    }

    public int getSongId() {
        return songId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public List<String> getArtists() {
        return Artists;
    }

    public void setArtists(List<String> Strings) {
        this.Artists = Strings;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return name+" "+ songId +" "+album;
    }

}
