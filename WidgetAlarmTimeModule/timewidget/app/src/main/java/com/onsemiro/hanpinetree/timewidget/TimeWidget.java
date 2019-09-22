package com.onsemiro.hanpinetree.timewidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.CalendarView;
import android.widget.RemoteViews;

import java.sql.Time;
import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class TimeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Calendar current = Calendar.getInstance(); //캘린더 싱글톤
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.time_widget);
        remoteViews.setTextViewText(R.id.time_widget_date_textview, current.get(Calendar.YEAR)+"년" +(current.get(Calendar.MONTH)+1)+"월"+current.get(Calendar.DAY_OF_MONTH)+"일"+current.get(Calendar.HOUR_OF_DAY)+"시" );
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(appWidgetId,remoteViews);

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.time_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.time_widget);
        Calendar cal = Calendar.getInstance();
        remoteViews.setTextViewText(R.id.time_widget_date_textview, cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.time_widget_date_textview, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
       Intent intent = new Intent(context, TimeWidget.class);
       intent.putExtra("mode","time");
       PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, 0);

       AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
       alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, pendingIntent);

    }

    //마지막 제거되는 순간
    @Override
    public void onDisabled(Context context) {
       //새로고침 기능 넣을거, 알람기능 등록!
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimeWidget.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,0);
        alarmManager.cancel(pendingIntent);//알람 해제
        pendingIntent.cancel(); //인텐트 해제

    }

    /**
     * AppWidgetProvider가 브로드캐스트 하위 클래스이기 때문에 이와같은 onReceive함수가 있다.
     *      * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getStringExtra("mode") != null){
            Calendar current = Calendar.getInstance(); //캘린더 싱글톤
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.time_widget);
            remoteViews.setTextViewText(R.id.time_widget_date_textview, current.get(Calendar.HOUR_OF_DAY)+":"+current.get(Calendar.MINUTE));
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(new ComponentName(context, TimeWidget.class),remoteViews);

        }else {
            super.onReceive(context, intent);
        }

    }
}

