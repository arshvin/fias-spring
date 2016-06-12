package medved.fias.storage;

import medved.fias.interchange.StorageData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by arshvin on 12.06.16.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data implements StorageData {
    private String name;
    private String parent;
    @XmlElement(name = "children")
    private List<String> child;

    public Data() {}

    public Data(String name, String parent, List<String> child) {
        this.name = name;
        this.parent = parent;
        this.child = child;
    }
}
