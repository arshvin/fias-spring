package medved.fias.content.parsers;

import java.io.File;

/**
 * Created by arshvin on 24.06.16.
 */
public interface Parser<T> {
    void feed(File filename);
    T getNextObj();
}
