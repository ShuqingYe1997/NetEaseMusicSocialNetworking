package musicSocialNetwork.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    public Tag(){}
    public Tag(String title){
        this.title=title;
    }

    public String getTitle() {
        return title;
    }
}
