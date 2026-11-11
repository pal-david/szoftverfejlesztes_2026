package hu.kuponrendszer.repository;

import hu.kuponrendszer.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByUserIdOrderByExpiryDateAsc(Long userId);
    List<Coupon> findAllByOrderByExpiryDateAsc();
}
