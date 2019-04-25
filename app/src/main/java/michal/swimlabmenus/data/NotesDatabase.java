package michal.swimlabmenus.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class}, version = 2, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteRepository noteRepo();
    private static volatile NotesDatabase instance;

    public static synchronized NotesDatabase getInstance(Context context) {
        if (instance == null)
            instance = create(context);
        return instance;
    }

    public NotesDatabase() {}

    private static NotesDatabase create(final Context context) {
        return Room.databaseBuilder(context,
            NotesDatabase.class, "notes-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}
