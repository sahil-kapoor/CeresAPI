package com.ceres.business;

import org.junit.Test;

/**
 * Created by renato on 04/04/2015.
 */

public class NonScriptFormuleTest {

    private static final double PESO = 75.1;
    private static final double ALTURA = 180.0;
    private static final double IDADE = 35;
    private static final double TAXA_DE_ATIVIDADE = 1.650;

    private static final double CINTURA = 90.0;
    private static final double QUADRIL = 90.0;
    private static final double PESCOCO = 50.0;

    private static final double TRICEPS = 18.2;
    private static final double SUPRA_ILIACA = 17.6;
    private static final double ABDOMEN = 22.8;
    private static final double COXA = 24.1;
    private static final double SUBESCAPULAR = 22.8;

    private static final double GORDURA = 1.03;

    @Test
    public void imc() {
        System.out.println("IMC");
        System.out.println(PESO / Math.pow((ALTURA * 100), 2));
        System.out.println("");
    }

    @Test
    public void basalMetabolism() {
        System.out.println("BASAL");
        System.out.println("MASC = " + (TAXA_DE_ATIVIDADE * (66 + ((13.7 * PESO) + (5 * ALTURA) - (6.8 * IDADE)))));
        System.out.println("FEM = " + (TAXA_DE_ATIVIDADE * (655 + ((9.6 * PESO) + (1.8 * ALTURA) - (4.7 * IDADE)))));
        System.out.println("");
    }

    @Test
    public void fatPercentageAdpometer() {
        System.out.println("FAT PERCENT ADPOMETER");
        System.out.println("MASC = " + (1.1714 - 0.0671 * Math.log10((TRICEPS + SUPRA_ILIACA + ABDOMEN))));
        System.out.println("FEM = " + (1.1665 - 0.0706 * Math.log10(SUBESCAPULAR + SUPRA_ILIACA + COXA)));
        System.out.println("");
    }

    @Test
    public void fatPercentageNonAdpometer() {
        System.out.println("FAT PERCENT NON ADPOMETER");
        System.out.println("MASC = " + (495 / (1.29579 - 0.35004 * (Math.log10(CINTURA + QUADRIL - PESCOCO)) + 0.22100 * (Math.log10(ALTURA))) - 450));
        System.out.println("FEM = " + (495 / (1.0324 - 0.19077 * (Math.log10(CINTURA - PESCOCO)) + 0.15456 * Math.log10(ALTURA)) - 450));
        System.out.println("");
    }

    @Test
    public void absoluteFat() {
        System.out.println("ABSOLUTE FAT");
        System.out.println(PESO * (GORDURA / 100D));
        System.out.println("");
    }

    public void massFatFree() {
        //System.out.println(PESO - GORDURA_ABSOLUTA);

    }
}
