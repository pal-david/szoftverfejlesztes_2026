package hu.kuponrendszer.repository;
import hu.kuponrendszer.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OfferRepository extends JpaRepository<Offer,Long> {
 List<Offer> findAllByOrderByExpiryDateAsc();
 List<Offer> findByExpiryDateGreaterThanEqualOrderByExpiryDateAsc(java.time.LocalDate date);
}
