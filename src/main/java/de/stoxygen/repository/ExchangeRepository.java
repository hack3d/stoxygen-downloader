package de.stoxygen.repository;

import de.stoxygen.model.Exchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ExchangeRepository extends CrudRepository<Exchange, Integer> {
    Exchange findByExchangesId(Integer exchangesId);

    Exchange findBySymbol(String symbol);
}
