package br.com.spotippos.properties.controller;

import br.com.spotippos.properties.model.Property;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Classe que representa o resource Property no REST
 *
 * @author Vinicius Cunhas
 */
public class PropertyResource extends ResourceSupport {

    private Property property;

    public PropertyResource(Property property) {
        this.property = property;
        add(linkTo(PropertyController.class).slash(property.getIdProperty()).withSelfRel());
    }

    public Property getProperty() {
        return property;
    }

}
