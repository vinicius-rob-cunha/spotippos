package br.com.spotippos.properties;

import br.com.spotippos.properties.controller.PropertyController;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Classe responsável por mapear os serviços disponíveis
 *
 * @author Vinicius Cunha
 */
@Component
public class ResourceProcessor implements org.springframework.hateoas.ResourceProcessor<RepositoryLinksResource> {

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        resource.add(linkTo(methodOn(PropertyController.class).register(null)).withRel("create"));
        resource.add(linkTo(methodOn(PropertyController.class).findById(null)).withRel("findById"));
        resource.add(linkTo(methodOn(PropertyController.class).findByCoordinates(null, null, null, null)).withRel("findByCoordinates"));
        resource.add(linkTo(methodOn(PropertyController.class).advancedSearch(null,  null, null,null,null)).withRel("advancedSearch"));
        return resource;
    }

}
