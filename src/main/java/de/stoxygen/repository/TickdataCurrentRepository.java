package de.stoxygen.repository;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Exchange;
import de.stoxygen.model.TickdataCurrent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface TickdataCurrentRepository extends CrudRepository<TickdataCurrent, Long> {
    TickdataCurrent findTop1ByExchangeAndBondAndAggregatedOrderByInsertTimestampAsc(Exchange exchange, Bond bond, Boolean aggregated);

    List<TickdataCurrent> findByExchangeAndBondAndAggregatedAndInsertTimestampGreaterThanEqual(Exchange exchange, Bond bond, Boolean aggregated, Date insertTimestamp);

    List<TickdataCurrent> findByExchangeAndBondAndAggregatedAndInsertTimestampBetween(Exchange exchange, Bond bond, Boolean aggregated, Date startTimestamp, Date endTimestamp);

    List<TickdataCurrent> findByExchangeAndBondAndInsertTimestampBetween(Exchange exchange, Bond bond, Date startTimestamp, Date endTimestamp);

}
