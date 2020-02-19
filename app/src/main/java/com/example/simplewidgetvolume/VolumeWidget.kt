package com.example.simplewidgetvolume

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Implementation of App Widget functionality.
 */
const val WIDGET_VOLUME = "WIDGET_VOLUME"

class VolumeWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if(WIDGET_VOLUME == intent?.action) {
            val appWidgetId = intent.getIntExtra("appWidgetId", 0)
            updateAppWidget(context!!, AppWidgetManager.getInstance(context), appWidgetId)
        }
        super.onReceive(context, intent)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val intent = Intent(context, VolumeWidget::class.java)
    intent.action = WIDGET_VOLUME
    intent.putExtra("appWidgetId", appWidgetId)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.volume_widget)
    Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show()
    views.setOnClickPendingIntent(R.id.speakerIV, pendingIntent)
    //views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}