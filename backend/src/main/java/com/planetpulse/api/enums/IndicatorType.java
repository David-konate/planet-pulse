package com.planetpulse.api.enums;

public enum IndicatorType {

    CO2_EMISSIONS("co2Emissions", "CO₂ emissions", "Mt", null),
    RENEWABLE_ENERGY("renewableEnergy", "Renewable energy share", "%", 100),
    FOREST_AREA("forestArea", "Forest area", "%", 100),
    PM25_EXPOSURE("pm25Exposure", "PM2.5 exposure", "µg/m³", null);

    private final String field; // name used in the JSON and in the entity
    private final String label;
    private final String unit;
    private final Integer max;

    IndicatorType(String field, String label, String unit, Integer max) {
        this.field = field;
        this.label = label;
        this.unit = unit;
        this.max = max;
    }

    public String getField() { return field; }
    public String getLabel() { return label; }
    public String getUnit() { return unit; }
    public Integer getMax() { return max; }
}
