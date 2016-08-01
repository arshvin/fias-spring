package medved.fias.content.downloaders;

import java.io.File;
import java.net.URI;
import java.util.concurrent.Future;

/**
 * Created by arshvin on 25.06.16.
 */
public class SimpleDownloader implements Downloader {
    private File destinationFile;
    private URI sourceUri;
    private URI CheckUri;

    @Override
    public void setSourceUri(URI uri) {

        sourceUri = uri;
    }

    public void setCheckUri(URI uri){

        CheckUri = uri;
    }

    @Override
    public void setDestinaton(File file) {

        destinationFile = file;
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
