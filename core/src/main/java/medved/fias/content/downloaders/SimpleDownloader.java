package medved.fias.content.downloaders;

import java.io.File;
import java.net.URI;
import java.util.concurrent.Future;

/**
 * Created by arshvin on 25.06.16.
 */
public class SimpleDownloader implements Downloader {
    @Override
    public void setUri(URI uri) {

    }

    @Override
    public void setDestinaton(File file) {

    }

    @Override
    public Boolean checkUpdate() {
        return null;
    }

    @Override
    public Future<Boolean> download() {
        return null;
    }
}
