package com.kadev.poc_widget;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            setupButtons(rootView);
            return rootView;
        }

        public void setupButtons(View rootView) {
            rootView.findViewById(R.id.btnChangeWidget).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnChangeWidgetOnClick((Button) v);
                }
            });
            rootView.findViewById(R.id.btnStartAlarmManager).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStartAlarmManagerOnClick((Button) v);
                }
            });
            rootView.findViewById(R.id.btnStopAlarmManager).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStopAlarmManagerOnClick((Button) v);
                }
            });
        }

        private void btnChangeWidgetOnClick(Button v) {
            Context context = getActivity();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            //Get the widget ids to find our widget to modify
            ComponentName appWidgetComponentName = new ComponentName(context, PrincessWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(appWidgetComponentName);

            for (int i = 0; i < appWidgetIds.length; i++){
                int appWidgetId = appWidgetIds[i];

                RemoteViews appWidgetViews = PrincessWidget.getWidgetRemoteViews(context, appWidgetId);
                //findViewId(R.id.appwidget_text).setText("HELLO!!")
                appWidgetViews.setCharSequence(R.id.appwidget_text, "setText", "HELLO!!");
                appWidgetManager.updateAppWidget(appWidgetId, appWidgetViews);
            }
        }

        private void btnStartAlarmManagerOnClick(Button v) {
            Context context = getActivity();
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PrincessWidget.getExplicitUpdatePendingIntent(context);

            long currentTimeMillis = System.currentTimeMillis();
            long intervalMillis = PrincessWidget.EXPLICIT_UPDATE_INTERVAL;
            am.setInexactRepeating(AlarmManager.RTC, currentTimeMillis + intervalMillis,
                    intervalMillis, pendingIntent);
        }

        private void btnStopAlarmManagerOnClick(Button v) {
            Context context = getActivity();
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PrincessWidget.getExplicitUpdatePendingIntent(context);
            am.cancel(pendingIntent);
        }

        private static PendingIntent getMyWidgetAlarmPendingIntent(Context context) {
            PendingIntent pendingIntent = null;

            return pendingIntent;
        }
    }
}
