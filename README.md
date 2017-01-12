
#StocksPanelLite

 [![minSdk](https://img.shields.io/badge/minSDK-16%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=16#)
 [![targetSdk](https://img.shields.io/badge/targetSDK-25-orange.svg?style=flat)](http://source.android.com/source/build-numbers.html)


### Description

App to track equities, indexes and etfs quoted in the Stock Market.

|  Indexes America        | Index details | Stock statsistics |
| ------------- | ------------- |------------- |
| ![imagen mia](https://raw.githubusercontent.com/cahergil/shareablefotos/master/spl/america.png)  | ![enter image description here](https://raw.githubusercontent.com/cahergil/shareablefotos/master/spl/index_nikkei.png) |![enter image description here](https://raw.githubusercontent.com/cahergil/shareablefotos/master/spl/sstats.png)
|

|  Stock details        | Watchlist |
| ------------- | ------------- |
|![enter image description here](https://raw.githubusercontent.com/cahergil/shareablefotos/master/spl/goog_details.png)|![enter image description here](https://raw.githubusercontent.com/cahergil/shareablefotos/master/spl/watchlist.png)|


### Features

Supports the more important markets and a  wide range of smaller countries. Due to supplier restrictions it doesn't provide real-time prices, it has a delay of several minutes.

The following is a list of the main features of the app:

- Provide information about the price of stocks(not real time).
- Provide information about the price of indexes and etf’s(not real time).
- Historical information in form of graphic.
- Financial and statistical indicators of traded stocks.
- Real time news of stocks(not all) quoted in the market.
- List of securities to follow as favorites.
- Main indexes views categorized by region(Europe,America,Asia).
- Economic and financial news categorized by region.

The app supports also RTL and D-PAD navigation.


### General Design

-Sync adapter: The app implements a sync adapter to synchronize data between the device and the server(Yahoo stocks Api and news rss). 
Scheduled at regular intervals of 7 minutes, CapstoneSyncAdapter run its method onPerformSync() on a background thread.
Inside this method are issued several network request , taking its results and storing the records in the database(Sqlite) through a content provider.
The data we get from this periodic implementation are the news and indexes for the differents regions(America, Europe and  Asia). The data is fetched
in a RESTfull manner using the Retrofit 2 type-safe HTTP client for Android and Java. Retrofit will do the fetch in a background thread and 
throught GSON convert it to Java plain objects.Finally the objects are inserted in the database.  For the news there isn't that conversion, are loaded
directly into Sqlite. 

-Retrofit: As the endpoint of the network request differs there are serveral implementation of the Retrofit Singleton pattern. They are located inside the iretrofit package. For example HistoricalRApi gets the data used to draw the charts(1 day, 7 days and so on). IndexComponentsRApi fetches the
data for the indexes(only some of them, marked in the UI with an eye icon) components.IndexOrShortInfoRApi gets the stock market data of indexes,etf's 
and the upper part(in Fragment_stock_summary.xml) the equities details. 


-ContentProvider and Loaders: Content provider is used as a layer before accesing the locally stored data and to take advantage of the power 
of Loaders loading asynchronously the data into the views of activities or fragments. The Loaders monitor the source of their data and deliver new results
when the content changes, handling also orientation changes. This architecture is used heavily in the app as one can see inside the database package and
inside fragments. 

-Sqlite database: To support the off-line use of the app, I decided to store the maximum amount of data in the database. That way the app provides
an enhanced UX and and the user can navigate through it with the latest data stored when for example in plane mode. 
There hasn't been registered any crash so far.

-App Widget: The app provides the user with an app widget, so that the user is informed constantly of the variations of the securities without having
to open the app. The collection widget enables the user to scroll vertically the list inside and to navigate to the details view of each element. This
scheme works for phone as well as for tablets.

-Tablet: The app also provides, for an enhanced user experience, the user with a two-pane version for 9 and 10 inches tablets.Notwidhstanding there is a bug in 6 inches tablets
that I couldn't resolve.

-Splash screen: The app has also a Splash screen to avoid waiting time at the beginning. On first install the waiting time is big, since the app needs
to load the symbols from the web into the database and there a roughly 70.000 to load. This happens only one time, the next start take only 2 seconds
to load the main screen. To coordinate these events the app uses LocalBroadcastManager between the SplashScreen Activity,the CapstoneSyncAdapter and
the RegionChartLoaderService.


### 3rd party libraries used
- [Retrofit v.2.0.0](https://github.com/square/retrofit)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [MPAndroidChart v.2.1.6] (https://github.com/PhilJay/MPAndroidChart)
- [Expandablerecyclerview] (https://github.com/bignerdranch/expandable-recycler-view)
- [Rss-manager v.0.23] (https://github.com/crazyhitty/Rss-Manager)
- [Linearlistview v.1.0.1](https://github.com/frankiesardo/LinearListView)
- [Glide v.3.5.2](https://github.com/bumptech/glide)
- [Stetho v.1.3.1](https://github.com/facebook/stetho)
- [Customtabs v.23.0.0](https://github.com/GoogleChrome/custom-tabs-client)
- [Leakcanary-Android v.1.3.1](https://github.com/square/leakcanary)
- [Crashlytics v.2.5](https://try.crashlytics.com/)

### License

MIT License

Copyright (c) 2016 Carlos Hernández Gil

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
