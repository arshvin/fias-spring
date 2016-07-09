package medved.fias.content;

import java.util.List;

/**
 * Created by arshvin on 23.05.16.
 */
public interface Data {
    String getName();
    String getParent();
    List<String> getChildren();
}
