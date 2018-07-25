package telesar.abastible.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import telesar.abastible.DAO.Mediciones;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface MedicionesRepository extends MongoRepository<Mediciones, Long> {



}
