package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

@Testcontainers
@SpringBootTest
class RegistrationServiceTest {

    private final UserRepository userRepository;
    private final RegistrationService registrationService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    RegistrationServiceTest(UserRepository userRepository, RegistrationService registrationService, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.registrationService = registrationService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @Test
    void connectionEstablished() {
        assertThat(mysql.isCreated()).isTrue();
        assertThat(mysql.isRunning()).isTrue();
    }

    @Test
    void databaseShouldBeEmpty() {
        List<User> users = userRepository.findAll();
        assertTrue(users.isEmpty());
    }

    @Test
    void shouldSaveUserInDatabase() {
        User user = new User("admin", "password");

        registrationService.register(user);

        List<User> users = userRepository.findAll();

        assertThat(users, containsInAnyOrder(
                hasProperty("username", is("admin"))
        ));

        assertThat(users, not(empty()));

        assertThat(users, hasSize(1));
    }

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute("TRUNCATE TABLE users");
    }

}