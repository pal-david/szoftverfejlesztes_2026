package hu.kuponrendszer.config;
import hu.kuponrendszer.model.*;
import hu.kuponrendszer.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
@Configuration
public class DemoData {
 @Bean CommandLineRunner seed(UserRepository users,OfferRepository offers,CouponRepository oldCoupons,PasswordEncoder encoder){return args->{
  users.findByEmail("demo@kupon.hu").orElseGet(()->users.save(new AppUser("Teszt","demo@kupon.hu",encoder.encode("Demo1234!"),"USER")));
  users.findByEmail("admin@kupon.hu").orElseGet(()->users.save(new AppUser("Adminisztrátor","admin@kupon.hu",encoder.encode("Admin1234!"),"ADMIN")));
  if(offers.count()==0){
   // Régi kiosztott kuponokból közös ajánlatokat készítünk. A régi táblát nem töröljük.
   if(oldCoupons.count()>0){
    oldCoupons.findAll().stream().collect(java.util.stream.Collectors.toMap(Coupon::getCode,c->c,(a,b)->a,java.util.LinkedHashMap::new))
     .values().forEach(c->offers.save(new Offer(c.getTitle(),c.getDescription(),c.getCode(),c.getExpiryDate(),c.getCategory(),c.getCategory())));
   } else {
    offers.save(new Offer("20% kedvezmény","Tavaszi ruhavásár","TAVASZ20",LocalDate.of(2027,1,1),"Ruházat","Urban Wear"));
    offers.save(new Offer("Ingyenes szállítás","Diák rendelési akció","BONUSZFREE",LocalDate.of(2026,12,31),"Szállítás","BookLab"));
    offers.save(new Offer("15% kedvezmény","Elektronikai kiegészítők","TECH15",LocalDate.of(2027,2,1),"Elektronika","TechZóna"));
    offers.save(new Offer("2+1 kávé","Egyetemi napok","CAFE241",LocalDate.of(2026,12,20),"Vendéglátás","Campus Café"));
    offers.save(new Offer("25% kedvezmény","Tanuláshoz könyvek","BOOK25",LocalDate.of(2027,4,10),"Tanulás","BookLab"));
    offers.save(new Offer("30% kedvezmény","Sportbérlet","FIT30",LocalDate.of(2027,3,30),"Sport","FitClub"));
   }
  }
 };}
}
