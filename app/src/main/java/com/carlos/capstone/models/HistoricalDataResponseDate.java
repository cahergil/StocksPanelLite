package com.carlos.capstone.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Carlos on 23/12/2015.
 */
public class HistoricalDataResponseDate {

    /**
     * uri : /instrument/1.0/goog/chartdata;type=quote;range=1m/json
     * ticker : goog
     * Company-Name : Alphabet Inc.
     * Exchange-Name : NMS
     * unit : DAY
     * timestamp :
     * first-trade : 20140327
     * last-trade : 20151222
     * currency : USD
     * previous_close_price : 756.6
     */

    private MetaEntity meta;
    /**
     * min : 20151123
     * max : 20151222
     */

    private DateEntity Date;
    /**
     * close : {"min":738.87,"max":767.04}
     * high : {"min":745.71,"max":775.955}
     * low : {"min":724.17,"max":758.96}
     * open : {"min":741.16,"max":768.9}
     * volume : {"min":838500,"max":3118000}
     */

    private RangesEntity ranges;
    /**
     * meta : {"uri":"/instrument/1.0/goog/chartdata;type=quote;range=1m/json","ticker":"goog","Company-Name":"Alphabet Inc.","Exchange-Name":"NMS","unit":"DAY","timestamp":"","first-trade":"20140327","last-trade":"20151222","currency":"USD","previous_close_price":756.6}
     * Date : {"min":20151123,"max":20151222}
     * labels : [20151123,20151130,20151207,20151214,20151221]
     * ranges : {"close":{"min":738.87,"max":767.04},"high":{"min":745.71,"max":775.955},"low":{"min":724.17,"max":758.96},"open":{"min":741.16,"max":768.9},"volume":{"min":838500,"max":3118000}}
     * series : [{"Date":20151123,"close":755.98,"high":762.708,"low":751.82,"open":757.45,"volume":1414500},{"Date":20151124,"close":748.28,"high":755.279,"low":737.63,"open":752,"volume":2333100},{"Date":20151125,"close":748.15,"high":752,"low":746.06,"open":748.14,"volume":1122100},{"Date":20151127,"close":750.26,"high":753.41,"low":747.49,"open":748.46,"volume":838500},{"Date":20151130,"close":742.6,"high":754.93,"low":741.27,"open":748.81,"volume":2035300},{"Date":20151201,"close":767.04,"high":768.95,"low":746.7,"open":747.11,"volume":2129900},{"Date":20151202,"close":762.38,"high":775.955,"low":758.96,"open":768.9,"volume":2195700},{"Date":20151203,"close":752.54,"high":768.995,"low":745.63,"open":766.01,"volume":2588900},{"Date":20151204,"close":766.81,"high":768.49,"low":750,"open":753.1,"volume":2741500},{"Date":20151207,"close":763.25,"high":768.73,"low":755.09,"open":767.77,"volume":1807300},{"Date":20151208,"close":762.37,"high":764.8,"low":754.2,"open":757.89,"volume":1827400},{"Date":20151209,"close":751.61,"high":764.23,"low":737.001,"open":759.17,"volume":2695300},{"Date":20151210,"close":749.46,"high":755.85,"low":743.83,"open":752.85,"volume":1984900},{"Date":20151211,"close":738.87,"high":745.71,"low":736.75,"open":741.16,"volume":2217500},{"Date":20151214,"close":747.77,"high":748.73,"low":724.17,"open":741.79,"volume":2412500},{"Date":20151215,"close":743.4,"high":758.08,"low":743.01,"open":753,"volume":2654600},{"Date":20151216,"close":758.09,"high":760.59,"low":739.435,"open":750,"volume":1980000},{"Date":20151217,"close":749.43,"high":762.68,"low":749,"open":762.42,"volume":1544600},{"Date":20151218,"close":739.31,"high":754.13,"low":738.15,"open":746.51,"volume":3118000},{"Date":20151221,"close":747.77,"high":750,"low":740,"open":746.13,"volume":1509200},{"Date":20151222,"close":750,"high":754.85,"low":745.53,"open":751.65,"volume":1365400}]
     */

    private List<Integer> labels;
    /**
     * Date : 20151123
     * close : 755.98
     * high : 762.708
     * low : 751.82
     * open : 757.45
     * volume : 1414500
     */

    private List<SeriesEntity> series;

    public void setMeta(MetaEntity meta) {
        this.meta = meta;
    }

    public void setDate(DateEntity Date) {
        this.Date = Date;
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

    public DateEntity getDate() {
        return Date;
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

        private String unit;
        private String timestamp;
        private String currency;
        private double previous_close_price;

        public void setUri(String uri) {
            this.uri = uri;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setPrevious_close_price(double previous_close_price) {
            this.previous_close_price = previous_close_price;
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

        public String getTimestamp() {
            return timestamp;
        }

        public String getCurrency() {
            return currency;
        }

        public double getPrevious_close_price() {
            return previous_close_price;
        }
    }

    public static class DateEntity {
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
         * min : 738.87
         * max : 767.04
         */

        private CloseEntity close;
        /**
         * min : 745.71
         * max : 775.955
         */

        private HighEntity high;
        /**
         * min : 724.17
         * max : 758.96
         */

        private LowEntity low;
        /**
         * min : 741.16
         * max : 768.9
         */

        private OpenEntity open;
        /**
         * min : 838500
         * max : 3118000
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
        private int Date;
        private double close;
        private double high;
        private double low;
        private double open;
        private long volume;

        public void setDate(int Date) {
            this.Date = Date;
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

        public int getDate() {
            return Date;
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
            dest.writeInt(this.Date);
            dest.writeDouble(this.close);
            dest.writeDouble(this.high);
            dest.writeDouble(this.low);
            dest.writeDouble(this.open);
            dest.writeLong(this.volume);
        }

        public SeriesEntity() {
        }

        protected SeriesEntity(Parcel in) {
            this.Date = in.readInt();
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
