package org.balaguta.currconv.data;

import org.balaguta.currconv.data.entity.Conversion;
import org.springframework.data.repository.CrudRepository;

public interface ConversionRepository extends CrudRepository<Conversion, Long> {
}
