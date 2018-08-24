package telesar.abastible.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import telesar.abastible.DAO.Mediciones;

import java.util.Date;
import java.util.List;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface MedicionesRepository extends MongoRepository<Mediciones, Long> {

List<Mediciones> findBySerial(String serial, Sort sort);

List<Mediciones> findByImei(String imei, Sort sort);

List<Mediciones> findByTimestampMedicionBetween(Date init, Date end, Sort sort);

}
