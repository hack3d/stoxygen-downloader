package de.stoxygen.repository;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BondRepository extends CrudRepository<Bond, Integer> {
    List<Bond> findByIsin(String isin);

    List<Bond> findByState(Integer state);

    Bond findByCryptoPair(String cryptoPair);

}
