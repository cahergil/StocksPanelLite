package com.carlos.capstone.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 28/12/2015.
 */
public class StockStatsResponse implements Parcelable {

    /**
     * count : 1
     * created : 2015-12-27T19:58:13Z
     * lang : es
     * results : {"stats":{"symbol":"GOOG","MarketCap":{"term":"intraday"},"EnterpriseValue":{"term":"Dec 27, 2015","content":"452280000000"},"TrailingPE":{"term":"ttm, intraday","content":"31.55"},"ForwardPE":{"term":"fye Dec 31, 2016","content":"21.93"},"PEGRatio":{"term":"5 yr expected","content":"1.5"},"PriceToSales":{"term":"ttm","content":"7.19"},"PriceToBook":{"term":"mrq","content":"4.44"},"EnterpriseValueToRevenue":{"term":"ttm","content":"6.3"},"EnterpriseValueToEBITDA":{"term":"ttm","content":"19.41"},"FiscalYearEnds":"Dec 31","MostRecentQuarter":{"term":"mrq","content":"Sep 30, 2015"},"ProfitMargin":{"term":"ttm","content":"22.86"},"OperatingMargin":{"term":"ttm","content":"25.61"},"ReturnOnAssets":{"term":"ttm","content":"8.51"},"ReturnOnEquity":{"term":"ttm","content":"14.36"},"Revenue":{"term":"ttm","content":"71760000000"},"RevenuePerShare":{"term":"ttm","content":"105.15"},"QtrlyRevenueGrowth":{"term":"yoy","content":"13"},"GrossProfit":{"term":"ttm","content":"40310000000"},"EBITDA":{"term":"ttm","content":"23300000000"},"NetIncomeAvlToCommon":{"term":"ttm","content":"15440000000"},"DilutedEPS":{"term":"ttm","content":"23.72"},"QtrlyEarningsGrowth":{"term":"yoy","content":"45.3"},"TotalCash":{"term":"mrq","content":"70910000000"},"TotalCashPerShare":{"term":"mrq","content":"103.11"},"TotalDebt":{"term":"mrq","content":"8500000000"},"TotalDebtToEquity":{"term":"mrq","content":"7.31"},"CurrentRatio":{"term":"mrq","content":"4.77"},"BookValuePerShare":{"term":"mrq","content":"169.03"},"OperatingCashFlow":{"term":"ttm","content":"25970000000"},"LeveredFreeCashFlow":{"term":"ttm","content":"12260000000"},"Beta":"1.03","P_52WeekChange":"40.53","SAndP50052WeekChange":"-1.33","P_52WeekHigh":{"term":"Dec 2, 2015","content":"775.96"},"P_52WeekLow":{"term":"Jan 12, 2015","content":"486.23"},"P_50DayMovingAverage":"745.29","P_200DayMovingAverage":"647.01","AvgVol":[{"term":"3 month","content":"2034450"},{"term":"10 day","content":"1793220"}],"SharesOutstanding":"345500000","Float":"588260000","PctHeldByInsiders":"0.01","PctHeldByInstitutions":"73.1","SharesShort":[{"term":"as of Dec 15, 2015","content":"3480000"},{"term":"prior month","content":"3960000"}],"ShortRatio":{"term":"as of Dec 15, 2015","content":"1.75"},"ShortPctOfFloat":{"term":"as of Dec 15, 2015"},"ForwardAnnualDividendRate":null,"ForwardAnnualDividendYield":null,"TrailingAnnualDividendYield":[null,null],"P_5YearAverageDividendYield":null,"PayoutRatio":null,"DividendDate":null,"ExDividendDate":null,"LastSplitFactor":{"term":"new per old","content":"10000000:10000000"},"LastSplitDate":"Apr 27, 2015"}}
     */

    private QueryEntity query;

    public void setQuery(QueryEntity query) {
        this.query = query;
    }

    public QueryEntity getQuery() {
        return query;
    }

