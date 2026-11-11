package hu.kuponrendszer.controller;
import hu.kuponrendszer.model.*;
import hu.kuponrendszer.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/coupons")
public class CouponController {
 private final UserRepository users; private final OfferRepository offers; private final ActivationRepository activations;
 public CouponController(UserRepository users,OfferRepository offers,ActivationRepository activations){this.users=users;this.offers=offers;this.activations=activations;}
 private AppUser loggedUser(Authentication auth){return users.findByEmail(auth.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));}
 @GetMapping public String available(Authentication auth,Model model){
  AppUser user=loggedUser(auth);
  Set<Long> taken=activations.findByUserIdOrderByActivatedAtDesc(user.getId()).stream().map(a->a.getOffer().getId()).collect(Collectors.toSet());
  model.addAttribute("name",user.getName()); model.addAttribute("offers",offers.findByExpiryDateGreaterThanEqualOrderByExpiryDateAsc(LocalDate.now()));
  model.addAttribute("takenIds",taken); model.addAttribute("activatedCount",taken.size());model.addAttribute("selectedTab","available");
  return "offers";
 }
 @GetMapping("/activated") public String activated(Authentication auth,Model model){
  AppUser user=loggedUser(auth);
  model.addAttribute("name",user.getName());model.addAttribute("activations",activations.findByUserIdOrderByActivatedAtDesc(user.getId()));
  model.addAttribute("activatedCount",activations.countByUserId(user.getId())); model.addAttribute("selectedTab","activated");
  return "offers";
 }
 @PostMapping("/{id}/activate") public String activate(@PathVariable Long id,Authentication auth){
  AppUser user=loggedUser(auth);
  Offer offer=offers.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
  if(!offer.isActive()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A kupon lejárt.");
  if(!activations.existsByUserIdAndOfferId(user.getId(),id)){
   try{ activations.saveAndFlush(new Activation(user,offer)); }
   catch(DataIntegrityViolationException ignored){ /* Párhuzamos kattintás: az egyedi index védi a duplikációt. */ }
  }
  return "redirect:/coupons/activated?success";
 }
}
