package by.bsuir.kostyademens.integrationTest;

import by.bsuir.kostyademens.exception.UserAlreadyExistsException;
import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.repository.UserRepository;
import by.bsuir.kostyademens.service.RegistrationService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

@Testcontainers
@SpringBootTest
class RegistrationServiceIntegrationTest {

    private final UserRepository userRepository;

    private final RegistrationService registrationService;

    private final SimpleStorageService storageService;

    private final JdbcTemplate jdbcTemplate;
    private User user;


    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @Autowired
    RegistrationServiceIntegrationTest(UserRepository userRepository, JdbcTemplate jdbcTemplate, RegistrationService registrationService, SimpleStorageService storageService) {
        this.registrationService = registrationService;
        this.storageService = storageService;
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    @BeforeEach
    void setUp() {
        user = new User("admin", "password");

    }

    @Test
    void connectionEstablished() {
        assertThat(mysql.isCreated(), Matchers.is(true));
        assertThat(mysql.isRunning(), Matchers.is(true));
    }

    @Test
    void databaseShouldBeEmpty() {
        List<User> users = userRepository.findAll();
        assertTrue(users.isEmpty());
    }

    @Test
    void shouldSaveUserInDatabase() {
        registrationService.register(user);

        List<User> users = userRepository.findAll();

        assertThat(users, containsInAnyOrder(
                hasProperty("username", is("admin"))
        ));

        assertThat(users, not(empty()));

        assertThat(users, hasSize(1));
    }

    @Test
    void shouldThrowUserAlreadyExistsException() {
        registrationService.register(user);

        assertThrows(UserAlreadyExistsException.class, () ->
                registrationService.register(user));
    }

    @AfterEach
    void cleanUp() {
        storageService.deleteFile("user-" + user.getId() + "-files/");
        jdbcTemplate.execute("TRUNCATE TABLE users");
    }

}