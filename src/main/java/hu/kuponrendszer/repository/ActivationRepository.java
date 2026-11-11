package hu.kuponrendszer.repository;
import hu.kuponrendszer.model.Activation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ActivationRepository extends JpaRepository<Activation,Long> {
 boolean existsByUserIdAndOfferId(Long userId,Long offerId);
 long countByUserId(Long userId);
 List<Activation> findByUserIdOrderByActivatedAtDesc(Long userId);
}
