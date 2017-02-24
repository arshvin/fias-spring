package medved.fias.storage.mappers.data;

import com.google.common.collect.ImmutableList;
import medved.fias.content.Data;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import medved.fias.storage.ConfigStorage;
import medved.fias.storage.DataHolderForTests;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;
import medved.fias.storage.mappers.exceptions.SchemaToDomainMapperException;
import medved.fias.storage.repositories.AddrObjJpaRepository;
import medved.fias.storage.repositories.HouseJpaRepository;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.ExpectedCount;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by arshvin on 27.01.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConfigStorage.class})
public class DataMapperImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AddrObjJpaRepository addrObjJpaRepository;
    @Autowired
    private HouseJpaRepository houseJpaRepository;

    private DataMapper dataMapper = null;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @After
    public void after(){
        addrObjJpaRepository.deleteAll();
    }

    @Before
    public void before(){
        //Lets instantiate the dataMapper only once here rather than at the object of the testclass creation time
        if (dataMapper == null){
            dataMapper = new DataMapperImpl(addrObjJpaRepository,houseJpaRepository);
        }
        //Create and fill test data
        List<House> housesList = new LinkedList<>();
        String[] uuidHouses = new String[]{
                "39d122fc-be61-47b1-be35-dbdee2ed00dc", //"AOGUID:1a5eeb18-7835-4f55-9d92-822f6a21737b AOID:dea66a01-195e-4e1f-b0b8-88d44add3b0c FORMALNAME:Плешково
                "783d937e-70aa-4ee3-80da-1ef7f913a019", //AOGUID:94e37e5c-c674-444b-b74e-6a9cb9f0df9f AOID:df5df308-9a71-46c7-ba07-d0f3bfd6722d FORMALNAME:Полевой
                "288c436d-8b82-4ca8-a5b8-6cb3d78d16b0", //AOGUID:f0d7370b-53b8-4db7-bb47-957e00066979 AOID:66a6dd5f-c140-406e-9e13-f97e638f4b5a FORMALNAME:Школьная
                "e9fe0fae-0113-44eb-974e-88d77a1f7ce1", //AOGUID:3fcae74c-203c-406b-8dc2-be3261d03400 AOID:667b4074-0d02-4b94-83f6-4c5d98c7252d FORMALNAME:Ленина
                "e8867960-6a4c-4b74-9d0e-4a1f226a08ae", //AOGUID:b9ad6dec-d71f-4cce-8a1f-5336bd21dc68 AOID:468f54c7-ef8c-4942-bba6-450c77fc24a4 FORMALNAME:Совхозный
                "a0413df7-99a5-4cc6-a879-32fd2b3e2eda"  //AOGUID:b9ad6dec-d71f-4cce-8a1f-5336bd21dc68 AOID:468f54c7-ef8c-4942-bba6-450c77fc24a4 FORMALNAME:Совхозный

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
                "6bd2b87b-89ae-4e55-a1fa-b208321a65c8", //FORMALNAME:Разнежье
                "57e3991f-e685-4ba2-9cef-9988e6fa5862", //FORMALNAME:Фролово
                "989df635-a9a0-4d58-85b9-64ed6da7e4cd", //FORMALNAME:Красная Горка - for test of 02970867-4367-45dd-9924-a7c1f6dd52b2

        };

        for (String uuid : uuidAddr) {
            AddrObj addrObj = DataHolderForTests.getAddrObjBy(uuid);

            logger.debug("AddrObj {} was fetched from fake storage", addrObj);

            if (addrObj.getParentObj() != null){
                addrObjJpaRepository.save(addrObj.getParentObj());

                logger.debug("Parent object {} was saved", addrObj.getParentObj());
            }

            addrObjList.add(addrObj);
            addrObjJpaRepository.save(addrObj);

            logger.debug("AddrOj {} was saved", addrObj);

        }

        for (String uuid : uuidHouses) {
            House house = DataHolderForTests.getHouseBy(uuid);

            AddrObj parentAddrObj = addrObjJpaRepository.findByAoGuid(house.getParentObj().getAoGuid());
            if (parentAddrObj != null){

                logger.debug("Parent obj {} of {} already exists at the db", parentAddrObj, house);

                house.setParentObj(parentAddrObj);
            }

            housesList.add(house);
            houseJpaRepository.save(house);

            logger.debug("The {} was saved", house);

        }
    }

    @Test
    public void testSchemaToDomain() throws Exception {

        /**
         * Tests right behavior of mapping from AddressObjects.Object to AddrObj
         */
        AddressObjects.Object addrObjectSchema01 = DataHolderForTests.getSchemaAddrObjBy("1a5eeb18-7835-4f55-9d92-822f6a21737b");
        logger.debug("AddressObjects.Object is {}", addrObjectSchema01);

        AddrObj addrObj01 = addrObjJpaRepository.findByAoGuid(UUID.fromString("1a5eeb18-7835-4f55-9d92-822f6a21737b"));
        AddrObj addrObj02 = dataMapper.schemaToDomain(addrObjectSchema01);

        logger.debug("addrObj01={}",addrObj01);
        logger.debug("addrObj02={}",addrObj02);

        Assert.assertEquals(addrObj01, addrObj02);

        /**
         * Test of throwing exception due to absent of the parent AddrObj
         *
         * This object was saved to the DB as the parent of another one.Therefore its parent shouldn't present at the DB
         */
        AddressObjects.Object addrObjectSchema02 = DataHolderForTests.getSchemaAddrObjBy("02970867-4367-45dd-9924-a7c1f6dd52b2");
        logger.debug("AddressObjects.Object is {}", addrObjectSchema02);

        thrown.expect(SchemaToDomainMapperException.class);
        thrown.expectMessage(CoreMatchers.startsWith("Couldn't find parent Object guid"));

        AddrObj addrObj03 = dataMapper.schemaToDomain(addrObjectSchema02);

    }

    @Test
    public void testSchemaToDomain1() throws Exception {

        /**
         * Normal mapping behavior test
         */
        Houses.House houseSchema01 = DataHolderForTests.getSchemaHouseBy("39d122fc-be61-47b1-be35-dbdee2ed00dc");

        House house02 = dataMapper.schemaToDomain(houseSchema01);
        House house01 = houseJpaRepository.findByHouseGuid(UUID.fromString("39d122fc-be61-47b1-be35-dbdee2ed00dc"));
        logger.debug("house01={}", house01);
        logger.debug("house02={}", house02);

        Assert.assertEquals(house01, house02);

        /**
         * Test of throwing exception due to absent of the parent AddrObj
         */
        Houses.House houseSchema02 = DataHolderForTests.getSchemaHouseBy("2096ef58-a4ef-4b37-89b1-029b1acf4533");
        House house03 = houseJpaRepository.findByHouseGuid(UUID.fromString("2096ef58-a4ef-4b37-89b1-029b1acf4533"));

        thrown.expect(SchemaToDomainMapperException.class);
        thrown.expectMessage(CoreMatchers.startsWith("Couldn't find parent guid"));

        House house04 = dataMapper.schemaToDomain(houseSchema02);


    }

    @Test
    public void testDomainToData() throws Exception {

        AddrObj addrObj = addrObjJpaRepository.findByAoGuid(UUID.fromString("b9ad6dec-d71f-4cce-8a1f-5336bd21dc68"));

        //Data domainToData(AddrObj addrObj) throws DomainToDataMapperException
        Data dataActual = dataMapper.domainToData(addrObj);

        Data dataExpected = new DataImpl(
                "Совхозный",
                "Фролово",
                ImmutableList.copyOf(new String [] {"14","42"})
        );

        Assert.assertEquals(dataExpected, dataActual);

    }

    @Test
    public void testDomainToData1() throws Exception {

       House house = houseJpaRepository.findByHouseGuid(UUID.fromString("288c436d-8b82-4ca8-a5b8-6cb3d78d16b0"));

        //Data domainToData(House houseObj) throws DomainToDataMapperException
        Data dataActual = dataMapper.domainToData(house);

        Data dataExpected = new DataImpl(
                "83",
                "Школьная",
                null
        );

        Assert.assertEquals(dataExpected, dataActual);

    }
}