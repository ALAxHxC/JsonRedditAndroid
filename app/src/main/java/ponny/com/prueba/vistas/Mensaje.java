package ponny.com.prueba.vistas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Daniel on 19/09/2016.
 * Clase encargada de mostrar los mensajes
 */
public class Mensaje {
    private Context mcontext;

    public Mensaje(Context mcontext) {

        this.mcontext = mcontext;
    }

    public void toastmensaje(String mensaje) {
        Toast.makeText(mcontext, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void FinalizarApp(String titulo, String cuerpo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle(titulo);
        builder.setMessage(cuerpo);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);

            }
        });
        builder.create();
        builder.show();

    }
}
