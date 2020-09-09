package com.ulp.servicio;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class MainActivity extends AppCompatActivity {

    private Intent verMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solicitarPermisos();
    }

    @Override
    protected void onResume() {
        super.onResume();

        verMensajes = new Intent(this,VerMensajes.class);
        startService(verMensajes);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(verMensajes);
    }


    //solicitud de permisos
    public void solicitarPermisos(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
        requestPermissions(new String[]{Manifest.permission.READ_SMS},1000);
    }

    }
}