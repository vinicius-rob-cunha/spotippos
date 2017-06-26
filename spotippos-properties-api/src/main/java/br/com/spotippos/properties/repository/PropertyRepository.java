package br.com.spotippos.properties.repository;

import br.com.spotippos.properties.model.Property;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repositorio para as operações nas propriedades
 *
 * @author Vinicius Cunha
 */
@RepositoryRestResource(exported = false)
public interface PropertyRepository extends CrudRepository<Property, Long>, QueryDslPredicateExecutor {

    public List<Property> findByXBetweenAndYBetween(Integer ax, Integer bx, Integer ay, Integer by);

}
