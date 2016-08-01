package medved.fias.content.downloaders;

import java.io.File;
import java.net.URI;
import java.util.concurrent.Future;

/**
 * Created by arshvin on 25.06.16.
 */
public interface Downloader {
    void setSourceUri(URI uri);
    void setCheckUri(URI uri);
    void setDestinaton(File file);
    Boolean checkUpdate();
    Future<Boolean> download();
}
