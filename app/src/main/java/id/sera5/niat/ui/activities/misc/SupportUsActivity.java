package id.sera5.niat.ui.activities.misc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.sera5.niat.R;
import id.sera5.niat.ui.activities.BaseActivity;

public class SupportUsActivity extends BaseActivity {

    @BindView(R.id.go_watch_add)
    Button goWatchAdd;
    @BindView(R.id.go_buy_pro)
    Button goBuyPro;
    @BindView(R.id.go_donate)
    Button goDonate;
    @BindView(R.id.adView)
    AdView adView;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_us);
        ButterKnife.bind(this);

        setTitle("Dukung Kami");

        setBackButton();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        MobileAds.initialize(this,
                getString(R.string.ad_donate));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_donate));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @OnClick({R.id.go_watch_add, R.id.go_buy_pro, R.id.go_donate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_watch_add:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                break;
            case R.id.go_buy_pro:
                break;
            case R.id.go_donate:
                break;
        }
    }
}
