package ponny.com.prueba.vistas;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import ponny.com.prueba.R;
import ponny.com.prueba.controlador.conexion.TareaConexionImagen;

import static ponny.com.prueba.controlador.Dispositivo.isTablet;

public class AppActivity extends AppCompatActivity {

    private TextView name, bundle, categoria, tipo;
    private ImageView imagen;

    private void orientacion() {
        if (isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

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
        setContentView(R.layout.activity_app);
        orientacion();
        cargarVistas();
        obtenerExtras();
    }

    private void obtenerExtras() {
        Bundle extras = getIntent().getExtras();
        String titulo = extras.getString(getString(R.string.json_title));
        String id = extras.getString(getString(R.string.json_id));
        String des = extras.getString(getString(R.string.json_description))+"\n"+extras.getString(getString(R.string.json_resumen));
        String pub = getString(R.string.json_submit_text);
        String seguidores=extras.getString(getString(R.string.json_subscribers));
        Log.println(Log.ASSERT, "json", titulo + id + des + pub);
        name.setText(titulo);
        categoria.setText("Seguidores: "+seguidores);
        bundle.setText(des);
        name.setText(extras.getString(getString(R.string.json_title)).toUpperCase());
        tipo.setText("Tipo: "+extras.getString(getString(R.string.json_tipo)));
        cargarimg(imagen, id, extras.getString(getString(R.string.id_json_url)));

    }

    /**
     * Carga views
     */
    public void cargarVistas() {
        getSupportActionBar().setTitle(getResources().getString(R.string.resumen_app));
        name = (TextView) findViewById(R.id.textViewEntryName);
        categoria = (TextView) findViewById(R.id.textViewCategoria);
        bundle = (TextView) findViewById(R.id.textViewDescription);
        imagen = (ImageView) findViewById(R.id.imageViewImagen);
        tipo=(TextView)findViewById(R.id.textViewTipo);
    }

    /**
     * Carga Imagen desde url dada
     **/

    private void cargarimg(ImageView imageView, String id, String url) {
        Log.println(Log.ASSERT, "IMAGEN", url);
        if (url == null || url.isEmpty() || url.equalsIgnoreCase("null")) {//Caso de que la imagen sea nula
            imagen.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.generic));

            Log.println(Log.ASSERT, "IMAGEN", url + "Asigna nula");
            return;
        }
        TareaConexionImagen tarea = new TareaConexionImagen(this,
                imageView,
                id + this.getResources().getString(R.string.ex_img));
        if (!tarea.cargaImagenExistente()) {
            tarea.execute(url);
        }
    }

}
