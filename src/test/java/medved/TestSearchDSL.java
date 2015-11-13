/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved;

import medved.domain.AddrObj;
import medved.domain.AddrObjRepository;
import medved.domain.House;
import medved.domain.HouseRepository;
import medved.dsl.AddrObjQuerySuite;
import medved.dsl.HouseQuerySuite;
import medved.generated.Houses;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author arshvin
 */
//@Ignore("Disactivated temporally for quikness...")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigApp.class)
public class TestSearchDSL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AddrObjQuerySuite addrObjQuerySuite;
    @Autowired
    private HouseQuerySuite houseObjQuerySuite;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private AddrObjRepository addrObjRepository;

    public void preparing() {

        //{aoId, aoGuid, parentObj, formalName, officialName, shortName, postalCode}
        String[][] addrObjTestData = {
                {"05918e28-8be0-49ac-8d5a-8805011a1f95","a9a71961-9363-44ba-91b5-ddf0463aebc2",null,
                        "Тамбовская","Тамбовская","обл","393290"},
                {"30532abb-f4be-4167-8785-6f4929eb1622","cda1b9dd-4a8c-486a-ab25-05229c579774",
                        "a9a71961-9363-44ba-91b5-ddf0463aebc2","Рассказовский","Рассказовский","р-н","393290"},
                {"7f24f009-e19b-48f7-aed2-d16904f9c0b9","724dea3f-c799-4880-aa56-f0c801a3359a",
                        "cda1b9dd-4a8c-486a-ab25-05229c579774","Никольское","Никольское","с","393290"},
                {"149c0ccb-51fd-4776-8c6d-000023253cdc","323febe6-cd89-4773-a46c-aab794fb7cbc",
                        "724dea3f-c799-4880-aa56-f0c801a3359a","Московская","Московская","ул","393290"},
                {"af757d44-3438-4040-9b68-d95099318998","da051ec8-da2e-4a66-b542-473b8d221ab4",null,
                        "Волгоградская","Волгоградская","обл",null},
                {"214ac499-b409-4ce2-aa0e-922b830ea27d","a52b7389-0cfe-46fb-ae15-298652a64cf8",
                        "da051ec8-da2e-4a66-b542-473b8d221ab4","Волгоград","Волгоград","г",null},
                {"ed3cadb1-9820-48ad-98e0-00002d832f71","0e8b1e3c-01e0-4ebd-9bfc-584b157b0b31",
                        "a52b7389-0cfe-46fb-ae15-298652a64cf8","Лавровая","Лавровая","ул",null},
                {"1ae9b068-1c65-4ab5-8b67-7b8fef389537","d028ec4f-f6da-4843-ada6-b68b3e0efa3d",null,
                        "Тульская","Тульская","обл","301126"},
                {"93506a4a-184d-4d6a-a10a-16b561d0abeb","e879c06b-d180-4177-a6ff-4aad5588c6b3",
                        "d028ec4f-f6da-4843-ada6-b68b3e0efa3d","Ленинский","Ленинский","р-н","301126"},
                {"b4ecdf0b-0897-4525-aa2a-dca524457fef","8dac1393-f914-47f7-b78b-3cbacd8950f2" ,
                        "e879c06b-d180-4177-a6ff-4aad5588c6b3","Верхние Брусы","Верхние Брусы","д","301126"},
        };

        for (String [] item : addrObjTestData){
            AddrObj a = new AddrObj(UUID.fromString(item[0]), UUID.fromString(item[1]),
                    item[2] == null ? null : addrObjRepository.findByAoGuid(UUID.fromString(item[2])),
                    item[3], item[4], item[5], item[6]);

            addrObjRepository.save(a);
            logger.info(String.format("Object %s has been saved", a));

        }

        //{houseId, houseGuid, parentObj, HouseNum, postalCode}
        String [][] houseTestData = {
                {"8b306985-8b80-48e2-9ee4-20b94a0b9f66","8b306985-8b80-48e2-9ee4-20b94a0b9f66",
                        "0e8b1e3c-01e0-4ebd-9bfc-584b157b0b31","31","416525"},
                {"558e60ea-c6e1-4128-b716-1d61b062be62","558e60ea-c6e1-4128-b716-1d61b062be62",
                        "0e8b1e3c-01e0-4ebd-9bfc-584b157b0b31","54","416525"},
                {"516ca8dc-6ed0-48e3-9395-012333b0aae9","516ca8dc-6ed0-48e3-9395-012333b0aae9",
                        "323febe6-cd89-4773-a46c-aab794fb7cbc","21","416525"},
                {"44c1c5cf-52a0-4ad5-acaa-000016cb3e32","44c1c5cf-52a0-4ad5-acaa-000016cb3e32",
                        "0e8b1e3c-01e0-4ebd-9bfc-584b157b0b31","4","680026"},
                {"fd353e0e-811b-4d4e-802e-000016ea8653","da4f20a8-bc3d-4f7b-b1f1-19cc0915c1d8",
                        "0e8b1e3c-01e0-4ebd-9bfc-584b157b0b31","6","658760"},
                {"a0ee7dfe-3d62-4203-8832-0000178f0c0c","a0ee7dfe-3d62-4203-8832-0000178f0c0c",
                        "0e8b1e3c-01e0-4ebd-9bfc-584b157b0b31","13А","363306"},
                {"1c9935bf-d39b-4561-833e-000017dd152c","f340eaeb-1fa8-4514-9801-67de6841683a",
                        "8dac1393-f914-47f7-b78b-3cbacd8950f2","2А","654038"},
                {"171b55f8-da87-4296-80e8-000018034740","171b55f8-da87-4296-80e8-000018034740",
                        "8dac1393-f914-47f7-b78b-3cbacd8950f2","21в","140120"},
                {"9bcad9af-5c99-423f-87a2-000018b74809","340e1114-1bf7-46f9-b4ea-ac9b2500764d",
                        "8dac1393-f914-47f7-b78b-3cbacd8950f2","3","662518"},
                {"c6fcb489-da7e-4f73-bbb0-000019033b11","c6fcb489-da7e-4f73-bbb0-000019033b11",
                        "8dac1393-f914-47f7-b78b-3cbacd8950f2","32","309574"},
                {"dfb8edb5-8200-437e-bf82-00001918e5a9","dfb8edb5-8200-437e-bf82-00001918e5a9",
                        "8dac1393-f914-47f7-b78b-3cbacd8950f2","72","423195"},
                {"63797a05-d005-4251-af9a-0000191a88b4","31be6f71-4273-45b6-8ab0-9fd83a41b6d3",
                        "8dac1393-f914-47f7-b78b-3cbacd8950f2","7","301030"},
                {"c9e04203-9a8e-4bb2-859f-000019554b6f","d901800d-0033-4877-a7ef-d1de5b9ea8b0",
                        "8dac1393-f914-47f7-b78b-3cbacd8950f2","21","625005"},
                {"e25e5538-895c-406d-b4bb-000019895190","e25e5538-895c-406d-b4bb-000019895190",
                        "8dac1393-f914-47f7-b78b-3cbacd8950f2","24","452638"}
        };

        for(String[] item:houseTestData){
            House h = new House(UUID.fromString(item[0]), UUID.fromString(item[1]),
                    addrObjRepository.findByAoGuid(UUID.fromString(item[2])), item[3], item[4]);
            houseRepository.save(h);
            logger.info(String.format("Object %s has been saved", h));
        }
    }

