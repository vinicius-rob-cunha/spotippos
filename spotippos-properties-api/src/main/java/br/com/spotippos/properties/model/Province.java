package br.com.spotippos.properties.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum que representa as provincias e seus limites.<br>
 * <br>
 * Esse enum foi baseado no arquivo spotippos-provinces.json que é a copia do arquivo
 * do desafio https://raw.githubusercontent.com/VivaReal/code-challenge/master/provinces.json
 *
 * @author Vinicius Cunha
 */
public enum Province {

    Gode(new int[]{0,1000}, new int[]{600,500}),
    Ruja(new int[]{400,1000}, new int[]{1100,500}),
    Jaby(new int[]{1100,1000}, new int[]{1400,500}),
    Scavy(new int[]{0,500}, new int[]{600,0}),
    Groola(new int[]{600,500}, new int[]{800,0}),
    Nova(new int[]{800,500}, new int[]{1400,0})
    ;

    private int[] upperLeft;
    private int[] bottomRight;

    Province(int[] upperLeft, int[] bottomRight) {
        this.upperLeft = upperLeft;
        this.bottomRight = bottomRight;
    }

    /**
     * Encontra as provincias que atendem as coordenas passadas como parâmetro
     * @param x
     * @param y
     * @return
     */
    public static List<Province> findProvinces(int x, int y){
        List<Province> provinces = new ArrayList<>();

        for(Province province : values()) {
            if(x > province.upperLeft[0] && x < province.bottomRight[0]
               && y < province.upperLeft[1] && y > province.bottomRight[1]) {
                provinces.add(province);
            }
        }

        return provinces;
    }

}
