package org.balaguta.currconv.data;

import org.balaguta.currconv.data.entity.Conversion;
import org.balaguta.currconv.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversionRepository extends CrudRepository<Conversion, Long> {
    List<Conversion> findFirst10ByUserOrderByTimestampDesc(User user);
}
