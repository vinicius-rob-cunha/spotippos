package br.com.spotippos.properties.model;

import java.util.List;

/**
 * Classe utilizado para carregar a base de dados inicial
 *
 * @author Vinicius Cunha
 */
public class PropertiesDataLoader {

    private Integer foundProperties;
    private List<Property> properties;

    public PropertiesDataLoader() { /* FOR SPRING */ }

    public PropertiesDataLoader(List<Property> properties) {
        this.properties = properties;
        foundProperties = properties.size();
    }

    public Integer getFoundProperties() {
        return foundProperties;
    }

    public void setFoundProperties(Integer foundProperties) {
        this.foundProperties = foundProperties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
