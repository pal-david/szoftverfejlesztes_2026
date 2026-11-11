package hu.kuponrendszer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80) private String name;
    @Column(nullable = false, length = 160) private String email;
    @Column(nullable = false) private String passwordHash;
    // Nullable, hogy a korábbi H2-adatbázis tábláját is gond nélkül lehessen bővíteni.
    // A régi felhasználók (NULL) automatikusan USER jogosultságúak.
    @Column(length = 20) private String role = "USER";

    public AppUser() {}
    public AppUser(String name, String email, String passwordHash) {
        this(name, email, passwordHash, "USER");
    }
    public AppUser(String name, String email, String passwordHash, String role) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return "ADMIN".equals(role) ? "ADMIN" : "USER"; }
}
