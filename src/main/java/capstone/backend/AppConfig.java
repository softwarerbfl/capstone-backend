package capstone.backend;

import capstone.backend.service.LoginService;
import capstone.backend.service.PostingService;
import capstone.backend.service.StudyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import java.util.Optional;

@Configuration
@EnableJpaRepositories(basePackages = {"capstone.backend"})
public class AppConfig {
    @Bean
    public LoginService loginService() {
        return new LoginService();
    }

    @Bean
    public StudyService studyService() {
        return new StudyService();
    }

    @Bean
    public PostingService postingService() {
        return new PostingService();
    }


}
