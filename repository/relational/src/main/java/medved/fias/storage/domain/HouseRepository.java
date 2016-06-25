/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.fias.storage.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author arshvin
 */
@Repository
@Transactional
public interface HouseRepository extends CrudRepository<House, UUID> {

    House findByHouseNum(String houseNum);
    House findByPostalCode(String postalCode);
    List<House> findByParentObj(AddrObj parentObj);
    House findByHouseId(UUID houseId);
    House findByHouseGuid(UUID houseGuid);
}
