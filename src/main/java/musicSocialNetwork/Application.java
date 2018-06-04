package musicSocialNetwork;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@SpringBootApplication
@EnableNeo4jRepositories("musicSocialNetwork.repositories")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner demo() {
        return args -> {
        };
    }
}

/*
*5/31
* 把这个application移到包下面居然Test就可以跑了，什么鬼(⊙ˍ⊙)
*
* 6/2
* repository.save（）是级联操作，也就是说一个Class的非基本属性类也要有一个repository,操作代价很大的，能省则省
* 或者可以规定save的depth,详见https://neo4j.com/docs/ogm-manual/current/reference/
* 的3.7.3.1. Save depth
*
*
* */
