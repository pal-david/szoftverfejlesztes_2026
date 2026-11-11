# KuponRendszer – ajánlatkatalógus és aktiválás (Spring Boot + H2)

## Mit tud?
- **ADMIN**: az ajánlatokat hozza létre, szerkeszti és törli az `/admin/coupons` oldalon.
- **USER**: `/coupons` – **Elérhető kuponok**; aktiválás gombbal aktivál egy ajánlatot.
- **USER**: `/coupons/activated` – **Aktivált kuponjaim**; csak a saját aktiválások és kuponkódok látszanak.
- A kuponkód **aktiválás előtt rejtett**; aktiválás után látható. Az aktiválás nem jelent automatikus beváltást.
- Egy felhasználó egy ajánlatot egyszer aktiválhat. A lejárt ajánlat nem aktiválható.
- A rendszer a már meglévő `app_users` / `coupons` táblát nem törli, új `offers` és `coupon_activations` táblákat használ.

## Indítás
1. IntelliJ IDEA → File → Open → a `pom.xml`-t tartalmazó **KuponRendszer** mappa.
2. Java SDK **21**. Maven függőségek betöltése.
3. A régi KuponRendszer alkalmazást állítsd le, mert mindkettő a 8080-as portot használja.
4. Futtasd `hu.kuponrendszer.KuponRendszerApplication` osztályt.
5. Nyisd meg: http://localhost:8080

### Helyi DEMO fiókok (nem éles használatra)
- ADMIN: `admin@kupon.hu` / `Admin1234!`
- USER: `demo@kupon.hu` / `Demo1234!`

## H2 adatbázis
- Konzol: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/kuponrendszer;DB_CLOSE_ON_EXIT=FALSE`
- Felhasználó: `sa`, jelszó: üres (ha az alkalmazás `application.properties` fájljában így szerepel)
- SQL: `SELECT * FROM OFFERS;`
- SQL: `SELECT * FROM COUPON_ACTIVATIONS;`
- SQL: `SELECT a.ID, u.EMAIL, o.TITLE, a.ACTIVATED_AT FROM COUPON_ACTIVATIONS a JOIN APP_USERS u ON a.USER_ID = u.ID JOIN OFFERS o ON a.OFFER_ID = o.ID;`

## Migráció / adatvédelem
- Az első induláskor, ha nincs `offers` adat, a korábbi `coupons` táblából ajánlatokat állítunk elő kuponkód szerint. A régi kupontulajdonosi hozzárendelés NEM lesz automatikus aktiválás: a felhasználó a katalógusból maga aktivál.
- A `data` mappádat másold a projekt gyökérmappájába, **csak ha meg akarod tartani** a korábbi H2-adatbázist. Előtte készíts biztonsági mentést. Különböző projekteknek saját `./data` mappájuk van.
- Ha a korábbi H2 adatbázis másik jelszóval jött létre, a `spring.datasource.password` értékét ahhoz kell igazítani. A `.properties` fájl átírása NEM változtatja meg az adatbázis jelszavát.
- A H2 konzol és a DEMO fiókok csak fejlesztéshez valók: éles környezet előtt tiltsd le / távolítsd el őket.
- A mintakód közös kuponkódot tárol ajánlatonként. Egyedi, egyszer használatos kódok vagy tényleges beváltáskezelés további fejlesztést igényelnek.

## Projektfelépítés
- `model/Offer.java`: mindenki számára látható ajánlat.
- `model/Activation.java`: melyik felhasználó melyik ajánlatot aktiválta.
- `model/AppUser.java`: bejelentkezett felhasználó, szerepkör.
- `repository/OfferRepository.java`, `ActivationRepository.java`: H2/JPA elérés.
- `controller/CouponController.java`: felhasználói katalógus, saját aktiválások, aktiválás végpontja.
- `controller/AdminCouponController.java`: csak admin által módosítható ajánlatok.
- `config/SecurityConfig.java`: jogosultságvédelem, CSRF.
- `templates/offers.html`: két fül, aktiválás, személyes kuponkód.
- `templates/admin-offers.html`: admin ajánlatlista.
- `templates/coupon-form.html`: admin létrehozás / szerkesztés.
- `static/css/style.css`: megjelenés, `static/js/app.js`: keresés, szűrés, kódmásolás.

## Tanári bemutató
1. Admin belép, felvesz egy új kedvezményt.
2. Kilép, USER belép, az **Elérhető kuponok** fülön megtalálja az ajánlatot.
3. **Kupon aktiválása** → átirányítás az **Aktivált kuponjaim** fülre, megjelenik a kuponkód.
4. Második aktiválás nem készít második rekordot, mert az adatbázisban egyedi `(user_id, offer_id)` kapcsolat van.
5. H2 Console: `SELECT * FROM COUPON_ACTIVATIONS;`
