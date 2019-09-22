package com.onsemiro.hanpinetree.widgetbuttoneventmodule;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class EventWidget extends AppWidgetProvider {

    private final String ACTION_BTN = "ButtonClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.event_widget);

        Intent intent = new Intent(context, EventWidget.class).setAction(ACTION_BTN);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.button2, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if(action.equals(ACTION_BTN)){
            //버튼 클릭 결과를 로그로 확인합니다.
            Log.d("이벤트클릭 테스트 ","클릭!");
            //버튼 클릭 결과를 위젯 위의 텍스트뷰를 변경함으로 확인합니다.
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.event_widget);
            ComponentName componentName = new ComponentName(context, EventWidget.class);

            remoteViews.setTextViewText(R.id.appwidget_text,"이벤트발생!");
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }

    }
}

