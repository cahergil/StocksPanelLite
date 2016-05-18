package com.carlos.capstone.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Carlos on 21/12/2015.
 */
public class YahooNewsResponse {


    /**
     * count : 15
     * created : 2015-12-21T16:40:55Z
     * lang : es-ES
     * diagnostics : {"publiclyCallable":"true","url":[{"execution-start-time":"1","execution-stop-time":"134","execution-time":"133","content":"http://finance.yahoo.com/q?s=aapl"},{"execution-start-time":"1","execution-stop-time":"134","execution-time":"133","content":"http://finance.yahoo.com/q?s=aapl"}],"user-time":"136","service-time":"259","build-version":"0.2.369"}
     * results : {"li":[{"a":{"data-ylk":"cp:Insider Monkey;o:e;","href":"http://us.rd.yahoo.com/finance/external/xinsidermonkey/SIG=13s7bnb4b/*http://www.insidermonkey.com/blog/here-is-why-investors-are-talking-walt-disney-co-dis-and-technology-stocks-today-404387/","content":"Here Is Why Investors Are Talking Walt Disney Co (DIS) And Technology Stocks Today"},"cite":{"span":"(Mon 11:21AM EST)","content":"at Insider Monkey"}},{"a":{"data-ylk":"cp:Gurufocus;o:i;","href":"http://finance.yahoo.com/news/two-gurus-buying-motion-falling-162055397.html","content":"Two Gurus Buying in Motion With a Falling Stock"},"cite":{"span":"(Mon 11:20AM EST)","content":"Gurufocus"}},{"a":{"data-ylk":"cp:USA TODAY;o:e;","href":"http://us.rd.yahoo.com/finance/external/xusatoday/SIG=13a5jok4o/*http://www.usatoday.com/story/money/columnist/strauss/2015/12/21/strauss-entrepreneur-mistakes/77686822/","content":"For entrepreneurs, mistakes costly \u2014 but helpful"},"cite":{"span":"(Mon 11:18AM EST)","content":"at USA TODAY"}},{"a":{"data-ylk":"cp:MarketWatch;o:e;","href":"http://us.rd.yahoo.com/finance/external/cbsm/SIG=11iiumket/*http://www.marketwatch.com/News/Story/Story.aspx?guid=FCA4B90E-A7F3-11E5-834E-1BB9ACED4E54&siteid=yhoof2","content":"Tim Cook oversimplifies U.S. \u2018skill\u2019 problem"},"cite":{"span":"(Mon 11:10AM EST)","content":"at MarketWatch"}},{"a":{"data-ylk":"cp:TheStreet;o:e;","href":"http://us.rd.yahoo.com/finance/external/tsmfe/SIG=145ouphv9/*http://www.thestreet.com/story/13404198/1/more-squawk-from-jim-cramer-apple-aapl-ceo-defends-tax-practices-it-s-a-crazy-system.html?puc=yahoo&cm_ven=YAHOO","content":"More Squawk from Jim Cramer: Apple (AAPL) CEO Defends Tax Practices, \u2018It\u2019s a Crazy System\u2019"},"cite":{"span":"(Mon 11:10AM EST)","content":"at TheStreet"}},{"a":{"data-ylk":"cp:CNBC;o:e;","href":"http://us.rd.yahoo.com/finance/external/cnbc/SIG=112a69f9v/*http://www.cnbc.com/id/103257636?__source=yahoo%7cfinance%7cheadline%7cheadline%7cstory&par=yahoo&doc=103257636","content":"Cramer: Apple's Tim Cook \u2018patriotic\u2019 on taxes"},"cite":{"span":"(Mon 11:08AM EST)","content":"at CNBC"}},{"a":{"data-ylk":"cp:Barrons.com;o:e;","href":"http://us.rd.yahoo.com/finance/external/barrons/SIG=12pkqap71/*http://blogs.barrons.com/stockstowatchtoday/2015/12/21/stocks-is-there-life-after-fang/?mod=yahoobarrons&ru=yahoo","content":"Stocks: Is There Life After FANG?"},"cite":{"span":"(Mon 11:00AM EST)","content":"at Barrons.com"}},{"a":{"data-ylk":"cp:AP;o:i;","href":"http://finance.yahoo.com/news/ericsson-apple-global-patent-pact-130110332.html","content":"Ericsson, Apple to develop 5G phones and end litigation"},"cite":{"span":"(Mon 10:54AM EST)","content":"AP"}},{"a":{"data-ylk":"cp:Investor's Business Daily;o:e;","href":"http://us.rd.yahoo.com/finance/external/investors/SIG=12caekmsj/*http://news.investors.com/122115-786243-stocks-take-early-gains-monday.htm?ven=yahoocp&src=aurlled&ven=yahoo","content":"Stocks Step Out To Early Gains; Ericsson Jumps On Apple Deal"},"cite":{"span":"(Mon 10:43AM EST)","content":"at Investor's Business Daily"}},{"a":{"data-ylk":"cp:MarketWatch;o:e;","href":"http://us.rd.yahoo.com/finance/external/cbsm/SIG=11iiumket/*http://www.marketwatch.com/News/Story/Story.aspx?guid=467ED306-A7F5-11E5-8F58-F7B780F5883A&siteid=yhoof2","content":"3 surprising reasons your portfolio is floundering"},"cite":{"span":"(Mon 10:40AM EST)","content":"at MarketWatch"}},{"a":{"data-ylk":"cp:24/7 Wall St.;o:e;","href":"http://us.rd.yahoo.com/finance/external/x247wallst/SIG=13mt04bk1/*http://247wallst.com/consumer-electronics/2015/12/21/how-the-apple-and-ericsson-collaboration-could-change-the-game/","content":"How the Apple and Ericsson Collaboration Could Change the Game"},"cite":{"span":"(Mon 10:35AM EST)","content":"at 24/7 Wall St."}},{"a":{"data-ylk":"cp:CNBC;o:i;","href":"http://finance.yahoo.com/video/pro-cooks-frustration-shared-investors-153400475.html","content":"Pro: Cook's frustration shared by investors"},"cite":{"span":"(Mon 10:34AM EST)","content":"CNBC"}},{"a":{"data-ylk":"cp:MarketWatch;o:e;","href":"http://us.rd.yahoo.com/finance/external/cbsm/SIG=11iiumket/*http://www.marketwatch.com/News/Story/Story.aspx?guid=E99AFF1C-A4EC-11E5-B9F4-AA52DC97C130&siteid=yhoof2","content":"A new Apple iPhone and a Tesla rival mark the most anticipated products of 2016"},"cite":{"span":"(Mon 10:24AM EST)","content":"at MarketWatch"}},{"a":{"data-ylk":"cp:Market Realist;o:i;","href":"http://finance.yahoo.com/news/fast-subscriber-growth-china-great-150657527.html","content":"Fast Subscriber Growth in China Is Great News for Apple"},"cite":{"span":"(Mon 10:06AM EST)","content":"Market Realist"}},{"a":{"data-ylk":"cp:Forbes;o:e;","href":"http://us.rd.yahoo.com/finance/external/forbes/SIG=136pqto56/*http://www.forbes.com/sites/investor/2015/12/21/these-8-dow-components-are-in-bear-market-territory/?utm_campaign=yahootix&partner=yahootix","content":"These 8 Dow Components Are In Bear Market Territory"},"cite":{"span":"(Mon 9:56AM EST)","content":"at Forbes"}}]}
     */

