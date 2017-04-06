package medved.fias.storage;

import medved.fias.storage.repositories.AddrObjJpaRepository;
import medved.fias.storage.repositories.HouseJpaRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by arshvin on 10.08.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConfigStorage.class})
public class DataStorageImplTest {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AddrObjJpaRepository addrObjJpaRepository;

    @Autowired
    private HouseJpaRepository houseJpaRepository;

    @Autowired
    private DataStorageImpl dataStorage;

    private String [] AddrsObjs = {
            "7a53668c-1cf4-4ed0-a4df-526e0baa7216",
            "b9ad6dec-d71f-4cce-8a1f-5336bd21dc68",
            "3fcae74c-203c-406b-8dc2-be3261d03400",
            "989df635-a9a0-4d58-85b9-64ed6da7e4cd",
            "337edd52-feb9-4335-816a-616b87ec18b1",
            "f0d7370b-53b8-4db7-bb47-957e00066979",
            "fce962f2-dff8-4eea-8413-5c94e0e69dec",
            "1a5eeb18-7835-4f55-9d92-822f6a21737b",
            "94e37e5c-c674-444b-b74e-6a9cb9f0df9f",
            "0208b4ad-dc6d-469f-8224-8e1c9f72f0ad",
            "bebfd75d-a0da-4bf9-8307-2e2c85eac463",
            "57e3991f-e685-4ba2-9cef-9988e6fa5862",
            "a142542c-b625-457b-844d-ba00f54d0074",
            "0c85bd26-3c00-48fa-bb7e-ba54103b5da7"
    };

    private String [] HouseObj = {
            "39d122fc-be61-47b1-be35-dbdee2ed00dc",
            "ddf6bae2-3188-4eae-9c9e-010480e5f610",
            "90cd6d00-2b96-4404-9336-0136b3b6470b",
            "4eb4875e-c3ca-455d-b0ad-0141a4b0c8cd",
            "172481e4-1394-46bc-ba16-bf829b0aac91",
            "783d937e-70aa-4ee3-80da-1ef7f913a019",
            "888804bf-ff43-41ae-818c-c5d0dcfa0490",
            "19ed9dc1-1f62-45aa-94f8-ceecd7e86f07",
            "b21b4829-2625-45fd-85b8-1a3b06766cb7",
            "783d937e-70aa-4ee3-80da-1ef7f913a019",
            "288c436d-8b82-4ca8-a5b8-6cb3d78d16b0",
            "e85c3b92-362d-433b-9d07-6f0d792d09ff",
            "aac7377b-27bf-48c4-aa00-b1eb73b1ccef",
            "c91a994a-22c8-41b5-a18d-1d6ab92fcfa6",
            "b1697e22-cb32-4e00-9e12-0357b13435c3",
            "e9fe0fae-0113-44eb-974e-88d77a1f7ce1",
            "636299a6-4df4-4dd5-ab43-00cd4cd5d3cb",
            "61e3be5a-3974-429e-a476-014158dbe0c1",
            "0a475087-5fbd-4d1e-8611-cc85cd55b68a"
    };

    @After
    public void cleanAllData(){
        houseJpaRepository.deleteAll();;
        addrObjJpaRepository.deleteAll();;
    }

    @Before
    public void prepareData(){

    }

    @Test
    public void testPutAddrObj(){

//        dataStorage.putAddrObj();
    }

    @Test
    public void testPutHouse() throws Exception {

    }

    @Test
    public void testGetObjectBy() throws Exception {

    }

    @Test
    public void testGetObjectBy1() throws Exception {

    }

    @Test
    public void testGetAddrObjAll() throws Exception {

    }

    @Test
    public void testGetHousesAll() throws Exception {

    }

    @Test
    public void testGetAddrObjCount() throws Exception {

    }

    @Test
    public void testGetHousesCount() throws Exception {

    }
}