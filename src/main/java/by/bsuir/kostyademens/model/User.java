package by.bsuir.kostyademens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Users")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "Username should contain at least 2 letters")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password should contain at least 3 letters")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
