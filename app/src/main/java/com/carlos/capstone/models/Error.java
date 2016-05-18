package com.carlos.capstone.models;

import java.util.List;

/**
 * Created by Carlos on 18/03/2016.
 */
public class Error {


    /**
     * uri : /instrument/1.0/NA7.BE/chartdata;type=quote;range=1d/json
     * ticker : na7.be
     */

    private MetaEntity meta;
    /**
     * meta : {"uri":"/instrument/1.0/NA7.BE/chartdata;type=quote;range=1d/json","ticker":"na7.be"}
     * errorid : 1
     * message : No data available for given Time Range - symbol: na7.be representation: quoterange: 1d start: -36764 end: 597233245
     * alternate_ranges : ["","1m","3m","6m","1y","2y","5y","my"]
     */

    private int errorid;
    private String message;
    private List<String> alternate_ranges;

    public void setMeta(MetaEntity meta) {
        this.meta = meta;
    }

    public void setErrorid(int errorid) {
        this.errorid = errorid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAlternate_ranges(List<String> alternate_ranges) {
        this.alternate_ranges = alternate_ranges;
    }

    public MetaEntity getMeta() {
        return meta;
    }

    public int getErrorid() {
        return errorid;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getAlternate_ranges() {
        return alternate_ranges;
    }

    public static class MetaEntity {
        private String uri;
        private String ticker;

        public void setUri(String uri) {
            this.uri = uri;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public String getUri() {
            return uri;
        }

        public String getTicker() {
            return ticker;
        }
    }
}
