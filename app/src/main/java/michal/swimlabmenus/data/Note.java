package michal.swimlabmenus.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Color;

@Entity
public class Note {

    public Note() {}

    @PrimaryKey(autoGenerate = true)
    public int noteId;

    public String text;

    public String title;

    @ColumnInfo(name = "text_size")
    public int textSize;

    public boolean bold;

    public boolean italic;

    @ColumnInfo(name = "text_color")
    public int textColor;

    @ColumnInfo(name = "title_color")
    public int titleColor;

    @ColumnInfo(name = "background_color")
    public int backgroundColor;

    public static class Builder {

        private String text = "";
        private String title = "";
        private int textSize = 12;
        private boolean bold = false;
        private boolean italic = false;
        private int textColor = Color.BLACK;
        private int titleColor = Color.BLACK;
        private int backgroundColor = Color.WHITE;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder bold() {
            this.bold = true; return this;
        }

        public Builder italic() {
            this.italic = true; return this;
        }

        public Builder textColor(int newColor) {
            this.textColor = newColor; return this;
        }

        public Builder backgroundColor(int bgColor) {
            this.backgroundColor = bgColor; return this;
        }

        public Builder titleColor(int titleColor) {
            this.titleColor = titleColor; return this;
        }

        public Builder title(String title) {
            this.title = title; return this;
        }

        public Note build() {
            return new Note(this);
        }
    }

    private Note(Builder builder) {
        this.text = builder.text;
        this.textSize = builder.textSize;
        this.bold = builder.bold;
        this.italic = builder.italic;
        this.textColor = builder.textColor;
        this.backgroundColor = builder.backgroundColor;
        this.titleColor = builder.titleColor;
        this.title = builder.title;
    }

}
