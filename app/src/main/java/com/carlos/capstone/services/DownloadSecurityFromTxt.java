package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.utils.TimeMeasure;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.Vector;

/**
 * Created by Carlos on 09/03/2016.
 */
public class DownloadSecurityFromTxt extends IntentService {
    private static final String LOG_TAG=DownloadSecurityFromTxt.class.getSimpleName();
    private TimeMeasure tm;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadSecurityFromTxt(String name) {
        super(name);
    }
    public DownloadSecurityFromTxt(){
        super(LOG_TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        tm=new TimeMeasure(LOG_TAG);
        //https://dl.dropboxusercontent.com/s/1h37ke12q10nqwb/Security_list_indexes.txt?dl=0
//        String[] endpoints=new String[]{
//                "https://dl.dropboxusercontent.com/s/rwkxo6cp2hp6v45/Security_list11.txt",
//                "https://dl.dropboxusercontent.com/s/1h37ke12q10nqwb/Security_list_indexes.txt",
//                "https://dl.dropboxusercontent.com/s/z3zbpeowksyl08n/Security_list_etf.txt"
//
//        };
        String[] endpoints=new String[]{
                "https://www.dropbox.com/s/onjyf69jgullnfi/Security_list11.txt?dl=0",
                "https://www.dropbox.com/s/byt3imrxzioc470/Security_list_etf.txt?dl=0",
                "https://www.dropbox.com/s/2byrir8or3xg57e/Security_list_indexes.txt?dl=0"

        };
        //https://dl.dropboxusercontent.com/s/z3zbpeowksyl08n/Security_list_etf.txt
        //String endpoint = "https://dl.dropboxusercontent.com/s/rwkxo6cp2hp6v45/Security_list11.txt";
        //delete
        int deleted=getContentResolver().delete(CapstoneContract.SecurityExcelEntity.CONTENT_URI,null,null);
        Log.d(LOG_TAG,"rows_deleted:"+deleted);

        for (int k=0;k<endpoints.length;k++) {
            tm.log("####### start Downloading SECURITIES "+k);
            URL url;
            URLConnection con = null;
            try {
                url = new URL(endpoints[k]);
                con = url.openConnection();
             //   BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                //the codification of the incomming files is ANSI. Setting UTF-8 doesnt work to properly save
                //accented strings. Probably Normalizer.normalize isn't neccesary, I haven't tested it
                // check out http://stackoverflow.com/questions/15091109/string-accent-database-encoding-specifications
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("windows-1252")));
                String line;
                Vector<ContentValues> values = new Vector<ContentValues>();
                ContentValues contentValues;
                String[] splittedString;
                int count = 0;
                while ((line = br.readLine()) != null) {
                    count++;
                    //header out
                    if (count == 1) {
                        continue;
                    }
                    //esto elimina unos cuantos registros blancos
                     if(StringUtils.isBlank(line)) {
                         continue;
                     }

                    splittedString = line.split("\\t");

                    contentValues = new ContentValues();

                    for (int i = 0; i < splittedString.length; i++) {
                        if (i == 0) {
                            //ticker
                            contentValues.put(CapstoneContract.SecurityExcelEntity.TICKER, splittedString[0]);
                        } else if (i == 1) {
                            //name

                            String name= WordUtils.capitalize(splittedString[1].replaceAll("\"", "").toLowerCase());
                            contentValues.put(CapstoneContract.SecurityExcelEntity.NAME, Normalizer.normalize(name, Normalizer.Form.NFD));
                        } else if (i == 2) {
                            //exchange
                            contentValues.put(CapstoneContract.SecurityExcelEntity.EXCHANGE, splittedString[2]);
                        } else if (i == 3) {
                            //country
                            contentValues.put(CapstoneContract.SecurityExcelEntity.COUNTRY, splittedString[3]);
                        } else if (i > 3) {
                            break;
                        }
                    }
                    contentValues.put(CapstoneContract.SecurityExcelEntity.ROWNUM,count);
                    if (k==0) {
                        contentValues.put(CapstoneContract.SecurityExcelEntity.SECURITY_TYPE,getString(R.string.equity));
                    } else if (k==1) {
                        contentValues.put(CapstoneContract.SecurityExcelEntity.SECURITY_TYPE,getString(R.string.index));
                    } else
                    {
                        contentValues.put(CapstoneContract.SecurityExcelEntity.SECURITY_TYPE,getString(R.string.etf));
                    }

                    values.add(contentValues);

                }
                Log.d(LOG_TAG, "list size" + values.size());
                tm.log("####### END Downloading SECURITIES "+ k);
                tm.log("####### BEFORE insert  SECURITIES "+ k);
                int rows=0;

                ContentValues[] inserted_values=new ContentValues[values.size()];
                values.toArray(inserted_values);
                Uri uri=CapstoneContract.SecurityExcelEntity.CONTENT_URI;
                rows=getContentResolver().bulkInsert(uri,inserted_values);
                Log.d(LOG_TAG, "Rows inserted in securit_excel table:" + rows);
                tm.log("####### AFTER insert  SECURITIES "+ k);
            } catch (MalformedURLException e) {
                Log.d(LOG_TAG, e.getMessage());
            } catch (IOException e) {
                Log.d(LOG_TAG, e.getMessage());
            } finally {

            }

        }//for

    }
//    private CellProcessor[] getProcessors(){
//
//        final  CellProcessor[] processors = new CellProcessor[] {
//                new UniqueHashCode()//ticker
//              //  new NotNull(), //name
//              //  new NotNull(), //exchange
//              //  new NotNull(), //country
//              //  new Optional()//catName
//               // new Optional()//catNumber
//        };
//
//        return processors;
//    }
//    private void parseCsv(InputStream inputStream) {
//        List<CompanyBean> companyBeanList=new ArrayList<CompanyBean>(20000);
//        ICsvBeanReader beanReader = null;
//        CsvPreference pref=new CsvPreference.Builder('"','\t',"\n").build();
//                //CsvPreference.TAB_PREFERENCE;
//
//        beanReader = new CsvBeanReader(new InputStreamReader(inputStream),pref);
//        try {
//            final String[] header =beanReader.getHeader(true);
//                    //new String[]{"ticker","name","exchange","country","catName","catNumber"};
//                    //
//            final CellProcessor[] processors = getProcessors();
//            CompanyBean companyBean;
//            while( (companyBean=beanReader.read(CompanyBean.class,header,processors))!=null ) {
//            companyBeanList.add(companyBean);
//
//            }
//            Log.d(LOG_TAG,"elements readed:"+companyBeanList.size());
//            tm.log("####### end Downloading SECURITIES");
//        } catch (IOException e) {
//            Log.d(LOG_TAG,""+e.getMessage());
//        } finally {
//            if(beanReader!=null) {
//                try {
//                    beanReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//    }

}
