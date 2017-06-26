package br.com.spotippos.properties.service;

import br.com.spotippos.properties.model.Property;
import br.com.spotippos.properties.model.Province;
import br.com.spotippos.properties.model.QProperty;
import br.com.spotippos.properties.repository.PropertyRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsavel por processar as informações e fazer as consulta das propriedades
 *
 * @author Vinicius
 */
@Service
public class PropertyService {

    @Autowired
    private PropertyRepository repository;

    /**
     * @return todos os imoveis dentro das coordenadas passadas como parâmetro
     */
    public List<Property> findByCoordinate(Integer ax, Integer ay, Integer bx, Integer by){
        int x1 = ax > bx ? bx : ax;
        int x2 = ax < bx ? bx : ax;
        int y1 = ay > by ? by : ay;
        int y2 = ay < by ? by : ay;

        return repository.findByXBetweenAndYBetween(x1, x2, y1, y2);
    }

    /**
     * @return todos os imoveis que atenda aos critérios de busca
     */
    public List<Property> advancedSearch(Long maxPrice, Long minPrice, Integer beds, Integer baths,
                                         String province) {
        Predicate query = createAdvancedSearchQuery(maxPrice, minPrice, beds, baths, province);

        List<Property> properties = new ArrayList<>();

        Iterable<Property> it = repository.findAll(query);
        it.forEach(properties::add);

        return properties;
    }

    /**
     * Monta a query baseado nos parâmetros recebidos
     */
    private Predicate createAdvancedSearchQuery(Long maxPrice, Long minPrice, Integer beds, Integer baths, String province) {
        QProperty property = QProperty.property;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(maxPrice != null && minPrice != null) {
            booleanBuilder.and(property.price.between(minPrice, maxPrice));
        } else if (maxPrice != null) {
            booleanBuilder.and(property.price.loe(maxPrice));
        } else if (minPrice != null) {
            booleanBuilder.and(property.price.goe(minPrice));
        }

        if(beds != null){
            booleanBuilder.and(property.beds.eq(beds));
        }

        if(baths != null){
            booleanBuilder.and(property.baths.eq(baths));
        }

        if(!StringUtils.isEmpty(province)) {
            booleanBuilder.and(property.provinces.contains(Province.valueOf(province)));
        }

        return booleanBuilder.getValue();
    }

    /**
     * Salva um imovel
     */
    public Property save(Property property) {
        return repository.save(property);
    }

    /**
     * @return um imovel pelo ID
     */
    public Property findOne(Long id) {
        return repository.findOne(id);
    }
}
