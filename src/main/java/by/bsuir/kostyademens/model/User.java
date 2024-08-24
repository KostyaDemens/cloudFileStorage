package by.bsuir.kostyademens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Table(name = "Users")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, message = "Username should contain at least two letters")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 4, message = "Password should contain at least four symbols")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
