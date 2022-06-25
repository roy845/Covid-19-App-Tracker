package com.example.finalproject;

public class Continent {

    String Cases,TodayCases,Deaths,todayDeaths,Recovered,TodayRecovered,Active,Critical,CasesPerOneMillion,DeathsPerOneMillion,
            Tests,TestsPerOneMillion,Population,continent,ActivePerOneMillion
            ,RecoveredPerOneMillion,CriticalPerOneMillion,icon;

    public Continent(String cases, String todayCases, String deaths, String todayDeaths, String recovered, String todayRecovered,
                     String active, String critical, String casesPerOneMillion, String deathsPerOneMillion, String tests,
                     String testsPerOneMillion, String population, String continent, String activePerOneMillion,
                     String recoveredPerOneMillion, String criticalPerOneMillion, String icon) {
        Cases = cases;
        TodayCases = todayCases;
        Deaths = deaths;
        this.todayDeaths = todayDeaths;
        Recovered = recovered;
        TodayRecovered = todayRecovered;
        Active = active;
        Critical = critical;
        CasesPerOneMillion = casesPerOneMillion;
        DeathsPerOneMillion = deathsPerOneMillion;
        Tests = tests;
        TestsPerOneMillion = testsPerOneMillion;
        Population = population;
        this.continent = continent;
        ActivePerOneMillion = activePerOneMillion;
        RecoveredPerOneMillion = recoveredPerOneMillion;
        CriticalPerOneMillion = criticalPerOneMillion;
        this.icon = icon;
    }

    public String getCases() {
        return Cases;
    }

    public void setCases(String cases) {
        Cases = cases;
    }

    public String getTodayCases() {
        return TodayCases;
    }

    public void setTodayCases(String todayCases) {
        TodayCases = todayCases;
    }

    public String getDeaths() {
        return Deaths;
    }

    public void setDeaths(String deaths) {
        Deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return Recovered;
    }

    public void setRecovered(String recovered) {
        Recovered = recovered;
    }

    public String getTodayRecovered() {
        return TodayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        TodayRecovered = todayRecovered;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getCritical() {
        return Critical;
    }

    public void setCritical(String critical) {
        Critical = critical;
    }

    public String getCasesPerOneMillion() {
        return CasesPerOneMillion;
    }

    public void setCasesPerOneMillion(String casesPerOneMillion) {
        CasesPerOneMillion = casesPerOneMillion;
    }

    public String getDeathsPerOneMillion() {
        return DeathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(String deathsPerOneMillion) {
        DeathsPerOneMillion = deathsPerOneMillion;
    }

    public String getTests() {
        return Tests;
    }

    public void setTests(String tests) {
        Tests = tests;
    }

    public String getTestsPerOneMillion() {
        return TestsPerOneMillion;
    }

    public void setTestsPerOneMillion(String testsPerOneMillion) {
        TestsPerOneMillion = testsPerOneMillion;
    }

    public String getPopulation() {
        return Population;
    }

    public void setPopulation(String population) {
        Population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getActivePerOneMillion() {
        return ActivePerOneMillion;
    }

    public void setActivePerOneMillion(String activePerOneMillion) {
        ActivePerOneMillion = activePerOneMillion;
    }

    public String getRecoveredPerOneMillion() {
        return RecoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(String recoveredPerOneMillion) {
        RecoveredPerOneMillion = recoveredPerOneMillion;
    }

    public String getCriticalPerOneMillion() {
        return CriticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(String criticalPerOneMillion) {
        CriticalPerOneMillion = criticalPerOneMillion;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
