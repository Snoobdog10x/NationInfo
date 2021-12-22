package com.example.nationinfo;

public class CountryCorona {
    private String name_of_country;
    private String flag_of_country;
    private int number_of_death_of_country;
    private int number_of_recovered_of_country;
    private int number_of_infected_of_country;

    public CountryCorona(String name_of_country, String flag_of_country, int number_of_death_of_country, int number_of_recovered_of_country, int number_of_infected_of_country) {
        this.name_of_country = name_of_country;
        this.flag_of_country = flag_of_country;
        this.number_of_death_of_country = number_of_death_of_country;
        this.number_of_recovered_of_country = number_of_recovered_of_country;
        this.number_of_infected_of_country = number_of_infected_of_country;
    }

    public String getName_of_country() {
        return name_of_country;
    }

    public void setName_of_country(String name_of_country) {
        this.name_of_country = name_of_country;
    }

    public String getFlag_of_country() {
        return flag_of_country;
    }

    public void setFlag_of_country(String flag_of_country) {
        this.flag_of_country = flag_of_country;
    }

    public int getNumber_of_death_of_country() {
        return number_of_death_of_country;
    }

    public void setNumber_of_death_of_country(int number_of_death_of_country) {
        this.number_of_death_of_country = number_of_death_of_country;
    }

    public int getNumber_of_recovered_of_country() {
        return number_of_recovered_of_country;
    }

    public void setNumber_of_recovered_of_country(int number_of_recovered_of_country) {
        this.number_of_recovered_of_country = number_of_recovered_of_country;
    }

    public int getNumber_of_infected_of_country() {
        return number_of_infected_of_country;
    }

    public void setNumber_of_infected_of_country(int number_of_infected_of_country) {
        this.number_of_infected_of_country = number_of_infected_of_country;
    }

    @Override
    public String toString() {
        return "CountryCorona{" +
                "name_of_country='" + name_of_country + '\'' +
                ", flag_of_country='" + flag_of_country + '\'' +
                ", number_of_death_of_country=" + number_of_death_of_country +
                ", number_of_recovered_of_country=" + number_of_recovered_of_country +
                ", number_of_infected_of_country=" + number_of_infected_of_country +
                '}';
    }
}
