package com.carlos.capstone.interfaces;

/**
 * Created by Carlos on 23/03/2016.
 */
public interface Callback {

    public void onItemSelected(String ticker, String companyName, String securityType);
    public void onItemInIndexesSelected(String ticker,String indexName,String region);
}
