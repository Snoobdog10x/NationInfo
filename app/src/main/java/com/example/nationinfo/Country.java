package com.example.nationinfo;

import java.io.Serializable;

public class Country implements Serializable {
    private String countryName;
    private String flag;
    private int population;
    private double areaInSqKm;
    private String countryCode;
    public Country(String countryName, int population, double areaInSqKm, String countryCode) {
        this.countryName = countryName;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.countryCode = countryCode;
        this.flag = "https://img.geonames.org/flags/x/" + countryCode.toLowerCase() + ".gif";;
    }

    public String getName() {
        return countryName;
    }

    public double getAreaInSqKm() {
        return areaInSqKm;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getFlag() {
        return flag;
    }

    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", flag='" + flag + '\'' +
                ", population=" + population +
                ", areaInSqKm=" + areaInSqKm +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
