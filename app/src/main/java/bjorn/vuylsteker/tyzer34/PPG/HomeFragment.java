package bjorn.vuylsteker.tyzer34.PPG;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import bjorn.vuylsteker.tyzer34.PPG.util.Global;
import bjorn.vuylsteker.tyzer34.PPG.util.WebViewClientFactory;

public class HomeFragment extends Fragment {

    private WebView mWebView;
    private WebView mWebViewHidden;

	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        if (MainActivity.isNetworkOnline()) {
            MainActivity.setPosition(0);
            MainActivity.update();

            mWebView = (WebView) rootView.findViewById(R.id.main_webview);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(WebViewClientFactory.NewsViewer);
            mWebView.loadUrl("http://www.poppriceguide.com/");

            if (Global.isLoggedIn()) {
                mWebViewHidden = (WebView) rootView.findViewById(R.id.webview_hidden);
                loadCollectionAmounts();
            }
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (!MainActivity.isNetworkOnline()) {
            // No Internet Connection
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
            alertDialog.setTitle("No Internet Connection");
            alertDialog.setMessage("This application makes use of an internet connection to successfully display available data. Please turn on your WiFi or Mobile Data in order to use this application.");
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            alertDialog.show();
        }
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack() {
        mWebView.goBack();
        if (!canGoBack()){
            mWebView.setWebViewClient(WebViewClientFactory.NewsViewer);
        }
    }


    private void loadCollectionAmounts() {
        mWebViewHidden.getSettings().setJavaScriptEnabled(true);
        mWebViewHidden.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        mWebViewHidden.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebViewHidden.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByClassName('col-25')[0].children[2].children[0].innerHTML.split(' ')[0] + ';' + document.getElementsByClassName('col-25')[0].children[2].children[4].innerHTML.split(' ')[0]);");
            }
        });
        mWebViewHidden.loadUrl("http://www.poppriceguide.com/guide/PopTracker/manage.php?opt=overview");
    }
}

class MyJavaScriptInterface
{
    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html)
    {
        // CollectionCount;WantedCount
        String[] values = html.split(";");
        if (values.length > 1) {
            Global.setCollectionCount(Integer.parseInt(values[0]));
            Global.setWantlistCount(Integer.parseInt(values[1]));
            Global.storeSettings();
        }
    }
}
