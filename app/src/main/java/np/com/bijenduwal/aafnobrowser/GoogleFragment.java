package np.com.bijenduwal.aafnobrowser;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleFragment extends Fragment {

    private ProgressBar google_progressBar;
    private String currentUrl;
    private WebView googleWebview;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    webViewGoBack();
                }
                break;
            }
        }
    };

    public GoogleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google, container, false);
        currentUrl = "http://google.com";
        google_progressBar = view.findViewById(R.id.google_progressBar);
        googleWebview = view.findViewById(R.id.google_webview_id);
        swipeRefreshLayout = view.findViewById(R.id.google_swipup_refresh_ID);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebPage();
            }
        });
        loadWebPage();
        return view;
    }

    private void loadWebPage() {

        googleWebview.getSettings().setJavaScriptEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        googleWebview.getSettings().setBuiltInZoomControls(true);
        googleWebview.getSettings().setDisplayZoomControls(false);
        googleWebview.loadUrl(currentUrl);

        google_progressBar.setVisibility(View.VISIBLE);

        googleWebview.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && googleWebview.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });

        googleWebview.setWebViewClient(new WebViewClient() {


            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                currentUrl = url;
                google_progressBar.setVisibility(View.VISIBLE);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                google_progressBar.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }


        });


    }


    private void webViewGoBack() {
        googleWebview.goBack();
    }
}