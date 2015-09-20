/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.domain;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author arshvin
 */
public interface HouseRepository extends CrudRepository<House, UUID> {

    House findByHouseNum(String houseNum);
    House findByPostalCode(String postalCode);
    List<House> findByParentObj(AddrObj parentObj);
    House findByHouseId(UUID houseId);
    House findByHouseGuid(UUID houseGuid);
}
