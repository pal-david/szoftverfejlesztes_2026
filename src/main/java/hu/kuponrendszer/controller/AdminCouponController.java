package hu.kuponrendszer.controller;
import hu.kuponrendszer.dto.OfferForm;
import hu.kuponrendszer.model.Offer;
import hu.kuponrendszer.repository.OfferRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@Controller
@RequestMapping("/admin/coupons")
public class AdminCouponController {
 private final OfferRepository offers;
 public AdminCouponController(OfferRepository offers){this.offers=offers;}
 @GetMapping public String index(Model model){ model.addAttribute("offers",offers.findAllByOrderByExpiryDateAsc()); return "admin-offers";}
 private Offer existing(Long id){return offers.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));}
 private void prepare(Model model,boolean editing,Long id){model.addAttribute("editing",editing);model.addAttribute("couponId",id);}
 @GetMapping("/new") public String newPage(Model model){model.addAttribute("offerForm",new OfferForm());prepare(model,false,null);return "coupon-form";}
 @PostMapping public String create(@Valid @ModelAttribute("offerForm") OfferForm form,BindingResult errors,Model model){
  if(errors.hasErrors()){prepare(model,false,null);return "coupon-form";}
  offers.save(new Offer(form.getTitle().trim(),form.getDescription().trim(),form.getCode().trim(),form.getExpiryDate(),form.getCategory(),form.getBrand().trim()));
  return "redirect:/admin/coupons?added";
 }
 @GetMapping("/{id}/edit") public String editPage(@PathVariable Long id,Model model){
  Offer offer=existing(id); OfferForm f=new OfferForm();f.setTitle(offer.getTitle());f.setDescription(offer.getDescription());f.setCode(offer.getCode());f.setExpiryDate(offer.getExpiryDate());f.setCategory(offer.getCategory());f.setBrand(offer.getBrand());
  model.addAttribute("offerForm",f);prepare(model,true,id);return "coupon-form";
 }
 @PostMapping("/{id}/edit") public String edit(@PathVariable Long id,@Valid @ModelAttribute("offerForm") OfferForm form,BindingResult errors,Model model){
  Offer offer=existing(id);if(errors.hasErrors()){prepare(model,true,id);return "coupon-form";}
  offer.update(form.getTitle().trim(),form.getDescription().trim(),form.getCode().trim(),form.getExpiryDate(),form.getCategory(),form.getBrand().trim());offers.save(offer);
  return "redirect:/admin/coupons?updated";
 }
 @PostMapping("/{id}/delete") public String delete(@PathVariable Long id){offers.delete(existing(id));return "redirect:/admin/coupons?deleted";}
}
