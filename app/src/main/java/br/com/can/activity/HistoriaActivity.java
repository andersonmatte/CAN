package br.com.can.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.can.R;

public class HistoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        getSupportActionBar().hide();
    }
}
