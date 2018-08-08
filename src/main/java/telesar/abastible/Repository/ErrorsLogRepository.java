package telesar.abastible.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import telesar.abastible.DAO.ErrorLog;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface ErrorsLogRepository extends MongoRepository<ErrorLog, Long> {



}