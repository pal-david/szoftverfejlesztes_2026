package hu.example.login;

/**
 * A bejelentkezés ellenőrzésének helye.
 * Jelenleg csak egy ideiglenes példa (admin / admin), hogy a hibaüzenet is kipróbálható legyen.
 */
public class AuthService {

    public static boolean login(String username, String password) {
        // TODO: backend hívás
        return "admin".equals(username) && "admin".equals(password);
    }
}
