package com.kadev.poc_widget;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        }

        private void btnStartAlarmManagerOnClick(Button v) {
            Context context = getActivity();
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        private void btnStopAlarmManagerOnClick(Button v) {
            Context context = getActivity();
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        private static PendingIntent getMyWidgetAlarmPendingIntent(Context context) {
            PendingIntent pendingIntent = null;

            return pendingIntent;
        }
    }
}
