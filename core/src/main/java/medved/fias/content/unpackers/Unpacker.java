package medved.fias.content.unpackers;

import java.io.File;

/**
 * Created by arshvin on 25.06.16.
 */
public interface Unpacker {
    void deflate();
    void setSource(File file);
    void setDestination(File file);
}
