package com.onsemiro.hanpinetree.widgetlistviewmodule;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetListview extends AppWidgetProvider {

    /**
     * 위젯의 크기 및 옵션이 변경될 때마다 호출되는 함수
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_listview);
        views.setTextViewText(R.id.widget_test_textview, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * 위젯이 바탕화면에 설치될 때마다 호출되는 함수
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        // RemoteViewsService 실행 등록시키는 함수
        Intent serviceIntent = new Intent(context, MyRemoteViewsService.class);
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_listview);
        widget.setRemoteAdapter(R.id.widget_listview, serviceIntent);
//        클릭이벤트 인텐트 유보.
        //보내기
        appWidgetManager.updateAppWidget(appWidgetIds, widget);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


}

