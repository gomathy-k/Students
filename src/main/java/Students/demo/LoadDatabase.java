package Students.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(StudentRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Student("John", "Doe", "02-02-2001","Male", "Male", "0173789786", " 32 Redhill road, Reigate")));
            log.info("Preloading " + repository.save(new Student("Jane", "Doe", "03-02-2001","Male", "Female", "08880808090", " 32 Reigate road, Reigate")));
            log.info("Preloading " + repository.save(new Student("Jason", "Green", "05-03-2002","Female", "Female", "12345678902", " 32 Reigate road, Reigate")));
        };
    }
}

