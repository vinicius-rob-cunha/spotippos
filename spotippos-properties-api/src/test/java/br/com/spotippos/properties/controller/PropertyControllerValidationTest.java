package br.com.spotippos.properties.controller;

import br.com.spotippos.properties.builder.PropertyBuilder;
import br.com.spotippos.properties.model.Property;
import br.com.spotippos.properties.repository.PropertyRepository;
import br.com.spotippos.properties.service.PropertyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureJsonTesters
public class PropertyControllerValidationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Property> json;

    @MockBean
    private PropertyService service;
    @MockBean
    private PropertyRepository repository;

    @Test
    public void deveCadastrarComSucesso() throws Exception {
        Property property = new PropertyBuilder().build();

        given(this.service.save(any())).willReturn(property);

        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        ).andExpect(status().isCreated());
    }

    @Test
    public void naoDevePermitirCadastrarSemOsCamposObrigatorios() throws Exception {
        mockMvc.perform(
                post("/properties").contentType(contentType).content("{}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    public void naoDevePermitirCadastrarComMenosDeUmQuarto() throws Exception {
        Property property = new PropertyBuilder().beds(0).build();
        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logref", is("beds")));
    }

    @Test
    public void naoDevePermitirCadastrarComMaisDe5Quartos() throws Exception {
        Property property = new PropertyBuilder().beds(6).build();
        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logref", is("beds")));
    }

    @Test
    public void naoDevePermitirCadastrarComMenosDeUmBanheiro() throws Exception {
        Property property = new PropertyBuilder().baths(0).build();
        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logref", is("baths")));
    }

    @Test
    public void naoDevePermitirCadastrarComMaisDe4Banheiros() throws Exception {
        Property property = new PropertyBuilder().baths(5).build();
        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logref", is("baths")));
    }

    @Test
    public void naoDevePermitirCadastrarComMenosDe20MetrosQuadrados() throws Exception {
        Property property = new PropertyBuilder().squareMeters(0).build();
        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logref", is("squareMeters")));
    }

    @Test
    public void naoDevePermitirCadastrarComMaisDe240MetrosQuadrados() throws Exception {
        Property property = new PropertyBuilder().squareMeters(250).build();
        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logref", is("squareMeters")));
    }

    @Test
    public void naoDevePermitirCadastrarForaDoPlanetaMin() throws Exception {
        Property property = new PropertyBuilder().x(-1).y(-1).build();
        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].logref", isIn(new String[]{"y", "x"})))
                .andExpect(jsonPath("$[1].logref", isIn(new String[]{"y", "x"})));
    }

    @Test
    public void naoDevePermitirCadastrarForaDoPlanetaMax() throws Exception {
        Property property = new PropertyBuilder().x(1401).y(1001).build();
        mockMvc.perform(
                post("/properties").contentType(contentType).content(json.write(property).getJson())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].logref", isIn(new String[]{"y", "x"})))
                .andExpect(jsonPath("$[1].logref", isIn(new String[]{"y", "x"})));
    }

}