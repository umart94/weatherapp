package com.superweather.weather;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                webView = (WebView) findViewById(R.id.myWebView1);
                webView.addJavascriptInterface(new JavaScriptFunctions(),"AndroidNative");
                //first argument is for java functions
                //second argument is for javascript functions

                webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        /*
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            webView.getSettings().setDatabasePath("/data/data/"+webView.getContext().getPackageName()+"/databases/");

        }
*/


        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //return super.onJsAlert(view, url, message, result);

                //first javascript to java bridge
                new AlertDialog.Builder(MainActivity.this).setMessage(message).setPositiveButton("Ok",null).create().show();

                result.confirm();
                return true;
                //a false  return means client will not handle the js alert
                //we can now use js alerts

                //true means you will have to use the android alert (js to java bridge)
            }


                                   });
        if(savedInstanceState==null) {
            webView.loadUrl("file:///android_asset/www/index.html");
        }
        //webView.loadUrl("http://10.0.2.2:3656/index.html");


        /**********code that will open reloaded html in new webview
         * webView.getSettings().setJavaScriptEnabled(true);
         // webView.loadUrl("file:///android_asset/www/index.html");
         webView.loadUrl("http://10.0.2.2:34680/index.html");
         */


        /**** this code will open reloaded html in same webview
         * webView.getSettings().setJavaScriptEnabled(true);
         * before loadUrl call
         * webView.setWebViewClient(new WebViewClient());
         // webView.loadUrl("file:///android_asset/www/index.html");
         webView.loadUrl("http://10.0.2.2:34680/index.html");
         */
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //return super.onKeyUp(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
           callJavascript("onBack()");

        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater(); inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
    }

    public void callJavascript(String call){
        WebView webView = (WebView) findViewById(R.id.myWebView1);
        webView.loadUrl("javascript:"+call);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_geo) {
            //return true;
           // Toast.makeText(MainActivity.this,"clicked geo",Toast.LENGTH_LONG).show();
            callJavascript("geolocate()");
        }
        else if(id==R.id.action_clear)
        {

            //Toast.makeText(MainActivity.this,"clicked clear",Toast.LENGTH_LONG).show();
                callJavascript("clear()");
        }

        return super.onOptionsItemSelected(item);
    }

    public class JavaScriptFunctions{

        @JavascriptInterface
        public void toast(String message){

            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

        }
        @JavascriptInterface
        public void finish(){
            MainActivity.this.finish();
        }
    }
}
