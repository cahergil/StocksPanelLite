package com.carlos.capstone.workerthreads;

import com.carlos.capstone.models.HistoricalDataResponseTimestamp;

import retrofit.Response;

/**
 * Created by Carlos on 18/02/2016.
 */
public class WorkerThreadChart implements Runnable {
    private Response<HistoricalDataResponseTimestamp> response;

    public WorkerThreadChart(Response<HistoricalDataResponseTimestamp> response){

        this.response=response;
    }
    @Override
    public void run() {

    }
}
