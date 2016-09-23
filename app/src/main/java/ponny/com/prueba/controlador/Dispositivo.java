package ponny.com.prueba.controlador;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Daniel on 19/09/2016.
 */
public class Dispositivo {
    /**
     * Reconoce el tipo de dispositivo que se este usando
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
