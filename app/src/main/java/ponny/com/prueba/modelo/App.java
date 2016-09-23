package ponny.com.prueba.modelo;

/**
 * Created by Daniel on 20/09/2016.
 */
public class App {
    /**
     * title
     **/
    private String titulo;
    /**
     * id
     **/
    private String id;
    /**
     * submit_text
     */
    private String submit_text;
    /**
     * icon_img
     **/
    private String urlImg;
    /**
     * header_title
     **/
    private String resumen;
    /**
     * subscribers
     */
    private String seguidores;
    /**
     * description
     */
    private String descripccion;
    /**
     * display_name
     **/
    private String categoria;
    /**public_description**/
    private String publicd_description;

    /*subreddit_type*/
    private String tipo;

    public String getPublicd_description() {
        return publicd_description;
    }

    public void setPublicd_description(String publicd_description) {
        this.publicd_description = publicd_description;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubmit_text() {
        return submit_text;
    }

    public void setSubmit_text(String submit_text) {
        this.submit_text = submit_text;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(String seguidores) {
        this.seguidores = seguidores;
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
