package com.toda.consultant;

import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import com.toda.consultant.util.Ikeys;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 通用web页面，需传入title与url
 *
 * @author zhaohaibin
 */
public class CommonWebActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    WebView webview;
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);
        ButterKnife.bind(this);
        url = getBundleStr(Ikeys.KEY_DATA);
        title = getBundleStr(Ikeys.KEY_TITLE);
        initView();
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
        }
    }


    @Override
    public void initView() {

        setTitle(title);
//        topBar.setLeftSecondText("关闭");

        WebSettings ws = webview.getSettings();
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        ws.setBuiltInZoomControls(true); // 设置显示缩放按钮
        ws.setDisplayZoomControls(false);
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
//        if (Build.VERSION.SDK_INT >= 21) {  //解决https链接加载非https图片报错
//            ws.setMixedContentMode( WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
//        }

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar == null) return;
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // 接受所有网站的证书
            }
        });
    }

    public WebView getWebview() {
        return webview;
    }

    public void loadUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        webview.clearCache(true);
        webview.destroy();
        super.onDestroy();
    }

    @Override
    public void onTopLeftClick() {
        if (webview.canGoBack())
            webview.goBack();// 返回前一个页面
        else
            finish();
    }

    @Override
    public void onTopRightClick() {
        finish();
    }

    protected void action(String action, String data) {
    }

    @Override
    public void onTopLeftSecondClick() {
        this.finish();
    }

}
