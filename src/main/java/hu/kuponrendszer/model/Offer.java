package hu.kuponrendszer.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="offers")
public class Offer {
 @OneToMany(mappedBy="offer",cascade=CascadeType.REMOVE,orphanRemoval=true) private java.util.List<Activation> activations=new java.util.ArrayList<>();
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=90) private String title;
 @Column(nullable=false,length=160) private String description;
 @Column(nullable=false,length=50) private String code;
 @Column(nullable=false) private LocalDate expiryDate;
 @Column(nullable=false,length=40) private String category;
 @Column(nullable=false,length=80) private String brand;
 public Offer() {}
 public Offer(String title,String description,String code,LocalDate expiryDate,String category,String brand){
  this.title=title;this.description=description;this.code=code;this.expiryDate=expiryDate;this.category=category;this.brand=brand;
 }
 public void update(String title,String description,String code,LocalDate expiryDate,String category,String brand){
  this.title=title;this.description=description;this.code=code;this.expiryDate=expiryDate;this.category=category;this.brand=brand;
 }
 public boolean isActive(){return !expiryDate.isBefore(LocalDate.now());}
 public Long getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;}
 public String getCode(){return code;} public LocalDate getExpiryDate(){return expiryDate;} public String getCategory(){return category;}
 public String getBrand(){return brand;}
}
