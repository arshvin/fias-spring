/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.fias.storage.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author arshvin
 */
@Repository
@Transactional
public interface AddrObjRepository extends CrudRepository<AddrObj,UUID> {

    AddrObj findByAoGuid(UUID aoGuid);
    AddrObj findByAoId(UUID aoId);
    AddrObj findByParentObj(AddrObj parentObj);
    AddrObj findByShortName(String shortName);
    AddrObj findByOfficialName(String officialName);
    AddrObj findByFormalName(String formalName);
    AddrObj findByPostalCode(String postalCode);
}
