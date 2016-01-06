package be.ehb.dt.stripmuseum.models;


import java.io.Serializable;

public class Ruimte implements Serializable {


    private int id;
    private String name;
    private String image;
    private int info_id;
    private String comic;
    private String creation_date;


    public Ruimte(int id, String name, String image, int info_id, String comic, String creation_date) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.info_id = info_id;
        this.comic = comic;
        this.creation_date = creation_date;
    }


    public Ruimte(){
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getCreation_date() {
        return this.creation_date;
    }
    public String getImage() {
        return this.image;
    }
    public int getInfo_id() {
        return this.info_id;
    }
    public String getComic() {
        return this.comic;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setImage(String image){
        this.image = image;
    }
    public void setInfo_id(int info_id){
        this.info_id = info_id;
    }
    public void setComic(String comic){
        this.comic = comic;
    }
    public void setCreation_date(String creation_date){
        this.creation_date = creation_date;
    }
}

