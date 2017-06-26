package br.com.spotippos.properties;

import br.com.spotippos.properties.model.PropertiesDataLoader;
import br.com.spotippos.properties.model.PropertiesSearchResult;
import br.com.spotippos.properties.repository.PropertyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static br.com.spotippos.properties.model.Province.findProvinces;

/**
 * Classe que inicia a aplicação e os dados no banco
 *
 * @author Vinicius Cunha
 */
@SpringBootApplication
public class SpotipposPropertiesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotipposPropertiesApiApplication.class, args);
	}

	/**
	 * Inicializa o banco de dados carregando os dados do arquivo spotippos-properties.json.<br>
     * Esse arquivo foi baseado no arquivo do desavio https://github.com/VivaReal/code-challenge/blob/master/properties.json?raw=true
	 */
	@Bean
	public CommandLineRunner init(PropertyRepository repository) throws IOException {
		ClassPathResource json = new ClassPathResource("spotippos-properties.json");

		ObjectMapper mapper = new ObjectMapper();
		PropertiesDataLoader spotipposProperties = mapper.readValue(json.getInputStream(), PropertiesDataLoader.class);

		return (evt) -> spotipposProperties.getProperties()
				                           .stream()
										   .map(p -> {
										   	    p.setIdProperty(null);
										   	    p.setProvinces(findProvinces(p.getX(), p.getY()));
										   	  	return p;
										   })
				                           .forEach(repository::save);
	}
}
