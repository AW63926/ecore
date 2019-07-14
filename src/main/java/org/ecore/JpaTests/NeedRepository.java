package org.ecore.JpaTests;

import org.ecore.model.Need;
import org.springframework.data.repository.CrudRepository;

public interface NeedRepository extends CrudRepository<Need, Long> {

}
