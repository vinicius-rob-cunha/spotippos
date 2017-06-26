package br.com.spotippos.properties.model;

import org.junit.Test;

import java.util.List;

import static br.com.spotippos.properties.model.Province.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProvinceTest {

    @Test
    public void devePertencerAGode(){
        List<Province> provinces = findProvinces(100, 800);
        assertEquals(1, provinces.size());
        assertEquals(Gode, provinces.get(0));
    }

    @Test
    public void devePertencerARuja(){
        List<Province> provinces = findProvinces(900, 800);
        assertEquals(1, provinces.size());
        assertEquals(Ruja, provinces.get(0));
    }

    @Test
    public void devePertencerAGodeERuja(){
        List<Province> provinces = findProvinces(580, 800);
        assertEquals(2, provinces.size());
        assertTrue(provinces.contains(Gode));
        assertTrue(provinces.contains(Ruja));
    }

    @Test
    public void devePertencerAJaby(){
        List<Province> provinces = findProvinces(1200, 800);
        assertEquals(1, provinces.size());
        assertEquals(Jaby, provinces.get(0));
    }

    @Test
    public void devePertencerAScavy(){
        List<Province> provinces = findProvinces(100, 100);
        assertEquals(1, provinces.size());
        assertEquals(Scavy, provinces.get(0));
    }

    @Test
    public void devePertencerAGroola(){
        List<Province> provinces = findProvinces(700, 100);
        assertEquals(1, provinces.size());
        assertEquals(Groola, provinces.get(0));
    }

    @Test
    public void devePertencerANova(){
        List<Province> provinces = findProvinces(1000, 100);
        assertEquals(1, provinces.size());
        assertEquals(Nova, provinces.get(0));
    }

}