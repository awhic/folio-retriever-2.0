package ind.awhic.ls.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.OffsetDateTime;

public class Data {
    private String ticker;
    private String name;
    private String exchange_short;
    @JsonIgnore
    private String exchange_Long;
    private String micCode;
    private String currency;
    private Double price;
    private double dayHigh;
    private double dayLow;
    private double dayOpen;
    private double the52_WeekHigh;
    private double the52_WeekLow;
    private long marketCap;
    private double previousClosePrice;
    private OffsetDateTime previousClosePriceTime;
    private double dayChange;
    private long volume;
    private boolean isExtendedHoursPrice;
    private OffsetDateTime lastTradeTime;

    public String getTicker() { return ticker; }
    public void setTicker(String value) { this.ticker = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getExchange_short() { return exchange_short; }
    public void setExchange_short(String value) { this.exchange_short = value; }

    public String getExchange_Long() { return exchange_Long; }
    public void setExchange_Long(String value) { this.exchange_Long = value; }

    public String getMicCode() { return micCode; }
    public void setMicCode(String value) { this.micCode = value; }

    public String getCurrency() { return currency; }
    public void setCurrency(String value) { this.currency = value; }

    public Double getPrice() { return price; }
    public void setPrice(Double value) { this.price = value; }

    public double getDayHigh() { return dayHigh; }
    public void setDayHigh(double value) { this.dayHigh = value; }

    public double getDayLow() { return dayLow; }
    public void setDayLow(double value) { this.dayLow = value; }

    public double getDayOpen() { return dayOpen; }
    public void setDayOpen(double value) { this.dayOpen = value; }

    public double getThe52WeekHigh() { return the52_WeekHigh; }
    public void setThe52WeekHigh(double value) { this.the52_WeekHigh = value; }

    public double getThe52WeekLow() { return the52_WeekLow; }
    public void setThe52WeekLow(double value) { this.the52_WeekLow = value; }

    public long getMarketCap() { return marketCap; }
    public void setMarketCap(long value) { this.marketCap = value; }

    public double getPreviousClosePrice() { return previousClosePrice; }
    public void setPreviousClosePrice(double value) { this.previousClosePrice = value; }

    public OffsetDateTime getPreviousClosePriceTime() { return previousClosePriceTime; }
    public void setPreviousClosePriceTime(OffsetDateTime value) { this.previousClosePriceTime = value; }

    public double getDayChange() { return dayChange; }
    public void setDayChange(double value) { this.dayChange = value; }

    public long getVolume() { return volume; }
    public void setVolume(long value) { this.volume = value; }

    public boolean getIsExtendedHoursPrice() { return isExtendedHoursPrice; }
    public void setIsExtendedHoursPrice(boolean value) { this.isExtendedHoursPrice = value; }

    public OffsetDateTime getLastTradeTime() { return lastTradeTime; }
    public void setLastTradeTime(OffsetDateTime value) { this.lastTradeTime = value; }
}
