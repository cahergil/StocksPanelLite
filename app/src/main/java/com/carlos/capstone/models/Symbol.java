package com.carlos.capstone.models;

/**
 * Created by Carlos on 19/02/2016.
 */
public class Symbol {

    String symbol;
    String name;
    String lastSale;
    String marketCap;
    String ipoYear;
    String sector;
    String industry;
    String summaryQuote;

    public Symbol(String symbol, String name, String lastSale, String marketCap, String ipoYear, String sector, String industry, String summaryQuote) {
        this.symbol = symbol;
        this.name = name;
        this.lastSale = lastSale;
        this.marketCap = marketCap;
        this.ipoYear = ipoYear;
        this.sector = sector;
        this.industry = industry;
        this.summaryQuote = summaryQuote;
    }
    public Symbol(){

    }
    public String getLastSale() {
        return lastSale;
    }

    public void setLastSale(String lastSale) {
        this.lastSale = lastSale;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getIpoYear() {
        return ipoYear;
    }

    public void setIpoYear(String ipoYear) {
        this.ipoYear = ipoYear;
    }

    public String getSummaryQuote() {
        return summaryQuote;
    }

    public void setSummaryQuote(String summaryQuote) {
        this.summaryQuote = summaryQuote;
    }




    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
