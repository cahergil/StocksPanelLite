package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.utils.TimeMeasure;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Carlos on 07/03/2016.
 */
public class DownloadSymbolFromExcel extends IntentService {
    private static final String LOG_TAG = DownloadSymbolFromExcel.class.getSimpleName();
    private TimeMeasure tm;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadSymbolFromExcel(String name) {
        super(name);
    }

    public DownloadSymbolFromExcel() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        tm=new TimeMeasure(LOG_TAG);
        tm.log("####### start Downloading SECURITIES");
        String endpoint = "https://carloshernandezgil.files.wordpress.com/2016/03/security_list_2003.xls";
        URL url;
        URLConnection con;
        try {
            url = new URL(endpoint);
            con = url.openConnection();
            parseExcel(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    private void parseExcel(InputStream inputStream) {
        //create a workbook

        HSSFWorkbook myWorkBook=null;
        HSSFSheet mySheet=null;

        try {
            myWorkBook = new HSSFWorkbook(inputStream);
            // Get the first sheet from workbook

            mySheet = myWorkBook.getSheetAt(0);
            // We now need something to iterate through the cells
            Iterator<Row> rowIter = mySheet.rowIterator();
            Vector<ContentValues> values = new Vector<ContentValues>();

            HSSFRow myRow;
            Iterator<Cell> cellIter;

            HSSFCell myCell;
            while (rowIter.hasNext()) {
                myRow = (HSSFRow) rowIter.next();
                // Skip the first 4 rows,0 based index
                if (myRow.getRowNum() < 4) {
                    continue;
                }
                //row 5,
                cellIter = myRow.cellIterator();
                ContentValues contentValues = new ContentValues();
                while (cellIter.hasNext()) {
                    myCell = (HSSFCell) cellIter.next();

                    if (myCell.getColumnIndex() > 3) {
                        continue;
                    }
                    switch (myCell.getColumnIndex()) {
                        case 0:
                            contentValues.put(CapstoneContract.SecurityExcelEntity.TICKER, myCell.getStringCellValue());
                            break;

                        case 1:
                            contentValues.put(CapstoneContract.SecurityExcelEntity.NAME, myCell.getStringCellValue());
                            break;
                        case 2:
                            contentValues.put(CapstoneContract.SecurityExcelEntity.EXCHANGE, myCell.getStringCellValue());
                            break;
                        case 3:
                            contentValues.put(CapstoneContract.SecurityExcelEntity.COUNTRY, myCell.getStringCellValue());
                            break;
                    }

                }
                values.add(contentValues);

            }
            inputStream.close();
            Log.d(LOG_TAG, "Numero de entradas" + values.size());
            tm.log("####### end Downloading SECURITIES");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
