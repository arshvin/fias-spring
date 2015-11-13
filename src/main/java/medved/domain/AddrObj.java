package medved.domain;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Created by arshvin on 25.05.15.
 */
@Indexed
@Entity
public class AddrObj {
    @Id
    @DocumentId
    @Column(name = "AOGUID")
    private UUID aoGuid;

    @Column(name = "AOID", unique = true, nullable = false)
    private UUID aoId;

    @ManyToOne
    @JoinColumn(name = "PARENTGUID")
    private AddrObj parentObj;

    @Field(index = Index.YES, analyze = Analyze.YES)
    @Column(name = "FORMALNAME", nullable = false)
    private String formalName;

    @Field(index = Index.YES, analyze = Analyze.YES)
    @Column(name = "OFFNAME")
    private String officialName;

    @Column(name = "SHORTNAME", nullable = false)
    private String shortName;

    @Field(index = Index.YES, analyze = Analyze.YES)
    @Column(name = "POSTALCODE", length = 6)
    private String postalCode;

    @OneToMany(mappedBy = "parentObj", cascade = {CascadeType.REMOVE})
    @IndexedEmbedded(depth = 1)
    private Set<House> houses;

    public AddrObj() {}

    public AddrObj(UUID aoId, UUID aoGuid, AddrObj parentObj, String formalName, String officialName, String shortName, String postalCode) {
        this.aoId = aoId;
        this.aoGuid = aoGuid;
        this.parentObj = parentObj;
        this.formalName = formalName;
        this.officialName = officialName;
        this.shortName = shortName;
        this.postalCode = postalCode;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public UUID getAoId() {
        return aoId;
    }

    public void setAoId(UUID aoId) {
        this.aoId = aoId;
    }

    public UUID getAoGuid() {
        return aoGuid;
    }

    public void setAoGuid(UUID aoGuid) {
        this.aoGuid = aoGuid;
    }

    public AddrObj getParentObj() {
        return parentObj;
    }

    public void setParentObj(AddrObj parentObj) {
        this.parentObj = parentObj;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddrObj addrObj = (AddrObj) o;

        if (!aoGuid.equals(addrObj.aoGuid)) return false;
        if (!aoId.equals(addrObj.aoId)) return false;
        if (parentObj != null ? !parentObj.equals(addrObj.parentObj) : addrObj.parentObj != null) return false;
        if (!formalName.equals(addrObj.formalName)) return false;
        if (!officialName.equals(addrObj.officialName)) return false;
        if (!shortName.equals(addrObj.shortName)) return false;
        return postalCode.equals(addrObj.postalCode);

    }

    @Override
    public int hashCode() {
        int result = aoGuid.hashCode();
        result = 31 * result + aoId.hashCode();
        result = 31 * result + (parentObj != null ? parentObj.hashCode() : 0);
        result = 31 * result + formalName.hashCode();
        result = 31 * result + (officialName != null ? officialName.hashCode() : 0);
        result = 31 * result + shortName.hashCode();
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddrObj{" +
                "aoGuid=" + aoGuid +
                ", aoId=" + aoId +
                ", parentObj=" + parentObj +
                ", formalName='" + formalName + '\'' +
                ", officialName='" + officialName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
