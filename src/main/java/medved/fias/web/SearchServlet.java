package medved.fias.web;

import medved.fias.interchange.Storage;
import medved.fias.interchange.StorageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by arshvin on 25.11.15.
 */
@RestController
public class SearchServlet {

    @Autowired
    private Storage storage;

    @RequestMapping(value = "name", method = RequestMethod.GET)
    StorageData requestByQuery(@RequestParam(name = "query") String name){
        return storage.getByText(name);
    }

    @RequestMapping(value = "uuid/{id}", method = RequestMethod.GET)
    StorageData requestByUuid(@PathVariable("id") UUID uuid){
        return storage.getById(uuid);
    }
}
