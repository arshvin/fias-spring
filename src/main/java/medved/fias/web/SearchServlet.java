package medved.fias.web;

import medved.fias.content.DataStorage;
import medved.fias.content.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by arshvin on 25.11.15.
 */
@RestController
public class SearchServlet {

    @Autowired
    private DataStorage dataStorage;

    @RequestMapping(value = "name", method = RequestMethod.GET)
    Data requestByQuery(@RequestParam(name = "query") String name){
        return dataStorage.getObjectByContent(name);
    }

    @RequestMapping(value = "uuid/{id}", method = RequestMethod.GET)
    Data requestByUuid(@PathVariable("id") UUID uuid){
        return dataStorage.getObjectByGuid(uuid);
    }
}
