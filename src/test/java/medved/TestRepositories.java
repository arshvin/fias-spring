package medved;

import static org.junit.Assert.*;

import medved.domain.AddrObj;
import medved.domain.AddrObjRepository;
import medved.domain.House;
import medved.domain.HouseRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;
import org.junit.Ignore;

/**
 * Created by arshvin on 26.05.15.
 */
//@Ignore("Disactivated temporally for quikness...")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = medved.App.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestRepositories {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private AddrObjRepository addrObjRepository;

    @Test
    public void test1SimpleCreate(){

        /**
         AOGUID:6f2cbfd8-692a-4ee4-9b16-067210bde3fc
         AOID:2ade0fc0-10a2-4a52-aee2-1ca4b5557b3c
         AREACODE:000
         ENDDATE:2079-06-06
         FORMALNAME:Башкортостан
         OFFNAME:Башкортостан
         POSTALCODE:450000
         REGIONCODE:02
         SHORTNAME:Респ
         STARTDATE:1900-01-01
         UPDATEDATE:2011-09-13
         */
        AddrObj objLevelTop = new AddrObj();
        objLevelTop.setAoGuid(UUID.fromString("6f2cbfd8-692a-4ee4-9b16-067210bde3fc"));
        objLevelTop.setAoId(UUID.fromString("2ade0fc0-10a2-4a52-aee2-1ca4b5557b3c"));
        objLevelTop.setParentObj(null);
        objLevelTop.setFormalName("Башкортостан");
        objLevelTop.setOfficialName("Башкортостан");
        objLevelTop.setShortName("Респ");
        objLevelTop.setPostalCode("450000");

        addrObjRepository.save(objLevelTop);

        /**
         AOGUID:2c9997d2-ce94-431a-96c9-722d2238d5c8
         AOID:6c222277-e9d1-43e1-8fcd-8e7bc4cfac69
         AREACODE:000
         ENDDATE:2079-06-06
         FORMALNAME:Нефтекамск
         OFFNAME:Нефтекамск
         PARENTGUID:6f2cbfd8-692a-4ee4-9b16-067210bde3fc
         POSTALCODE:452683
         REGIONCODE:02
         SHORTNAME:г
         STARTDATE:2013-10-31
         UPDATEDATE:2013-12-14

         */
        AddrObj objLevel2 = new AddrObj();
        objLevel2.setAoGuid(UUID.fromString("2c9997d2-ce94-431a-96c9-722d2238d5c8"));
        objLevel2.setAoId(UUID.fromString("6c222277-e9d1-43e1-8fcd-8e7bc4cfac69"));
        objLevel2.setParentObj(objLevelTop);
        objLevel2.setFormalName("Нефтекамск");
        objLevel2.setOfficialName("Нефтекамск");
        objLevel2.setShortName("г");
        objLevel2.setPostalCode("452683");

        addrObjRepository.save(objLevel2);

        /**
        <Object AOID="fc1294b0-ac37-4257-a848-4ae4818d805c"
         AOGUID="ee6cdb2f-ded3-49ed-b324-b2307c6e9cc7"
         PARENTGUID="2c9997d2-ce94-431a-96c9-722d2238d5c8"
         PREVID="3b7d3506-e573-4136-8673-e03e3713bd77"
         FORMALNAME="Пушкина" OFFNAME="Пушкина" SHORTNAME="пер"
         AOLEVEL="7" REGIONCODE="02" AREACODE="000" AUTOCODE="0"
         CITYCODE="003" CTARCODE="000" PLACECODE="000"
         STREETCODE="0071" EXTRCODE="0000" SEXTCODE="000"
         PLAINCODE="020000030000071" CODE="02000003000007100"
         CURRSTATUS="0" ACTSTATUS="1" LIVESTATUS="1" CENTSTATUS="0"
         OPERSTATUS="21" IFNSFL="0264" IFNSUL="0264" OKATO="80427000000"
         OKTMO="80727000" STARTDATE="2013-10-31" ENDDATE="2079-06-06" UPDATEDATE="2015-04-21" />
         * **/

        AddrObj addrObjLevel3 = new AddrObj(
                UUID.fromString("fc1294b0-ac37-4257-a848-4ae4818d805c"),
                UUID.fromString("ee6cdb2f-ded3-49ed-b324-b2307c6e9cc7"),
                objLevel2,
                "Пушкина",
                "Пушкина",
                "пер",
                "452681"
        );

        addrObjRepository.save(addrObjLevel3);

        /*
        AOGUID:ee6cdb2f-ded3-49ed-b324-b2307c6e9cc7
        HOUSEGUID:1d43c361-9850-4b8a-8081-032423e3497e
        HOUSEID:1d43c361-9850-4b8a-8081-032423e3497e
        COUNTER:7
        ENDDATE:2013-10-29
        ESTSTATUS:2
        HOUSENUM:18
        POSTALCODE:452683
        STARTDATE:1900-01-01
        STATSTATUS:0
        STRSTATUS:0
        UPDATEDATE:2012-03-24
        */

        House house1 = new House();
        house1.setHouseId(UUID.fromString("1d43c361-9850-4b8a-8081-032423e3497e"));
        house1.setHouseGuid(UUID.fromString("1d43c361-9850-4b8a-8081-032423e3497e"));
        house1.setParentObj(addrObjLevel3);
        house1.setHouseNum("18");
        house1.setPostalCode("452683");

        houseRepository.save(house1);

        /**
         AOGUID:ee6cdb2f-ded3-49ed-b324-b2307c6e9cc7
         COUNTER:21
         ENDDATE:2013-10-31
         ESTSTATUS:2
         HOUSEGUID:066cfc13-579b-4254-8d71-1943615d761a
         HOUSEID:8c4d27dc-6de3-4137-a676-03930d1bea73
         HOUSENUM:4
         POSTALCODE:452681
         STARTDATE:2013-10-29
         STATSTATUS:0
         STRSTATUS:0
         UPDATEDATE:2013-12-11
         */
        House house2 = new House(
                UUID.fromString("8c4d27dc-6de3-4137-a676-03930d1bea73"),
                UUID.fromString("066cfc13-579b-4254-8d71-1943615d761a"),
                addrObjLevel3,
                "4",
                "420101");

        houseRepository.save(house2);

    }

    @Test
    public void test2SimpleRead(){
        AddrObj addrObj1 = addrObjRepository.findByAoGuid(UUID.fromString("2c9997d2-ce94-431a-96c9-722d2238d5c8"));
        AddrObj addrObj2 = addrObjRepository.findByAoId(UUID.fromString("6c222277-e9d1-43e1-8fcd-8e7bc4cfac69"));
        AddrObj addrObj3 = addrObjRepository.findByFormalName("Пушкина");
        AddrObj addrObj4 = addrObjRepository.findByOfficialName("Нефтекамск");
        AddrObj addrObj5 = addrObjRepository.findByShortName("Респ");
        AddrObj addrObj6 = addrObjRepository.findByPostalCode("450000");
        AddrObj addrObj7 = addrObjRepository.findByParentObj(addrObj4);

        assertNotNull(addrObj1);
        assertNotNull(addrObj2);
        assertNotNull(addrObj3);
        assertNotNull(addrObj4);
        assertNotNull(addrObj5);
        assertNotNull(addrObj6);
        assertNotNull(addrObj7);
        assertEquals(addrObj2, addrObj4);
        assertEquals(addrObj5, addrObj6);
        assertEquals(addrObj3, addrObj7);

        House house1 = houseRepository.findByHouseGuid(UUID.fromString("066cfc13-579b-4254-8d71-1943615d761a"));
        House house2 = houseRepository.findByHouseId(UUID.fromString("8c4d27dc-6de3-4137-a676-03930d1bea73"));
        House house4 = houseRepository.findByPostalCode("420101");
        House house5 = houseRepository.findByHouseNum("18");
        List<House> house3 = houseRepository.findByParentObj(addrObj3);
        assertNotNull(house1);
        assertNotNull(house2);
        assertNotNull(house4);
        assertNotNull(house5);
        assertEquals(2, house3.size());
    }

    @Test
    public void test3SimpleUpdate(){
        AddrObj addrObj = addrObjRepository.findByFormalName("Пушкина");
        addrObj.setOfficialName("Пушкина-Колотушкина");
        addrObjRepository.save(addrObj);

        House houseObj = houseRepository.findByHouseNum("18");
        houseObj.setPostalCode("XXXXXX");
        houseRepository.save(houseObj);
    }

    @Test
    public void test4SimpleDelete(){
        List<House> houses = (List<House>) houseRepository.findAll();
        List<AddrObj> addrObjs = (List<AddrObj>) addrObjRepository.findAll();

        houseRepository.delete(houses);
        addrObjRepository.delete(addrObjs);
    }
}
