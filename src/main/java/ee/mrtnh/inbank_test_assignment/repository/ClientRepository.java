package ee.mrtnh.inbank_test_assignment.repository;

import ee.mrtnh.inbank_test_assignment.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findByPersonalCode(long personalCode);

}
