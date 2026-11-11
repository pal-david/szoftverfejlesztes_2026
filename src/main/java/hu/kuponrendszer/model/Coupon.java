package hu.kuponrendszer.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 90) private String title;
    @Column(nullable = false, length = 160) private String description;
    @Column(nullable = false, length = 50) private String code;
    @Column(nullable = false) private LocalDate expiryDate;
    @Column(nullable = false, length = 40) private String category;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    public Coupon() {}
    public Coupon(String title, String description, String code, LocalDate expiryDate, String category, AppUser user) {
        this.title = title; this.description = description; this.code = code;
        this.expiryDate = expiryDate; this.category = category; this.user = user;
    }
    public boolean isActive() { return !expiryDate.isBefore(LocalDate.now()); }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCode() { return code; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public String getCategory() { return category; }
    public AppUser getUser() { return user; }
    public void update(String title, String description, String code, LocalDate expiryDate, String category, AppUser user) {
        this.title = title; this.description = description; this.code = code;
        this.expiryDate = expiryDate; this.category = category; this.user = user;
    }
}
