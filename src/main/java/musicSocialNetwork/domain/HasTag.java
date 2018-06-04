package musicSocialNetwork.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type="INTERESTED_IN")
public class HasTag {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private User user;

    @EndNode
    private Tag tag;

    private Integer count;

    public HasTag(){}
    public HasTag(User user,Tag tag,Integer count){
        this.user=user;
        this.tag=tag;
        this.count=count;
    }

    public User getUser() {
        return user;
    }

    public int getCount() {
        return count;
    }

    public Tag getTag() {
        return tag;
    }

    public String toString(){
        return getUser().getNickname()+" "+tag.getTitle()+" "+getCount();
    }
}
