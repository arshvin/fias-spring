package medved.fias.security;

import java.util.Date;

/**
 * Created by arshvin on 13.07.16.
 */
public interface Token {

    String getName();
    void setName(String name);
    String getHash();
    void setHash(String hash);
    Date getCreated();
    void setCreated(Date created);

}
