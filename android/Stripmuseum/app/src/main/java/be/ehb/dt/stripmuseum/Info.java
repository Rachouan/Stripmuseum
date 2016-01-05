package be.ehb.dt.stripmuseum;

/**
 * Created by rachouanrejeb on 04/01/16.
 */
public class Info {

    private int il_id;
    private int i_id;
    private String info;
    private String lang;
    private String name;
    private String image;


    public Info(int il_id, int i_id, String info, String lang, String name) {
        this.il_id = il_id;
        this.i_id = i_id;
        this.info = info;
        this.lang = lang;
        this.name = name;
    }

    public Info(){

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIl_id() {
        return il_id;
    }

    public void setIl_id(int il_id) {
        this.il_id = il_id;
    }

    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
