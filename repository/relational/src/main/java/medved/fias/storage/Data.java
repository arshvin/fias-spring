package medved.fias.storage;

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
public class Data implements medved.fias.content.Data {
    private String name;
    private String parent;
    @XmlElement(name = "children")
    private List<String> children;

    public Data() {}

    public Data(String name, String parent, List<String> child) {
        this.name = name;
        this.parent = parent;
        this.children = child;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public List<String> getChildren() {
        return children;
    }
}
