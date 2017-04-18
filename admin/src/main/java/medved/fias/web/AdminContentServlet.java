package medved.fias.web;

import medved.fias.content.Data;
import medved.fias.content.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by arshvin on 14.04.17.
 */
@RestController
@RequestMapping("/admin/content")
public class AdminContentServlet {
    @Autowired
    private DataStorage dataStorage;

    @GetMapping("/list/{id}")
    public List<Data> getContentData(@PathVariable UUID id,
                                     @RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "size", required = false) Integer size){
        page = (page > 0) ? page : 0;
        //TODO:take out this hardcodded setting to the config file (property file)
        size = (size > 0) ? size : 50;

        return dataStorage.getChildrenOf(id, new PageRequest(page, size));
    }

    @GetMapping("/detail/{id}")
    public Data getContentDetail(@PathVariable UUID id){
        return dataStorage.getObjectBy(id);
    };

    @GetMapping("/find/{query}")
    public List<Data> getContentByText(@PathVariable String query,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size){
        page = (page > 0) ? page : 0;
        //TODO:take out this hardcodded setting to the config file (property file)
        size = (size > 0) ? size : 50;

        return dataStorage.getObjectsBy(query, new PageRequest(page, size));
    }

}
