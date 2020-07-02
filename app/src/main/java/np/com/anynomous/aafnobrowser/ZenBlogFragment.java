package np.com.anynomous.aafnobrowser;


import android.os.Bundle;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZenBlogFragment extends Fragment {

    private String currentUrl;
    private WebView zenBlogWebview;
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

    public ZenBlogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zen_blog, container, false);

        currentUrl = "https://hamrobazaar.com/";
        zenBlogWebview = view.findViewById(R.id.zenBlog_webview_id);
        swipeRefreshLayout = view.findViewById(R.id.zenBlog_swipup_refresh_ID);
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

        zenBlogWebview.getSettings().setJavaScriptEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        zenBlogWebview.getSettings().setBuiltInZoomControls(true);
        zenBlogWebview.getSettings().setDisplayZoomControls(false);
        zenBlogWebview.loadUrl(currentUrl);

        zenBlogWebview.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && zenBlogWebview.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });

        zenBlogWebview.setWebViewClient(new WebViewClient() {


            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                currentUrl = url;
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }


        });


    }

    private void webViewGoBack() {
        zenBlogWebview.goBack();
    }


}