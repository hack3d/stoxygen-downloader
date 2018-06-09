package de.stoxygen.repository;

import de.stoxygen.model.Tickdata1Minute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface Tickdata1minuteRepository extends CrudRepository<Tickdata1Minute, Long> {
}
