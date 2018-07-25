package telesar.abastible.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import telesar.abastible.DAO.Log;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface LogRepository extends MongoRepository<Log, Long> {



}