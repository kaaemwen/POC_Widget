package com.kadev.poc_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class PrincessWidget extends AppWidgetProvider {
    public static final String ACTION_EXPLICIT_UPDATE = "com.kadev.poc_widget.EXPLICIT_UPDATE";
    public static final long EXPLICIT_UPDATE_INTERVAL = 5000; //5 seconds *** Never use a long interval like this

    static PendingIntent getExplicitUpdatePendingIntent(Context context){
        Intent intent = getExplicitUpdateIntent(context);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        return pendingIntent;
    }

    static Intent getExplicitUpdateIntent(Context context){
        Intent intent = new Intent(context, PrincessWidget.class);
        intent.setAction(ACTION_EXPLICIT_UPDATE);

        return intent;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = getWidgetRemoteViews(context, appWidgetId);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("kadev", "------- onReceive action : " + action);
        if (PrincessWidget.ACTION_EXPLICIT_UPDATE.equalsIgnoreCase(action)){    //If the activity receive a broadcast
            doExplicitUpdate(context, intent);
        } else {
            super.onReceive(context, intent);
        }
    }

    private void doExplicitUpdate(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName appWidgetComponentName = new ComponentName(context, PrincessWidget.class);

        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
        );

        //If appWidgetId we just need it and not all of it
        int[] appWidgetIds = appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID ?
                appWidgetManager.getAppWidgetIds(appWidgetComponentName) :
                new int[]{appWidgetId};

        if (appWidgetIds != null && appWidgetIds.length > 0)
            onUpdate(context, appWidgetManager, appWidgetIds);

    }

    public static RemoteViews getWidgetRemoteViews(Context context, int appWidgetId){
        Intent button1Intent = new Intent(context, ShowImageActivity.class);
        button1Intent.setAction("com.kadev.poc_widget.BUTTON1_CLICK");
        button1Intent.putExtra(ShowImageActivity.EXTRA_IMAGE_RESOURCE_ID, R.drawable.fat_princesses);
        PendingIntent button1PendingIntent = PendingIntent.getActivity(
                context, 0, button1Intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent button2Intent = new Intent(context, ShowImageActivity.class);
        button2Intent.setAction("com.kadev.poc_widget.BUTTON2_CLICK");
        button2Intent.putExtra(ShowImageActivity.EXTRA_IMAGE_RESOURCE_ID, R.drawable.fat_princesses_eating);
        PendingIntent button2PendingIntent = PendingIntent.getActivity(
                context, 0, button2Intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);

        boolean use2Buttons = options.getBoolean(PrincessWidgetConfigActivity.USE_2_BUTTONS, true);
        int resourceId = use2Buttons ? R.layout.princess_widget : R.layout.princess_widget_one_button;

        RemoteViews appWidgetViews = new RemoteViews(context.getPackageName(), resourceId);
        appWidgetViews.setOnClickPendingIntent(R.id.btnPrincess, button1PendingIntent);
        if (use2Buttons)
            appWidgetViews.setOnClickPendingIntent(R.id.btnPrincessTwo, button2PendingIntent);

        return appWidgetViews;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

