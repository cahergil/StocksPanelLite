package com.carlos.capstone.models;

import java.util.List;

/**
 * Created by Carlos on 29/01/2016.
 */
public class IndexOrShortInfoDataResponse {

    /**
     * meta : {"type":"resource-list","start":0,"count":8}
     * resources : [{"resource":{"classname":"Quote","fields":{"change":"38.508301","chg_percent":"0.861837","day_high":"4533.810059","day_low":"4447.500488","issuer_name":"NASDAQ Composite","name":"NASDAQ Composite","price":"4506.675781","symbol":"^IXIC","ts":"1454019359","type":"index","utctime":"2016-01-28T22:15:59+0000","volume":"0","year_high":"5231.940000","year_low":"4292.140000"}}},{"resource":{"classname":"Quote","fields":{"change":"125.179688","chg_percent":"0.785098","day_high":"16102.139648","day_low":"15863.719727","issuer_name":"Dow Jones Industrial Average","name":"Dow Jones Industrial Average","price":"16069.639648","symbol":"^DJI","ts":"1454016637","type":"index","utctime":"2016-01-28T21:30:37+0000","volume":"130122469","year_high":"18351.400000","year_low":"15370.300000"}}},{"resource":{"classname":"Quote","fields":{"change":"10.410034","chg_percent":"0.552858","day_high":"1902.959961","day_low":"1873.650024","issuer_name":"S&amp;P 500","name":"S&amp;P 500","price":"1893.359985","symbol":"^GSPC","ts":"1454016637","type":"index","utctime":"2016-01-28T21:30:37+0000","volume":"820296447","year_high":"2134.720000","year_low":"1812.290000"}}},{"resource":{"classname":"Quote","fields":{"change":"0.000000","chg_percent":"0.000000","day_high":"39099.820312","day_low":"37996.093750","issuer_name":"IBOVESPA","name":"IBOVESPA","price":"38630.191406","symbol":"^BVSP","ts":"1454069095","type":"index","utctime":"2016-01-29T12:04:55+0000","volume":"0","year_high":"58575.000000","year_low":"37046.000000"}}},{"resource":{"classname":"Quote","fields":{"change":"283.128906","chg_percent":"0.672357","day_high":"42445.449219","day_low":"42055.269531","issuer_name":"IPC","name":"IPC","price":"42393.019531","symbol":"^MXX","ts":"1454015185","type":"index","utctime":"2016-01-28T21:06:25+0000","volume":"255049040","year_high":"46078.100000","year_low":"39256.600000"}}},{"resource":{"classname":"Quote","fields":{"change":"-148850.000000","chg_percent":"-99.233337","day_high":"1150.000000","day_low":"1150.000000","issuer_name":"B.V.DE CCS.","name":"B.V.DE CCS.","price":"1150.000000","symbol":"BVCC.CR","ts":"1453998378","type":"equity","utctime":"2016-01-28T16:26:18+0000","volume":"0","year_high":"1150.000000","year_low":"0.000000"}}},{"resource":{"classname":"Quote","fields":{"change":"96.910156","chg_percent":"0.898433","day_high":"11195.330078","day_low":"10788.200195","issuer_name":"MERVAL BUENOS AIRES","name":"MERVAL BUENOS AIRES","price":"10883.490234","symbol":"^MERV","ts":"1454011263","type":"index","utctime":"2016-01-28T20:01:03+0000","volume":"0","year_high":"14597.200000","year_low":"8256.360000"}}},{"resource":{"classname":"Quote","fields":{"change":"46.800049","chg_percent":"1.319724","day_high":"3600.060059","day_low":"3546.860107","issuer_name":"IPSA SANTIAGO DE CHILE","name":"IPSA SANTIAGO DE CHILE","price":"3593.000000","symbol":"^IPSA","ts":"1454014952","type":"index","utctime":"2016-01-28T21:02:32+0000","volume":"0","year_high":"4148.230000","year_low":"3418.770000"}}}]
     */

    private ListEntity list;

    public void setList(ListEntity list) {
        this.list = list;
    }

    public ListEntity getList() {
        return list;
    }

