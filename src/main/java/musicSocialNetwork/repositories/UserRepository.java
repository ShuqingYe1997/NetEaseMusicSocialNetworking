package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.HasRecord;
import musicSocialNetwork.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User,Long> {
    User findByNickname(String nickname);
    User findUserByUserId(int userId);

    @Query("MATCH (u1:User),(u2:User) WHERE u1.userId={userId1} AND u2.userId={userId2} " +
            "MERGE (u1)-[r:FOLLOWS]->(u2) " +
            "SET u1.follows = u1.follows+1 " +
            "SET u2.followeds = u2.followeds+1")
    void update(@Param("userId1") int userId1, @Param("userId2") int userId2);

    @Query("MATCH (u1:User)-[:FOLLOWS]->(u2:User),(u2)-[:FOLLOWS]->(u) WHERE u1.userId={userId} AND u1<>u " +
            "and not(u1)-[:FOLLOWS]->(u) RETURN u LIMIT 20")
    List<User> findSkipFollowers(@Param("userId") int userId);

    @Query("MATCH (u1:User)-[l1:LISTENED]->()<-[l2:LISTENED]-(u2:User) where u1.userId={userId1} AND " +
            "not(u1)-[:FOLLOWS]->(u2) AND u1<>u2 RETURN l2")
    List<HasRecord> updateBySong(@Param("userId1") int userId1);

//    Repository中自定义一个方法，方法上使用@Query(“cypher”)语句，结果返回Object，大多数情况都是List<Map<Stirng,Object>>类型的结果集，可以断点调试下
}
