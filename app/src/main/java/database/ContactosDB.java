package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import modelo.Contactos;

public class ContactosDB extends SQLiteOpenHelper {

    private static final String NOMBRE_DB = "contactos.db";
    private static final int VERSION_DB = 1;

    public ContactosDB(Context context){
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE contactos (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL, " +
                        "telefono TEXT NOT NULL, " +
                        "email TEXT, " +
                        "imagenid TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contactos");
        onCreate(db);
    }

    public void eliminarTodosContactos(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM contactos");
        database.close();
    }

    public void eliminarContacto(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("contactos","id = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public void insertar(Contactos contacto){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre",contacto.getNombre());
        values.put("telefono", contacto.getTelefono());
        values.put("email", contacto.getEmail());
        values.put("imagenid",contacto.getFoto());

        database.insert("contactos", null, values);
        database.close();
    }

    public void actualizar(Contactos contactos){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre",contactos.getNombre());
        values.put("telefono", contactos.getTelefono());
        values.put("email", contactos.getEmail());
        values.put("imagenid",contactos.getFoto());

        database.update("contactos",values, "id = ?", new String[]{String.valueOf(contactos.getId())});
        database.close();
    }

    public List<Contactos> leerTodos(){
        List<Contactos> lista = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM contactos", null);

        if (cursor.moveToFirst()){
            do {
                Contactos contactos = new Contactos(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                lista.add(contactos);
            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return lista;
    }

    public Contactos leerContacto(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Contactos contactos = null;
        Cursor cursor = database.rawQuery("SELECT * FROM contactos WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            contactos = new Contactos(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
        }

        cursor.close();
        database.close();
        return contactos;
    }





}
