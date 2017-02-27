package com.kadev.poc_widget;

import android.app.Activity;
import android.app.Fragment;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

public class PrincessWidgetConfigActivity extends AppCompatActivity {
    public final static String USE_2_BUTTONS = "use 2 buttons";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princess_widget_config);

        // Make sure widget doesn't get placed if user
        //  abandoned config screen
        setResult(RESULT_CANCELED);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.akitty_widget_config, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        RadioGroup mButtonSelectionGroup = null;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_princess_widget_config, container, false);

            mButtonSelectionGroup = (RadioGroup) rootView.findViewById(R.id.radioGroupButtonSelection);
            setupButtons(rootView);

            return rootView;
        }

        void setupButtons(View rootView) {
            rootView.findViewById(R.id.buttonValidConfig).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnDoneOnClick((Button) v);
                }
            });
        }

        private void btnDoneOnClick(Button v) {
            Activity activity = getActivity();

            boolean use2Buttons = mButtonSelectionGroup.getCheckedRadioButtonId() == R.id.radioTwoButtons;
            Log.i("kadev", "[Config] use2Button=%b" + use2Buttons);
            int appWidgetId = resolveAppWidgetId(activity);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(activity);

            Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
            if(options == null)
                options = new Bundle();

            options.putBoolean(USE_2_BUTTONS, use2Buttons);
            appWidgetManager.updateAppWidgetOptions(appWidgetId, options);

            Intent intent = PrincessWidget.getExplicitUpdateIntent(activity);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);  //allows to not send to every activity
            activity.sendBroadcast(intent);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            activity.setResult(RESULT_OK, resultValue);
            activity.finish();
        }

        int resolveAppWidgetId(Activity activity) {
            int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

            Intent createIntent = getActivity().getIntent();

            Bundle extras = createIntent.getExtras();
            if(extras != null) {
                appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
            }

            return appWidgetId;
        }
    }
}
