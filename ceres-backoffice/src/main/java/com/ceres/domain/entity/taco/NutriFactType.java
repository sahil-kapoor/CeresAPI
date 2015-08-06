package com.ceres.domain.entity.taco;

/**
 * Created by leonardo on 07/03/15.
 */
public enum NutriFactType {

    HUMIDITY("Umidade"),
    ENERGY("Energia"),
    PROTEIN("Proteína"),
    LIPIDS("Lipídeos"),
    CHOLESTEROL("Colesterol"),
    CARBOHYDRATE("Carboidrato"),
    FIBER("Fibra Alimentar"),
    ASH("Cinzas"),
    CALCIUM("Cálcio"),
    MAGNESIUM("Magnésio"),
    MANGANESE("Manganês"),
    MATCH("Fósforo"),
    IRON("Ferro"),
    SODIUM("Sódio"),
    POTASSIUM("Potássio"),
    COPPER("Cobre"),
    ZINC("Zinco"),
    RETINOL("Retinol"),
    RE("RE"),
    SAR("RAE"),
    THIAMINE("Tiamina"),
    RIBOFLAVIN("Riboflavina"),
    PYRIDOXINE("Piridoxina"),
    NIACIN("Niacina"),
    VITAMIN("Vitamina C");

    private String nutrifact;

    NutriFactType(String nutrifact) {
        this.nutrifact = nutrifact;
    }

    public String value() {
        return this.nutrifact;
    }


}