    public static class QueryEntity implements Parcelable {
        private int count;
        private String created;
        private String lang;
        /**
         * stats : {"symbol":"GOOG","MarketCap":{"term":"intraday"},"EnterpriseValue":{"term":"Dec 27, 2015","content":"452280000000"},"TrailingPE":{"term":"ttm, intraday","content":"31.55"},"ForwardPE":{"term":"fye Dec 31, 2016","content":"21.93"},"PEGRatio":{"term":"5 yr expected","content":"1.5"},"PriceToSales":{"term":"ttm","content":"7.19"},"PriceToBook":{"term":"mrq","content":"4.44"},"EnterpriseValueToRevenue":{"term":"ttm","content":"6.3"},"EnterpriseValueToEBITDA":{"term":"ttm","content":"19.41"},"FiscalYearEnds":"Dec 31","MostRecentQuarter":{"term":"mrq","content":"Sep 30, 2015"},"ProfitMargin":{"term":"ttm","content":"22.86"},"OperatingMargin":{"term":"ttm","content":"25.61"},"ReturnOnAssets":{"term":"ttm","content":"8.51"},"ReturnOnEquity":{"term":"ttm","content":"14.36"},"Revenue":{"term":"ttm","content":"71760000000"},"RevenuePerShare":{"term":"ttm","content":"105.15"},"QtrlyRevenueGrowth":{"term":"yoy","content":"13"},"GrossProfit":{"term":"ttm","content":"40310000000"},"EBITDA":{"term":"ttm","content":"23300000000"},"NetIncomeAvlToCommon":{"term":"ttm","content":"15440000000"},"DilutedEPS":{"term":"ttm","content":"23.72"},"QtrlyEarningsGrowth":{"term":"yoy","content":"45.3"},"TotalCash":{"term":"mrq","content":"70910000000"},"TotalCashPerShare":{"term":"mrq","content":"103.11"},"TotalDebt":{"term":"mrq","content":"8500000000"},"TotalDebtToEquity":{"term":"mrq","content":"7.31"},"CurrentRatio":{"term":"mrq","content":"4.77"},"BookValuePerShare":{"term":"mrq","content":"169.03"},"OperatingCashFlow":{"term":"ttm","content":"25970000000"},"LeveredFreeCashFlow":{"term":"ttm","content":"12260000000"},"Beta":"1.03","P_52WeekChange":"40.53","SAndP50052WeekChange":"-1.33","P_52WeekHigh":{"term":"Dec 2, 2015","content":"775.96"},"P_52WeekLow":{"term":"Jan 12, 2015","content":"486.23"},"P_50DayMovingAverage":"745.29","P_200DayMovingAverage":"647.01","AvgVol":[{"term":"3 month","content":"2034450"},{"term":"10 day","content":"1793220"}],"SharesOutstanding":"345500000","Float":"588260000","PctHeldByInsiders":"0.01","PctHeldByInstitutions":"73.1","SharesShort":[{"term":"as of Dec 15, 2015","content":"3480000"},{"term":"prior month","content":"3960000"}],"ShortRatio":{"term":"as of Dec 15, 2015","content":"1.75"},"ShortPctOfFloat":{"term":"as of Dec 15, 2015"},"ForwardAnnualDividendRate":null,"ForwardAnnualDividendYield":null,"TrailingAnnualDividendYield":[null,null],"P_5YearAverageDividendYield":null,"PayoutRatio":null,"DividendDate":null,"ExDividendDate":null,"LastSplitFactor":{"term":"new per old","content":"10000000:10000000"},"LastSplitDate":"Apr 27, 2015"}
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

        public static class ResultsEntity implements Parcelable {
            /**
             * symbol : GOOG
             * MarketCap : {"term":"intraday"}
             * EnterpriseValue : {"term":"Dec 27, 2015","content":"452280000000"}
             * TrailingPE : {"term":"ttm, intraday","content":"31.55"}
             * ForwardPE : {"term":"fye Dec 31, 2016","content":"21.93"}
             * PEGRatio : {"term":"5 yr expected","content":"1.5"}
             * PriceToSales : {"term":"ttm","content":"7.19"}
             * PriceToBook : {"term":"mrq","content":"4.44"}
             * EnterpriseValueToRevenue : {"term":"ttm","content":"6.3"}
             * EnterpriseValueToEBITDA : {"term":"ttm","content":"19.41"}
             * FiscalYearEnds : Dec 31
             * MostRecentQuarter : {"term":"mrq","content":"Sep 30, 2015"}
             * ProfitMargin : {"term":"ttm","content":"22.86"}
             * OperatingMargin : {"term":"ttm","content":"25.61"}
             * ReturnOnAssets : {"term":"ttm","content":"8.51"}
             * ReturnOnEquity : {"term":"ttm","content":"14.36"}
             * Revenue : {"term":"ttm","content":"71760000000"}
             * RevenuePerShare : {"term":"ttm","content":"105.15"}
             * QtrlyRevenueGrowth : {"term":"yoy","content":"13"}
             * GrossProfit : {"term":"ttm","content":"40310000000"}
             * EBITDA : {"term":"ttm","content":"23300000000"}
             * NetIncomeAvlToCommon : {"term":"ttm","content":"15440000000"}
             * DilutedEPS : {"term":"ttm","content":"23.72"}
             * QtrlyEarningsGrowth : {"term":"yoy","content":"45.3"}
             * TotalCash : {"term":"mrq","content":"70910000000"}
             * TotalCashPerShare : {"term":"mrq","content":"103.11"}
             * TotalDebt : {"term":"mrq","content":"8500000000"}
             * TotalDebtToEquity : {"term":"mrq","content":"7.31"}
             * CurrentRatio : {"term":"mrq","content":"4.77"}
             * BookValuePerShare : {"term":"mrq","content":"169.03"}
             * OperatingCashFlow : {"term":"ttm","content":"25970000000"}
             * LeveredFreeCashFlow : {"term":"ttm","content":"12260000000"}
             * Beta : 1.03
             * P_52WeekChange : 40.53
             * SAndP50052WeekChange : -1.33
             * P_52WeekHigh : {"term":"Dec 2, 2015","content":"775.96"}
             * P_52WeekLow : {"term":"Jan 12, 2015","content":"486.23"}
             * P_50DayMovingAverage : 745.29
             * P_200DayMovingAverage : 647.01
             * AvgVol : [{"term":"3 month","content":"2034450"},{"term":"10 day","content":"1793220"}]
             * SharesOutstanding : 345500000
             * Float : 588260000
             * PctHeldByInsiders : 0.01
             * PctHeldByInstitutions : 73.1
             * SharesShort : [{"term":"as of Dec 15, 2015","content":"3480000"},{"term":"prior month","content":"3960000"}]
             * ShortRatio : {"term":"as of Dec 15, 2015","content":"1.75"}
             * ShortPctOfFloat : {"term":"as of Dec 15, 2015"}
             * ForwardAnnualDividendRate : null
             * ForwardAnnualDividendYield : null
             * TrailingAnnualDividendYield : [null,null]
             * P_5YearAverageDividendYield : null
             * PayoutRatio : null
             * DividendDate : null
             * ExDividendDate : null
             * LastSplitFactor : {"term":"new per old","content":"10000000:10000000"}
             * LastSplitDate : Apr 27, 2015
             */

            private StatsEntity stats;

            public void setStats(StatsEntity stats) {
                this.stats = stats;
            }

            public StatsEntity getStats() {
                return stats;
            }

            public static class StatsEntity implements Parcelable {
                private String symbol;
                /**
                 * term : intraday
                 */

                private MarketCapEntity MarketCap;
                /**
                 * term : Dec 27, 2015
                 * content : 452280000000
                 */

                private EnterpriseValueEntity EnterpriseValue;
                /**
                 * term : ttm, intraday
                 * content : 31.55
                 */

                private TrailingPEEntity TrailingPE;
                /**
                 * term : fye Dec 31, 2016
                 * content : 21.93
                 */

                private ForwardPEEntity ForwardPE;
                /**
                 * term : 5 yr expected
                 * content : 1.5
                 */

                private PEGRatioEntity PEGRatio;
                /**
                 * term : ttm
                 * content : 7.19
                 */

                private PriceToSalesEntity PriceToSales;
                /**
                 * term : mrq
                 * content : 4.44
                 */

                private PriceToBookEntity PriceToBook;
                /**
                 * term : ttm
                 * content : 6.3
                 */

                private EnterpriseValueToRevenueEntity EnterpriseValueToRevenue;
                /**
                 * term : ttm
                 * content : 19.41
                 */

                private EnterpriseValueToEBITDAEntity EnterpriseValueToEBITDA;
                private String FiscalYearEnds;
                /**
                 * term : mrq
                 * content : Sep 30, 2015
                 */

                private MostRecentQuarterEntity MostRecentQuarter;
                /**
                 * term : ttm
                 * content : 22.86
                 */

                private ProfitMarginEntity ProfitMargin;
                /**
                 * term : ttm
                 * content : 25.61
                 */

                private OperatingMarginEntity OperatingMargin;
                /**
                 * term : ttm
                 * content : 8.51
                 */

                private ReturnOnAssetsEntity ReturnOnAssets;
                /**
                 * term : ttm
                 * content : 14.36
                 */

                private ReturnOnEquityEntity ReturnOnEquity;
                /**
                 * term : ttm
                 * content : 71760000000
                 */

                private RevenueEntity Revenue;
                /**
                 * term : ttm
                 * content : 105.15
                 */

                private RevenuePerShareEntity RevenuePerShare;
                /**
                 * term : yoy
                 * content : 13
                 */

                private QtrlyRevenueGrowthEntity QtrlyRevenueGrowth;
                /**
                 * term : ttm
                 * content : 40310000000
                 */

                private GrossProfitEntity GrossProfit;
                /**
                 * term : ttm
                 * content : 23300000000
                 */

                private EBITDAEntity EBITDA;
                /**
                 * term : ttm
                 * content : 15440000000
                 */

                private NetIncomeAvlToCommonEntity NetIncomeAvlToCommon;
                /**
                 * term : ttm
                 * content : 23.72
                 */

                private DilutedEPSEntity DilutedEPS;
                /**
                 * term : yoy
                 * content : 45.3
                 */

                private QtrlyEarningsGrowthEntity QtrlyEarningsGrowth;
                /**
                 * term : mrq
                 * content : 70910000000
                 */

                private TotalCashEntity TotalCash;
                /**
                 * term : mrq
                 * content : 103.11
                 */

                private TotalCashPerShareEntity TotalCashPerShare;
                /**
                 * term : mrq
                 * content : 8500000000
                 */

                private TotalDebtEntity TotalDebt;
                /**
                 * term : mrq
                 * content : 7.31
                 */

                private TotalDebtToEquityEntity TotalDebtToEquity;
                /**
                 * term : mrq
                 * content : 4.77
                 */

