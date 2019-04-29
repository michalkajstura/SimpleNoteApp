package michal.swimlabmenus.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Note implements Parcelable {

    public Note() {}

    @PrimaryKey(autoGenerate = true)
    private int noteId;

    private String text;

    private String title;

    @ColumnInfo(name = "text_size")
    private int textSize;

    private boolean bold;

    private boolean italic;

    @ColumnInfo(name = "text_color")
    private int textColor;

    @ColumnInfo(name = "background_color")
    private int backgroundColor;

    private int font;

    public static class Builder {

        private int noteId;
        private String text = "";
        private String title = "";
        private int textSize = 12;
        private boolean bold = false;
        private boolean italic = false;
        private int textColor = Color.BLACK;
        private int backgroundColor = Color.WHITE;
        private int font = 0;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder noteId(int noteId) {
            this.noteId = noteId;
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

        public Builder title(String title) {
            this.title = title; return this;
        }

        public Builder font(int font) {
            this.font = font; return this;
        }

        public Builder copy(Note another) {
            noteId = another.noteId;
            text = another.text;
            title = another.title;
            bold = another.bold;
            italic = another.italic;
            textSize = another.textSize;
            textColor = another.textColor;
            backgroundColor = another.backgroundColor;
            font = another.font;
            return this;
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
        this.title = builder.title;
        this.font = builder.font;
    }

    public Note(Parcel in) {
        this.noteId = in.readInt();
        this.text = in.readString();
        this.textSize = in.readInt();
        this.bold = in.readInt() != 0;
        this.italic =  in.readInt() != 0;
        this.textColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.title = in.readString();
        this.font = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noteId);
        dest.writeString(text);
        dest.writeInt(textSize);
        dest.writeInt(bold ? 1 : 0);
        dest.writeInt(italic ? 1 : 0);
        dest.writeInt(textColor);
        dest.writeInt(backgroundColor);
        dest.writeString(title);
        dest.writeInt(font);
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };


    public int getNoteId() {
        return noteId;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public int getTextSize() {
        return textSize;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }
}
