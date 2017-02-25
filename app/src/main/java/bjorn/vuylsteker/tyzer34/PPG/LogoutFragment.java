package bjorn.vuylsteker.tyzer34.PPG;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import bjorn.vuylsteker.tyzer34.PPG.util.Global;
import bjorn.vuylsteker.tyzer34.PPG.util.WebViewClientFactory;

public class LogoutFragment extends Fragment {

    private WebView mWebView;

	public LogoutFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        mWebView = (WebView) rootView.findViewById(R.id.main_webview);
        //mWebView.setVisibility(View.INVISIBLE);
        RelativeLayout loadingView = (RelativeLayout) rootView.findViewById(R.id.gif_layout);
        mWebView.setWebChromeClient(new WebViewClientFactory().visibleAfterLoad(loadingView));
        mWebView.setWebViewClient(WebViewClientFactory.logoutClient);
        mWebView.loadUrl("http://www.poppriceguide.com/wp-login.php?action=logout&_wpnonce=766d52e946&redirect_to=http%3A%2F%2Fwww.poppriceguide.com%2Fmember-login%2F%3Floggedout%3Dtrue");

        return rootView;
    }

}
