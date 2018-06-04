package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TagRepository extends Neo4jRepository<Tag,Long>{

    Tag findTagByTitle(String title);
}
