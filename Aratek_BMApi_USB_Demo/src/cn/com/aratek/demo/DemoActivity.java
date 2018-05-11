package cn.com.aratek.demo;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
public class DemoActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Config.HAS_FINGERPRINT_DEVICE) {
            createTab(getString(R.string.fingerprint), new Intent(this, FingerprintDemo.class));
        }
        if (Config.HAS_IDCARD_DEVICE) {
            createTab(getString(R.string.idcard), new Intent(this, IDCardDemo.class));
        }

        final TabWidget tabWidget = getTabHost().getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            tabWidget.getChildAt(i).getLayoutParams().height = 52;
        }
    }

    private void createTab(String text, Intent intent) {
        TabHost tabHost = getTabHost();
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        TextView tv = (TextView) view.findViewById(R.id.title);
        tv.setText(text);
        tabHost.addTab(tabHost.newTabSpec(text).setIndicator(view).setContent(intent));
    }

}