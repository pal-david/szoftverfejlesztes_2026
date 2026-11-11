package hu.kuponrendszer.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class CouponForm {
    @NotBlank @Size(max = 90) private String title;
    @NotBlank @Size(max = 160) private String description;
    @NotBlank @Size(max = 50) private String code;
    @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) private LocalDate expiryDate;
    @NotBlank @Size(max = 40) private String category = "Egyéb";
    @NotNull(message = "Válassz felhasználót a kuponhoz.") private Long userId;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
