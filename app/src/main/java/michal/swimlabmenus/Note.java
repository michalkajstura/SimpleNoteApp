package michal.swimlabmenus;

import android.graphics.Color;

public class Note {
    private final String text;
    private final int textSize;
    private final boolean bold;
    private final boolean italic;
    private final int textColor;
    private final int titleColor;
    private final int backgroundColor;

    public static class Builder {
        private String text = "";
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
    }

}
