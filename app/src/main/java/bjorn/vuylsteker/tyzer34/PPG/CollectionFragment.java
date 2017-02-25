package bjorn.vuylsteker.tyzer34.PPG;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import bjorn.vuylsteker.tyzer34.PPG.util.GifView;
import bjorn.vuylsteker.tyzer34.PPG.util.WebViewClientFactory;

public class CollectionFragment extends Fragment {

    private WebView mWebView;

	public CollectionFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        mWebView = (WebView) rootView.findViewById(R.id.main_webview);
        RelativeLayout loadingView = (RelativeLayout) rootView.findViewById(R.id.gif_layout);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebViewClientFactory().visibleAfterLoad(loadingView));
        mWebView.setWebViewClient(WebViewClientFactory.CollectionViewer);
        mWebView.loadUrl("http://www.poppriceguide.com/guide/PopTracker/manage.php?opt=collection");

        return rootView;
    }
}
