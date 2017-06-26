package br.com.spotippos.properties.repository;

import br.com.spotippos.properties.builder.PropertyBuilder;
import br.com.spotippos.properties.model.Property;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PropertyRepositoryTest {

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
    public void naoDeveEncontrarNadaComAsCoordenadaSemOrdem() {
        List<Property> properties = repository.findByXBetweenAndYBetween(200, 950, 800, 600);
        assertTrue(properties.isEmpty());
    }

    @Test
    public void deveEncontrarImoveisComAsCoordenadaOrdenadas() {
        List<Property> properties = repository.findByXBetweenAndYBetween(200, 800, 600, 950);
        assertEquals(2, properties.size());
    }

}