                private CurrentRatioEntity CurrentRatio;
                /**
                 * term : mrq
                 * content : 169.03
                 */

                private BookValuePerShareEntity BookValuePerShare;
                /**
                 * term : ttm
                 * content : 25970000000
                 */

                private OperatingCashFlowEntity OperatingCashFlow;
                /**
                 * term : ttm
                 * content : 12260000000
                 */

                private LeveredFreeCashFlowEntity LeveredFreeCashFlow;
                private String Beta;
                private String P_52WeekChange;
                private String SAndP50052WeekChange;
                /**
                 * term : Dec 2, 2015
                 * content : 775.96
                 */

                private P52WeekHighEntity P_52WeekHigh;
                /**
                 * term : Jan 12, 2015
                 * content : 486.23
                 */

                private P52WeekLowEntity P_52WeekLow;
                private String P_50DayMovingAverage;
                private String P_200DayMovingAverage;
                private String SharesOutstanding;
                private String Float;
                private String PctHeldByInsiders;
                private String PctHeldByInstitutions;
                /**
                 * term : as of Dec 15, 2015
                 * content : 1.75
                 */

                private ShortRatioEntity ShortRatio;
                /**
                 * term : as of Dec 15, 2015
                 */

                private ShortPctOfFloatEntity ShortPctOfFloat;
                private transient Object ForwardAnnualDividendRate;
                private transient Object ForwardAnnualDividendYield;
                private transient Object P_5YearAverageDividendYield;
                private transient Object PayoutRatio;
                private transient Object DividendDate;
                private transient Object ExDividendDate;
                /**
                 * term : new per old
                 * content : 10000000:10000000
                 */

                private LastSplitFactorEntity LastSplitFactor;
                private String LastSplitDate;
                /**
                 * term : 3 month
                 * content : 2034450
                 */

                private List<AvgVolEntity> AvgVol;
                /**
                 * term : as of Dec 15, 2015
                 * content : 3480000
                 */

                private List<SharesShortEntity> SharesShort;
                private List<?> TrailingAnnualDividendYield;

                public void setSymbol(String symbol) {
                    this.symbol = symbol;
                }

                public void setMarketCap(MarketCapEntity MarketCap) {
                    this.MarketCap = MarketCap;
                }

                public void setEnterpriseValue(EnterpriseValueEntity EnterpriseValue) {
                    this.EnterpriseValue = EnterpriseValue;
                }

                public void setTrailingPE(TrailingPEEntity TrailingPE) {
                    this.TrailingPE = TrailingPE;
                }

                public void setForwardPE(ForwardPEEntity ForwardPE) {
                    this.ForwardPE = ForwardPE;
                }

                public void setPEGRatio(PEGRatioEntity PEGRatio) {
                    this.PEGRatio = PEGRatio;
                }

                public void setPriceToSales(PriceToSalesEntity PriceToSales) {
                    this.PriceToSales = PriceToSales;
                }

                public void setPriceToBook(PriceToBookEntity PriceToBook) {
                    this.PriceToBook = PriceToBook;
                }

                public void setEnterpriseValueToRevenue(EnterpriseValueToRevenueEntity EnterpriseValueToRevenue) {
                    this.EnterpriseValueToRevenue = EnterpriseValueToRevenue;
                }

                public void setEnterpriseValueToEBITDA(EnterpriseValueToEBITDAEntity EnterpriseValueToEBITDA) {
                    this.EnterpriseValueToEBITDA = EnterpriseValueToEBITDA;
                }

                public void setFiscalYearEnds(String FiscalYearEnds) {
                    this.FiscalYearEnds = FiscalYearEnds;
                }

                public void setMostRecentQuarter(MostRecentQuarterEntity MostRecentQuarter) {
                    this.MostRecentQuarter = MostRecentQuarter;
                }

                public void setProfitMargin(ProfitMarginEntity ProfitMargin) {
                    this.ProfitMargin = ProfitMargin;
                }

                public void setOperatingMargin(OperatingMarginEntity OperatingMargin) {
                    this.OperatingMargin = OperatingMargin;
                }

                public void setReturnOnAssets(ReturnOnAssetsEntity ReturnOnAssets) {
                    this.ReturnOnAssets = ReturnOnAssets;
                }

                public void setReturnOnEquity(ReturnOnEquityEntity ReturnOnEquity) {
                    this.ReturnOnEquity = ReturnOnEquity;
                }

                public void setRevenue(RevenueEntity Revenue) {
                    this.Revenue = Revenue;
                }

                public void setRevenuePerShare(RevenuePerShareEntity RevenuePerShare) {
                    this.RevenuePerShare = RevenuePerShare;
                }

                public void setQtrlyRevenueGrowth(QtrlyRevenueGrowthEntity QtrlyRevenueGrowth) {
                    this.QtrlyRevenueGrowth = QtrlyRevenueGrowth;
                }

                public void setGrossProfit(GrossProfitEntity GrossProfit) {
                    this.GrossProfit = GrossProfit;
                }

                public void setEBITDA(EBITDAEntity EBITDA) {
                    this.EBITDA = EBITDA;
                }

                public void setNetIncomeAvlToCommon(NetIncomeAvlToCommonEntity NetIncomeAvlToCommon) {
                    this.NetIncomeAvlToCommon = NetIncomeAvlToCommon;
                }

                public void setDilutedEPS(DilutedEPSEntity DilutedEPS) {
                    this.DilutedEPS = DilutedEPS;
                }

                public void setQtrlyEarningsGrowth(QtrlyEarningsGrowthEntity QtrlyEarningsGrowth) {
                    this.QtrlyEarningsGrowth = QtrlyEarningsGrowth;
                }

                public void setTotalCash(TotalCashEntity TotalCash) {
                    this.TotalCash = TotalCash;
                }

                public void setTotalCashPerShare(TotalCashPerShareEntity TotalCashPerShare) {
                    this.TotalCashPerShare = TotalCashPerShare;
                }

                public void setTotalDebt(TotalDebtEntity TotalDebt) {
                    this.TotalDebt = TotalDebt;
                }

                public void setTotalDebtToEquity(TotalDebtToEquityEntity TotalDebtToEquity) {
                    this.TotalDebtToEquity = TotalDebtToEquity;
                }

                public void setCurrentRatio(CurrentRatioEntity CurrentRatio) {
                    this.CurrentRatio = CurrentRatio;
                }

                public void setBookValuePerShare(BookValuePerShareEntity BookValuePerShare) {
                    this.BookValuePerShare = BookValuePerShare;
                }

                public void setOperatingCashFlow(OperatingCashFlowEntity OperatingCashFlow) {
                    this.OperatingCashFlow = OperatingCashFlow;
                }

                public void setLeveredFreeCashFlow(LeveredFreeCashFlowEntity LeveredFreeCashFlow) {
                    this.LeveredFreeCashFlow = LeveredFreeCashFlow;
                }

                public void setBeta(String Beta) {
                    this.Beta = Beta;
                }

                public void setP_52WeekChange(String P_52WeekChange) {
                    this.P_52WeekChange = P_52WeekChange;
                }

                public void setSAndP50052WeekChange(String SAndP50052WeekChange) {
                    this.SAndP50052WeekChange = SAndP50052WeekChange;
                }

                public void setP_52WeekHigh(P52WeekHighEntity P_52WeekHigh) {
                    this.P_52WeekHigh = P_52WeekHigh;
                }

