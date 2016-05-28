package medved.fias.interchange;

import java.util.List;

/**
 * Created by arshvin on 23.05.16.
 */
public interface StorageData {
    String getName();
    String getParent();
    List<String> getChildren();
}
