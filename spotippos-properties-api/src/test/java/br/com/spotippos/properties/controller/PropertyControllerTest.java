package br.com.spotippos.properties.controller;

import br.com.spotippos.properties.builder.PropertyBuilder;
import br.com.spotippos.properties.model.Property;
import br.com.spotippos.properties.repository.PropertyRepository;
import br.com.spotippos.properties.service.PropertyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService service;
    @MockBean
    private PropertyRepository repository;

    @Test
    public void retornar404CasoNaoSejaEncontradaUmaPropriedade() throws Exception {
        mockMvc.perform(
                get("/properties/1")
        )
        .andExpect(status().isNotFound());
    }

    @Test
    public void deveRetornarOJSONComOImovelESeuLink() throws Exception {
        Property property = new PropertyBuilder().build();
        given(this.service.findOne(any())).willReturn(property);

        mockMvc.perform(
                get("/properties/1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.property.title", is(property.getTitle())))
                .andExpect(jsonPath("$.property.price", is(property.getPrice().intValue())))
                .andExpect(jsonPath("$.property.description", is(property.getDescription())))
                .andExpect(jsonPath("$.property.x", is(property.getX())))
                .andExpect(jsonPath("$.property.y", is(property.getY())))
                .andExpect(jsonPath("$.property.beds", is(property.getBeds())))
                .andExpect(jsonPath("$.property.baths", is(property.getBaths())))
                .andExpect(jsonPath("$.property.squareMeters", is(property.getSquareMeters())))
                .andExpect(jsonPath("$.property.provinces", hasSize(property.getProvinces().size())))
                .andExpect(jsonPath("$._links.self", notNullValue()));
    }

    @Test
    public void deveRetornarAListaDePropriedadesPorCoordenadasComSeusLinks() throws Exception {
        given(this.service.findByCoordinate(anyInt(),anyInt(),anyInt(),anyInt())).willReturn(
                Arrays.asList(new PropertyBuilder().build(), new PropertyBuilder().build(), new PropertyBuilder().build())
        );

        mockMvc.perform(
                get("/properties?ax=0&ay=0&bx=0&by=0")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.properties", hasSize(3)))
                .andExpect(jsonPath("$.foundProperties", is(3)))
                .andExpect(jsonPath("$.properties[0].links[0].rel", is("self")))
                .andExpect(jsonPath("$.properties[1].links[0].rel", is("self")))
                .andExpect(jsonPath("$.properties[2].links[0].rel", is("self")));
    }

    @Test
    public void deveRetornarAListaDePropriedadesPorBuscaAvancadaComSeusLinks() throws Exception {
        given(this.service.advancedSearch(anyLong(),anyLong(),anyInt(),anyInt(), anyString())).willReturn(
                Arrays.asList(new PropertyBuilder().build(), new PropertyBuilder().build(), new PropertyBuilder().build())
        );

        mockMvc.perform(
                get("/properties/advanced?maxPrice=&minPrice=&beds=&baths=&province=")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.properties", hasSize(3)))
                .andExpect(jsonPath("$.foundProperties", is(3)))
                .andExpect(jsonPath("$.properties[0].links[0].rel", is("self")))
                .andExpect(jsonPath("$.properties[1].links[0].rel", is("self")))
                .andExpect(jsonPath("$.properties[2].links[0].rel", is("self")));
    }

}