package ponny.com.prueba.persistencia;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ponny.com.prueba.R;
import ponny.com.prueba.vistas.Mensaje;

/**
 * Created by Daniel on 20/09/2016.
 */
public class JsonManager {

    /**
     * Valida si existe json
     **/
    public boolean validarJson(Context context) {
        try {
            FileInputStream finput = context.openFileInput(context.getString(R.string.apps_file));
            String line;
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            try {

                br = new BufferedReader(new InputStreamReader(finput));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;

            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //  json = sb.toString();
            Log.println(Log.INFO, "JSON", "Encontro JSON");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;

        }
    }

    /**
     * Carga JsonManager
     **/
    public String cargarJson(Context context) {
        try {
            FileInputStream finput = context.openFileInput(context.getString(R.string.apps_file));
            String line;
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            try {

                br = new BufferedReader(new InputStreamReader(finput));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();


            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Guarda json
     **/
    public void guardarJson(String json, Context mContext, Mensaje mensajes) {

        /** Si no encuentra el archivo guardar **/

        FileOutputStream fout = null;
        try {
/** elimina archivo y recrea**/
            eliminarJson(mContext, mensajes);
            fout = mContext.openFileOutput(mContext.getString(R.string.apps_file), Context.MODE_PRIVATE);
            guardarCacheJson(json, fout, mensajes, mContext);
            Log.println(Log.INFO, "JSON", "Guardo JsonManager");

        } catch (FileNotFoundException e1) {
            mensajes.FinalizarApp(mContext.getString(R.string.error), mContext.getString(R.string.error_json));
            e1.printStackTrace();
        }


    }

    /**
     * Guarda jsonManager en el cache
     **/
    private void guardarCacheJson(String json, FileOutputStream fout, Mensaje mensajes, Context mContext) {
        byte[] contentInBytes = json.getBytes();
        try {
            fout.write(contentInBytes);
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
            mensajes.FinalizarApp(mContext.getString(R.string.error), mContext.getString(R.string.error_json));
        }
    }

    /**
     * Elimina el json existente
     * @param mContext
     * @param mensajes
     */
    private void eliminarJson(Context mContext, Mensaje mensajes) {
        File f = new File(mContext.getApplicationContext().getCacheDir(), mContext.getString(R.string.apps_file));
        if (f.exists()) {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                mensajes.FinalizarApp(mContext.getString(R.string.error), mContext.getString(R.string.error_json));
                e.printStackTrace();
            }
        }
    }
}
