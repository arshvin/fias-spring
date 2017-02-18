package medved.fias.storage.mappers.data;

import com.google.common.collect.FluentIterable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Comparator;
import java.util.List;

/**
 * Created by arshvin on 12.06.16.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataImpl implements medved.fias.content.Data {
    private String name;
    private String parent;
    @XmlElement(name = "children")
    private List<String> children;

    public DataImpl() {}

    public DataImpl(String name, String parent, List<String> children) {
        this.name = name;
        this.parent = parent;
        this.children = FluentIterable.from(children).toSortedList(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
            }
        });
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataImpl data = (DataImpl) o;

        if (!name.equals(data.name)) return false;
        if (!parent.equals(data.parent)) return false;
        return children.equals(data.children);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + parent.hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DataImpl{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", children=" + children +
                '}';
    }
}
