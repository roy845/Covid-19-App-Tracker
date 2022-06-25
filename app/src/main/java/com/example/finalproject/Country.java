package com.example.finalproject;

public class Country {

    private String flag,country,cases,todayCases,deaths,todayDeaths,recovered,active,critical,casesPerOneMillion
            ,todayRecovered,deathsPerOneMillion,tests,testsPerOneMillion,population,continent,oneCasePerPeople,oneDeathPerPeople
              ,oneTestPerPeople,activePerOneMillion,recoveredPerOneMillion,criticalPerOneMillion;

    public Country(String flag, String country, String cases, String todayCases, String deaths, String todayDeaths, String recovered, String active,
                   String critical,String casesPerOneMillion,String todayRecovered,String deathsPerOneMillion, String tests,String testsPerOneMillion,
                   String population,String continent,String oneCasePerPeople,String oneDeathPerPeople,String oneTestPerPeople
    ,String activePerOneMillion,String recoveredPerOneMillion,String criticalPerOneMillion) {
        this.flag = flag;
        this.country = country;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.active = active;
        this.critical = critical;

        this.todayRecovered = todayRecovered;
        this.deathsPerOneMillion = deathsPerOneMillion;
        this.tests = tests;
        this.population = population;
        this.continent = continent;
        this.oneCasePerPeople = oneCasePerPeople;
        this.oneDeathPerPeople = oneDeathPerPeople;
        this.oneTestPerPeople = oneTestPerPeople;
        this.activePerOneMillion = activePerOneMillion;
        this.recoveredPerOneMillion = recoveredPerOneMillion;
        this.criticalPerOneMillion = criticalPerOneMillion;
        this.testsPerOneMillion = testsPerOneMillion;
        this.casesPerOneMillion = casesPerOneMillion;

    }


    public String getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(String casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(String oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public String getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(String oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public String getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public void setOneTestPerPeople(String oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public String getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(String activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public String getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(String recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public String getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(String criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public String getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(String testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public String getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(String deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

}
