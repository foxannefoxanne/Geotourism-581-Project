package eecs581_582.cortez.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import eecs581_582.cortez.R;
import eecs581_582.cortez.backend.Constants;

/* ********************************************************************
 * HelpActivity is the screen that displays helpful information for
 * the user concerning the page from which they just came. It will pull
 * the display content from '/app/res/values/strings.xml'
 *
 * - The only user-accessible action should be the phone's Back button,
 * which will return them to the previous screen.
 */

public class HelpActivity extends Activity {

    public static final String TAG = HelpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setUpViewFromIntent(getIntent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     *
     * @param intent
     */
    private void setUpViewFromIntent(Intent intent) {

        // Set the WebView
        WebView webView = (WebView) findViewById(R.id.helpActivityWebView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set content depending on where the user has come from:
        String whatitsays = "Coming to Help from";
        String LocalHelpContent = "Error: Caller undefined.";
        Constants.Caller helpFrom = (Constants.Caller) intent.getSerializableExtra(getString(R.string.action_help));
        // helpFrom should now contain a value from eecs581_582.cortez.backend.Constants
        switch (helpFrom) {
            case MAPSELECT_ACTIVITY:
                whatitsays += " MapSelectActivity";
                LocalHelpContent = getString(R.string.HelpActivityContentForMapSelectActivity);
                break;
            case MAP_ACTIVITY: {
                whatitsays += " MapActivity";
                LocalHelpContent = getString(R.string.HelpActivityContentForMapActivity);
                break;
            }
            case INFO_ACTIVITY: {
                whatitsays += " InfoActivity";
                LocalHelpContent = getString(R.string.HelpActivityContentForInfoActivity);
                break;
            }
            case YOUTUBE_ACTIVITY: {
                whatitsays += " YouTubeActivity";
                LocalHelpContent = getString(R.string.HelpActivityContentForYoutubeActivity);
                break;
            }
            case MEDIASELECT_ACTIVITY: {
                whatitsays += " MediaSelectActivity (I think)";
                LocalHelpContent = getString(R.string.HelpActivityContentForMediaSelectActivity);
                break;
            }
            case UNKNOWN: {
                // We couldn't determine where this came from
                whatitsays += "... somewhere...";
                break;
            }
            default:{
                // OH GOD, WHAT DID YOU DO?
                whatitsays += " HELL!";

                /* NOTE: getCallingActivity().getShortClassName() doesn't allow
                 * as much flexibility as the above enum values do, which is why
                 * I chose not to use it. It would also require us as programmers
                 * to perfectly spell out the string representation for each class
                 * for each case above. Enums are a type-safe, sensible choice here.
                 */
                Log.d(TAG, getCallingActivity().getShortClassName());
                break;
            }
        }
        Log.d(TAG, whatitsays);

        webView.loadData(
                LocalHelpContent,
                "text/html",
                "utf-8");
    }

}
