package br.com.spotippos.properties.controller;

import br.com.spotippos.properties.model.PropertiesSearchResult;
import br.com.spotippos.properties.model.Property;
import br.com.spotippos.properties.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.spotippos.properties.model.Province.findProvinces;

/**
 * Controller responsável por gerenciar os imoveis de Spotippos
 *
 * @author Vinicius Cunha
 */
@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService service;

    @PreAuthorize("hasAuthority('FOO_WRITE')")
    @PostMapping
    public ResponseEntity register(@Valid @RequestBody final Property property){
        property.setProvinces(findProvinces(property.getX(), property.getY()));
        Property newProperty = service.save(property);

        Link propertyLink = new PropertyResource(newProperty).getLink("self");
        URI location = URI.create(propertyLink.getHref());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public HttpEntity<PropertyResource> findById(@PathVariable final Long id){
        Property property = service.findOne(id);
        return property == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(new PropertyResource(property));
    }

    @GetMapping
    public HttpEntity<PropertiesSearchResult> findByCoordinates(@RequestParam final Integer ax, @RequestParam final Integer ay,
                                                                @RequestParam final Integer bx, @RequestParam final Integer by){
        List<PropertyResource> properties = service.findByCoordinate(ax, ay, bx, by)
                                                   .stream().map(PropertyResource::new)
                                                   .collect(Collectors.toList());
        return ResponseEntity.ok(new PropertiesSearchResult(properties));
    }

    @GetMapping("/advanced")
    public HttpEntity<PropertiesSearchResult> advancedSearch(@RequestParam final Long maxPrice, @RequestParam final Long minPrice,
                                                             @RequestParam final Integer beds, @RequestParam final Integer baths,
                                                             @RequestParam final String province){
        List<PropertyResource> properties = service.advancedSearch(maxPrice, minPrice, beds, baths, province)
                                                   .stream().map(PropertyResource::new)
                                                   .collect(Collectors.toList());
        return ResponseEntity.ok(new PropertiesSearchResult(properties));
    }

}
