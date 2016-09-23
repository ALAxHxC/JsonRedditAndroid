package ponny.com.prueba.vistas.listas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ponny.com.prueba.R;
import ponny.com.prueba.modelo.App;
import ponny.com.prueba.persistencia.JsonManager;
import ponny.com.prueba.vistas.AppActivity;
import ponny.com.prueba.vistas.Mensaje;
import ponny.com.prueba.vistas.adapters.AdaptadorApps;

import static ponny.com.prueba.controlador.Dispositivo.isTablet;

/**
 * Muestra listado de apps
 */
public class ListaApps extends AppCompatActivity {
    private JsonManager jsonManager;
    private String contenido;
    private JSONArray apps;
    private List<App> arrayAps;
    private GridView grid;
    private AdaptadorApps adapter;

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
        setContentView(R.layout.activity_lista_apps);
        orientacion();
        jsonManager = new JsonManager();
        arrayAps = new ArrayList<>();
        cargarJson();
        configurarVista();
        new Mensaje(this).toastmensaje(getString(R.string.click_sobre_imagen));
    }

    /**
     * Carga el jsonManager desde cache
     **/
    private void cargarJson() {
        contenido = jsonManager.cargarJson(ListaApps.this);
        try {
            apps = new JSONObject(contenido).getJSONObject("data").getJSONArray("children");
            cargarApps();
            Log.println(Log.ASSERT, "JSON", "El tamaño es" + arrayAps.size());
        } catch (JSONException e) {
            Log.println(Log.INFO, "JSON", "Imposible castear");
            e.printStackTrace();
        }
        //    Log.println(Log.ASSERT,"JSON",contenido);
    }

    /**
     * convierte el jsonManager a objetos
     **/
    private void cargarApps() throws JSONException {
        for (int i = 0; i < apps.length(); i++) {
            JSONObject objeto = apps.getJSONObject(i).getJSONObject("data");
            App app = new App();
            app.setId(objeto.get(getString(R.string.json_id)).toString());
            app.setCategoria(objeto.getString(getString(R.string.json_display_name)));
            app.setTitulo(objeto.getString(getString(R.string.json_display_name)));
            app.setDescripccion(objeto.getString(getString(R.string.json_description)));
            app.setResumen(objeto.getString(getString(R.string.json_title)));
            app.setSeguidores(objeto.getString(getString(R.string.json_subscribers)));
            app.setSubmit_text(objeto.getString(getString(R.string.json_submit_text)));
            app.setUrlImg(objeto.getString(getString(R.string.json_header_img)));
            app.setPublicd_description(objeto.getString(getString(R.string.json_public_description)));
            app.setTipo(objeto.getString(getString(R.string.json_tipo)));
            arrayAps.add(app);
        }
    }

    /**
     * Carga vistas
     **/
    private void configurarVista() {
        getSupportActionBar().setTitle(getResources().getString(R.string.apps));
        adapter = new AdaptadorApps(ListaApps.this, arrayAps);
        grid = (GridView) findViewById(R.id.gridViewElementosEntry);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cargarApp(position);
            }
        });


    }

    /**
     * Carga resumen de la app
     *
     * @param position item seleccionado
     */
    private void cargarApp(int position) {
        Bundle bundle = bundleApp(arrayAps.get(position));
        Intent intent = new Intent(getApplicationContext(), AppActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private Bundle bundleApp(App app) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.json_id), app.getId());
        bundle.putString(getString(R.string.json_display_name), app.getCategoria());
        bundle.putString(getString(R.string.json_description), app.getPublicd_description());
        bundle.putString(getString(R.string.json_resumen), app.getResumen() + "\n" + app.getDescripccion());
        bundle.putString(getString(R.string.json_title), app.getTitulo());
        bundle.putString(getString(R.string.json_header_img), app.getUrlImg());
        bundle.putString(getString(R.string.json_subscribers), app.getSeguidores());
        bundle.putString(getString(R.string.id_json_url), app.getUrlImg());
        bundle.putString(getString(R.string.json_tipo), app.getTipo());
        return bundle;
    }
}
