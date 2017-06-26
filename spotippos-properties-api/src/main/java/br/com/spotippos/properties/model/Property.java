package br.com.spotippos.properties.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Classe que representa um modelo de um propriedade
 *
 * @author Vinicius Cunha
 */
@Entity
public class Property {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long idProperty;

    @NotNull private String title;
    @NotNull private Long price;
    @NotNull private String description;

    @Min(0) @Max(1400)
    @NotNull private Integer x;

    @Min(0) @Max(1000)
    @NotNull private Integer y;

    @Min(1) @Max(5)
    @NotNull private Integer beds;

    @Min(1) @Max(4)
    @NotNull private Integer baths;

    @Min(20) @Max(240)
    @NotNull private Integer squareMeters;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="province", joinColumns=@JoinColumn(name="property_id"))
    private List<Province> provinces;

    public Long getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(Long idProperty) {
        this.idProperty = idProperty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getBaths() {
        return baths;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public Integer getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }
}
