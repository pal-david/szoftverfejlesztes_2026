package hu.kuponrendszer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
@SpringBootTest @AutoConfigureMockMvc
@TestPropertySource(properties={"spring.datasource.url=jdbc:h2:mem:role_test;DB_CLOSE_DELAY=-1","spring.jpa.hibernate.ddl-auto=create-drop"})
class RoleAccessTest {
 @Autowired MockMvc mvc;
 @Test @WithMockUser(roles="USER") void userCannotOpenAdmin() throws Exception{mvc.perform(get("/admin/coupons")).andExpect(status().isForbidden());}
 @Test @WithMockUser(roles="USER") void userCannotCreateOffer() throws Exception{mvc.perform(post("/admin/coupons").with(csrf())).andExpect(status().isForbidden());}
 @Test @WithMockUser(roles="USER") void userCannotEditOffer() throws Exception{mvc.perform(get("/admin/coupons/1/edit")).andExpect(status().isForbidden());}
 @Test @WithMockUser(roles="USER") void userCannotUseOldEditRoute() throws Exception{mvc.perform(get("/coupons/1/edit")).andExpect(status().isForbidden());}
 @Test @WithMockUser(roles="ADMIN") void adminCannotActivateUserCoupon() throws Exception{mvc.perform(post("/coupons/1/activate").with(csrf())).andExpect(status().isForbidden());}
 @Test @WithMockUser(roles="ADMIN") void adminCanViewCreationForm() throws Exception{mvc.perform(get("/admin/coupons/new")).andExpect(status().isOk());}
}
