package bjorn.vuylsteker.tyzer34.PPG.util;

/**
 * Created by Tyzer34 on 22/05/2016.
 */

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.WebView;
import android.widget.Toast;

import bjorn.vuylsteker.tyzer34.PPG.HomeFragment;
import bjorn.vuylsteker.tyzer34.PPG.LoginFragment;
import bjorn.vuylsteker.tyzer34.PPG.LogoutFragment;
import bjorn.vuylsteker.tyzer34.PPG.MainActivity;
import bjorn.vuylsteker.tyzer34.PPG.PopViewFragment;
import bjorn.vuylsteker.tyzer34.PPG.R;

public class WebViewClientFactory {

    private static PauseForVisible pause;

    public VisibleAfterLoad visibleAfterLoad(View loadingView) {
        return new VisibleAfterLoad(loadingView);
    }

    private class VisibleAfterLoad extends WebChromeClient {

        View loadingView;

        public VisibleAfterLoad(View view) {
            this.loadingView = view;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                Handler handler1 = new Handler();
                handler1.postDelayed(new PauseForInvisible(loadingView), 800);
                Handler handler2 = new Handler();
                handler2.postDelayed(new PauseForVisible(view), 1000);
            }
        }
    }

    public final static WebViewClient NewsViewer = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String ppg = "www.poppriceguide.com";
            if (url.contains(ppg)){
                view.loadUrl(url);
                return false;
            } else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            view.loadUrl("javascript:function executeThis(){\n" +
                    "scrollup = document.getElementById('scroll-up');\n" +
                    "scrollup.setAttribute('href', '#page');" +
                    "document.getElementById('page').innerHTML = document.getElementById('colormag_featured_posts_vertical_widget-7').innerHTML + scrollup.outerHTML;" +
                    "document.getElementById('page').className = 'widget widget_featured_posts widget_featured_posts_vertical widget_featured_meta clearfix';" +
                    "document.getElementsByTagName('body')[0].className = 'widget_slider_area';" +
                    "} executeThis();");
            view.setWebViewClient(NewsArticleViewer);
            Handler handler = new Handler();
            handler.postDelayed(new PauseForVisible(view), 500);
        }
    };

    public final static WebViewClient NewsArticleViewer = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String ppg = "www.poppriceguide.com";
            if (url.contains(ppg)){
                view.loadUrl(url);
                return false;
            } else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            view.loadUrl("javascript:function executeThis(){\n" +
                    "scrollup = document.getElementById('scroll-up');\n" +
                    "scrollup.setAttribute('href', '#page');" +
                    "document.getElementById('page').innerHTML = document.getElementById('primary').innerHTML + scrollup.outerHTML;" +
                    "document.getElementById('page').className = 'widget widget_featured_posts widget_featured_posts_vertical widget_featured_meta clearfix';" +
                    "document.getElementsByTagName('body')[0].className = 'widget_slider_area';" +
                    "} executeThis();");
            view.getSettings().setLoadWithOverviewMode(true);
            view.getSettings().setUseWideViewPort(true);
            view.setInitialScale(200);
            Handler handler = new Handler();
            handler.postDelayed(new PauseForVisible(view), 800);
        }
    };

    public final static WebViewClient FriendCollectionViewer = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String ppg = "www.poppriceguide.com";
            if (url.contains(ppg)){
                view.loadUrl(url);
                return false;
            } else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            view.setVisibility(View.INVISIBLE);
            if (url.contains("www.poppriceguide.com/guide/p/")) {
                Global.setLastPopURL(url);
                MainActivity.replaceFragment(new PopViewFragment());
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            view.loadUrl("javascript:function executeThis(){\n\n" +
                    "scrollup = document.getElementById('scroll-up');\n" +
                    "scrollup.setAttribute('href', '#page');" +
                    "\tdocument.getElementsByTagName('body')[0].className = 'widget_slider_area';\n" +
                    "\tdocument.getElementById('page').innerHTML = document.getElementById('primary').children[1].innerHTML;\n" +
                    "\tdocument.getElementById('page').innerHTML = document.getElementById('colllist').innerHTML + scrollup.outerHTML;\n" +
                    "\t} executeThis();");
        }
    };

    public final static WebViewClient CollectionViewer = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String ppg = "www.poppriceguide.com";
            if (url.contains(ppg)){
                view.loadUrl(url);
                return false;
            } else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            view.setVisibility(View.INVISIBLE);
            if (url.contains("www.poppriceguide.com/guide/p/")) {
                Global.setLastPopURL(url);
                MainActivity.replaceFragment(new PopViewFragment());
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            view.loadUrl("javascript:function executeThis(){\n" +
                    "scrollup = document.getElementById('scroll-up');\n" +
                    "scrollup.setAttribute('href', '#page');\n" +
                    "document.getElementById('page').innerHTML = document.getElementById('primary').children[1].innerHTML + scrollup.outerHTML;\n" +
                    "document.getElementsByTagName('body')[0].className = 'widget_slider_area';\n" +
                    "document.getElementsByClassName('col-25')[0].style.display = 'none';\n" +
                    "document.getElementsByClassName('col-25')[1].style.display = 'none';\n" +
                    "title = document.getElementsByClassName('ppg')[0];\n" +
                    "title.style.textAlign = 'center';" +
                    "} executeThis();");
        }
    };

    public final static WebViewClient SearchResultsViewer = new WebViewClient() {

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            view.setVisibility(View.INVISIBLE);
            if (url.contains("www.poppriceguide.com/guide/p/")) {
                Global.setLastPopURL(url);
                MainActivity.replaceFragment(new PopViewFragment());
            }
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            view.loadUrl("javascript:function executeThis(){\n" +
                    "    scrollup = document.getElementById('scroll-up');\n" +
                    "    scrollup.setAttribute('href', '#page');\n" +
                    "\tdocument.getElementById('page').innerHTML = document.getElementById('content').children[0].children[3].innerHTML + scrollup.outerHTML;\n" +
                    "\tdocument.getElementsByTagName('body')[0].className = 'widget_slider_area';\n" +
                    "\threfs = document.getElementsByTagName('a');\n" +
                    "\tfor (i=0; i<hrefs.length;i++) {\n" +
                    "\t\thref = hrefs[i].getAttribute('href');\n" +
                    "\t\tif (href.indexOf('wanted.php') >= 0) {\n" +
                    "\t\t\threfs[i].outerHTML = hrefs[i].innerHTML;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "\tfor (i=0; i<hrefs.length;i++) {\n" +
                    "\t\thref = hrefs[i].getAttribute('href');\n" +
                    "\t\tif (href.indexOf('incollection.php') >= 0) {\n" +
                    "\t\t\threfs[i].outerHTML = hrefs[i].innerHTML;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "\tfor (i=0; i<hrefs.length;i++) {\n" +
                    "\t\thref = hrefs[i].getAttribute('href');\n" +
                    "\t\tif (href.indexOf('forsale.php') >= 0) {\n" +
                    "\t\t\threfs[i].outerHTML = hrefs[i].innerHTML;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "\tfor (i=0; i<hrefs.length;i++) {\n" +
                    "\t\thref = hrefs[i].getAttribute('href');\n" +
                    "\t\tif (href.indexOf('member-login') >= 0) {\n" +
                    "\t\t\threfs[i].outerHTML = hrefs[i].innerHTML;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "\tfor (i=0; i<hrefs.length;i++) {\n" +
                    "\t\thref = hrefs[i].getAttribute('href');\n" +
                    "\t\tif (href.indexOf('registration') >= 0) {\n" +
                    "\t\t\threfs[i].outerHTML = hrefs[i].innerHTML;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "} executeThis();");
        }
    };

    public final static WebViewClient POPViewer = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:function executeThis(){\n" +
                    "\tdocument.getElementById('page').innerHTML = document.getElementsByClassName('col-100')[0].innerHTML;\n" +
                    "\tdocument.getElementsByTagName('body')[0].className = 'widget_slider_area';\n" +
                    "\tebay = document.getElementsByClassName('col-100nopad');\n" +
                    "\tfor (i=0; i<ebay.length;i++) {\n" +
                    "\t\tebay[i].style.display = 'none';\n" +
                    "\t}\n" +
                    "\tdocument.getElementsByClassName('guidebread')[0].style.display = 'none';\n" +
                    "\tpgtitle = document.getElementsByClassName('pgtitle');\n" +
                    "\tfor (i=0; i<pgtitle.length;i++) {\n" +
                    "\t\tif (pgtitle[i].innerHTML == 'Find related items'){\n" +
                    "\t\t\tpgtitle[i].parentElement.style.display = 'none';\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "\tlaststuff = document.getElementsByClassName('col-100');\n" +
                    "\tfor (i=0; i<laststuff.length;i++) {\n" +
                    "\t\tlaststuff[i].style.display = 'none';\n" +
                    "\t}\n" +
                    "\tdocument.getElementsByTagName('img')[0].style = 'border: 1px solid #bbbbbb; padding: 5px; max-width: 100%; margin-bottom: 15px; margin-top: 15px;';\n" +
                    "\ttemp = document.getElementsByClassName('col-33')[0].innerHTML;\n" +
                    "\tvalue = document.getElementsByClassName('col-66')[0].children[0].outerHTML;\n" +
                    "\tdocument.getElementsByClassName('col-66')[0].children[0].style.display = 'none';\n" +
                    "\tdocument.getElementsByClassName('col-33')[0].innerHTML = value + temp;\n" +
                    "\tdocument.getElementsByClassName('col-50')[1].style.display = 'none';\n" +
                    "\threfs = document.getElementsByTagName('a');\n" +
                    "\tfor (i=0; i<hrefs.length;i++) {\n" +
                    "\t\thref = hrefs[i].getAttribute('href');\n" +
                    "\t\tif (href.indexOf('wanted.php') >= 0) {\n" +
                    "\t\t\threfs[i].outerHTML = hrefs[i].innerHTML;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "\tfor (i=0; i<hrefs.length;i++) {\n" +
                    "\t\thref = hrefs[i].getAttribute('href');\n" +
                    "\t\tif (href.indexOf('incollection.php') >= 0) {\n" +
                    "\t\t\threfs[i].outerHTML = hrefs[i].innerHTML;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "} executeThis();");
        }
    };

    public final static WebViewClient UpcomingViewer = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String ppg = "www.poppriceguide.com";
            if (url.contains(ppg)){
                view.loadUrl(url);
                return false;
            } else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.setVisibility(View.INVISIBLE);
            if (url.contains("www.poppriceguide.com/guide/p/")) {
                Global.setLastPopURL(url);
                MainActivity.replaceFragment(new PopViewFragment());
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:function executeThis(){\n" +
                    "scrollup = document.getElementById('scroll-up');\n" +
                    "scrollup.setAttribute('href', '#page');" +
                    "document.getElementById('page').innerHTML = document.getElementById('comingsoon').innerHTML + scrollup.outerHTML;" +
                    "document.getElementById('page').style = 'font-size: 14px;';" +
                    "document.getElementsByTagName('body')[0].className = 'widget_slider_area';" +
                    "items = document.getElementsByClassName('col-100nopad');" +
                    "for (i=0; i<items.length;i++) {" +
                    "col25 = items[i].getElementsByClassName('col-25')[0];" +
                    "if (typeof col25 != 'undefined'){" +
                    "col25_child = col25.children[0].children[0];" +
                    "if (typeof col25_child != 'undefined'){" +
                    "col25_child.style = 'max-width: 80px; max-height: 80px; border: 1px solid #aaaaaa; margin: 10px; border-radius: 8px; margin-top: 15px;';" +
                    "} else {" +
                    "col25_noImg = col25.children[0];" +
                    "if (typeof col25_noImg != 'undefined'){" +
                    "col25_noImg.style = 'max-width: 80px; max-height: 80px; border: 1px solid #aaaaaa; margin: 10px; border-radius: 8px; margin-top: 15px;';" +
                    "}" +
                    "}" +
                    "}" +
                    "col50 = items[i].getElementsByClassName('col-50')[0];" +
                    "if (typeof col50 != 'undefined'){" +
                    "col50.style = 'margin: 10px 0px 0px; padding-left: 0px; font-size: 12px;';" +
                    "col50.children[0].style = 'font-size: 14px;';" +
                    "if (col50.innerHTML.indexOf('Pop!') < 0) {" +
                    "items[i].style.display = 'none';" +
                    "}" +
                    "}" +
                    "col25_2 = items[i].getElementsByClassName('col-25')[1];" +
                    "if (typeof col25_2 != 'undefined'){" +
                    "font = col25_2.children[0];" +
                    "if (typeof font != 'undefined'){" +
                    "font.style = 'font-weight: bold; text-decoration: underline; font-style: italic; font-size: 13px;';" +
                    "}" +
                    "img = col25_2.getElementsByTagName('a')[0];" +
                    "if (typeof img != 'undefined'){" +
                    "img.children[0].style = 'margin-top: 5px;';" +
                    "}" +
                    "}" +
                    "}" +
                    "styleOne = 'background: #ffffff;';" +
                    "styleTwo = 'background: #eeeeee;';" +
                    "lastStyle = styleTwo;" +
                    "for (i=0; i<items.length;i++) {" +
                    "col25 = items[i].getElementsByClassName('col-25')[0];" +
                    "if (typeof col25 != 'undefined'){" +
                    "if (items[i].style.display != 'none') {" +
                    "if (lastStyle == styleTwo){" +
                    "items[i].style = styleOne;" +
                    "lastStyle = styleOne;" +
                    "} else if (lastStyle == styleOne) {" +
                    "items[i].style = styleTwo;" +
                    "lastStyle = styleTwo;" +
                    "}" +
                    "}" +
                    "}" +
                    "}" +
                    "} executeThis();");
            //view.getSettings().setLoadWithOverviewMode(true);
            //view.getSettings().setUseWideViewPort(true);
            view.setInitialScale(150);
        }
    };

    public static WebViewClient loginClient() {
        return new Login();
    }

    private static class Login extends WebViewClient {

        Boolean tried = false;
        Boolean submitted = false;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (submitted) {
                if (tried){
                    if (url.contains("wp-login")) {
                        Global.setLoggedIn(false);
                        Toast.makeText(((Activity) view.getContext()).getApplicationContext(),
                                "Incorrect Credentials", Toast.LENGTH_LONG).show();
                        tried = false;
                        submitted = false;
                        view.loadUrl("about:blank");
                    } else {
                        Toast.makeText(((Activity) view.getContext()).getApplicationContext(),
                                "Login Succesful!", Toast.LENGTH_SHORT).show();
                        Global.setLoggedIn(true);
                        MainActivity.replaceFragment(new HomeFragment());
                    }
                }
                if (url.contains("wp-login")) {
                    tried = true;
                }
            } else {
                Toast.makeText(((Activity) view.getContext()).getApplicationContext(),
                        "Logging in..", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            if (url.contains("wp-login")) {
                String js = "javascript:function executeThis() {" +
                        "document.getElementById('user_login').value = '" + Global.getMail() + "';" +
                        "document.getElementById('user_pass').value = '" + Global.getPass() + "';" +
                        "document.getElementById('rememberme').checked = true;" +
                        "document.getElementById('wp-submit').click();" +
                        "} executeThis();";
                view.loadUrl(js);
                submitted = true;
            }
        }
    };

    public final static WebViewClient logoutClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!url.contains("wp-login")) {
                Toast.makeText(((Activity) view.getContext()).getApplicationContext(),
                        "Logged out!", Toast.LENGTH_LONG).show();
                Global.setLoggedIn(false);
                MainActivity.replaceFragment(new HomeFragment());
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }

    };

    public static WebViewClient statsViewer() {
        return new StatisticsViewer();
    }

    private static class StatisticsViewer extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String ppg = "www.poppriceguide.com";
            if (url.contains(ppg)){
                view.loadUrl(url);
                return false;
            } else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            //view.setVisibility(View.INVISIBLE);
            if (url.contains("www.poppriceguide.com/guide/p/")) {
                Global.setLastPopURL(url);
                MainActivity.replaceFragment(new PopViewFragment());
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:function loadAPI()\n" +
                    "{\n" +
                    "    var script = document.createElement(\"script\");\n" +
                    "    script.src = \"https://www.gstatic.com/charts/loader.js\";\n" +
                    "    script.type = \"text/javascript\";\n" +
                    "    document.getElementsByTagName(\"head\")[0].appendChild(script);\n" +
                    "} loadAPI();");
            Handler handler = new Handler();
            handler.postDelayed(new PauseForLoadAPI(view), 2000);
        }
    };

    public final static WebViewClient RegisterViewer = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String ppg = "www.poppriceguide.com";
            if (url.contains(ppg)){
                view.loadUrl(url);
                return false;
            } else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            view.setVisibility(View.INVISIBLE);
            if (url.contains("www.poppriceguide.com/guide/p/")) {
                Global.setLastPopURL(url);
                MainActivity.replaceFragment(new PopViewFragment());
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            view.loadUrl("javascript:function executeThis(){\n" +
                    "\tdocument.getElementById('page').innerHTML = document.getElementById('primary').innerHTML;\n" +
                    "\tdocument.getElementsByTagName('body')[0].className = 'widget_slider_area';\n" +
                    "\tdocument.getElementsByClassName('wpum-helper-links')[0].style.display = 'none';\n" +
                    "\t} executeThis();");
            view.getSettings().setLoadWithOverviewMode(true);
            view.getSettings().setUseWideViewPort(true);
            view.setInitialScale(200);
        }
    };

    public final static WebViewClient ForumViewer = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String ppg = "www.poppriceguide.com";
            if (url.contains(ppg)){
                view.loadUrl(url);
                return false;
            } else  {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            view.setVisibility(View.INVISIBLE);
            if (url.contains("www.poppriceguide.com/guide/p/")) {
                Global.setLastPopURL(url);
                MainActivity.replaceFragment(new PopViewFragment());
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:function executeThis(){\n" +
                    "\tdocument.getElementById('page').innerHTML = document.getElementById('bbpress-forums').outerHTML;\n" +
                    "\tdocument.getElementsByTagName('body')[0].className = 'entry-content clearfix';\n" +
                    "\ttemp = document.getElementsByClassName('bbp-breadcrumb')[0];\n" +
                    "\tif (typeof temp != 'undefined') {\n" +
                    "\t\ttemp.style.display = 'none';\n" +
                    "\t}\n" +
                    "\ttemp = document.getElementsByClassName('bbp-search-form')[0];\n" +
                    "\tif (typeof temp != 'undefined') {\n" +
                    "\t\ttemp.style.display = 'none';\n" +
                    "\t}\n" +
                    "\ttemp = document.getElementsByClassName('bbpress_mark_all_read_wrapper')[0];\n" +
                    "\tif (typeof temp != 'undefined') {\n" +
                    "\t\ttemp.style.display = 'none';\n" +
                    "\t}\n" +
                    "\threfs = document.getElementsByTagName('a');\n" +
                    "\tfor (i=0; i<hrefs.length;i++) {\n" +
                    "\t\thref = hrefs[i].getAttribute('href');\n" +
                    "\t\tif (href.indexOf('wp-login.php') >= 0) {\n" +
                    "\t\t\threfs[i].outerHTML = hrefs[i].innerHTML;\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "} executeThis();");
        }
    };

}

