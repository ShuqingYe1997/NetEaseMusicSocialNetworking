package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User,Long> {
    User findByNickname(String nickname);
    User findUserByUserId(int userId);
}
