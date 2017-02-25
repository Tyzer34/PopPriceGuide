package bjorn.vuylsteker.tyzer34.PPG;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import bjorn.vuylsteker.tyzer34.PPG.util.Global;
import bjorn.vuylsteker.tyzer34.PPG.util.WebViewClientFactory;

public class RegisterFragment extends Fragment {

    private WebView mWebView;

	public RegisterFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        mWebView = (WebView) rootView.findViewById(R.id.main_webview);
        RelativeLayout loadingView = (RelativeLayout) rootView.findViewById(R.id.gif_layout);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebViewClientFactory().visibleAfterLoad(loadingView));
        mWebView.setWebViewClient(WebViewClientFactory.RegisterViewer);
        mWebView.loadUrl("http://www.poppriceguide.com/registration/");

        return rootView;
    }
}
