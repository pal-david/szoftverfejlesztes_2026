package hu.kuponrendszer.dto;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
public class OfferForm {
 @NotBlank @Size(max=90) private String title;
 @NotBlank @Size(max=160) private String description;
 @NotBlank @Size(max=50) private String code;
 @NotBlank @Size(max=80) private String brand;
 @NotNull @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) private LocalDate expiryDate;
 @NotBlank @Size(max=40) private String category="Egyéb";
 public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getCode(){return code;} public void setCode(String v){code=v;}
 public String getBrand(){return brand;} public void setBrand(String v){brand=v;}
 public LocalDate getExpiryDate(){return expiryDate;} public void setExpiryDate(LocalDate v){expiryDate=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
}
