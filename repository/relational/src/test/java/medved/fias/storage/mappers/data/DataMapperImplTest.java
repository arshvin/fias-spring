package medved.fias.storage.mappers.data;

import medved.fias.storage.ConfigStorage;
import medved.fias.storage.DataHolderForTests;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;
import medved.fias.storage.repositories.AddrObjJpaRepository;
import medved.fias.storage.repositories.HouseJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by arshvin on 27.01.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConfigStorage.class})
public class DataMapperImplTest {

    @Autowired
    private AddrObjJpaRepository addrObjJpaRepository;
    @Autowired
    private HouseJpaRepository houseJpaRepository;

    private DataMapper dataMapper = new DataMapperImpl(addrObjJpaRepository,houseJpaRepository);

    @Before
    public void before(){
        //Create and fill test data
        List<House> housesList = new LinkedList<>();
        String[] uuidHouses = new String[]{
                "39d122fc-be61-47b1-be35-dbdee2ed00dc", //"AOGUID:1a5eeb18-7835-4f55-9d92-822f6a21737b AOID:dea66a01-195e-4e1f-b0b8-88d44add3b0c FORMALNAME:Плешково
                "783d937e-70aa-4ee3-80da-1ef7f913a019", //AOGUID:94e37e5c-c674-444b-b74e-6a9cb9f0df9f AOID:df5df308-9a71-46c7-ba07-d0f3bfd6722d FORMALNAME:Полевой
                "288c436d-8b82-4ca8-a5b8-6cb3d78d16b0", //AOGUID:f0d7370b-53b8-4db7-bb47-957e00066979 AOID:66a6dd5f-c140-406e-9e13-f97e638f4b5a FORMALNAME:Школьная
                "e9fe0fae-0113-44eb-974e-88d77a1f7ce1", //AOGUID:3fcae74c-203c-406b-8dc2-be3261d03400 AOID:667b4074-0d02-4b94-83f6-4c5d98c7252d FORMALNAME:Ленина
                "e8867960-6a4c-4b74-9d0e-4a1f226a08ae", //AOGUID:b9ad6dec-d71f-4cce-8a1f-5336bd21dc68 AOID:468f54c7-ef8c-4942-bba6-450c77fc24a4 FORMALNAME:Совхозный
        };

        List<AddrObj> addrObjList = new LinkedList<>();
        String[] uuidAddr = new String[]{
                "1a5eeb18-7835-4f55-9d92-822f6a21737b", //FORMALNAME:Плешково
                "94e37e5c-c674-444b-b74e-6a9cb9f0df9f", //FORMALNAME:Полевой
                "f0d7370b-53b8-4db7-bb47-957e00066979", //FORMALNAME:Школьная
                "3fcae74c-203c-406b-8dc2-be3261d03400", //FORMALNAME:Ленина
                "b9ad6dec-d71f-4cce-8a1f-5336bd21dc68", //FORMALNAME:Совхозный
                "0c85bd26-3c00-48fa-bb7e-ba54103b5da7", //FORMALNAME:Центральное
                "a142542c-b625-457b-844d-ba00f54d0074", //FORMALNAME:Молчаны
                "f52b4fbe-a35b-4cac-9c92-43267674b66c", //FORMALNAME:Иваньково
                "6bd2b87b-89ae-4e55-a1fa-b208321a65c8", //ORMALNAME:Разнежье
                "57e3991f-e685-4ba2-9cef-9988e6fa5862", //FORMALNAME:Фролово
                "308b0e8d-720d-45d8-abf7-9b1df375f1480",//FORMALNAME:Кимрский
        };

        for (String uuid : uuidAddr) {
            AddrObj addrObj = DataHolderForTests.getAddrObjBy(uuid);
            addrObjList.add(addrObj);
            addrObjJpaRepository.save(addrObj);
        }

        for (String uuid : uuidHouses) {
            House house = DataHolderForTests.getHouseBy(uuid);
            housesList.add(house);
            houseJpaRepository.save(house);
        }



    }

    @Test
    public void testSchemaToDomain() throws Exception {
    }

//    @Test
//    public void testSchemaToDomain1() throws Exception {
//
//    }
//
//    @Test
//    public void testDomainToData() throws Exception {
//
//    }
//
//    @Test
//    public void testDomainToData1() throws Exception {
//
//    }

}