                public void setP_52WeekLow(P52WeekLowEntity P_52WeekLow) {
                    this.P_52WeekLow = P_52WeekLow;
                }

                public void setP_50DayMovingAverage(String P_50DayMovingAverage) {
                    this.P_50DayMovingAverage = P_50DayMovingAverage;
                }

                public void setP_200DayMovingAverage(String P_200DayMovingAverage) {
                    this.P_200DayMovingAverage = P_200DayMovingAverage;
                }

                public void setSharesOutstanding(String SharesOutstanding) {
                    this.SharesOutstanding = SharesOutstanding;
                }

                public void setFloat(String Float) {
                    this.Float = Float;
                }

                public void setPctHeldByInsiders(String PctHeldByInsiders) {
                    this.PctHeldByInsiders = PctHeldByInsiders;
                }

                public void setPctHeldByInstitutions(String PctHeldByInstitutions) {
                    this.PctHeldByInstitutions = PctHeldByInstitutions;
                }

                public void setShortRatio(ShortRatioEntity ShortRatio) {
                    this.ShortRatio = ShortRatio;
                }

                public void setShortPctOfFloat(ShortPctOfFloatEntity ShortPctOfFloat) {
                    this.ShortPctOfFloat = ShortPctOfFloat;
                }

                public void setForwardAnnualDividendRate(Object ForwardAnnualDividendRate) {
                    this.ForwardAnnualDividendRate = ForwardAnnualDividendRate;
                }

                public void setForwardAnnualDividendYield(Object ForwardAnnualDividendYield) {
                    this.ForwardAnnualDividendYield = ForwardAnnualDividendYield;
                }

                public void setP_5YearAverageDividendYield(Object P_5YearAverageDividendYield) {
                    this.P_5YearAverageDividendYield = P_5YearAverageDividendYield;
                }

                public void setPayoutRatio(Object PayoutRatio) {
                    this.PayoutRatio = PayoutRatio;
                }

                public void setDividendDate(Object DividendDate) {
                    this.DividendDate = DividendDate;
                }

                public void setExDividendDate(Object ExDividendDate) {
                    this.ExDividendDate = ExDividendDate;
                }

                public void setLastSplitFactor(LastSplitFactorEntity LastSplitFactor) {
                    this.LastSplitFactor = LastSplitFactor;
                }

                public void setLastSplitDate(String LastSplitDate) {
                    this.LastSplitDate = LastSplitDate;
                }

                public void setAvgVol(List<AvgVolEntity> AvgVol) {
                    this.AvgVol = AvgVol;
                }

                public void setSharesShort(List<SharesShortEntity> SharesShort) {
                    this.SharesShort = SharesShort;
                }

                public void setTrailingAnnualDividendYield(List<?> TrailingAnnualDividendYield) {
                    this.TrailingAnnualDividendYield = TrailingAnnualDividendYield;
                }

                public String getSymbol() {
                    return symbol;
                }

                public MarketCapEntity getMarketCap() {
                    return MarketCap;
                }

                public EnterpriseValueEntity getEnterpriseValue() {
                    return EnterpriseValue;
                }

                public TrailingPEEntity getTrailingPE() {
                    return TrailingPE;
                }

                public ForwardPEEntity getForwardPE() {
                    return ForwardPE;
                }

                public PEGRatioEntity getPEGRatio() {
                    return PEGRatio;
                }

                public PriceToSalesEntity getPriceToSales() {
                    return PriceToSales;
                }

                public PriceToBookEntity getPriceToBook() {
                    return PriceToBook;
                }

                public EnterpriseValueToRevenueEntity getEnterpriseValueToRevenue() {
                    return EnterpriseValueToRevenue;
                }

                public EnterpriseValueToEBITDAEntity getEnterpriseValueToEBITDA() {
                    return EnterpriseValueToEBITDA;
                }

                public String getFiscalYearEnds() {
                    return FiscalYearEnds;
                }

                public MostRecentQuarterEntity getMostRecentQuarter() {
                    return MostRecentQuarter;
                }

                public ProfitMarginEntity getProfitMargin() {
                    return ProfitMargin;
                }

                public OperatingMarginEntity getOperatingMargin() {
                    return OperatingMargin;
                }

                public ReturnOnAssetsEntity getReturnOnAssets() {
                    return ReturnOnAssets;
                }

                public ReturnOnEquityEntity getReturnOnEquity() {
                    return ReturnOnEquity;
                }

                public RevenueEntity getRevenue() {
                    return Revenue;
                }

                public RevenuePerShareEntity getRevenuePerShare() {
                    return RevenuePerShare;
                }

                public QtrlyRevenueGrowthEntity getQtrlyRevenueGrowth() {
                    return QtrlyRevenueGrowth;
                }

                public GrossProfitEntity getGrossProfit() {
                    return GrossProfit;
                }

                public EBITDAEntity getEBITDA() {
                    return EBITDA;
                }

                public NetIncomeAvlToCommonEntity getNetIncomeAvlToCommon() {
                    return NetIncomeAvlToCommon;
                }

                public DilutedEPSEntity getDilutedEPS() {
                    return DilutedEPS;
                }

                public QtrlyEarningsGrowthEntity getQtrlyEarningsGrowth() {
                    return QtrlyEarningsGrowth;
                }

                public TotalCashEntity getTotalCash() {
                    return TotalCash;
                }

                public TotalCashPerShareEntity getTotalCashPerShare() {
                    return TotalCashPerShare;
                }

                public TotalDebtEntity getTotalDebt() {
                    return TotalDebt;
                }

                public TotalDebtToEquityEntity getTotalDebtToEquity() {
                    return TotalDebtToEquity;
                }

                public CurrentRatioEntity getCurrentRatio() {
                    return CurrentRatio;
                }

                public BookValuePerShareEntity getBookValuePerShare() {
                    return BookValuePerShare;
                }

                public OperatingCashFlowEntity getOperatingCashFlow() {
                    return OperatingCashFlow;
                }

                public LeveredFreeCashFlowEntity getLeveredFreeCashFlow() {
                    return LeveredFreeCashFlow;
                }

                public String getBeta() {
                    return Beta;
                }

                public String getP_52WeekChange() {
                    return P_52WeekChange;
                }

                public String getSAndP50052WeekChange() {
                    return SAndP50052WeekChange;
                }

                public P52WeekHighEntity getP_52WeekHigh() {
                    return P_52WeekHigh;
                }

                public P52WeekLowEntity getP_52WeekLow() {
                    return P_52WeekLow;
                }

                public String getP_50DayMovingAverage() {
                    return P_50DayMovingAverage;
                }

                public String getP_200DayMovingAverage() {
                    return P_200DayMovingAverage;
                }

                public String getSharesOutstanding() {
                    return SharesOutstanding;
                }

                public String getFloat() {
                    return Float;
                }

                public String getPctHeldByInsiders() {
                    return PctHeldByInsiders;
                }

                public String getPctHeldByInstitutions() {
                    return PctHeldByInstitutions;
                }

                public ShortRatioEntity getShortRatio() {
                    return ShortRatio;
                }

                public ShortPctOfFloatEntity getShortPctOfFloat() {
                    return ShortPctOfFloat;
                }

                public Object getForwardAnnualDividendRate() {
                    return ForwardAnnualDividendRate;
                }

                public Object getForwardAnnualDividendYield() {
                    return ForwardAnnualDividendYield;
                }

