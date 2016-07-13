package medved.fias.security;

import java.util.List;

/**
 * Created by arshvin on 13.07.16.
 */
public interface UserStorage {
    void saveToken(Token token);
    List<Token> getTokensAll();
    void removeToken(Token token);
    void saveAdminUser(User user);
    List<User> getUsersAll();
    void removeAdminUser(User user);
}