//    @AfterClass
//    public static void cleaning(){
//        addrObjRepository.deleteAll();
//    }

    @Test
    public void testSimpleQuery() {

        preparing();

        @SuppressWarnings("unchecked")
        List<AddrObj> result = addrObjQuerySuite.simpleQuery("Тамбовская", "formalName", "officialName").execute();
        Assert.assertEquals(1, addrObjQuerySuite.getResultSize());
        Assert.assertEquals("05918e28-8be0-49ac-8d5a-8805011a1f95", result.get(0).getAoId().toString());

        result = addrObjQuerySuite.simpleQuery("Московская", "formalName", "officialName").execute();
        Assert.assertEquals(1,addrObjQuerySuite.getResultSize());
        Assert.assertEquals("149c0ccb-51fd-4776-8c6d-000023253cdc", result.get(0).getAoId().toString());

        result = addrObjQuerySuite.simpleQuery("Рассказовский", "formalName", "officialName").execute();
        Assert.assertEquals(1,addrObjQuerySuite.getResultSize());
        Assert.assertEquals("30532abb-f4be-4167-8785-6f4929eb1622",result.get(0).getAoId().toString());

        result = addrObjQuerySuite.simpleQuery("393290", "postalCode").execute();
        Assert.assertEquals(4,addrObjQuerySuite.getResultSize());

        result = addrObjQuerySuite.simpleQuery("301126", "postalCode").execute();
        Assert.assertEquals(3,addrObjQuerySuite.getResultSize());

        result = addrObjQuerySuite.simpleQuery("416525", "houses.postalCode").execute();
        Assert.assertEquals(2,addrObjQuerySuite.getResultSize());

        result = addrObjQuerySuite.simpleQuery("662518", "houses.postalCode").execute();
        Assert.assertEquals(1,addrObjQuerySuite.getResultSize());

        // tests for houses


    }
}
