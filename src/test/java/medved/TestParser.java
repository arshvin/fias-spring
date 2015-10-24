/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved;

import medved.domain.AddrObjRepository;
import medved.domain.HouseRepository;
import medved.parsers.AddrObjParser;
import medved.parsers.HouseParser;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 *
 * @author arshvin
 */
//@Ignore("Disactivated temporally for quikness...")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigApp.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestParser {
    @Autowired
    @Qualifier("simpleParser1")
    private SimpleAddrObjParser simpleAddrObjParser;

    @Autowired
    @Qualifier("simpleParser2")
    private SimpleHouseParser simpleHouseParser;

    @Autowired
    private AddrObjRepository addrObjRepository;

    @Autowired
    private AddrObjParser addrObjParser;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseParser houseParser;

    @Test
    public void testSimpleParsers() throws FileNotFoundException, XMLStreamException, JAXBException {
        /**
         * Merely test parsing functionality provided by AbstractParser.class
         * */
        simpleAddrObjParser.feed(ClassLoader.getSystemResource("AS_ADDROBJ_TEST_forCreate.xml").getPath());
        simpleAddrObjParser.parse();

        simpleHouseParser.feed(ClassLoader.getSystemResource("AS_HOUSE_TEST_forCreate.xml").getPath());
        simpleHouseParser.parse();
    }

    @Test
    public void testAddrObjParserForCreate() throws FileNotFoundException, XMLStreamException, JAXBException{

        String [] AddrObjGUIDs = {
                "a9a71961-9363-44ba-91b5-ddf0463aebc2",
                "cda1b9dd-4a8c-486a-ab25-05229c579774",
                "724dea3f-c799-4880-aa56-f0c801a3359a",
                "323febe6-cd89-4773-a46c-aab794fb7cbc",
                "da051ec8-da2e-4a66-b542-473b8d221ab4",
                "a52b7389-0cfe-46fb-ae15-298652a64cf8",
                "0e8b1e3c-01e0-4ebd-9bfc-584b157b0b31",
                "d028ec4f-f6da-4843-ada6-b68b3e0efa3d",
                "e879c06b-d180-4177-a6ff-4aad5588c6b3",
                "8dac1393-f914-47f7-b78b-3cbacd8950f2"
        };

        String [] GUIDs2 = {
                "1b9deb75-7dbe-4844-85c6-0960df4185aa",
                "0208b4ad-dc6d-469f-8224-8e1c9f72f0ad"
        };

        for (String item : AddrObjGUIDs){
            assertNull(addrObjRepository.findByAoGuid(UUID.fromString(item)));
        }

        for (String item : GUIDs2){
            assertNull(addrObjRepository.findByAoGuid(UUID.fromString(item)));
        }
        
        String testXml = ClassLoader.getSystemResource("AS_ADDROBJ_TEST_forCreate.xml").getPath();
        addrObjParser.feed(testXml);
        addrObjParser.parse();
        
        for (String item : AddrObjGUIDs){
            assertNotNull(addrObjRepository.findByAoGuid(UUID.fromString(item)));
        }

        for (String item : GUIDs2){
            assertNull(addrObjRepository.findByAoGuid(UUID.fromString(item)));
        }
    }

    @Test
    public void testHouseParserForCreate() throws FileNotFoundException, XMLStreamException, JAXBException {
        assertFalse(houseRepository.findAll().iterator().hasNext());

        String [] HouseGUIDs = {
                "8b306985-8b80-48e2-9ee4-20b94a0b9f66",
                "558e60ea-c6e1-4128-b716-1d61b062be62",
                "516ca8dc-6ed0-48e3-9395-012333b0aae9"
        };

        String testXml = ClassLoader.getSystemResource("AS_HOUSE_TEST_forCreate.xml").getPath();
        houseParser.feed(testXml);
        houseParser.parse();


        for (String item : HouseGUIDs){
            assertNotNull(houseRepository.findByHouseGuid(UUID.fromString(item)));
        }
        assertNull(houseRepository.findByHouseGuid(UUID.fromString("43a8d374-ef16-4604-aac8-0000032dfdac")));
    }

    @Test
    public void testAddrObjParserForUpdate() throws FileNotFoundException, XMLStreamException, JAXBException{

        String [] GUIDs = {
                "149c0ccb-51fd-4776-8c6d-000023253cdc",
                "ed3cadb1-9820-48ad-98e0-00002d832f71"
        };

        for (String item: GUIDs){
            assertNotNull(addrObjRepository.findByAoId(UUID.fromString(item)));
        }

        String testXml = ClassLoader.getSystemResource("AS_ADDROBJ_TEST_forUpdate.xml").getPath();
        addrObjParser.feed(testXml);
        addrObjParser.parse();

        for (String item: GUIDs){
            assertNull(addrObjRepository.findByAoId(UUID.fromString(item)));
        }
    }
}
