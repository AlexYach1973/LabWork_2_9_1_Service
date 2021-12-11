package com.example.android.labwork_2_9_1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private final IBinder mBinder = new MyBinder();
    private final ArrayList<String> list = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int rnd = (int) (Math.random() * 5);

        if (list.size() >= 15) { // Ограничили размер списка
            list.remove(0); // Удаляем первый єлемент
        }
        switch (rnd) {
            case 0:
                list.add("Linux");
                break;
            case 1:
                list.add("Android");
                break;
            case 2:
                list.add("iPhone");
                break;
            case 3:
                list.add("Windows");
                break;
            case 4:
                list.add("custom OS");
                break;
            default:
                list.add("new OS");
                break;
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public List<String> getWordList() {
        return list;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

}