    private QueryEntity query;

    public void setQuery(QueryEntity query) {
        this.query = query;
    }

    public QueryEntity getQuery() {
        return query;
    }

    public static class QueryEntity {
        private ResultsEntity results;

        public void setResults(ResultsEntity results) {
            this.results = results;
        }

        public ResultsEntity getResults() {
            return results;
        }

        public static class ResultsEntity {
            /**
             * a : {"data-ylk":"cp:Insider Monkey;o:e;","href":"http://us.rd.yahoo.com/finance/external/xinsidermonkey/SIG=13s7bnb4b/*http://www.insidermonkey.com/blog/here-is-why-investors-are-talking-walt-disney-co-dis-and-technology-stocks-today-404387/","content":"Here Is Why Investors Are Talking Walt Disney Co (DIS) And Technology Stocks Today"}
             * cite : {"span":"(Mon 11:21AM EST)","content":"at Insider Monkey"}
             */

            private List<LiEntity> li;

            public void setLi(List<LiEntity> li) {
                this.li = li;
            }

            public List<LiEntity> getLi() {
                return li;
            }

            public static class LiEntity implements Parcelable {
                /**
                 * data-ylk : cp:Insider Monkey;o:e;
                 * href : http://us.rd.yahoo.com/finance/external/xinsidermonkey/SIG=13s7bnb4b/*http://www.insidermonkey.com/blog/here-is-why-investors-are-talking-walt-disney-co-dis-and-technology-stocks-today-404387/
                 * content : Here Is Why Investors Are Talking Walt Disney Co (DIS) And Technology Stocks Today
                 */

                private AEntity a;
                /**
                 * span : (Mon 11:21AM EST)
                 * content : at Insider Monkey
                 */

                private CiteEntity cite;

                public void setA(AEntity a) {
                    this.a = a;
                }

                public void setCite(CiteEntity cite) {
                    this.cite = cite;
                }

                public AEntity getA() {
                    return a;
                }

                public CiteEntity getCite() {
                    return cite;
                }

                public static class AEntity implements Parcelable {
                    private String href;
                    private String content;

                    public void setHref(String href) {
                        this.href = href;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getHref() {
                        return href;
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
                        dest.writeString(this.href);
                        dest.writeString(this.content);
                    }

                    public AEntity() {
                    }

                    protected AEntity(Parcel in) {
                        this.href = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<AEntity> CREATOR = new Creator<AEntity>() {
                        public AEntity createFromParcel(Parcel source) {
                            return new AEntity(source);
                        }

                        public AEntity[] newArray(int size) {
                            return new AEntity[size];
                        }
                    };
                }

                public static class CiteEntity implements Parcelable {
                    private String span;
                    private String content;

                    public void setSpan(String span) {
                        this.span = span;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getSpan() {
                        return span;
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
                        dest.writeString(this.span);
                        dest.writeString(this.content);
                    }

                    public CiteEntity() {
                    }

                    protected CiteEntity(Parcel in) {
                        this.span = in.readString();
                        this.content = in.readString();
                    }

                    public static final transient Creator<CiteEntity> CREATOR = new Creator<CiteEntity>() {
                        public CiteEntity createFromParcel(Parcel source) {
                            return new CiteEntity(source);
                        }

                        public CiteEntity[] newArray(int size) {
                            return new CiteEntity[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeParcelable(this.a, flags);
                    dest.writeParcelable(this.cite, flags);
                }

                public LiEntity() {
                }

                protected LiEntity(Parcel in) {
                    this.a = in.readParcelable(AEntity.class.getClassLoader());
                    this.cite = in.readParcelable(CiteEntity.class.getClassLoader());
                }

                public static final transient Parcelable.Creator<LiEntity> CREATOR = new Parcelable.Creator<LiEntity>() {
                    public LiEntity createFromParcel(Parcel source) {
                        return new LiEntity(source);
                    }

                    public LiEntity[] newArray(int size) {
                        return new LiEntity[size];
                    }
                };
            }
        }
    }
}
