package poly.edu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private String username;

    private String password;

    private String email;

    private Boolean activated;

    private String role; // USER | ADMIN
}
