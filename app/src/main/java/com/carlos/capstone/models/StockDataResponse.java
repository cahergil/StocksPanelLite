package com.carlos.capstone.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 26/12/2015.
 */
public class StockDataResponse {


    /**
     * count : 1
     * created : 2015-12-26T10:35:27Z
     * lang : es-ES
     * results : {"quote":{"symbol":"GOOG","Ask":"755.22","AverageDailyVolume":"2034450","Bid":"742.00","AskRealtime":null,"BidRealtime":null,"BookValue":"169.03","Change_PercentChange":"-1.91 - -0.25%","Change":"-1.91","Commission":null,"Currency":"USD","ChangeRealtime":null,"AfterHoursChangeRealtime":null,"DividendShare":null,"LastTradeDate":"12/24/2015","TradeDate":null,"EarningsShare":"23.72","ErrorIndicationreturnedforsymbolchangedinvalid":null,"EPSEstimateCurrentYear":"28.99","EPSEstimateNextYear":"34.12","EPSEstimateNextQuarter":"7.80","DaysLow":"746.62","DaysHigh":"751.35","YearLow":"486.23","YearHigh":"775.96","HoldingsGainPercent":null,"AnnualizedGain":null,"HoldingsGain":null,"HoldingsGainPercentRealtime":null,"HoldingsGainRealtime":null,"MoreInfo":null,"OrderBookRealtime":null,"MarketCapitalization":"514.69B","MarketCapRealtime":null,"EBITDA":"23.30B","ChangeFromYearLow":"262.18","PercentChangeFromYearLow":"+53.92%","LastTradeRealtimeWithTime":null,"ChangePercentRealtime":null,"ChangeFromYearHigh":"-27.55","PercebtChangeFromYearHigh":"-3.55%","LastTradeWithTime":"1:00pm - <b>748.40<\/b>","LastTradePriceOnly":"748.40","HighLimit":null,"LowLimit":null,"DaysRange":"746.62 - 751.35","DaysRangeRealtime":null,"FiftydayMovingAverage":"745.29","TwoHundreddayMovingAverage":"647.01","ChangeFromTwoHundreddayMovingAverage":"101.39","PercentChangeFromTwoHundreddayMovingAverage":"+15.67%","ChangeFromFiftydayMovingAverage":"3.11","PercentChangeFromFiftydayMovingAverage":"+0.42%","Name":"Alphabet Inc.","Notes":null,"Open":"749.55","PreviousClose":"750.31","PricePaid":null,"ChangeinPercent":"-0.25%","PriceSales":"7.19","PriceBook":"4.44","ExDividendDate":null,"PERatio":"31.55","DividendPayDate":null,"PERatioRealtime":null,"PEGRatio":"1.50","PriceEPSEstimateCurrentYear":"25.82","PriceEPSEstimateNextYear":"21.93","Symbol":"GOOG","SharesOwned":null,"ShortRatio":"2.32","LastTradeTime":"1:00pm","TickerTrend":null,"OneyrTargetPrice":"853.67","Volume":"527223","HoldingsValue":null,"HoldingsValueRealtime":null,"YearRange":"486.23 - 775.96","DaysValueChange":null,"DaysValueChangeRealtime":null,"StockExchange":"NMS","DividendYield":null,"PercentChange":"-0.25%"}}
     */

    private QueryEntity query;

    public void setQuery(QueryEntity query) {
        this.query = query;
    }

    public QueryEntity getQuery() {
        return query;
    }

    public static class QueryEntity {
        private int count;
        private String created;
        private String lang;
        /**
         * quote : {"symbol":"GOOG","Ask":"755.22","AverageDailyVolume":"2034450","Bid":"742.00","AskRealtime":null,"BidRealtime":null,"BookValue":"169.03","Change_PercentChange":"-1.91 - -0.25%","Change":"-1.91","Commission":null,"Currency":"USD","ChangeRealtime":null,"AfterHoursChangeRealtime":null,"DividendShare":null,"LastTradeDate":"12/24/2015","TradeDate":null,"EarningsShare":"23.72","ErrorIndicationreturnedforsymbolchangedinvalid":null,"EPSEstimateCurrentYear":"28.99","EPSEstimateNextYear":"34.12","EPSEstimateNextQuarter":"7.80","DaysLow":"746.62","DaysHigh":"751.35","YearLow":"486.23","YearHigh":"775.96","HoldingsGainPercent":null,"AnnualizedGain":null,"HoldingsGain":null,"HoldingsGainPercentRealtime":null,"HoldingsGainRealtime":null,"MoreInfo":null,"OrderBookRealtime":null,"MarketCapitalization":"514.69B","MarketCapRealtime":null,"EBITDA":"23.30B","ChangeFromYearLow":"262.18","PercentChangeFromYearLow":"+53.92%","LastTradeRealtimeWithTime":null,"ChangePercentRealtime":null,"ChangeFromYearHigh":"-27.55","PercebtChangeFromYearHigh":"-3.55%","LastTradeWithTime":"1:00pm - <b>748.40<\/b>","LastTradePriceOnly":"748.40","HighLimit":null,"LowLimit":null,"DaysRange":"746.62 - 751.35","DaysRangeRealtime":null,"FiftydayMovingAverage":"745.29","TwoHundreddayMovingAverage":"647.01","ChangeFromTwoHundreddayMovingAverage":"101.39","PercentChangeFromTwoHundreddayMovingAverage":"+15.67%","ChangeFromFiftydayMovingAverage":"3.11","PercentChangeFromFiftydayMovingAverage":"+0.42%","Name":"Alphabet Inc.","Notes":null,"Open":"749.55","PreviousClose":"750.31","PricePaid":null,"ChangeinPercent":"-0.25%","PriceSales":"7.19","PriceBook":"4.44","ExDividendDate":null,"PERatio":"31.55","DividendPayDate":null,"PERatioRealtime":null,"PEGRatio":"1.50","PriceEPSEstimateCurrentYear":"25.82","PriceEPSEstimateNextYear":"21.93","Symbol":"GOOG","SharesOwned":null,"ShortRatio":"2.32","LastTradeTime":"1:00pm","TickerTrend":null,"OneyrTargetPrice":"853.67","Volume":"527223","HoldingsValue":null,"HoldingsValueRealtime":null,"YearRange":"486.23 - 775.96","DaysValueChange":null,"DaysValueChangeRealtime":null,"StockExchange":"NMS","DividendYield":null,"PercentChange":"-0.25%"}
         */

