package de.stoxygen.repository;

import de.stoxygen.model.TickdataCurrent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TickdataCurrentRepository extends CrudRepository<TickdataCurrent, Long> {
}
