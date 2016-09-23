package ponny.com.prueba.vistas.imagenes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;


import java.util.ArrayList;
import java.util.List;

import ponny.com.prueba.R;

/**
 * Created by Daniel on 11/06/2016.
 */
public class Fondos {
    Context mContext;

    public Fondos(Context mContext) {
        this.mContext = mContext;
    }

    public List<Drawable> getFondos() {
        List<Drawable> lista = new ArrayList<>();
        lista.add(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.banner_1, null));
        lista.add(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.banner_2, null));
        lista.add(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.banner_3, null));
        lista.add(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.banner_4, null));
        return lista;

    }

    public Drawable getGeneral() {
        return ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.fondo, null);
    }
}
