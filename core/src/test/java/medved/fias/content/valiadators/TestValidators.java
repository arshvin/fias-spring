package medved.fias.content.valiadators;


import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.testng.Assert.*;

/**
 * Created by arshvin on 15.10.15.
 */
//@Ignore("Disactivated temporally for quikness...")
public class TestValidators {

    @Test
    //TODO: Clean up
    public void testAddrObjXml() throws IOException, SAXException {
//        SchemaValidator validator = new SchemaValidatorImpl(
//                ClassLoader.getSystemResource("AS_ADDROBJ_2_250_01_04_01_01.xsd").getPath(),
//                ClassLoader.getSystemResource("AS_ADDROBJ_TEST_forCreate.xml").getPath());
//        assertTrue(validator.examine());
    }

    @Test
    //TODO: Clean up
    public void testHouseXml() throws IOException, SAXException {
//        SchemaValidator validator = new SchemaValidatorImpl(
//                ClassLoader.getSystemResource("AS_HOUSE_2_250_02_04_01_01.xsd").getPath(),
//                ClassLoader.getSystemResource("AS_HOUSE_TEST_forCreate.xml").getPath());
//        assertTrue(validator.examine());
    }
}
