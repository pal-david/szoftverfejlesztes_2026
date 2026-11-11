package hu.kuponrendszer.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="coupon_activations",uniqueConstraints=@UniqueConstraint(columnNames={"user_id","offer_id"}))
public class Activation {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private AppUser user;
 @ManyToOne(fetch=FetchType.EAGER,optional=false) @JoinColumn(name="offer_id",nullable=false) private Offer offer;
 @Column(nullable=false) private LocalDateTime activatedAt;
 public Activation(){}
 public Activation(AppUser user,Offer offer){this.user=user;this.offer=offer;this.activatedAt=LocalDateTime.now();}
 public Long getId(){return id;} public AppUser getUser(){return user;} public Offer getOffer(){return offer;}
 public LocalDateTime getActivatedAt(){return activatedAt;}
}
