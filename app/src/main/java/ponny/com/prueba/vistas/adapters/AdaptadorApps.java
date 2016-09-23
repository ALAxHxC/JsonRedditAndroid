package ponny.com.prueba.vistas.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ponny.com.prueba.R;
import ponny.com.prueba.controlador.conexion.TareaConexionImagen;
import ponny.com.prueba.modelo.App;
import ponny.com.prueba.vistas.imagenes.Fondos;

/**
 * Created by Daniel on 20/09/2016.
 */
public class AdaptadorApps extends BaseAdapter {
    Context mContext;
    Fondos fondos;
    List<App> listaApps;
    int contador = 0;

    public AdaptadorApps(Context mContext, List<App> listaApps) {
        this.mContext = mContext;
        this.listaApps = listaApps;
        fondos = new Fondos(mContext);
    }

    @Override
    public int getCount() {
        return listaApps.size();
    }

    @Override
    public Object getItem(int position) {
        return listaApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        App app = listaApps.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.lista_entrys, null);
        TextView descripccion = (TextView) convertView.findViewById(R.id.textViewAutorEntry);
        TextView name = (TextView) convertView.findViewById(R.id.textViewNameEntry);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageViewIcoEntry);
       descripccion.setText("Descargas "+app.getSeguidores()+"\n"+app.getResumen());
        name.setText(app.getTitulo().toUpperCase());

        darFondo(convertView);
        contador++;
        cargarimg(img, app);

        return convertView;
    }




    /**
     * Carga Imagen desde url dada
     **/

    private void cargarimg(ImageView imageView, App app) {
        Log.println(Log.ASSERT, "IMAGEN", app.getUrlImg());
        if (app.getUrlImg() == null || app.getUrlImg().isEmpty() || app.getUrlImg().equalsIgnoreCase("null")) {//Caso de que la imagen sea nula
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.generic));
            Log.println(Log.ASSERT, "IMAGEN", app.getUrlImg() + "Asigna nula");
            return;
        }
        TareaConexionImagen tarea = new TareaConexionImagen(mContext,
                imageView,
                app.getId() + mContext.getResources().getString(R.string.ex_img));
        if (!tarea.ValidarConexion()) {
            tarea.execute(app.getUrlImg());
        }
    }

    /**
     * Construye el fondo intercalado
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void darFondo(View view) {
        if (contador == fondos.getFondos().size()) {
            contador = 0;
        }
        if (contador < fondos.getFondos().size()) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(fondos.getFondos().get(contador));
            } else {
                view.setBackground(fondos.getFondos().get(contador));
            }
        }
    }
}
