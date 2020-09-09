package com.ulp.servicio;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import java.util.Date;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class VerMensajes extends Service {
    public VerMensajes() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //inicie un servicio que cada 9000 milisegundos acceda al Content Provider
    final Uri sms = Telephony.Sms.CONTENT_URI;
    final ContentResolver c = getContentResolver();

    Runnable leerMsj = new Runnable() {
        @Override
        public void run() {
            //traemos el while
            while  (true){

                Cursor cursor =c.query(sms,null,null,null,null);

                if(cursor.getCount()>0){
                    int control=0;
                    while(cursor.moveToNext() && control<5){
                        //extraemos el numero del celu
                        String movil = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));
                        //extraemos la fecha del msj
                        String fecha = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.DATE));
                        //parseamos el string a lon de la fecha
                        long dateLong = Long.parseLong(fecha);
                        //instanciamos un objeto fecha con el long
                        Date date = new Date(dateLong);
                        //extraemos el msj
                        String msj = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY));

                        //sacamos por consola
                        Log.d("consola","Mensaje : "+msj+" del Numero :"+movil+" recibido el "+date.toString());

                        //incrementamos el control
                        control++;
                    }
                    try {
                        Thread.sleep(9000); //dormimos el hilo por 9000 ms

                    } catch (InterruptedException e) {
                        Log.e("Error", e.getMessage());
                    }
                }

            }

            //
        }
    };
    Thread lector=new Thread(leerMsj);
    lector.start();
    return START_STICKY;



    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