class PauseForVisible implements Runnable {

    private WebView mView;

    public PauseForVisible(WebView view){
        mView = view;
    }

    public void run()
    {
        mView.setVisibility(mView.VISIBLE);
    }
}

class PauseForInvisible implements Runnable {

    private View mView;

    public PauseForInvisible(View view){
        mView = view;
    }

    public void run()
    {
        mView.setVisibility(mView.GONE);
    }
}

class PauseForLoadAPI implements Runnable {

    private WebView mView;

    public PauseForLoadAPI(WebView view){
        mView = view;
    }

    public void run()
    {
        mView.loadUrl("javascript:function executeThis(){\n" +
                "scrollup = document.getElementById('scroll-up');\n" +
                "scrollup.setAttribute('href', '#page');\n" +
                "document.getElementById('page').innerHTML = document.getElementById('primary').children[1].innerHTML + scrollup.outerHTML;\n" +
                "body = document.getElementsByTagName('body')[0];\n" +
                "body.className = 'widget_slider_area';\n" +
                "body.style.padding = 10;\n" +
                "title = document.getElementsByClassName('ppg')[0];\n" +
                "valuebox = document.getElementsByClassName('col-25')[0];\n" +
                "collection = document.getElementsByClassName('col-50')[0].getElementsByClassName('itemrow-small');\n" +
                "topten = document.getElementsByClassName('col-25')[1];\n" +
                "document.getElementsByClassName('col-50')[0].style.display = 'none';\n" +
                "topten.style.display = 'none';\n" +
                "title.innerHTML = 'Collection Statistics';\n" +
                "title.style.textAlign = 'center';\n" +
                "purchaseFromDict = {};\n" +
                "purchaseDateDict = {};\n" +
                "purchaseAvgPriceDict = {};\n" +
                "purchaseSpendingsDict = {};\n" +
                "var month = new Array();\n" +
                "month[0] = \"January\";\n" +
                "month[1] = \"February\";\n" +
                "month[2] = \"March\";\n" +
                "month[3] = \"April\";\n" +
                "month[4] = \"May\";\n" +
                "month[5] = \"June\";\n" +
                "month[6] = \"July\";\n" +
                "month[7] = \"August\";\n" +
                "month[8] = \"September\";\n" +
                "month[9] = \"October\";\n" +
                "month[10] = \"November\";\n" +
                "month[11] = \"December\";\n" +
                "totalInvested = 0.00;\n" +
                "for (i = 0; i < month.length; i++){\n" +
                "purchaseDateDict[month[i]] = 0;\n" +
                "purchaseSpendingsDict[month[i]] = 0;\n" +
                "}\n" +
                "for (i = 0; i < collection.length; i++) {\n" +
                "curData = collection[i].getElementsByClassName('dropdown-menu')[0].children[0].children[0];\n" +
                "purchasePrice = parseFloat(curData.getAttribute('data-colpurchaseprice'));\n" +
                "purchaseDate = curData.getAttribute('data-colpurchasedate').trim();\n" +
                "purchaseMonth = month[parseInt(purchaseDate.split('/')[1])-1];\n" +
                "purchaseDateDict[purchaseMonth] += 1;\n" +
                "purchaseSpendingsDict[purchaseMonth] += purchasePrice;\n" +
                "purchaseFrom = curData.getAttribute('data-colpurchasefrom').trim();\n" +
                "if (purchaseFrom in purchaseFromDict){\n" +
                "purchaseFromDict[purchaseFrom] += 1;\n" +
                "purchaseAvgPriceDict[purchaseFrom] += purchasePrice;\n" +
                "} else {\n" +
                "purchaseFromDict[purchaseFrom] = 1;\n" +
                "purchaseAvgPriceDict[purchaseFrom] = purchasePrice;\n" +
                "}\n" +
                "totalInvested += purchasePrice;\n" +
                "}\n" +
                "valuebox.innerHTML = document.getElementsByClassName('valuebox')[0].innerHTML;\n" +
                "valuebox.className = 'valuebox';\n" +
                "htmlTemp = valuebox.children[0].innerHTML;\n" +
                "htmlTemp = htmlTemp + '<br>Total Invested<br><h3>" + Global.getMonetaryUnit() + "' + totalInvested.toFixed(2) + '</h3>';\n" +
                "valuebox.children[0].innerHTML = htmlTemp;\n" +
                "itemsPF = Object.keys(purchaseFromDict).map(function(key) {\n" +
                "    return [key, purchaseFromDict[key]];\n" +
                "});\n" +
                "itemsPF.sort(function(a, b){\n" +
                "    if(a[0] < b[0]) return -1;\n" +
                "    if(a[0] > b[0]) return 1;\n" +
                "    return 0;\n" +
                "});\n" +
                "purchaseFromHTML = '<br><div id=\"chart_div_purchaseFrom\" align=\"center\"></div>';\n" +
                "itemsPM = Object.keys(purchaseDateDict).map(function(key) {\n" +
                "    return [key, purchaseDateDict[key]];\n" +
                "});\n" +
                "var d = new Date();\n" +
                "var n = d.getMonth();\n" +
                "itemsPM.sort(function(a, b){\n" +
                "\taMonth = month.indexOf(a[0]);\n" +
                "\tif (aMonth > n){\n" +
                "\taMonth -= 12;\n" +
                "\t}\n" +
                "\tbMonth = month.indexOf(b[0]);\n" +
                "\tif (bMonth > n){\n" +
                "\tbMonth -= 12;\n" +
                "\t}\n" +
                "    return bMonth - aMonth;\n" +
                "});\n" +
                "purchaseDateHTML = '<br><div id=\"chart_div_purchaseDate\" align=\"center\"></div>';\n" +
                "for (key in purchaseAvgPriceDict) {\n" +
                "purchaseAvgPriceDict[key] /= purchaseFromDict[key];\n" +
                "}\n" +
                "itemsAP = Object.keys(purchaseAvgPriceDict).map(function(key) {\n" +
                "    return [key, purchaseAvgPriceDict[key]];\n" +
                "});\n" +
                "itemsAP.sort(function(a, b){\n" +
                "    if(a[0] < b[0]) return -1;\n" +
                "    if(a[0] > b[0]) return 1;\n" +
                "    return 0;\n" +
                "});\n" +
                "purchaseAvgPriceHTML = '<br><div id=\"chart_div_purchaseAvgPrice\" align=\"center\"></div>';\n" +
                "itemsPS = Object.keys(purchaseSpendingsDict).map(function(key) {\n" +
                "    return [key, purchaseSpendingsDict[key]];\n" +
                "});\n" +
                "var d = new Date();\n" +
                "var n = d.getMonth();\n" +
                "itemsPS.sort(function(a, b){\n" +
                "\taMonth = month.indexOf(a[0]);\n" +
                "\tif (aMonth > n){\n" +
                "\taMonth -= 12;\n" +
                "\t}\n" +
                "\tbMonth = month.indexOf(b[0]);\n" +
                "\tif (bMonth > n){\n" +
                "\tbMonth -= 12;\n" +
                "\t}\n" +
                "    return bMonth - aMonth;\n" +
                "});\n" +
                "purchaseSpendingsHTML = '<br><div id=\"chart_div_purchaseSpendings\" align=\"center\"></div>';\n" +
                "document.getElementsByClassName('col-100nopad')[0].innerHTML = document.getElementsByClassName('col-100nopad')[0].innerHTML + purchaseFromHTML + purchaseDateHTML + purchaseAvgPriceHTML + purchaseSpendingsHTML;\n" +
                "loadCharts(itemsPF, itemsPM, itemsAP, itemsPS);\n" +
                "}\n" +
                "\n" +
                "function loadCharts(itemsPF, itemsPM, itemsAP, itemsPS)\n" +
                "{\n" +
                "google.charts.load('current', {packages: ['corechart', 'bar']});\n" +
                "google.charts.setOnLoadCallback(function() {drawCharts(itemsPF, itemsPM, itemsAP, itemsPS)});\n" +
                "}\n" +
                "\n" +
                "function drawCharts(itemsPF, itemsPM, itemsAP, itemsPS) {\n" +
                "\t  hPF = itemsPF.length * 40;\n" +
                "\t  itemsPF.unshift(['Purchased From', 'Amount']);\n" +
                "      var dataPF = google.visualization.arrayToDataTable(itemsPF);\n" +
                "\t  hPM = itemsPM.length * 40;\n" +
                "\t  itemsPM.unshift(['Purchased Month', 'Amount']);\n" +
                "      var dataPM = google.visualization.arrayToDataTable(itemsPM);\n" +
                "\t  hAP = itemsAP.length * 40;\n" +
                "\t  itemsAP.unshift(['Purchased From', 'Average Price']);\n" +
                "      var dataAP = google.visualization.arrayToDataTable(itemsAP);\n" +
                "\t  hPS = itemsPM.length * 40;\n" +
                "\t  itemsPS.unshift(['Purchased Month', 'Spendings']);\n" +
                "      var dataPS = google.visualization.arrayToDataTable(itemsPS);\n" +
                "      \n" +
                "\t  var optionsPF = {\n" +
                "        chart: {title: 'Places where you have purchased POP!s',\n" +
                "          subtitle: 'Based on the input Purchased From, taken from each POP! in the collection'},\n" +
                "          colors: ['#238842'],\n" +
                "\t\t  titleTextStyle: {color: '#2f952f'},\n" +
                "          bars: 'horizontal',\n" +
                "\t\t  height: hPF,\n" +
                "          axes: { y: { 0: {side: 'right'}}, x: { 0: {side: 'top'} } },\n" +
                "          legend: { position: 'none' },\n" +
                "\t\t  backgroundColor: 'transparent'\n" +
                "      };\n" +
                "\t  var optionsPM = {\n" +
                "        chart: {title: 'Amount of POP!s purchased in the last year',\n" +
                "          subtitle: 'Based on the input Purchased Date, taken from each POP! in the collection with format DD/MM/YYYY (or variants where month is an integer in the middle of the notation, split by a common slash)'},\n" +
                "          colors: ['#238842'],\n" +
                "\t\t  titleTextStyle: {color: '#2f952f'},\n" +
                "          bars: 'horizontal',\n" +
                "\t\t  height: hPM,\n" +
                "          axes: { y: { 0: {side: 'right'}}, x: { 0: {side: 'top'} } },\n" +
                "          legend: { position: 'none' },\n" +
                "\t\t  backgroundColor: 'transparent'\n" +
                "      };\n" +
                "\t  var optionsAP = {\n" +
                "        chart: {title: 'Average price of POP!s purchased from different places',\n" +
                "          subtitle: 'Based on the inputs Purchased Price and Purchased From, taken from each POP! in the collection'},\n" +
                "          colors: ['#238842'],\n" +
                "\t\t  titleTextStyle: {color: '#2f952f'},\n" +
                "          bars: 'horizontal',\n" +
                "\t\t  height: hAP,\n" +
                "          axes: { y: { 0: {side: 'right'}}, x: { 0: {side: 'top'} } },\n" +
                "          legend: { position: 'none' },\n" +
                "\t\t  backgroundColor: 'transparent'\n" +
                "      };\n" +
                "\t  var optionsPS = {\n" +
                "        chart: {title: 'Amount of money spent on POP!s per month in the last year',\n" +
                "          subtitle: 'Based on the inputs Purchased Price and Purchased Date, taken from each POP! in the collection with format DD/MM/YYYY (or variants where month is an integer in the middle of the notation, split by a common slash)'},\n" +
                "          colors: ['#238842'],\n" +
                "\t\t  titleTextStyle: {color: '#2f952f'},\n" +
                "          bars: 'horizontal',\n" +
                "\t\t  height: hPS,\n" +
                "          axes: { y: { 0: {side: 'right'}}, x: { 0: {side: 'top'} } },\n" +
                "          legend: { position: 'none' },\n" +
                "\t\t  backgroundColor: 'transparent'\n" +
                "      };\n" +
                "\t  \n" +
                "      var chartPF = new google.charts.Bar(document.getElementById('chart_div_purchaseFrom'));\n" +
                "      chartPF.draw(dataPF, google.charts.Bar.convertOptions(optionsPF));\n" +
                "      var chartPM = new google.charts.Bar(document.getElementById('chart_div_purchaseDate'));\n" +
                "      chartPM.draw(dataPM, google.charts.Bar.convertOptions(optionsPM));\n" +
                "\t  var chartAP = new google.charts.Bar(document.getElementById('chart_div_purchaseAvgPrice'));\n" +
                "      chartAP.draw(dataAP, google.charts.Bar.convertOptions(optionsAP));\n" +
                "\t  var chartPS = new google.charts.Bar(document.getElementById('chart_div_purchaseSpendings'));\n" +
                "      chartPS.draw(dataPS, google.charts.Bar.convertOptions(optionsPS));\n" +
                "    }" +
                "\t\n" +
                "\texecuteThis();");
    }
}
