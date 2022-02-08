package cl.prueba.globalLogic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.prueba.globalLogic.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long>{

}
