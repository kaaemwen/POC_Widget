package com.kadev.poc_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class PrincessWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = getWidgetRemoteViews(context);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static RemoteViews getWidgetRemoteViews(Context context){
        Intent buttonIntent = new Intent(context, ShowImageActivity.class);
        buttonIntent.putExtra(ShowImageActivity.EXTRA_IMAGE_RESOURCE_ID, R.drawable.fat_princesses);
        PendingIntent buttonPendingIntent = PendingIntent.getActivity(
                context, 0, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews appWidgetViews = new RemoteViews(context.getPackageName(), R.layout.princess_widget);
        appWidgetViews.setOnClickPendingIntent(R.id.btnPrincess, buttonPendingIntent);

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

