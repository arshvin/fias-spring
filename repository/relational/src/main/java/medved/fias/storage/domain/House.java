package medved.fias.storage.domain;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import java.util.UUID;
import javax.persistence.*;

/**
 * Created by arshvin on 25.05.15.
 */
@Indexed
@Entity
public class House {
    @Id
    @DocumentId
    @Column(name = "HOUSEGUID")
    private UUID houseGuid;

    @Column(name = "HOUSEID", nullable = false)
    private UUID houseId;

    @ManyToOne
    @JoinColumn(name = "AOGUID", nullable = false)
    @ContainedIn
    private AddrObj parentObj;

    @Field(index = Index.YES, analyze = Analyze.YES)
    @Column(name = "HOUSEMUN")
    private String houseNum;

    @Field(index = Index.YES, analyze = Analyze.YES)
    @Column(name = "POSTALCODE")
    private String postalCode;

    public House() {}

    public House(UUID houseId, UUID houseGuid, AddrObj parentObj, String HouseNum, String postalCode) {
        this.houseId = houseId;
        this.houseGuid = houseGuid;
        this.parentObj = parentObj;
        this.houseNum = HouseNum;
        this.postalCode = postalCode;
    }

    public UUID getHouseId() {
        return houseId;
    }

    public void setHouseId(UUID houseId) {
        this.houseId = houseId;
    }

    public UUID getHouseGuid() {
        return houseGuid;
    }

    public void setHouseGuid(UUID houseGuid) {
        this.houseGuid = houseGuid;
    }

    public AddrObj getParentObj() {
        return parentObj;
    }

    public void setParentObj(AddrObj parentObj) {
        this.parentObj = parentObj;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String HouseNum) {
        this.houseNum = HouseNum;
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

        House house = (House) o;

        if (houseGuid != null ? !houseGuid.equals(house.houseGuid) : house.houseGuid != null) return false;
        if (houseId != null ? !houseId.equals(house.houseId) : house.houseId != null) return false;
        if (!parentObj.equals(house.parentObj)) return false;
        if (houseNum != null ? !houseNum.equals(house.houseNum) : house.houseNum != null) return false;
        return !(postalCode != null ? !postalCode.equals(house.postalCode) : house.postalCode != null);

    }

    @Override
    public int hashCode() {
        int result = houseGuid != null ? houseGuid.hashCode() : 0;
        result = 31 * result + houseId.hashCode();
        result = 31 * result + parentObj.hashCode();
        result = 31 * result + (houseNum != null ? houseNum.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseGuid=" + houseGuid +
                ", houseId=" + houseId +
                ", parentObj=" + parentObj +
                ", houseNum='" + houseNum + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