                public Object getP_5YearAverageDividendYield() {
                    return P_5YearAverageDividendYield;
                }

                public Object getPayoutRatio() {
                    return PayoutRatio;
                }

                public Object getDividendDate() {
                    return DividendDate;
                }

                public Object getExDividendDate() {
                    return ExDividendDate;
                }

                public LastSplitFactorEntity getLastSplitFactor() {
                    return LastSplitFactor;
                }

                public String getLastSplitDate() {
                    return LastSplitDate;
                }

                public List<AvgVolEntity> getAvgVol() {
                    return AvgVol;
                }

                public List<SharesShortEntity> getSharesShort() {
                    return SharesShort;
                }

                public List<?> getTrailingAnnualDividendYield() {
                    return TrailingAnnualDividendYield;
                }

                public static class MarketCapEntity implements Parcelable {
                    private String term;
                    private String content;

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public String getTerm() {
                        return term;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public MarketCapEntity() {
                    }

                    protected MarketCapEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<MarketCapEntity> CREATOR = new Creator<MarketCapEntity>() {
                        public MarketCapEntity createFromParcel(Parcel source) {
                            return new MarketCapEntity(source);
                        }

                        public MarketCapEntity[] newArray(int size) {
                            return new MarketCapEntity[size];
                        }
                    };
                }

                public static class EnterpriseValueEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public EnterpriseValueEntity() {
                    }

                    protected EnterpriseValueEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<EnterpriseValueEntity> CREATOR = new Creator<EnterpriseValueEntity>() {
                        public EnterpriseValueEntity createFromParcel(Parcel source) {
                            return new EnterpriseValueEntity(source);
                        }

                        public EnterpriseValueEntity[] newArray(int size) {
                            return new EnterpriseValueEntity[size];
                        }
                    };
                }

                public static class TrailingPEEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public TrailingPEEntity() {
                    }

                    protected TrailingPEEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<TrailingPEEntity> CREATOR = new Creator<TrailingPEEntity>() {
                        public TrailingPEEntity createFromParcel(Parcel source) {
                            return new TrailingPEEntity(source);
                        }

                        public TrailingPEEntity[] newArray(int size) {
                            return new TrailingPEEntity[size];
                        }
                    };
                }

                public static class ForwardPEEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public ForwardPEEntity() {
                    }

                    protected ForwardPEEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<ForwardPEEntity> CREATOR = new Creator<ForwardPEEntity>() {
                        public ForwardPEEntity createFromParcel(Parcel source) {
                            return new ForwardPEEntity(source);
                        }

                        public ForwardPEEntity[] newArray(int size) {
                            return new ForwardPEEntity[size];
                        }
                    };
                }

                public static class PEGRatioEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public PEGRatioEntity() {
                    }

