package medved.fias.web;

import medved.fias.interchange.Storage;
import medved.fias.interchange.StorageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by arshvin on 25.11.15.
 */
@RestController
@RequestMapping("search/")
public class SearchServlet {

    @Autowired
    private Storage storage;

    @RequestMapping(path = "common/", method = RequestMethod.GET)
    StorageData commonRequest(@RequestParam String name){
        return storage.getByText(name);
    }
    @RequestMapping(path = "uuid/", method = RequestMethod.GET)
    StorageData commonRequest(@RequestParam UUID uuid){
        return storage.getById(uuid);
    }

}
