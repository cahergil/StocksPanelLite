package com.carlos.capstone;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

/**
 * Created by Carlos on 31/12/2015.
 */
public class FragmentNewsDetail extends WebViewFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle=getArguments();
        final String url=bundle.getString("url");
        getActivity().setTitle(bundle.getString("title"));
        WebView webView=getWebView();

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view,int progress){
                getActivity().setTitle("Loading...");
                getActivity().setProgress(progress*100);
                if(progress>=25) {
                   getActivity().setTitle(url);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.getSettings().setJavaScriptEnabled(true);
      //  webView.setWebViewClient(new WebViewOverrideUrl()); parece que va mas rapido sin esto
        webView.loadUrl(url);


        return view;
    }
    private class WebViewOverrideUrl extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