        private ResultsEntity results;

        public void setCount(int count) {
            this.count = count;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public void setResults(ResultsEntity results) {
            this.results = results;
        }

        public int getCount() {
            return count;
        }

        public String getCreated() {
            return created;
        }

        public String getLang() {
            return lang;
        }

        public ResultsEntity getResults() {
            return results;
        }

        public static class ResultsEntity {
            /**
             * symbol : GOOG
             * Ask : 755.22
             * AverageDailyVolume : 2034450
             * Bid : 742.00
             * AskRealtime : null
             * BidRealtime : null
             * BookValue : 169.03
             * Change_PercentChange : -1.91 - -0.25%
             * Change : -1.91
             * Commission : null
             * Currency : USD
             * ChangeRealtime : null
             * AfterHoursChangeRealtime : null
             * DividendShare : null
             * LastTradeDate : 12/24/2015
             * TradeDate : null
             * EarningsShare : 23.72
             * ErrorIndicationreturnedforsymbolchangedinvalid : null
             * EPSEstimateCurrentYear : 28.99
             * EPSEstimateNextYear : 34.12
             * EPSEstimateNextQuarter : 7.80
             * DaysLow : 746.62
             * DaysHigh : 751.35
             * YearLow : 486.23
             * YearHigh : 775.96
             * HoldingsGainPercent : null
             * AnnualizedGain : null
             * HoldingsGain : null
             * HoldingsGainPercentRealtime : null
             * HoldingsGainRealtime : null
             * MoreInfo : null
             * OrderBookRealtime : null
             * MarketCapitalization : 514.69B
             * MarketCapRealtime : null
             * EBITDA : 23.30B
             * ChangeFromYearLow : 262.18
             * PercentChangeFromYearLow : +53.92%
             * LastTradeRealtimeWithTime : null
             * ChangePercentRealtime : null
             * ChangeFromYearHigh : -27.55
             * PercebtChangeFromYearHigh : -3.55%
             * LastTradeWithTime : 1:00pm - <b>748.40</b>
             * LastTradePriceOnly : 748.40
             * HighLimit : null
             * LowLimit : null
             * DaysRange : 746.62 - 751.35
             * DaysRangeRealtime : null
             * FiftydayMovingAverage : 745.29
             * TwoHundreddayMovingAverage : 647.01
             * ChangeFromTwoHundreddayMovingAverage : 101.39
             * PercentChangeFromTwoHundreddayMovingAverage : +15.67%
             * ChangeFromFiftydayMovingAverage : 3.11
             * PercentChangeFromFiftydayMovingAverage : +0.42%
             * Name : Alphabet Inc.
             * Notes : null
             * Open : 749.55
             * PreviousClose : 750.31
             * PricePaid : null
             * ChangeinPercent : -0.25%
             * PriceSales : 7.19
             * PriceBook : 4.44
             * ExDividendDate : null
             * PERatio : 31.55
             * DividendPayDate : null
             * PERatioRealtime : null
             * PEGRatio : 1.50
             * PriceEPSEstimateCurrentYear : 25.82
             * PriceEPSEstimateNextYear : 21.93
             * Symbol : GOOG
             * SharesOwned : null
             * ShortRatio : 2.32
             * LastTradeTime : 1:00pm
             * TickerTrend : null
             * OneyrTargetPrice : 853.67
             * Volume : 527223
             * HoldingsValue : null
             * HoldingsValueRealtime : null
             * YearRange : 486.23 - 775.96
             * DaysValueChange : null
             * DaysValueChangeRealtime : null
             * StockExchange : NMS
             * DividendYield : null
             * PercentChange : -0.25%
             */

            private QuoteEntity quote;

            public void setQuote(QuoteEntity quote) {
                this.quote = quote;
            }

            public QuoteEntity getQuote() {
                return quote;
            }

            public static class QuoteEntity implements Parcelable {
                private String symbol;
                private String Ask;
                private String AverageDailyVolume;
                private String Bid;
                private Object AskRealtime;
                private Object BidRealtime;
                private String BookValue;
                private String Change_PercentChange;
                private String Change;
                private Object Commission;
                private String Currency;
                private Object ChangeRealtime;
                private Object AfterHoursChangeRealtime;
                private Object DividendShare;
                private String LastTradeDate;
                private Object TradeDate;
                private String EarningsShare;
                private Object ErrorIndicationreturnedforsymbolchangedinvalid;
                private String EPSEstimateCurrentYear;
                private String EPSEstimateNextYear;
                private String EPSEstimateNextQuarter;
                private String DaysLow;
                private String DaysHigh;
                private String YearLow;
                private String YearHigh;
                private Object HoldingsGainPercent;
                private Object AnnualizedGain;
                private Object HoldingsGain;
                private Object HoldingsGainPercentRealtime;
                private Object HoldingsGainRealtime;
                private Object MoreInfo;
                private Object OrderBookRealtime;
                private String MarketCapitalization;
                private Object MarketCapRealtime;
                private String EBITDA;
                private String ChangeFromYearLow;
                private String PercentChangeFromYearLow;
                private Object LastTradeRealtimeWithTime;
                private Object ChangePercentRealtime;
                private String ChangeFromYearHigh;
                private String PercebtChangeFromYearHigh;
                private String LastTradeWithTime;
                private String LastTradePriceOnly;
                private Object HighLimit;
                private Object LowLimit;
                private String DaysRange;
                private Object DaysRangeRealtime;
                private String FiftydayMovingAverage;
                private String TwoHundreddayMovingAverage;
                private String ChangeFromTwoHundreddayMovingAverage;
                private String PercentChangeFromTwoHundreddayMovingAverage;
                private String ChangeFromFiftydayMovingAverage;
                private String PercentChangeFromFiftydayMovingAverage;
                private String Name;
                private Object Notes;
                private String Open;
                private String PreviousClose;
                private Object PricePaid;
                private String ChangeinPercent;
                private String PriceSales;
                private String PriceBook;
                private Object ExDividendDate;
                private String PERatio;
                private Object DividendPayDate;
                private Object PERatioRealtime;
                private String PEGRatio;
                private String PriceEPSEstimateCurrentYear;
                private String PriceEPSEstimateNextYear;

                private Object SharesOwned;
                private String ShortRatio;
                private String LastTradeTime;
                private Object TickerTrend;
                private String OneyrTargetPrice;
                private String Volume;
                private Object HoldingsValue;
                private Object HoldingsValueRealtime;
                private String YearRange;
                private Object DaysValueChange;
                private Object DaysValueChangeRealtime;
                private String StockExchange;
                private Object DividendYield;
                private String PercentChange;

                public void setSymbol(String symbol) {
                    this.symbol = symbol;
                }

                public void setAsk(String Ask) {
                    this.Ask = Ask;
                }

                public void setAverageDailyVolume(String AverageDailyVolume) {
                    this.AverageDailyVolume = AverageDailyVolume;
                }

                public void setBid(String Bid) {
                    this.Bid = Bid;
                }

                public void setAskRealtime(Object AskRealtime) {
                    this.AskRealtime = AskRealtime;
                }

                public void setBidRealtime(Object BidRealtime) {
                    this.BidRealtime = BidRealtime;
                }

                public void setBookValue(String BookValue) {
                    this.BookValue = BookValue;
                }

                public void setChange_PercentChange(String Change_PercentChange) {
                    this.Change_PercentChange = Change_PercentChange;
                }

                public void setChange(String Change) {
                    this.Change = Change;
                }

                public void setCommission(Object Commission) {
                    this.Commission = Commission;
                }

                public void setCurrency(String Currency) {
                    this.Currency = Currency;
                }

                public void setChangeRealtime(Object ChangeRealtime) {
                    this.ChangeRealtime = ChangeRealtime;
                }

                public void setAfterHoursChangeRealtime(Object AfterHoursChangeRealtime) {
                    this.AfterHoursChangeRealtime = AfterHoursChangeRealtime;
                }

                public void setDividendShare(Object DividendShare) {
                    this.DividendShare = DividendShare;
                }

                public void setLastTradeDate(String LastTradeDate) {
                    this.LastTradeDate = LastTradeDate;
                }

                public void setTradeDate(Object TradeDate) {
                    this.TradeDate = TradeDate;
                }

                public void setEarningsShare(String EarningsShare) {
                    this.EarningsShare = EarningsShare;
                }

                public void setErrorIndicationreturnedforsymbolchangedinvalid(Object ErrorIndicationreturnedforsymbolchangedinvalid) {
                    this.ErrorIndicationreturnedforsymbolchangedinvalid = ErrorIndicationreturnedforsymbolchangedinvalid;
                }

                public void setEPSEstimateCurrentYear(String EPSEstimateCurrentYear) {
                    this.EPSEstimateCurrentYear = EPSEstimateCurrentYear;
                }

                public void setEPSEstimateNextYear(String EPSEstimateNextYear) {
                    this.EPSEstimateNextYear = EPSEstimateNextYear;
                }

                public void setEPSEstimateNextQuarter(String EPSEstimateNextQuarter) {
                    this.EPSEstimateNextQuarter = EPSEstimateNextQuarter;
                }

                public void setDaysLow(String DaysLow) {
                    this.DaysLow = DaysLow;
                }

                public void setDaysHigh(String DaysHigh) {
                    this.DaysHigh = DaysHigh;
                }

                public void setYearLow(String YearLow) {
                    this.YearLow = YearLow;
                }

                public void setYearHigh(String YearHigh) {
                    this.YearHigh = YearHigh;
                }

                public void setHoldingsGainPercent(Object HoldingsGainPercent) {
                    this.HoldingsGainPercent = HoldingsGainPercent;
                }

                public void setAnnualizedGain(Object AnnualizedGain) {
                    this.AnnualizedGain = AnnualizedGain;
                }

                public void setHoldingsGain(Object HoldingsGain) {
                    this.HoldingsGain = HoldingsGain;
                }

                public void setHoldingsGainPercentRealtime(Object HoldingsGainPercentRealtime) {
                    this.HoldingsGainPercentRealtime = HoldingsGainPercentRealtime;
                }

                public void setHoldingsGainRealtime(Object HoldingsGainRealtime) {
                    this.HoldingsGainRealtime = HoldingsGainRealtime;
                }

                public void setMoreInfo(Object MoreInfo) {
                    this.MoreInfo = MoreInfo;
                }

                public void setOrderBookRealtime(Object OrderBookRealtime) {
                    this.OrderBookRealtime = OrderBookRealtime;
                }

                public void setMarketCapitalization(String MarketCapitalization) {
                    this.MarketCapitalization = MarketCapitalization;
                }

                public void setMarketCapRealtime(Object MarketCapRealtime) {
                    this.MarketCapRealtime = MarketCapRealtime;
                }

                public void setEBITDA(String EBITDA) {
                    this.EBITDA = EBITDA;
                }

                public void setChangeFromYearLow(String ChangeFromYearLow) {
                    this.ChangeFromYearLow = ChangeFromYearLow;
                }

                public void setPercentChangeFromYearLow(String PercentChangeFromYearLow) {
                    this.PercentChangeFromYearLow = PercentChangeFromYearLow;
                }

                public void setLastTradeRealtimeWithTime(Object LastTradeRealtimeWithTime) {
                    this.LastTradeRealtimeWithTime = LastTradeRealtimeWithTime;
                }

                public void setChangePercentRealtime(Object ChangePercentRealtime) {
                    this.ChangePercentRealtime = ChangePercentRealtime;
                }

                public void setChangeFromYearHigh(String ChangeFromYearHigh) {
                    this.ChangeFromYearHigh = ChangeFromYearHigh;
                }

                public void setPercebtChangeFromYearHigh(String PercebtChangeFromYearHigh) {
                    this.PercebtChangeFromYearHigh = PercebtChangeFromYearHigh;
                }

                public void setLastTradeWithTime(String LastTradeWithTime) {
                    this.LastTradeWithTime = LastTradeWithTime;
                }

                public void setLastTradePriceOnly(String LastTradePriceOnly) {
                    this.LastTradePriceOnly = LastTradePriceOnly;
                }

                public void setHighLimit(Object HighLimit) {
                    this.HighLimit = HighLimit;
                }

                public void setLowLimit(Object LowLimit) {
                    this.LowLimit = LowLimit;
                }

                public void setDaysRange(String DaysRange) {
                    this.DaysRange = DaysRange;
                }

                public void setDaysRangeRealtime(Object DaysRangeRealtime) {
                    this.DaysRangeRealtime = DaysRangeRealtime;
                }

                public void setFiftydayMovingAverage(String FiftydayMovingAverage) {
                    this.FiftydayMovingAverage = FiftydayMovingAverage;
                }

                public void setTwoHundreddayMovingAverage(String TwoHundreddayMovingAverage) {
                    this.TwoHundreddayMovingAverage = TwoHundreddayMovingAverage;
                }

                public void setChangeFromTwoHundreddayMovingAverage(String ChangeFromTwoHundreddayMovingAverage) {
                    this.ChangeFromTwoHundreddayMovingAverage = ChangeFromTwoHundreddayMovingAverage;
                }

                public void setPercentChangeFromTwoHundreddayMovingAverage(String PercentChangeFromTwoHundreddayMovingAverage) {
                    this.PercentChangeFromTwoHundreddayMovingAverage = PercentChangeFromTwoHundreddayMovingAverage;
                }

                public void setChangeFromFiftydayMovingAverage(String ChangeFromFiftydayMovingAverage) {
                    this.ChangeFromFiftydayMovingAverage = ChangeFromFiftydayMovingAverage;
                }

                public void setPercentChangeFromFiftydayMovingAverage(String PercentChangeFromFiftydayMovingAverage) {
                    this.PercentChangeFromFiftydayMovingAverage = PercentChangeFromFiftydayMovingAverage;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public void setNotes(Object Notes) {
                    this.Notes = Notes;
                }

                public void setOpen(String Open) {
                    this.Open = Open;
                }

                public void setPreviousClose(String PreviousClose) {
                    this.PreviousClose = PreviousClose;
                }

                public void setPricePaid(Object PricePaid) {
                    this.PricePaid = PricePaid;
                }

                public void setChangeinPercent(String ChangeinPercent) {
                    this.ChangeinPercent = ChangeinPercent;
                }

                public void setPriceSales(String PriceSales) {
                    this.PriceSales = PriceSales;
                }

                public void setPriceBook(String PriceBook) {
                    this.PriceBook = PriceBook;
                }

                public void setExDividendDate(Object ExDividendDate) {
                    this.ExDividendDate = ExDividendDate;
                }

                public void setPERatio(String PERatio) {
                    this.PERatio = PERatio;
                }

                public void setDividendPayDate(Object DividendPayDate) {
                    this.DividendPayDate = DividendPayDate;
                }

                public void setPERatioRealtime(Object PERatioRealtime) {
                    this.PERatioRealtime = PERatioRealtime;
                }

                public void setPEGRatio(String PEGRatio) {
                    this.PEGRatio = PEGRatio;
                }

                public void setPriceEPSEstimateCurrentYear(String PriceEPSEstimateCurrentYear) {
                    this.PriceEPSEstimateCurrentYear = PriceEPSEstimateCurrentYear;
                }

                public void setPriceEPSEstimateNextYear(String PriceEPSEstimateNextYear) {
                    this.PriceEPSEstimateNextYear = PriceEPSEstimateNextYear;
                }



                public void setSharesOwned(Object SharesOwned) {
                    this.SharesOwned = SharesOwned;
                }

                public void setShortRatio(String ShortRatio) {
                    this.ShortRatio = ShortRatio;
                }

                public void setLastTradeTime(String LastTradeTime) {
                    this.LastTradeTime = LastTradeTime;
                }

                public void setTickerTrend(Object TickerTrend) {
                    this.TickerTrend = TickerTrend;
                }

                public void setOneyrTargetPrice(String OneyrTargetPrice) {
                    this.OneyrTargetPrice = OneyrTargetPrice;
                }

                public void setVolume(String Volume) {
                    this.Volume = Volume;
                }

                public void setHoldingsValue(Object HoldingsValue) {
                    this.HoldingsValue = HoldingsValue;
                }

                public void setHoldingsValueRealtime(Object HoldingsValueRealtime) {
                    this.HoldingsValueRealtime = HoldingsValueRealtime;
                }

                public void setYearRange(String YearRange) {
                    this.YearRange = YearRange;
                }

                public void setDaysValueChange(Object DaysValueChange) {
                    this.DaysValueChange = DaysValueChange;
                }

                public void setDaysValueChangeRealtime(Object DaysValueChangeRealtime) {
                    this.DaysValueChangeRealtime = DaysValueChangeRealtime;
                }

                public void setStockExchange(String StockExchange) {
                    this.StockExchange = StockExchange;
                }

                public void setDividendYield(Object DividendYield) {
                    this.DividendYield = DividendYield;
                }

                public void setPercentChange(String PercentChange) {
                    this.PercentChange = PercentChange;
                }

                public String getSymbol() {
                    return symbol;
                }

                public String getAsk() {
                    return Ask;
                }

                public String getAverageDailyVolume() {
                    return AverageDailyVolume;
                }

                public String getBid() {
                    return Bid;
                }

                public Object getAskRealtime() {
                    return AskRealtime;
                }

                public Object getBidRealtime() {
                    return BidRealtime;
                }

                public String getBookValue() {
                    return BookValue;
                }

                public String getChange_PercentChange() {
                    return Change_PercentChange;
                }

                public String getChange() {
                    return Change;
                }

                public Object getCommission() {
                    return Commission;
                }

                public String getCurrency() {
                    return Currency;
                }

                public Object getChangeRealtime() {
                    return ChangeRealtime;
                }

                public Object getAfterHoursChangeRealtime() {
                    return AfterHoursChangeRealtime;
                }

                public Object getDividendShare() {
                    return DividendShare;
                }

                public String getLastTradeDate() {
                    return LastTradeDate;
                }

                public Object getTradeDate() {
                    return TradeDate;
                }

                public String getEarningsShare() {
                    return EarningsShare;
                }

                public Object getErrorIndicationreturnedforsymbolchangedinvalid() {
                    return ErrorIndicationreturnedforsymbolchangedinvalid;
                }

                public String getEPSEstimateCurrentYear() {
                    return EPSEstimateCurrentYear;
                }

                public String getEPSEstimateNextYear() {
                    return EPSEstimateNextYear;
                }

                public String getEPSEstimateNextQuarter() {
                    return EPSEstimateNextQuarter;
                }

                public String getDaysLow() {
                    return DaysLow;
                }

                public String getDaysHigh() {
                    return DaysHigh;
                }

                public String getYearLow() {
                    return YearLow;
                }

                public String getYearHigh() {
                    return YearHigh;
                }

                public Object getHoldingsGainPercent() {
                    return HoldingsGainPercent;
                }

                public Object getAnnualizedGain() {
                    return AnnualizedGain;
                }

                public Object getHoldingsGain() {
                    return HoldingsGain;
                }

                public Object getHoldingsGainPercentRealtime() {
                    return HoldingsGainPercentRealtime;
                }

                public Object getHoldingsGainRealtime() {
                    return HoldingsGainRealtime;
                }

                public Object getMoreInfo() {
                    return MoreInfo;
                }

                public Object getOrderBookRealtime() {
                    return OrderBookRealtime;
                }

                public String getMarketCapitalization() {
                    return MarketCapitalization;
                }

                public Object getMarketCapRealtime() {
                    return MarketCapRealtime;
                }

                public String getEBITDA() {
                    return EBITDA;
                }

                public String getChangeFromYearLow() {
                    return ChangeFromYearLow;
                }

                public String getPercentChangeFromYearLow() {
                    return PercentChangeFromYearLow;
                }

                public Object getLastTradeRealtimeWithTime() {
                    return LastTradeRealtimeWithTime;
                }

                public Object getChangePercentRealtime() {
                    return ChangePercentRealtime;
                }

                public String getChangeFromYearHigh() {
                    return ChangeFromYearHigh;
                }

                public String getPercebtChangeFromYearHigh() {
                    return PercebtChangeFromYearHigh;
                }

                public String getLastTradeWithTime() {
                    return LastTradeWithTime;
                }

                public String getLastTradePriceOnly() {
                    return LastTradePriceOnly;
                }

                public Object getHighLimit() {
                    return HighLimit;
                }

                public Object getLowLimit() {
                    return LowLimit;
                }

                public String getDaysRange() {
                    return DaysRange;
                }

                public Object getDaysRangeRealtime() {
                    return DaysRangeRealtime;
                }

                public String getFiftydayMovingAverage() {
                    return FiftydayMovingAverage;
                }

                public String getTwoHundreddayMovingAverage() {
                    return TwoHundreddayMovingAverage;
                }

                public String getChangeFromTwoHundreddayMovingAverage() {
                    return ChangeFromTwoHundreddayMovingAverage;
                }

                public String getPercentChangeFromTwoHundreddayMovingAverage() {
                    return PercentChangeFromTwoHundreddayMovingAverage;
                }

                public String getChangeFromFiftydayMovingAverage() {
                    return ChangeFromFiftydayMovingAverage;
                }

                public String getPercentChangeFromFiftydayMovingAverage() {
                    return PercentChangeFromFiftydayMovingAverage;
                }

                public String getName() {
                    return Name;
                }

                public Object getNotes() {
                    return Notes;
                }

                public String getOpen() {
                    return Open;
                }

                public String getPreviousClose() {
                    return PreviousClose;
                }

                public Object getPricePaid() {
                    return PricePaid;
                }

                public String getChangeinPercent() {
                    return ChangeinPercent;
                }

                public String getPriceSales() {
                    return PriceSales;
                }

                public String getPriceBook() {
                    return PriceBook;
                }

                public Object getExDividendDate() {
                    return ExDividendDate;
                }

                public String getPERatio() {
                    return PERatio;
                }

                public Object getDividendPayDate() {
                    return DividendPayDate;
                }

                public Object getPERatioRealtime() {
                    return PERatioRealtime;
                }

                public String getPEGRatio() {
                    return PEGRatio;
                }

                public String getPriceEPSEstimateCurrentYear() {
                    return PriceEPSEstimateCurrentYear;
                }

                public String getPriceEPSEstimateNextYear() {
                    return PriceEPSEstimateNextYear;
                }



                public Object getSharesOwned() {
                    return SharesOwned;
                }

                public String getShortRatio() {
                    return ShortRatio;
                }

                public String getLastTradeTime() {
                    return LastTradeTime;
                }

                public Object getTickerTrend() {
                    return TickerTrend;
                }

                public String getOneyrTargetPrice() {
                    return OneyrTargetPrice;
                }

                public String getVolume() {
                    return Volume;
                }

                public Object getHoldingsValue() {
                    return HoldingsValue;
                }

                public Object getHoldingsValueRealtime() {
                    return HoldingsValueRealtime;
                }

                public String getYearRange() {
                    return YearRange;
                }

                public Object getDaysValueChange() {
                    return DaysValueChange;
                }

                public Object getDaysValueChangeRealtime() {
                    return DaysValueChangeRealtime;
                }

                public String getStockExchange() {
                    return StockExchange;
                }

                public Object getDividendYield() {
                    return DividendYield;
                }

                public String getPercentChange() {
                    return PercentChange;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.symbol);
                    dest.writeString(this.Ask);
                    dest.writeString(this.AverageDailyVolume);
                    dest.writeString(this.Bid);
                  //  dest.writeParcelable(this.AskRealtime, flags);
                  //  dest.writeParcelable(this.BidRealtime, flags);
                    dest.writeString(this.BookValue);
                    dest.writeString(this.Change_PercentChange);
                    dest.writeString(this.Change);
                  //  dest.writeParcelable(this.Commission, flags);
                    dest.writeString(this.Currency);
                  //  dest.writeParcelable(this.ChangeRealtime, flags);
                  //  dest.writeParcelable(this.AfterHoursChangeRealtime, flags);
                  //  dest.writeParcelable(this.DividendShare, flags);
                    dest.writeString(this.LastTradeDate);
                  //  dest.writeParcelable(this.TradeDate, flags);
                    dest.writeString(this.EarningsShare);
                  //  dest.writeParcelable(this.ErrorIndicationreturnedforsymbolchangedinvalid, flags);
                    dest.writeString(this.EPSEstimateCurrentYear);
                    dest.writeString(this.EPSEstimateNextYear);
                    dest.writeString(this.EPSEstimateNextQuarter);
                    dest.writeString(this.DaysLow);
                    dest.writeString(this.DaysHigh);
                    dest.writeString(this.YearLow);
                    dest.writeString(this.YearHigh);
                  //  dest.writeParcelable(this.HoldingsGainPercent, flags);
                  //  dest.writeParcelable(this.AnnualizedGain, flags);
                  //  dest.writeParcelable(this.HoldingsGain, flags);
                  //  dest.writeParcelable(this.HoldingsGainPercentRealtime, flags);
                  //  dest.writeParcelable(this.HoldingsGainRealtime, flags);
                  //  dest.writeParcelable(this.MoreInfo, flags);
                  //  dest.writeParcelable(this.OrderBookRealtime, flags);
                    dest.writeString(this.MarketCapitalization);
                  //  dest.writeParcelable(this.MarketCapRealtime, flags);
                    dest.writeString(this.EBITDA);
                    dest.writeString(this.ChangeFromYearLow);
                    dest.writeString(this.PercentChangeFromYearLow);
                 //   dest.writeParcelable(this.LastTradeRealtimeWithTime, flags);
                 //   dest.writeParcelable(this.ChangePercentRealtime, flags);
                    dest.writeString(this.ChangeFromYearHigh);
                    dest.writeString(this.PercebtChangeFromYearHigh);
                    dest.writeString(this.LastTradeWithTime);
                    dest.writeString(this.LastTradePriceOnly);
                 //   dest.writeParcelable(this.HighLimit, flags);
                  //  dest.writeParcelable(this.LowLimit, flags);
                    dest.writeString(this.DaysRange);
                 //   dest.writeParcelable(this.DaysRangeRealtime, flags);
                    dest.writeString(this.FiftydayMovingAverage);
                    dest.writeString(this.TwoHundreddayMovingAverage);
                    dest.writeString(this.ChangeFromTwoHundreddayMovingAverage);
                    dest.writeString(this.PercentChangeFromTwoHundreddayMovingAverage);
                    dest.writeString(this.ChangeFromFiftydayMovingAverage);
                    dest.writeString(this.PercentChangeFromFiftydayMovingAverage);
                    dest.writeString(this.Name);
                 //   dest.writeParcelable(this.Notes, flags);
                    dest.writeString(this.Open);
                    dest.writeString(this.PreviousClose);
                 //   dest.writeParcelable(this.PricePaid, flags);
                    dest.writeString(this.ChangeinPercent);
                    dest.writeString(this.PriceSales);
                    dest.writeString(this.PriceBook);
                 //   dest.writeParcelable(this.ExDividendDate, flags);
                    dest.writeString(this.PERatio);
                 //   dest.writeParcelable(this.DividendPayDate, flags);
                 //   dest.writeParcelable(this.PERatioRealtime, flags);
                    dest.writeString(this.PEGRatio);
                    dest.writeString(this.PriceEPSEstimateCurrentYear);
                    dest.writeString(this.PriceEPSEstimateNextYear);
                 //   dest.writeParcelable(this.SharesOwned, flags);
                    dest.writeString(this.ShortRatio);
                    dest.writeString(this.LastTradeTime);
                 //   dest.writeParcelable(this.TickerTrend, flags);
                    dest.writeString(this.OneyrTargetPrice);
                    dest.writeString(this.Volume);
                 //   dest.writeParcelable(this.HoldingsValue, flags);
                  //  dest.writeParcelable(this.HoldingsValueRealtime, flags);
                    dest.writeString(this.YearRange);
                 //   dest.writeParcelable(this.DaysValueChange, flags);
                 //   dest.writeParcelable(this.DaysValueChangeRealtime, flags);
                    dest.writeString(this.StockExchange);
                 //   dest.writeParcelable(this.DividendYield, flags);
                    dest.writeString(this.PercentChange);
                }

                public QuoteEntity() {
                }

                protected QuoteEntity(Parcel in) {
                    this.symbol = in.readString();
                    this.Ask = in.readString();
                    this.AverageDailyVolume = in.readString();
                    this.Bid = in.readString();
                    this.AskRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.BidRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.BookValue = in.readString();
                    this.Change_PercentChange = in.readString();
                    this.Change = in.readString();
                    this.Commission = in.readParcelable(Object.class.getClassLoader());
                    this.Currency = in.readString();
                    this.ChangeRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.AfterHoursChangeRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.DividendShare = in.readParcelable(Object.class.getClassLoader());
                    this.LastTradeDate = in.readString();
                    this.TradeDate = in.readParcelable(Object.class.getClassLoader());
                    this.EarningsShare = in.readString();
                    this.ErrorIndicationreturnedforsymbolchangedinvalid = in.readParcelable(Object.class.getClassLoader());
                    this.EPSEstimateCurrentYear = in.readString();
                    this.EPSEstimateNextYear = in.readString();
                    this.EPSEstimateNextQuarter = in.readString();
                    this.DaysLow = in.readString();
                    this.DaysHigh = in.readString();
                    this.YearLow = in.readString();
                    this.YearHigh = in.readString();
                    this.HoldingsGainPercent = in.readParcelable(Object.class.getClassLoader());
                    this.AnnualizedGain = in.readParcelable(Object.class.getClassLoader());
                    this.HoldingsGain = in.readParcelable(Object.class.getClassLoader());
                    this.HoldingsGainPercentRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.HoldingsGainRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.MoreInfo = in.readParcelable(Object.class.getClassLoader());
                    this.OrderBookRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.MarketCapitalization = in.readString();
                    this.MarketCapRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.EBITDA = in.readString();
                    this.ChangeFromYearLow = in.readString();
                    this.PercentChangeFromYearLow = in.readString();
                    this.LastTradeRealtimeWithTime = in.readParcelable(Object.class.getClassLoader());
                    this.ChangePercentRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.ChangeFromYearHigh = in.readString();
                    this.PercebtChangeFromYearHigh = in.readString();
                    this.LastTradeWithTime = in.readString();
                    this.LastTradePriceOnly = in.readString();
                    this.HighLimit = in.readParcelable(Object.class.getClassLoader());
                    this.LowLimit = in.readParcelable(Object.class.getClassLoader());
                    this.DaysRange = in.readString();
                    this.DaysRangeRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.FiftydayMovingAverage = in.readString();
                    this.TwoHundreddayMovingAverage = in.readString();
                    this.ChangeFromTwoHundreddayMovingAverage = in.readString();
                    this.PercentChangeFromTwoHundreddayMovingAverage = in.readString();
                    this.ChangeFromFiftydayMovingAverage = in.readString();
                    this.PercentChangeFromFiftydayMovingAverage = in.readString();
                    this.Name = in.readString();
                    this.Notes = in.readParcelable(Object.class.getClassLoader());
                    this.Open = in.readString();
                    this.PreviousClose = in.readString();
                    this.PricePaid = in.readParcelable(Object.class.getClassLoader());
                    this.ChangeinPercent = in.readString();
                    this.PriceSales = in.readString();
                    this.PriceBook = in.readString();
                    this.ExDividendDate = in.readParcelable(Object.class.getClassLoader());
                    this.PERatio = in.readString();
                    this.DividendPayDate = in.readParcelable(Object.class.getClassLoader());
                    this.PERatioRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.PEGRatio = in.readString();
                    this.PriceEPSEstimateCurrentYear = in.readString();
                    this.PriceEPSEstimateNextYear = in.readString();
                    this.SharesOwned = in.readParcelable(Object.class.getClassLoader());
                    this.ShortRatio = in.readString();
                    this.LastTradeTime = in.readString();
                    this.TickerTrend = in.readParcelable(Object.class.getClassLoader());
                    this.OneyrTargetPrice = in.readString();
                    this.Volume = in.readString();
                    this.HoldingsValue = in.readParcelable(Object.class.getClassLoader());
                    this.HoldingsValueRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.YearRange = in.readString();
                    this.DaysValueChange = in.readParcelable(Object.class.getClassLoader());
                    this.DaysValueChangeRealtime = in.readParcelable(Object.class.getClassLoader());
                    this.StockExchange = in.readString();
                    this.DividendYield = in.readParcelable(Object.class.getClassLoader());
                    this.PercentChange = in.readString();
                }

                public static final transient Parcelable.Creator<QuoteEntity> CREATOR = new Parcelable.Creator<QuoteEntity>() {
                    public QuoteEntity createFromParcel(Parcel source) {
                        return new QuoteEntity(source);
                    }

                    public QuoteEntity[] newArray(int size) {
                        return new QuoteEntity[size];
                    }
                };
            }
        }
    }
}