                    protected PEGRatioEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<PEGRatioEntity> CREATOR = new Creator<PEGRatioEntity>() {
                        public PEGRatioEntity createFromParcel(Parcel source) {
                            return new PEGRatioEntity(source);
                        }

                        public PEGRatioEntity[] newArray(int size) {
                            return new PEGRatioEntity[size];
                        }
                    };
                }

                public static class PriceToSalesEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public PriceToSalesEntity() {
                    }

                    protected PriceToSalesEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<PriceToSalesEntity> CREATOR = new Creator<PriceToSalesEntity>() {
                        public PriceToSalesEntity createFromParcel(Parcel source) {
                            return new PriceToSalesEntity(source);
                        }

                        public PriceToSalesEntity[] newArray(int size) {
                            return new PriceToSalesEntity[size];
                        }
                    };
                }

                public static class PriceToBookEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public PriceToBookEntity() {
                    }

                    protected PriceToBookEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<PriceToBookEntity> CREATOR = new Creator<PriceToBookEntity>() {
                        public PriceToBookEntity createFromParcel(Parcel source) {
                            return new PriceToBookEntity(source);
                        }

                        public PriceToBookEntity[] newArray(int size) {
                            return new PriceToBookEntity[size];
                        }
                    };
                }

                public static class EnterpriseValueToRevenueEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public EnterpriseValueToRevenueEntity() {
                    }

                    protected EnterpriseValueToRevenueEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<EnterpriseValueToRevenueEntity> CREATOR = new Creator<EnterpriseValueToRevenueEntity>() {
                        public EnterpriseValueToRevenueEntity createFromParcel(Parcel source) {
                            return new EnterpriseValueToRevenueEntity(source);
                        }

                        public EnterpriseValueToRevenueEntity[] newArray(int size) {
                            return new EnterpriseValueToRevenueEntity[size];
                        }
                    };
                }

                public static class EnterpriseValueToEBITDAEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public EnterpriseValueToEBITDAEntity() {
                    }

                    protected EnterpriseValueToEBITDAEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<EnterpriseValueToEBITDAEntity> CREATOR = new Creator<EnterpriseValueToEBITDAEntity>() {
                        public EnterpriseValueToEBITDAEntity createFromParcel(Parcel source) {
                            return new EnterpriseValueToEBITDAEntity(source);
                        }

                        public EnterpriseValueToEBITDAEntity[] newArray(int size) {
                            return new EnterpriseValueToEBITDAEntity[size];
                        }
                    };
                }

                public static class MostRecentQuarterEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public MostRecentQuarterEntity() {
                    }

                    protected MostRecentQuarterEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<MostRecentQuarterEntity> CREATOR = new Creator<MostRecentQuarterEntity>() {
                        public MostRecentQuarterEntity createFromParcel(Parcel source) {
                            return new MostRecentQuarterEntity(source);
                        }

                        public MostRecentQuarterEntity[] newArray(int size) {
                            return new MostRecentQuarterEntity[size];
                        }
                    };
                }

                public static class ProfitMarginEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public ProfitMarginEntity() {
                    }

                    protected ProfitMarginEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<ProfitMarginEntity> CREATOR = new Creator<ProfitMarginEntity>() {
                        public ProfitMarginEntity createFromParcel(Parcel source) {
                            return new ProfitMarginEntity(source);
                        }

                        public ProfitMarginEntity[] newArray(int size) {
                            return new ProfitMarginEntity[size];
                        }
                    };
                }

                public static class OperatingMarginEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public OperatingMarginEntity() {
                    }

                    protected OperatingMarginEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<OperatingMarginEntity> CREATOR = new Creator<OperatingMarginEntity>() {
                        public OperatingMarginEntity createFromParcel(Parcel source) {
                            return new OperatingMarginEntity(source);
                        }

                        public OperatingMarginEntity[] newArray(int size) {
                            return new OperatingMarginEntity[size];
                        }
                    };
                }

                public static class ReturnOnAssetsEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public ReturnOnAssetsEntity() {
                    }

                    protected ReturnOnAssetsEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<ReturnOnAssetsEntity> CREATOR = new Creator<ReturnOnAssetsEntity>() {
                        public ReturnOnAssetsEntity createFromParcel(Parcel source) {
                            return new ReturnOnAssetsEntity(source);
                        }

                        public ReturnOnAssetsEntity[] newArray(int size) {
                            return new ReturnOnAssetsEntity[size];
                        }
                    };
                }

                public static class ReturnOnEquityEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public ReturnOnEquityEntity() {
                    }

                    protected ReturnOnEquityEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<ReturnOnEquityEntity> CREATOR = new Creator<ReturnOnEquityEntity>() {
                        public ReturnOnEquityEntity createFromParcel(Parcel source) {
                            return new ReturnOnEquityEntity(source);
                        }

                        public ReturnOnEquityEntity[] newArray(int size) {
                            return new ReturnOnEquityEntity[size];
                        }
                    };
                }

                public static class RevenueEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public RevenueEntity() {
                    }

                    protected RevenueEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<RevenueEntity> CREATOR = new Creator<RevenueEntity>() {
                        public RevenueEntity createFromParcel(Parcel source) {
                            return new RevenueEntity(source);
                        }

                        public RevenueEntity[] newArray(int size) {
                            return new RevenueEntity[size];
                        }
                    };
                }

                public static class RevenuePerShareEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public RevenuePerShareEntity() {
                    }

                    protected RevenuePerShareEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<RevenuePerShareEntity> CREATOR = new Creator<RevenuePerShareEntity>() {
                        public RevenuePerShareEntity createFromParcel(Parcel source) {
                            return new RevenuePerShareEntity(source);
                        }

                        public RevenuePerShareEntity[] newArray(int size) {
                            return new RevenuePerShareEntity[size];
                        }
                    };
                }

                public static class QtrlyRevenueGrowthEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public QtrlyRevenueGrowthEntity() {
                    }

                    protected QtrlyRevenueGrowthEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<QtrlyRevenueGrowthEntity> CREATOR = new Creator<QtrlyRevenueGrowthEntity>() {
                        public QtrlyRevenueGrowthEntity createFromParcel(Parcel source) {
                            return new QtrlyRevenueGrowthEntity(source);
                        }

                        public QtrlyRevenueGrowthEntity[] newArray(int size) {
                            return new QtrlyRevenueGrowthEntity[size];
                        }
                    };
                }

                public static class GrossProfitEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public GrossProfitEntity() {
                    }

                    protected GrossProfitEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient  Creator<GrossProfitEntity> CREATOR = new Creator<GrossProfitEntity>() {
                        public GrossProfitEntity createFromParcel(Parcel source) {
                            return new GrossProfitEntity(source);
                        }

                        public GrossProfitEntity[] newArray(int size) {
                            return new GrossProfitEntity[size];
                        }
                    };
                }

                public static class EBITDAEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public EBITDAEntity() {
                    }

                    protected EBITDAEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<EBITDAEntity> CREATOR = new Creator<EBITDAEntity>() {
                        public EBITDAEntity createFromParcel(Parcel source) {
                            return new EBITDAEntity(source);
                        }

                        public EBITDAEntity[] newArray(int size) {
                            return new EBITDAEntity[size];
                        }
                    };
                }

                public static class NetIncomeAvlToCommonEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public NetIncomeAvlToCommonEntity() {
                    }

                    protected NetIncomeAvlToCommonEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<NetIncomeAvlToCommonEntity> CREATOR = new Creator<NetIncomeAvlToCommonEntity>() {
                        public NetIncomeAvlToCommonEntity createFromParcel(Parcel source) {
                            return new NetIncomeAvlToCommonEntity(source);
                        }

                        public NetIncomeAvlToCommonEntity[] newArray(int size) {
                            return new NetIncomeAvlToCommonEntity[size];
                        }
                    };
                }

                public static class DilutedEPSEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public DilutedEPSEntity() {
                    }

                    protected DilutedEPSEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<DilutedEPSEntity> CREATOR = new Creator<DilutedEPSEntity>() {
                        public DilutedEPSEntity createFromParcel(Parcel source) {
                            return new DilutedEPSEntity(source);
                        }

                        public DilutedEPSEntity[] newArray(int size) {
                            return new DilutedEPSEntity[size];
                        }
                    };
                }

                public static class QtrlyEarningsGrowthEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public QtrlyEarningsGrowthEntity() {
                    }

                    protected QtrlyEarningsGrowthEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<QtrlyEarningsGrowthEntity> CREATOR = new Creator<QtrlyEarningsGrowthEntity>() {
                        public QtrlyEarningsGrowthEntity createFromParcel(Parcel source) {
                            return new QtrlyEarningsGrowthEntity(source);
                        }

                        public QtrlyEarningsGrowthEntity[] newArray(int size) {
                            return new QtrlyEarningsGrowthEntity[size];
                        }
                    };
                }

                public static class TotalCashEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public TotalCashEntity() {
                    }

                    protected TotalCashEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<TotalCashEntity> CREATOR = new Creator<TotalCashEntity>() {
                        public TotalCashEntity createFromParcel(Parcel source) {
                            return new TotalCashEntity(source);
                        }

                        public TotalCashEntity[] newArray(int size) {
                            return new TotalCashEntity[size];
                        }
                    };
                }

                public static class TotalCashPerShareEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public TotalCashPerShareEntity() {
                    }

                    protected TotalCashPerShareEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<TotalCashPerShareEntity> CREATOR = new Creator<TotalCashPerShareEntity>() {
                        public TotalCashPerShareEntity createFromParcel(Parcel source) {
                            return new TotalCashPerShareEntity(source);
                        }

                        public TotalCashPerShareEntity[] newArray(int size) {
                            return new TotalCashPerShareEntity[size];
                        }
                    };
                }

                public static class TotalDebtEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public TotalDebtEntity() {
                    }

                    protected TotalDebtEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<TotalDebtEntity> CREATOR = new Creator<TotalDebtEntity>() {
                        public TotalDebtEntity createFromParcel(Parcel source) {
                            return new TotalDebtEntity(source);
                        }

                        public TotalDebtEntity[] newArray(int size) {
                            return new TotalDebtEntity[size];
                        }
                    };
                }

                public static class TotalDebtToEquityEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public TotalDebtToEquityEntity() {
                    }

                    protected TotalDebtToEquityEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<TotalDebtToEquityEntity> CREATOR = new Creator<TotalDebtToEquityEntity>() {
                        public TotalDebtToEquityEntity createFromParcel(Parcel source) {
                            return new TotalDebtToEquityEntity(source);
                        }

                        public TotalDebtToEquityEntity[] newArray(int size) {
                            return new TotalDebtToEquityEntity[size];
                        }
                    };
                }

                public static class CurrentRatioEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public CurrentRatioEntity() {
                    }

                    protected CurrentRatioEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<CurrentRatioEntity> CREATOR = new Creator<CurrentRatioEntity>() {
                        public CurrentRatioEntity createFromParcel(Parcel source) {
                            return new CurrentRatioEntity(source);
                        }

                        public CurrentRatioEntity[] newArray(int size) {
                            return new CurrentRatioEntity[size];
                        }
                    };
                }

                public static class BookValuePerShareEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public BookValuePerShareEntity() {
                    }

                    protected BookValuePerShareEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<BookValuePerShareEntity> CREATOR = new Creator<BookValuePerShareEntity>() {
                        public BookValuePerShareEntity createFromParcel(Parcel source) {
                            return new BookValuePerShareEntity(source);
                        }

                        public BookValuePerShareEntity[] newArray(int size) {
                            return new BookValuePerShareEntity[size];
                        }
                    };
                }

                public static class OperatingCashFlowEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public OperatingCashFlowEntity() {
                    }

                    protected OperatingCashFlowEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<OperatingCashFlowEntity> CREATOR = new Creator<OperatingCashFlowEntity>() {
                        public OperatingCashFlowEntity createFromParcel(Parcel source) {
                            return new OperatingCashFlowEntity(source);
                        }

                        public OperatingCashFlowEntity[] newArray(int size) {
                            return new OperatingCashFlowEntity[size];
                        }
                    };
                }

                public static class LeveredFreeCashFlowEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public LeveredFreeCashFlowEntity() {
                    }

                    protected LeveredFreeCashFlowEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<LeveredFreeCashFlowEntity> CREATOR = new Creator<LeveredFreeCashFlowEntity>() {
                        public LeveredFreeCashFlowEntity createFromParcel(Parcel source) {
                            return new LeveredFreeCashFlowEntity(source);
                        }

                        public LeveredFreeCashFlowEntity[] newArray(int size) {
                            return new LeveredFreeCashFlowEntity[size];
                        }
                    };
                }

                public static class P52WeekHighEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public P52WeekHighEntity() {
                    }

                    protected P52WeekHighEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<P52WeekHighEntity> CREATOR = new Creator<P52WeekHighEntity>() {
                        public P52WeekHighEntity createFromParcel(Parcel source) {
                            return new P52WeekHighEntity(source);
                        }

                        public P52WeekHighEntity[] newArray(int size) {
                            return new P52WeekHighEntity[size];
                        }
                    };
                }

                public static class P52WeekLowEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public P52WeekLowEntity() {
                    }

                    protected P52WeekLowEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<P52WeekLowEntity> CREATOR = new Creator<P52WeekLowEntity>() {
                        public P52WeekLowEntity createFromParcel(Parcel source) {
                            return new P52WeekLowEntity(source);
                        }

                        public P52WeekLowEntity[] newArray(int size) {
                            return new P52WeekLowEntity[size];
                        }
                    };
                }

                public static class ShortRatioEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public ShortRatioEntity() {
                    }

                    protected ShortRatioEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<ShortRatioEntity> CREATOR = new Creator<ShortRatioEntity>() {
                        public ShortRatioEntity createFromParcel(Parcel source) {
                            return new ShortRatioEntity(source);
                        }

                        public ShortRatioEntity[] newArray(int size) {
                            return new ShortRatioEntity[size];
                        }
                    };
                }

                public static class ShortPctOfFloatEntity implements Parcelable {
                    private String term;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public String getTerm() {
                        return term;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                    }

                    public ShortPctOfFloatEntity() {
                    }

                    protected ShortPctOfFloatEntity(Parcel in) {
                        this.term = in.readString();
                    }

                    public static final transient Creator<ShortPctOfFloatEntity> CREATOR = new Creator<ShortPctOfFloatEntity>() {
                        public ShortPctOfFloatEntity createFromParcel(Parcel source) {
                            return new ShortPctOfFloatEntity(source);
                        }

                        public ShortPctOfFloatEntity[] newArray(int size) {
                            return new ShortPctOfFloatEntity[size];
                        }
                    };
                }

                public static class LastSplitFactorEntity {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }
                }

                public static class AvgVolEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public AvgVolEntity() {
                    }

                    protected AvgVolEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<AvgVolEntity> CREATOR = new Creator<AvgVolEntity>() {
                        public AvgVolEntity createFromParcel(Parcel source) {
                            return new AvgVolEntity(source);
                        }

                        public AvgVolEntity[] newArray(int size) {
                            return new AvgVolEntity[size];
                        }
                    };
                }

                public static class SharesShortEntity implements Parcelable {
                    private String term;
                    private String content;

                    public void setTerm(String term) {
                        this.term = term;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getTerm() {
                        return term;
                    }

                    public String getContent() {
                        return content;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.term);
                        dest.writeString(this.content);
                    }

                    public SharesShortEntity() {
                    }

                    protected SharesShortEntity(Parcel in) {
                        this.term = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<SharesShortEntity> CREATOR = new Creator<SharesShortEntity>() {
                        public SharesShortEntity createFromParcel(Parcel source) {
                            return new SharesShortEntity(source);
                        }

                        public SharesShortEntity[] newArray(int size) {
                            return new SharesShortEntity[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.symbol);
                    dest.writeParcelable(this.MarketCap, flags);
                    dest.writeParcelable(this.EnterpriseValue, flags);
                    dest.writeParcelable(this.TrailingPE, flags);
                    dest.writeParcelable(this.ForwardPE, flags);
                    dest.writeParcelable(this.PEGRatio, flags);
                    dest.writeParcelable(this.PriceToSales, flags);
                    dest.writeParcelable(this.PriceToBook, flags);
                    dest.writeParcelable(this.EnterpriseValueToRevenue, flags);
                    dest.writeParcelable(this.EnterpriseValueToEBITDA, flags);
                    dest.writeString(this.FiscalYearEnds);
                    dest.writeParcelable(this.ProfitMargin, flags);
                    dest.writeParcelable(this.MostRecentQuarter, flags);
                    dest.writeParcelable(this.OperatingMargin, flags);
                    dest.writeParcelable(this.ReturnOnAssets, flags);
                    dest.writeParcelable(this.ReturnOnEquity, flags);
                    dest.writeParcelable(this.Revenue, flags);
                    dest.writeParcelable(this.RevenuePerShare, flags);
                    dest.writeParcelable(this.QtrlyRevenueGrowth, flags);
                    dest.writeParcelable(this.GrossProfit, flags);
                    dest.writeParcelable(this.EBITDA, flags);
                    dest.writeParcelable(this.NetIncomeAvlToCommon, flags);
                    dest.writeParcelable(this.DilutedEPS, flags);
                    dest.writeParcelable(this.QtrlyEarningsGrowth, flags);
                    dest.writeParcelable(this.TotalCash, flags);
                    dest.writeParcelable(this.TotalCashPerShare, flags);
                    dest.writeParcelable(this.TotalDebt, flags);
                    dest.writeParcelable(this.TotalDebtToEquity, flags);
                    dest.writeParcelable(this.CurrentRatio, flags);
                    dest.writeParcelable(this.BookValuePerShare, flags);
                    dest.writeParcelable(this.OperatingCashFlow, flags);
                    dest.writeParcelable(this.LeveredFreeCashFlow, flags);
                    dest.writeString(this.Beta);
                    dest.writeString(this.P_52WeekChange);
                    dest.writeString(this.SAndP50052WeekChange);
                    dest.writeParcelable(this.P_52WeekHigh, flags);
                    dest.writeParcelable(this.P_52WeekLow, flags);
                    dest.writeString(this.P_50DayMovingAverage);
                    dest.writeString(this.P_200DayMovingAverage);
                    dest.writeString(this.SharesOutstanding);
                    dest.writeString(this.Float);
                    dest.writeString(this.PctHeldByInsiders);
                    dest.writeString(this.PctHeldByInstitutions);
                    dest.writeParcelable(this.ShortRatio, flags);
                    dest.writeParcelable(this.ShortPctOfFloat, flags);
                 //   dest.writeParcelable(this.ForwardAnnualDividendRate, flags);
                 //   dest.writeParcelable(this.ForwardAnnualDividendYield, flags);
                //    dest.writeParcelable(this.P_5YearAverageDividendYield, flags);
                //    dest.writeParcelable(this.PayoutRatio, flags);
                //    dest.writeParcelable(this.DividendDate, flags);
                 //   dest.writeParcelable(this.ExDividendDate, flags);
                 //   dest.writeParcelable(this.LastSplitFactor, flags);
                    dest.writeString(this.LastSplitDate);
                    dest.writeList(this.AvgVol);
                    dest.writeList(this.SharesShort);
                    dest.writeList(this.TrailingAnnualDividendYield);
                }

                public StatsEntity() {
                }

                protected StatsEntity(Parcel in) {
                    this.symbol = in.readString();
                    this.MarketCap = in.readParcelable(MarketCapEntity.class.getClassLoader());
                    this.EnterpriseValue = in.readParcelable(EnterpriseValueEntity.class.getClassLoader());
                    this.TrailingPE = in.readParcelable(TrailingPEEntity.class.getClassLoader());
                    this.ForwardPE = in.readParcelable(ForwardPEEntity.class.getClassLoader());
                    this.PEGRatio = in.readParcelable(PEGRatioEntity.class.getClassLoader());
                    this.PriceToSales = in.readParcelable(PriceToSalesEntity.class.getClassLoader());
                    this.PriceToBook = in.readParcelable(PriceToBookEntity.class.getClassLoader());
                    this.EnterpriseValueToRevenue = in.readParcelable(EnterpriseValueToRevenueEntity.class.getClassLoader());
                    this.EnterpriseValueToEBITDA = in.readParcelable(EnterpriseValueToEBITDAEntity.class.getClassLoader());
                    this.FiscalYearEnds = in.readString();
                    this.ProfitMargin = in.readParcelable(ProfitMarginEntity.class.getClassLoader());
                    this.MostRecentQuarter = in.readParcelable(MostRecentQuarterEntity.class.getClassLoader());
                    this.OperatingMargin = in.readParcelable(OperatingMarginEntity.class.getClassLoader());
                    this.ReturnOnAssets = in.readParcelable(ReturnOnAssetsEntity.class.getClassLoader());
                    this.ReturnOnEquity = in.readParcelable(ReturnOnEquityEntity.class.getClassLoader());
                    this.Revenue = in.readParcelable(RevenueEntity.class.getClassLoader());
                    this.RevenuePerShare = in.readParcelable(RevenuePerShareEntity.class.getClassLoader());
                    this.QtrlyRevenueGrowth = in.readParcelable(QtrlyRevenueGrowthEntity.class.getClassLoader());
                    this.GrossProfit = in.readParcelable(GrossProfitEntity.class.getClassLoader());
                    this.EBITDA = in.readParcelable(EBITDAEntity.class.getClassLoader());
                    this.NetIncomeAvlToCommon = in.readParcelable(NetIncomeAvlToCommonEntity.class.getClassLoader());
                    this.DilutedEPS = in.readParcelable(DilutedEPSEntity.class.getClassLoader());
                    this.QtrlyEarningsGrowth = in.readParcelable(QtrlyEarningsGrowthEntity.class.getClassLoader());
                    this.TotalCash = in.readParcelable(TotalCashEntity.class.getClassLoader());
                    this.TotalCashPerShare = in.readParcelable(TotalCashPerShareEntity.class.getClassLoader());
                    this.TotalDebt = in.readParcelable(TotalDebtEntity.class.getClassLoader());
                    this.TotalDebtToEquity = in.readParcelable(TotalDebtToEquityEntity.class.getClassLoader());
                    this.CurrentRatio = in.readParcelable(CurrentRatioEntity.class.getClassLoader());
                    this.BookValuePerShare = in.readParcelable(BookValuePerShareEntity.class.getClassLoader());
                    this.OperatingCashFlow = in.readParcelable(OperatingCashFlowEntity.class.getClassLoader());
                    this.LeveredFreeCashFlow = in.readParcelable(LeveredFreeCashFlowEntity.class.getClassLoader());
                    this.Beta = in.readString();
                    this.P_52WeekChange = in.readString();
                    this.SAndP50052WeekChange = in.readString();
                    this.P_52WeekHigh = in.readParcelable(P52WeekHighEntity.class.getClassLoader());
                    this.P_52WeekLow = in.readParcelable(P52WeekLowEntity.class.getClassLoader());
                    this.P_50DayMovingAverage = in.readString();
                    this.P_200DayMovingAverage = in.readString();
                    this.SharesOutstanding = in.readString();
                    this.Float = in.readString();
                    this.PctHeldByInsiders = in.readString();
                    this.PctHeldByInstitutions = in.readString();
                    this.ShortRatio = in.readParcelable(ShortRatioEntity.class.getClassLoader());
                    this.ShortPctOfFloat = in.readParcelable(ShortPctOfFloatEntity.class.getClassLoader());
                  //  this.ForwardAnnualDividendRate = in.readParcelable(Object.class.getClassLoader());
                  //  this.ForwardAnnualDividendYield = in.readParcelable(Object.class.getClassLoader());
                  //  this.P_5YearAverageDividendYield = in.readParcelable(Object.class.getClassLoader());
                  //  this.PayoutRatio = in.readParcelable(Object.class.getClassLoader());
                  //  this.DividendDate = in.readParcelable(Object.class.getClassLoader());
                  //  this.ExDividendDate = in.readParcelable(Object.class.getClassLoader());
                  //  this.LastSplitFactor = in.readParcelable(LastSplitFactorEntity.class.getClassLoader());
                  //  this.LastSplitDate = in.readString();
                    this.AvgVol = new ArrayList<AvgVolEntity>();
                    in.readList(this.AvgVol, List.class.getClassLoader());
                    this.SharesShort = new ArrayList<SharesShortEntity>();
                    in.readList(this.SharesShort, List.class.getClassLoader());
                //    this.TrailingAnnualDividendYield = new ArrayList<?>();
                //    in.readList(this.TrailingAnnualDividendYield, List.class.getClassLoader());
                }

                public static final transient Parcelable.Creator<StatsEntity> CREATOR = new Parcelable.Creator<StatsEntity>() {
                    public StatsEntity createFromParcel(Parcel source) {
                        return new StatsEntity(source);
                    }

                    public StatsEntity[] newArray(int size) {
                        return new StatsEntity[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.stats, 0);
            }

            public ResultsEntity() {
            }

            protected ResultsEntity(Parcel in) {
                this.stats = in.readParcelable(StatsEntity.class.getClassLoader());
            }

            public static final transient Creator<ResultsEntity> CREATOR = new Creator<ResultsEntity>() {
                public ResultsEntity createFromParcel(Parcel source) {
                    return new ResultsEntity(source);
                }

                public ResultsEntity[] newArray(int size) {
                    return new ResultsEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.count);
            dest.writeString(this.created);
            dest.writeString(this.lang);
            dest.writeParcelable(this.results, flags);
        }

        public QueryEntity() {
        }

        protected QueryEntity(Parcel in) {
            this.count = in.readInt();
            this.created = in.readString();
            this.lang = in.readString();
            this.results = in.readParcelable(ResultsEntity.class.getClassLoader());
        }

        public static final transient Creator<QueryEntity> CREATOR = new Creator<QueryEntity>() {
            public QueryEntity createFromParcel(Parcel source) {
                return new QueryEntity(source);
            }

            public QueryEntity[] newArray(int size) {
                return new QueryEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.query, flags);
    }

    public StockStatsResponse() {
    }

    protected StockStatsResponse(Parcel in) {
        this.query = in.readParcelable(QueryEntity.class.getClassLoader());
    }

    public static final transient Parcelable.Creator<StockStatsResponse> CREATOR = new Parcelable.Creator<StockStatsResponse>() {
        public StockStatsResponse createFromParcel(Parcel source) {
            return new StockStatsResponse(source);
        }

        public StockStatsResponse[] newArray(int size) {
            return new StockStatsResponse[size];
        }
    };
}
