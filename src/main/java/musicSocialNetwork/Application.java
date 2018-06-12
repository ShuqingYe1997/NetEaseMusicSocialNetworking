package musicSocialNetwork;

import musicSocialNetwork.domain.HasRecord;
import musicSocialNetwork.domain.Record;
import musicSocialNetwork.domain.Song;
import musicSocialNetwork.domain.User;
import musicSocialNetwork.repositories.SongRepository;
import musicSocialNetwork.repositories.UserRepository;
import netEaseMusic.connect.SongRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Iterator;
import java.util.List;


@SpringBootApplication
@EnableNeo4jRepositories("musicSocialNetwork.repositories")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}

