package com.example.android.labwork_2_9_1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);

        List<String> wordList = new ArrayList<>();

        // Адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, wordList);
        listView.setAdapter(adapter);

        Button btnUpdate = findViewById(R.id.button_update);
        btnUpdate.setOnClickListener(v -> {
            if (service != null) {
                // Запускаем Сервис
                startService(new Intent(this, MyService.class));

                Toast.makeText(MainActivity.this, "Размер массива: " +
                                service.getWordList().size(), Toast.LENGTH_SHORT).show();

                wordList.clear();
                wordList.addAll(service.getWordList());
                // Обновляем список
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Запускаем сервис
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Останавливаем сервис
        unbindService(mConnection);
    }

    // Связь с Сервисом
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MyService.MyBinder b = (MyService.MyBinder) binder;
            service = b.getService();

            Toast.makeText(MainActivity.this, "Связь с Сервисом установлена",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }
    };
}