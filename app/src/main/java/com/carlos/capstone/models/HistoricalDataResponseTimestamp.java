package com.carlos.capstone.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 22/12/2015.
 */
public class HistoricalDataResponseTimestamp {


    /**
     * uri : /instrument/1.0/goog/chartdata;type=quote;range=1d/json
     * ticker : goog
     * Company-Name : Alphabet Inc.
     * Exchange-Name : NMS
     * unit : MIN
     * timezone : EST
     * currency : USD
     * gmtoffset : -18000
     * previous_close : 747.77
     */

    private MetaEntity meta;
    /**
     * min : 1450794600
     * max : 1450818000
     */

    private TimestampEntity Timestamp;
    /**
     * close : {"min":748.175,"max":754.85}
     * high : {"min":748.28,"max":754.85}
     * low : {"min":747.7175,"max":754.44}
     * open : {"min":747.78,"max":754.655}
     * volume : {"min":0,"max":45900}
     */

    private RangesEntity ranges;
    /**
     * meta : {"uri":"/instrument/1.0/goog/chartdata;type=quote;range=1d/json","ticker":"goog","Company-Name":"Alphabet Inc.","Exchange-Name":"NMS","unit":"MIN","timezone":"EST","currency":"USD","gmtoffset":-18000,"previous_close":747.77}
     * Timestamp : {"min":1450794600,"max":1450818000}
     * labels : [1450796400,1450800000,1450803600,1450807200,1450810800,1450814400,1450818000]
     * ranges : {"close":{"min":748.175,"max":754.85},"high":{"min":748.28,"max":754.85},"low":{"min":747.7175,"max":754.44},"open":{"min":747.78,"max":754.655},"volume":{"min":0,"max":45900}}
     * series : [{"Timestamp":1450794655,"close":751.88,"high":751.99,"low":751.145,"open":751.55,"volume":45900},{"Timestamp":1450794663,"close":751,"high":751.635,"low":750.88,"open":751.56,"volume":13900},{"Timestamp":1450794730,"close":751.556,"high":751.68,"low":751,"open":751.57,"volume":8400},{"Timestamp":1450794788,"close":752.08,"high":752.45,"low":751.4,"open":751.4,"volume":5000},{"Timestamp":1450794844,"close":751.6,"high":752.3668,"low":751.51,"open":752.1,"volume":9500},{"Timestamp":1450794900,"close":751.49,"high":752.2,"low":750.88,"open":752,"volume":8200},{"Timestamp":1450795006,"close":750.1515,"high":750.66,"low":750.08,"open":750.66,"volume":1300},{"Timestamp":1450795020,"close":750.49,"high":751.01,"low":750,"open":750,"volume":4800},{"Timestamp":1450795091,"close":750.71,"high":750.93,"low":749.9,"open":750.81,"volume":6200},{"Timestamp":1450795145,"close":750.36,"high":750.6,"low":749.58,"open":750.6,"volume":3200},{"Timestamp":1450795235,"close":750.48,"high":750.86,"low":750.47,"open":750.47,"volume":3400},{"Timestamp":1450795260,"close":750.4,"high":750.75,"low":750.03,"open":750.35,"volume":1700},{"Timestamp":1450795321,"close":748.88,"high":750.59,"low":748.88,"open":750.2,"volume":4700},{"Timestamp":1450795381,"close":748.8017,"high":748.9648,"low":748.8017,"open":748.92,"volume":800},{"Timestamp":1450795441,"close":749,"high":749,"low":748.01,"open":748.5,"volume":8300},{"Timestamp":1450795506,"close":749.0399,"high":749.04,"low":748.6075,"open":749,"volume":6100},{"Timestamp":1450795561,"close":749.01,"high":749.05,"low":748.617,"open":748.617,"volume":2900},{"Timestamp":1450795621,"close":748.5625,"high":749.21,"low":748.43,"open":748.95,"volume":6000},{"Timestamp":1450795684,"close":748.6749,"high":748.92,"low":748.13,"open":748.6208,"volume":4600},{"Timestamp":1450795758,"close":748.7,"high":749.04,"low":748.4,"open":748.6,"volume":3700},{"Timestamp":1450795801,"close":748.175,"high":748.65,"low":747.94,"open":748.52,"volume":2600},{"Timestamp":1450795878,"close":748.28,"high":748.28,"low":748.03,"open":748.12,"volume":900},{"Timestamp":1450795929,"close":748.54,"high":749.27,"low":747.7175,"open":747.78,"volume":5400},{"Timestamp":1450795981,"close":748.758,"high":748.758,"low":748.33,"open":748.62,"volume":1700},{"Timestamp":1450796060,"close":750.24,"high":750.24,"low":749.002,"open":749.16,"volume":4300},{"Timestamp":1450796105,"close":750.32,"high":751,"low":749.83,"open":750.28,"volume":11000},{"Timestamp":1450796167,"close":750.7,"high":750.7,"low":750.041,"open":750.47,"volume":1200},{"Timestamp":1450796224,"close":750.68,"high":750.68,"low":750.15,"open":750.16,"volume":2000},{"Timestamp":1450796281,"close":750.47,"high":750.7,"low":750.34,"open":750.7,"volume":800},{"Timestamp":1450796351,"close":750.23,"high":750.7,"low":750.1801,"open":750.1801,"volume":3500},{"Timestamp":1450796453,"close":749.74,"high":750.5392,"low":749.32,"open":750.32,"volume":3900},{"Timestamp":1450796463,"close":749.73,"high":749.85,"low":749.27,"open":749.57,"volume":3400},{"Timestamp":1450796570,"close":750.46,"high":750.46,"low":750.46,"open":750.46,"volume":400},{"Timestamp":1450796589,"close":750.205,"high":750.49,"low":750.04,"open":750.48,"volume":4300},{"Timestamp":1450796656,"close":749.85,"high":749.85,"low":749.3,"open":749.82,"volume":4800},{"Timestamp":1450796700,"close":749.88,"high":750.37,"low":749.74,"open":749.81,"volume":3100},{"Timestamp":1450796769,"close":749.25,"high":750.075,"low":748.92,"open":750.0625,"volume":4200},{"Timestamp":1450796834,"close":749.62,"high":749.78,"low":749.3338,"open":749.3338,"volume":1600},{"Timestamp":1450796883,"close":749.01,"high":749.376,"low":748.52,"open":749.12,"volume":3100},{"Timestamp":1450796949,"close":749.28,"high":749.28,"low":748.694,"open":748.9,"volume":2900},{"Timestamp":1450797003,"close":749.57,"high":749.81,"low":749.2,"open":749.28,"volume":5500},{"Timestamp":1450797119,"close":749.653,"high":750.1,"low":749.37,"open":749.51,"volume":3800},{"Timestamp":1450797179,"close":749.86,"high":750.4,"low":749.72,"open":749.85,"volume":3900},{"Timestamp":1450797180,"close":749.92,"high":750.06,"low":749.82,"open":749.87,"volume":3600},{"Timestamp":1450797262,"close":749.56,"high":750,"low":749.5,"open":750,"volume":2200},{"Timestamp":1450797319,"close":749.34,"high":749.55,"low":748.6,"open":749.5,"volume":5500},{"Timestamp":1450797419,"close":748.71,"high":749.267,"low":748.61,"open":749.267,"volume":3600},{"Timestamp":1450797424,"close":749.42,"high":749.42,"low":748.5,"open":748.97,"volume":3600},{"Timestamp":1450797487,"close":749.32,"high":749.32,"low":748.6736,"open":749.18,"volume":5300},{"Timestamp":1450797545,"close":748.925,"high":749.4,"low":748.5501,"open":749.4,"volume":1900},{"Timestamp":1450797605,"close":749.78,"high":749.97,"low":748.67,"open":748.85,"volume":4000},{"Timestamp":1450797670,"close":750.31,"high":750.76,"low":749.67,"open":749.68,"volume":3400},{"Timestamp":1450797746,"close":752,"high":752,"low":750.415,"open":750.415,"volume":9200},{"Timestamp":1450797781,"close":752.0035,"high":752.44,"low":751.83,"open":752,"volume":10500},{"Timestamp":1450797843,"close":753.53,"high":753.54,"low":752.01,"open":752.01,"volume":8000},{"Timestamp":1450797900,"close":752.7085,"high":753.5,"low":752.55,"open":753.42,"volume":6600},{"Timestamp":1450797977,"close":753.77,"high":753.77,"low":752.77,"open":753.204,"volume":6500},{"Timestamp":1450798020,"close":754,"high":754.11,"low":753.77,"open":753.77,"volume":3700},{"Timestamp":1450798090,"close":753.8,"high":754,"low":753.2301,"open":753.59,"volume":3500},{"Timestamp":1450798148,"close":753.16,"high":753.78,"low":753.1223,"open":753.6975,"volume":1500},{"Timestamp":1450798207,"close":753.8,"high":753.8,"low":753.08,"open":753.15,"volume":3700},{"Timestamp":1450798261,"close":754.09,"high":754.2,"low":753.6,"open":753.96,"volume":8000},{"Timestamp":1450798331,"close":754.07,"high":754.07,"low":753.55,"open":753.99,"volume":6000},{"Timestamp":1450798386,"close":754.15,"high":754.15,"low":753.8652,"open":754.01,"volume":4600},{"Timestamp":1450798451,"close":754.25,"high":754.49,"low":754.1441,"open":754.1441,"volume":1500},{"Timestamp":1450798505,"close":754.5442,"high":754.78,"low":754.035,"open":754.035,"volume":6400},{"Timestamp":1450798560,"close":754.85,"high":754.85,"low":754.44,"open":754.47,"volume":5800},{"Timestamp":1450798623,"close":754,"high":754.6899,"low":754,"open":754.45,"volume":1400},{"Timestamp":1450798680,"close":753.5301,"high":753.9999,"low":753.24,"open":753.6788,"volume":5100},{"Timestamp":1450798744,"close":754.185,"high":754.43,"low":753.6975,"open":753.7075,"volume":3300},{"Timestamp":1450798807,"close":754.3302,"high":754.675,"low":754.02,"open":754.24,"volume":7000},{"Timestamp":1450798869,"close":754.34,"high":754.655,"low":754.34,"open":754.655,"volume":400},{"Timestamp":1450798940,"close":754.61,"high":754.61,"low":754.11,"open":754.44,"volume":2500},{"Timestamp":1450799003,"close":754.14,"high":754.61,"low":753.84,"open":754.61,"volume":2900},{"Timestamp":1450799047,"close":753.57,"high":754.11,"low":753.102,"open":753.8401,"volume":6800},{"Timestamp":1450799106,"close":753.397,"high":753.8604,"low":753.397,"open":753.57,"volume":1200},{"Timestamp":1450799166,"close":753.3,"high":753.664,"low":753.01,"open":753.61,"volume":3000},{"Timestamp":1450799237,"close":753.6,"high":753.75,"low":752.662,"open":753.15,"volume":2800},{"Timestamp":1450799282,"close":753.14,"high":753.3,"low":752.895,"open":753.22,"volume":2700},{"Timestamp":1450799346,"close":753.124,"high":753.68,"low":752.87,"open":753.2,"volume":26300},{"Timestamp":1450799409,"close":753.4123,"high":753.65,"low":753,"open":753.133,"volume":3800},{"Timestamp":1450799513,"close":752.661,"high":753.025,"low":752.66,"open":753,"volume":2700},{"Timestamp":1450799520,"close":752.72,"high":752.9356,"low":752.72,"open":752.925,"volume":900},{"Timestamp":1450799583,"close":752.36,"high":752.8432,"low":752.36,"open":752.81,"volume":1800},{"Timestamp":1450799643,"close":752.78,"high":752.94,"low":752.645,"open":752.94,"volume":2400},{"Timestamp":1450799712,"close":752.9,"high":753.1,"low":752.4141,"open":752.87,"volume":5500},{"Timestamp":1450799786,"close":752.8884,"high":752.8884,"low":752.67,"open":752.67,"volume":700},{"Timestamp":1450799830,"close":751.75,"high":752.6992,"low":751.75,"open":752.63,"volume":4500},{"Timestamp":1450799882,"close":751.0052,"high":751.62,"low":751.0052,"open":751.62,"volume":3900},{"Timestamp":1450799967,"close":751.34,"high":751.55,"low":751.1,"open":751.4,"volume":3700},{"Timestamp":1450800006,"close":751.8,"high":751.87,"low":751.34,"open":751.34,"volume":3200},{"Timestamp":1450800061,"close":751.72,"high":751.72,"low":751.6,"open":751.65,"volume":1100},{"Timestamp":1450800176,"close":750.55,"high":751.3,"low":750.55,"open":751.28,"volume":2600},{"Timestamp":1450800183,"close":750.36,"high":750.861,"low":750.28,"open":750.705,"volume":3100},{"Timestamp":1450800243,"close":750.785,"high":750.785,"low":750.4,"open":750.4,"volume":1400},{"Timestamp":1450800335,"close":750.91,"high":750.91,"low":750.845,"open":750.845,"volume":200},{"Timestamp":1450800361,"close":750.9,"high":751.2,"low":750.84,"open":751.09,"volume":4300},{"Timestamp":1450800456,"close":751.12,"high":751.15,"low":750.98,"open":751.0561,"volume":0},{"Timestamp":1450800493,"close":751.175,"high":751.25,"low":750.82,"open":750.85,"volume":4400},{"Timestamp":1450800561,"close":751.12,"high":751.2399,"low":750.68,"open":750.96,"volume":4400},{"Timestamp":1450800622,"close":751.0176,"high":751.05,"low":750.91,"open":750.93,"volume":0},{"Timestamp":1450800682,"close":750.7325,"high":751.11,"low":750.715,"open":751.11,"volume":2600},{"Timestamp":1450800732,"close":751.415,"high":751.415,"low":750.65,"open":750.68,"volume":2800},{"Timestamp":1450800783,"close":751.6225,"high":751.64,"low":751.3,"open":751.356,"volume":3000},{"Timestamp":1450800844,"close":751.16,"high":751.635,"low":751.16,"open":751.61,"volume":7800},{"Timestamp":1450800902,"close":750.6,"high":751,"low":750.6,"open":751,"volume":1900},{"Timestamp":1450801019,"close":750.4101,"high":750.63,"low":750.4101,"open":750.46,"volume":1900},{"Timestamp":1450801020,"close":750.25,"high":750.7,"low":750.25,"open":750.51,"volume":1800},{"Timestamp":1450801090,"close":750.74,"high":750.74,"low":750.32,"open":750.56,"volume":2300},{"Timestamp":1450801141,"close":750.5728,"high":751.0599,"low":750.5728,"open":751.0599,"volume":900},{"Timestamp":1450801201,"close":750.4549,"high":750.5,"low":750.315,"open":750.5,"volume":900},{"Timestamp":1450801264,"close":750.215,"high":750.32,"low":750,"open":750.23,"volume":5600},{"Timestamp":1450801331,"close":750.47,"high":750.47,"low":750.07,"open":750.07,"volume":800},{"Timestamp":1450801382,"close":749.99,"high":750.22,"low":749.96,"open":750.22,"volume":1100},{"Timestamp":1450801456,"close":749.94,"high":750.1,"low":749.9,"open":750.01,"volume":1000},{"Timestamp":1450801505,"close":749.71,"high":750,"low":749.5,"open":749.87,"volume":4500},{"Timestamp":1450801646,"close":750.0325,"high":750.1524,"low":750.0325,"open":750.1524,"volume":300},{"Timestamp":1450801734,"close":749.91,"high":750.15,"low":749.91,"open":750.06,"volume":1300},{"Timestamp":1450801752,"close":750.106,"high":750.106,"low":749.94,"open":749.94,"volume":700},{"Timestamp":1450801821,"close":750.055,"high":750.219,"low":750.055,"open":750.06,"volume":500},{"Timestamp":1450801867,"close":750.26,"high":750.3,"low":750.06,"open":750.06,"volume":2400},{"Timestamp":1450801924,"close":749.7,"high":750.2099,"low":749.7,"open":750,"volume":3100},{"Timestamp":1450802001,"close":749.75,"high":749.75,"low":749.75,"open":749.75,"volume":600},{"Timestamp":1450802070,"close":749.74,"high":750.24,"low":749.74,"open":750.24,"volume":1500},{"Timestamp":1450802101,"close":750.55,"high":750.55,"low":750.07,"open":750.0989,"volume":1000},{"Timestamp":1450802162,"close":749.8825,"high":750.415,"low":749.74,"open":750.16,"volume":2700},{"Timestamp":1450802251,"close":750.045,"high":750.1,"low":749.63,"open":749.65,"volume":3600},{"Timestamp":1450802286,"close":750.382,"high":750.65,"low":749.79,"open":750.04,"volume":5700},{"Timestamp":1450802399,"close":751.146,"high":751.146,"low":750.09,"open":750.73,"volume":7300},{"Timestamp":1450802400,"close":750.325,"high":751,"low":750.12,"open":750.69,"volume":4400},{"Timestamp":1450802471,"close":750.55,"high":750.8,"low":750.01,"open":750.4,"volume":3700},{"Timestamp":1450802527,"close":750.48,"high":751.36,"low":750.48,"open":750.73,"volume":2900},{"Timestamp":1450802585,"close":750.34,"high":751.08,"low":750.09,"open":750.8,"volume":4400},{"Timestamp":1450802640,"close":750.2673,"high":750.62,"low":750,"open":750.62,"volume":3600},{"Timestamp":1450802756,"close":750.52,"high":750.55,"low":750.4,"open":750.4,"volume":800},{"Timestamp":1450802761,"close":750.765,"high":750.88,"low":750.5,"open":750.62,"volume":3600},{"Timestamp":1450802820,"close":750.8712,"high":751.22,"low":750.32,"open":750.665,"volume":12400},{"Timestamp":1450802880,"close":750.71,"high":750.71,"low":750.71,"open":750.71,"volume":100},{"Timestamp":1450802938,"close":750.6701,"high":750.71,"low":750.06,"open":750.71,"volume":17300},{"Timestamp":1450802992,"close":750.5,"high":751.2,"low":750.3,"open":750.8199,"volume":5900},{"Timestamp":1450803056,"close":750.64,"high":750.89,"low":750.64,"open":750.71,"volume":9100},{"Timestamp":1450803077,"close":750.6,"high":750.75,"low":750.6,"open":750.7201,"volume":1700},{"Timestamp":1450803162,"close":750.31,"high":750.8799,"low":750.31,"open":750.47,"volume":1200},{"Timestamp":1450803225,"close":749.92,"high":750.362,"low":749.92,"open":750.281,"volume":3800},{"Timestamp":1450803298,"close":749.91,"high":750.005,"low":749.91,"open":749.9844,"volume":2900},{"Timestamp":1450803319,"close":750.07,"high":750.3224,"low":750.07,"open":750.3224,"volume":1000},{"Timestamp":1450803416,"close":749.9,"high":750.145,"low":749.65,"open":750.07,"volume":3200},{"Timestamp":1450803462,"close":749.99,"high":749.99,"low":749.99,"open":749.99,"volume":100},{"Timestamp":1450803577,"close":749.91,"high":749.92,"low":749.82,"open":749.82,"volume":1600},{"Timestamp":1450803638,"close":749.948,"high":750.1,"low":749.948,"open":750.1,"volume":1000},{"Timestamp":1450803697,"close":749.71,"high":749.75,"low":749.71,"open":749.75,"volume":1500},{"Timestamp":1450803772,"close":748.951,"high":749.14,"low":748.951,"open":749.14,"volume":3600},{"Timestamp":1450803818,"close":748.64,"high":748.64,"low":748.64,"open":748.64,"volume":800}]
     */

