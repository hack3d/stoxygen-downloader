package de.stoxygen.repository;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;
import de.stoxygen.model.Tickdata1Minute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface Tickdata1minuteRepository extends CrudRepository<Tickdata1Minute, Long> {
    List<Tickdata1Minute> findByBonds(Bond bonds);

    List<Tickdata1Minute> findByBondsAndExchangesAndInsertTimestampLessThanEqual(Bond bonds, Exchange exchanges, Date startTimestamp);
}