    public static class ListEntity {
        /**
         * resource : {"classname":"Quote","fields":{"change":"38.508301","chg_percent":"0.861837","day_high":"4533.810059","day_low":"4447.500488","issuer_name":"NASDAQ Composite","name":"NASDAQ Composite","price":"4506.675781","symbol":"^IXIC","ts":"1454019359","type":"index","utctime":"2016-01-28T22:15:59+0000","volume":"0","year_high":"5231.940000","year_low":"4292.140000"}}
         */

        private List<ResourcesEntity> resources;

        public void setResources(List<ResourcesEntity> resources) {
            this.resources = resources;
        }

        public List<ResourcesEntity> getResources() {
            return resources;
        }

        public static class ResourcesEntity {
            /**
             * classname : Quote
             * fields : {"change":"38.508301","chg_percent":"0.861837","day_high":"4533.810059","day_low":"4447.500488","issuer_name":"NASDAQ Composite","name":"NASDAQ Composite","price":"4506.675781","symbol":"^IXIC","ts":"1454019359","type":"index","utctime":"2016-01-28T22:15:59+0000","volume":"0","year_high":"5231.940000","year_low":"4292.140000"}
             */

            private ResourceEntity resource;

            public void setResource(ResourceEntity resource) {
                this.resource = resource;
            }

            public ResourceEntity getResource() {
                return resource;
            }

            public static class ResourceEntity {
                private String classname;
                /**
                 * change : 38.508301
                 * chg_percent : 0.861837
                 * day_high : 4533.810059
                 * day_low : 4447.500488
                 * issuer_name : NASDAQ Composite
                 * name : NASDAQ Composite
                 * price : 4506.675781
                 * symbol : ^IXIC
                 * ts : 1454019359
                 * type : index
                 * utctime : 2016-01-28T22:15:59+0000
                 * volume : 0
                 * year_high : 5231.940000
                 * year_low : 4292.140000
                 */

                private FieldsEntity fields;

                public void setClassname(String classname) {
                    this.classname = classname;
                }

                public void setFields(FieldsEntity fields) {
                    this.fields = fields;
                }

                public String getClassname() {
                    return classname;
                }

                public FieldsEntity getFields() {
                    return fields;
                }

                public static class FieldsEntity {
                    private String change;
                    private String chg_percent;
                    private String day_high;
                    private String day_low;
                    private String issuer_name;
                    private String name;
                    private String price;
                    private String symbol;
                    private String ts;
                    private String type;
                    private String utctime;
                    private String volume;
                    private String year_high;
                    private String year_low;

                    public void setChange(String change) {
                        this.change = change;
                    }

                    public void setChg_percent(String chg_percent) {
                        this.chg_percent = chg_percent;
                    }

                    public void setDay_high(String day_high) {
                        this.day_high = day_high;
                    }

                    public void setDay_low(String day_low) {
                        this.day_low = day_low;
                    }

                    public void setIssuer_name(String issuer_name) {
                        this.issuer_name = issuer_name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public void setSymbol(String symbol) {
                        this.symbol = symbol;
                    }

                    public void setTs(String ts) {
                        this.ts = ts;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public void setUtctime(String utctime) {
                        this.utctime = utctime;
                    }

                    public void setVolume(String volume) {
                        this.volume = volume;
                    }

                    public void setYear_high(String year_high) {
                        this.year_high = year_high;
                    }

                    public void setYear_low(String year_low) {
                        this.year_low = year_low;
                    }

                    public String getChange() {
                        return change;
                    }

                    public String getChg_percent() {
                        return chg_percent;
                    }

                    public String getDay_high() {
                        return day_high;
                    }

                    public String getDay_low() {
                        return day_low;
                    }

                    public String getIssuer_name() {
                        return issuer_name;
                    }

                    public String getName() {
                        return name;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public String getSymbol() {
                        return symbol;
                    }

                    public String getTs() {
                        return ts;
                    }

                    public String getType() {
                        return type;
                    }

                    public String getUtctime() {
                        return utctime;
                    }

                    public String getVolume() {
                        return volume;
                    }

                    public String getYear_high() {
                        return year_high;
                    }

                    public String getYear_low() {
                        return year_low;
                    }
                }
            }
        }
    }
}
