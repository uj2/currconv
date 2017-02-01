package org.balaguta.currconv.data;

import org.balaguta.currconv.data.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {
    Country findByName(String name);
    List<Country> findAllByOrderByName();
}
