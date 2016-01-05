package be.ehb.dt.stripmuseum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.springframework.core.io.Resource;

/**
 * Created by rachouanrejeb on 18/12/15.
 */
public class ItemMenu {

    private String title;
    private int type;
    private String subtitle;
    private int bg;
    private Intent mainclass;

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Intent getMainclass() {
        return mainclass;
    }

    public void setMainclass(Intent mainclass) {
        this.mainclass = mainclass;
    }

    public ItemMenu(String title, int type, String subtitle, int bg, Intent mainclass) {
        this.title = title;
        this.type = type;
        this.subtitle = subtitle;
        this.bg = bg;
        this.mainclass = mainclass;
    }
}
