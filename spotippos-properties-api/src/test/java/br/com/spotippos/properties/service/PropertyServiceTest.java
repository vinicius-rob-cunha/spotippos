package br.com.spotippos.properties.service;

import br.com.spotippos.properties.builder.PropertyBuilder;
import br.com.spotippos.properties.model.Property;
import br.com.spotippos.properties.repository.PropertyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PropertyServiceTest {

    @Autowired
    private PropertyService service;

    @Autowired
    private PropertyRepository repository;

    @Before
    public void prepareData(){
        repository.deleteAll();
        repository.save(new PropertyBuilder().x(1257).y(928).beds(2).build());
        repository.save(new PropertyBuilder().x(679).y(680).baths(1).build());
        repository.save(new PropertyBuilder().x(1051).y(441).price(230020L).build());
        repository.save(new PropertyBuilder().x(252).y(868).beds(2).build());
        repository.save(new PropertyBuilder().x(34).y(660).price(500100L).build());
    }

    @Test
    public void deveEncontrarOsImoveisDeAcordoComACoordenada() {
        List<Property> properties = service.findByCoordinate(200, 950, 800, 600);
        assertEquals(2, properties.size());
    }

    @Test
    public void deveEncontrarImoveisPorProvincia() {
        List<Property> properties = service.advancedSearch(null,null,null,null, "Gode");
        assertEquals(2, properties.size());
    }

    @Test
    public void deveEncontrarImoveisPorQuantidadeDeBanheiros() {
        List<Property> properties = service.advancedSearch(null,null,null,1, "");
        assertEquals(1, properties.size());
    }

    @Test
    public void deveEncontrarImoveisPorQuantidadeDeCamas() {
        List<Property> properties = service.advancedSearch(null,null,2,null, null);
        assertEquals(2, properties.size());
    }

    @Test
    public void deveEncontrarImoveisPorQuantidadeDeCamasEProvincia() {
        List<Property> properties = service.advancedSearch(null,null,2,null, "Gode");
        assertEquals(1, properties.size());
    }

    @Test
    public void deveEncontrarImoveisPeloPreçoMinimo() {
        List<Property> properties = service.advancedSearch(null,230020L,null,null, "");
        assertEquals(2, properties.size());
    }

    @Test
    public void deveEncontrarImoveisPeloPreçoMaximo() {
        List<Property> properties = service.advancedSearch(230000L,null,null,null, "");
        assertEquals(3, properties.size());
    }

    @Test
    public void deveEncontrarImoveisPorRangeDePreço() {
        List<Property> properties = service.advancedSearch(500100L,230020L,null,null, "");
        assertEquals(2, properties.size());
    }

    @TestConfiguration
    static class PropertyServiceTestConfiguration {
        @Bean
        public PropertyService propertyService() {
            return new PropertyService();
        }
    }

}