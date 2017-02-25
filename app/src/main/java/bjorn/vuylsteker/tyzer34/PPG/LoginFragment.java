package bjorn.vuylsteker.tyzer34.PPG;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import bjorn.vuylsteker.tyzer34.PPG.util.Global;
import bjorn.vuylsteker.tyzer34.PPG.util.WebViewClientFactory;

public class LoginFragment extends Fragment implements View.OnClickListener {

    Button btn_login;
    EditText et_mail;
    EditText et_pass;
    WebView mWebView;

    public LoginFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        if (Global.isLoggedIn()){
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, new LogoutFragment());
            ft.commit();
        }

        btn_login = (Button) rootView.findViewById(R.id.login_button);
        et_mail = (EditText) rootView.findViewById(R.id.login_mail);
        et_pass = (EditText) rootView.findViewById(R.id.login_pass);
        mWebView = (WebView) rootView.findViewById(R.id.login_webview);

        et_mail.setText(Global.getMail());
        et_pass.setText(Global.getPass());

        btn_login.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button){
            String mail = et_mail.getText().toString();
            String pass = et_pass.getText().toString();
            if (  ( !mail.equals("")) && ( !pass.equals("")) )
            {
                login(mail, pass);
            }
            else if ( ( !mail.equals("")) )
            {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please enter your password", Toast.LENGTH_SHORT).show();
            }
            else if ( ( !pass.equals("")) )
            {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please enter your email or username", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please enter your credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login(String mail, String pass){
        Global.setMail(mail);
        Global.setPass(pass);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setSavePassword(false);
        WebViewClient login = WebViewClientFactory.loginClient();
        mWebView.setWebViewClient(login);
        mWebView.loadUrl("http://www.poppriceguide.com/wp-login.php");
    }

}