    private List<Integer> labels;
    /**
     * Timestamp : 1450794655
     * close : 751.88
     * high : 751.99
     * low : 751.145
     * open : 751.55
     * volume : 45900
     */

    private List<SeriesEntity> series;

    public void setMeta(MetaEntity meta) {
        this.meta = meta;
    }

    public void setTimestamp(TimestampEntity Timestamp) {
        this.Timestamp = Timestamp;
    }

    public void setRanges(RangesEntity ranges) {
        this.ranges = ranges;
    }

    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }

    public void setSeries(List<SeriesEntity> series) {
        this.series = series;
    }

    public MetaEntity getMeta() {
        return meta;
    }

    public TimestampEntity getTimestamp() {
        return Timestamp;
    }

    public RangesEntity getRanges() {
        return ranges;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public List<SeriesEntity> getSeries() {
        return series;
    }

    public static class MetaEntity {
        private String uri;
        private String ticker;
        @SerializedName("Company-Name")
        private String company_name;
        @SerializedName("Exchange-Name")
        private String exchange_name;
        private String unit;
        private String timezone;
        private String currency;
        private int gmtoffset;
        private double previous_close;

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getExchange_name() {
            return exchange_name;
        }

        public void setExchange_name(String exchange_name) {
            this.exchange_name = exchange_name;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setGmtoffset(int gmtoffset) {
            this.gmtoffset = gmtoffset;
        }

        public void setPrevious_close(double previous_close) {
            this.previous_close = previous_close;
        }

        public String getUri() {
            return uri;
        }

        public String getTicker() {
            return ticker;
        }

        public String getUnit() {
            return unit;
        }

        public String getTimezone() {
            return timezone;
        }

        public String getCurrency() {
            return currency;
        }

        public int getGmtoffset() {
            return gmtoffset;
        }

        public double getPrevious_close() {
            return previous_close;
        }
    }

    public static class TimestampEntity {
        private int min;
        private int max;

        public void setMin(int min) {
            this.min = min;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }

    public static class RangesEntity {
        /**
         * min : 748.175
         * max : 754.85
         */

        private CloseEntity close;
        /**
         * min : 748.28
         * max : 754.85
         */

        private HighEntity high;
        /**
         * min : 747.7175
         * max : 754.44
         */

        private LowEntity low;
        /**
         * min : 747.78
         * max : 754.655
         */

        private OpenEntity open;
        /**
         * min : 0
         * max : 45900
         */

        private VolumeEntity volume;

        public void setClose(CloseEntity close) {
            this.close = close;
        }

        public void setHigh(HighEntity high) {
            this.high = high;
        }

        public void setLow(LowEntity low) {
            this.low = low;
        }

        public void setOpen(OpenEntity open) {
            this.open = open;
        }

        public void setVolume(VolumeEntity volume) {
            this.volume = volume;
        }

        public CloseEntity getClose() {
            return close;
        }

        public HighEntity getHigh() {
            return high;
        }

        public LowEntity getLow() {
            return low;
        }

        public OpenEntity getOpen() {
            return open;
        }

        public VolumeEntity getVolume() {
            return volume;
        }

        public static class CloseEntity {
            private double min;
            private double max;

            public void setMin(double min) {
                this.min = min;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getMin() {
                return min;
            }

            public double getMax() {
                return max;
            }
        }

        public static class HighEntity {
            private double min;
            private double max;

            public void setMin(double min) {
                this.min = min;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getMin() {
                return min;
            }

            public double getMax() {
                return max;
            }
        }

        public static class LowEntity {
            private double min;
            private double max;

            public void setMin(double min) {
                this.min = min;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getMin() {
                return min;
            }

            public double getMax() {
                return max;
            }
        }

        public static class OpenEntity {
            private double min;
            private double max;

            public void setMin(double min) {
                this.min = min;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getMin() {
                return min;
            }

            public double getMax() {
                return max;
            }
        }

        public static class VolumeEntity {
            private long min;
            private long max;

            public void setMin(long min) {
                this.min = min;
            }

            public void setMax(long max) {
                this.max = max;
            }

            public long getMin() {
                return min;
            }

            public long getMax() {
                return max;
            }
        }
    }

    public static class SeriesEntity implements Parcelable {
        private int Timestamp;
        private double close;
        private double high;
        private double low;
        private double open;
        private long volume;

        public void setTimestamp(int Timestamp) {
            this.Timestamp = Timestamp;
        }

        public void setClose(double close) {
            this.close = close;
        }

        public void setHigh(double high) {
            this.high = high;
        }

        public void setLow(double low) {
            this.low = low;
        }

        public void setOpen(double open) {
            this.open = open;
        }

        public void setVolume(long volume) {
            this.volume = volume;
        }

        public int getTimestamp() {
            return Timestamp;
        }

        public double getClose() {
            return close;
        }

        public double getHigh() {
            return high;
        }

        public double getLow() {
            return low;
        }

        public double getOpen() {
            return open;
        }

        public long getVolume() {
            return volume;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.Timestamp);
            dest.writeDouble(this.close);
            dest.writeDouble(this.high);
            dest.writeDouble(this.low);
            dest.writeDouble(this.open);
            dest.writeLong(this.volume);
        }

        public SeriesEntity() {
        }

        protected SeriesEntity(Parcel in) {
            this.Timestamp = in.readInt();
            this.close = in.readDouble();
            this.high = in.readDouble();
            this.low = in.readDouble();
            this.open = in.readDouble();
            this.volume = in.readLong();
        }

        public static final transient Parcelable.Creator<SeriesEntity> CREATOR = new Parcelable.Creator<SeriesEntity>() {
            public SeriesEntity createFromParcel(Parcel source) {
                return new SeriesEntity(source);
            }

            public SeriesEntity[] newArray(int size) {
                return new SeriesEntity[size];
            }
        };
    }
}
