package br.com.spotippos.properties.model;

import br.com.spotippos.properties.controller.PropertyResource;

import java.util.List;

/**
 * Bean que representa a resposta de uma busca que pode retornar diversas propriedades
 *
 * @author Vinicius Cunha
 */
public class PropertiesSearchResult {

    private Integer foundProperties;
    private List<PropertyResource> properties;

    public PropertiesSearchResult() { /* FOR SPRING */ }

    public PropertiesSearchResult(List<PropertyResource> properties) {
        this.properties = properties;
        foundProperties = properties.size();
    }

    public Integer getFoundProperties() {
        return foundProperties;
    }

    public List<PropertyResource> getProperties() {
        return properties;
    }

}
