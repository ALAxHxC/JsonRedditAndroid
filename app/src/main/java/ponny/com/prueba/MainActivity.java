package ponny.com.prueba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import ponny.com.prueba.controlador.conexion.Internet;
import ponny.com.prueba.controlador.conexion.TareaConexionJSON;
import ponny.com.prueba.persistencia.JsonManager;
import ponny.com.prueba.vistas.listas.ListaApps;
import ponny.com.prueba.vistas.Mensaje;

import static ponny.com.prueba.controlador.Dispositivo.isTablet;

public class MainActivity extends AppCompatActivity {


    private Mensaje mensajes;
    private JsonManager jsonManager;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientacion();
        Log.println(Log.ASSERT, "NUEVA COFIG", "NEUVA");
        //here you can handle orientation change
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        orientacion();
        cargarVistas();
        validadDatos();
    }

    /**
     * Genera un fullscreen en la acitividad
     **/
    public void setFullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    /**
     * Define la orientacion en caso que haya un cambio en el dispositivo
     */
    private void orientacion() {
        if (isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    /**
     * Elementos activity
     */
    private void cargarVistas() {
        mensajes = new Mensaje(MainActivity.this);
        ImageView imagen = (ImageView) findViewById(R.id.imageViewInicio);
        imagen.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.splash, null));
        jsonManager = new JsonManager();
    }

    /**
     * valida si se debe descargar o usar cache
     **/
    private void validadDatos() {
        if (Internet.SalidaInternet(MainActivity.this)) {
            iniciarDescarga();
        } else if (cargarJson()) {
            Log.println(Log.INFO, "MAIN", "Encontro jsonManager");
            mensajes.toastmensaje(getString(R.string.funcion_cache));
            Splash();
        } else {
            mensajes.FinalizarApp(getString(R.string.error), getString(R.string.fallo_internet));
        }
    }

    /**
     * Envia a lista de apps
     **/

    private void Splash() {
        mensajes.toastmensaje(getResources().getString(R.string.caragando));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(getApplicationContext(), ListaApps.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, Toast.LENGTH_SHORT + 1000);

    }

    /**
     * Solicita descarga
     */
    private void iniciarDescarga() {
        TareaConexionJSON tarea = new TareaConexionJSON(this, mHandlerWS, new ProgressDialog(MainActivity.this));
        tarea.execute();
    }

    /**
     * Handler web rest service
     */
    private final Handler mHandlerWS = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 2:

                    String respuesta = msg.getData().getString("result");
                    if (respuesta.startsWith("ERROR")) {
                        mensajes.toastmensaje(getResources().getString(R.string.fallo_internet));
                    } else {

                        guardarJson(respuesta);
                        Splash();
                    }
                    break;
            }
        }
    };

    /**
     * Valida si ya tiene almacenado el JSON
     *
     * @return validacion
     */
    private boolean cargarJson() {
        return jsonManager.validarJson(MainActivity.this);
    }

    /**
     * Guarda JsonManager
     **/
    private void guardarJson(String json) {
        JsonManager manager = new JsonManager();
        manager.guardarJson(json, MainActivity.this, mensajes);
    }

}
