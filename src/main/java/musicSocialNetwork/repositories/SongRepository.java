package musicSocialNetwork.repositories;

import musicSocialNetwork.domain.Song;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends Neo4jRepository<Song,Long> {

    Song findSongBySongIdEquals(int SongId);
    List<Song> findSongsBySongId(int SongId);
}
