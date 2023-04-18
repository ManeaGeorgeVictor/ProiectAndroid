package com.example.proiect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //CREAM TABELELE:
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryString1 = "create table Myusers(USERNAME,EMAIL,PASSWORD)";
        sqLiteDatabase.execSQL(queryString1);

        String queryString2 = "create table Cart(USERNAME, PRODUCT, PRICE, OTHERTYPE)"; //other type=training drill/equipment
        sqLiteDatabase.execSQL(queryString2);

        String queryString3 = "create table Orderplace(USERNAME, FULLNAME, ADDRESS, CONTACTNUMBER,PINCODE,TOTALAMOUNT,OTHERTYPE)"; //other type=training drill/equipment
        sqLiteDatabase.execSQL(queryString3);
    }

    //UPGRADEAZA TABELUL PE CARE IL VREI:
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void registerNewUser(String username, String email, String password) {
        //CONTENT VALUES E CA UN DICTIONAR CARE INSERESEAZA IN LINIILE DIN TABEL.
        //CHEILE SUNT COLOANELE TABELULUI, IAR VALORILE SUNT DATELE CORESPUNZATOARE FIECAREI COLOANE DIN TABEL, FORMAND O NOUA LINIE IN TABEL
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", username);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", password);

        SQLiteDatabase database = getWritableDatabase();//CA SA PUTEM SCRIE EFECTIV IN TABEL, SA PUTEM INSERA NOUA LINIE(E MAI GENERAL PT INSERT UPDATE DELETE)
        database.insert("Myusers", null, contentValues);//AL 2 LEA PARAMETRU E NULL PT CA EL SE REFERA LA DACA VREI SA LASI VREO COLOANA NULL SI NOI NU VREM
        database.close();
    }

    public int loginUser(String username, String password) {
        int result = 0; //0=nu exista, 1=exista
        String str[] = new String[2];//rawQuery VREA ARGUMENTELE INTR-UN ARRAY DE STRINGURI CA SA STIE CE PUNE IN LOCUL LUI ?
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();//TREBUIE SA FACEM UN SELECT,DECI AVEM NEVOIE DE READABLE DATABASE
        Cursor cursor = db.rawQuery("select * from Myusers where username=? and password=?", str);//? VA FI INLOCUIT PE RAND CU str[0], APOI str[1]
        if (cursor.moveToFirst())//DACA EXISTA LINIA RESPECTIVA IN TABEL
        {
            result = 1;
        }
        return result;
    }

    public void addToCart(String username, String product, float price, String othertype) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", username);
        contentValues.put("PRODUCT", product);
        contentValues.put("PRICE", price);
        contentValues.put("OTHERTYPE", othertype);
        SQLiteDatabase db = getWritableDatabase(); //de data aceasta, vrem sa scriem in ea.
        db.insert("Cart", null, contentValues);
        db.close();
    }

    public int checkIfItemIsAlreadyInCart(String username, String product) //verificam daca linia respectiva exista deja in tabel,pentru a nu o adauga din nou
    {
        int result = 0; //0 daca nu exista, 1 daca exista
        String helpingString[] = new String[2];
        helpingString[0] = username;
        helpingString[1] = product;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Cart where username=? and product=?", helpingString);
        if (cursor.moveToFirst()) //DACA EXISTA LINIA RESPECTIVA IN TABEL
        {
            result = 1;
        }
        db.close();
        return result;
    }

    public void removeCart(String username, String othertype) {
        String helpingString[] = new String[2];
        helpingString[0] = username;
        helpingString[1] = othertype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Cart", "username=? and othertype=?", helpingString);
        db.close();
    }

    public void addOrder(String username,
                         String fullname,
                         String address,
                         String contact,
                         int pincode,
                         float price,
                         String othertype)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("USERNAME",username);
        contentValues.put("FULLNAME",fullname);
        contentValues.put("ADDRESS",address);
        contentValues.put("CONTACTNUMBER",contact);
        contentValues.put("PINCODE",pincode);
        contentValues.put("TOTALAMOUNT",price);
        contentValues.put("OTHERTYPE",othertype);
        SQLiteDatabase db=getWritableDatabase();
        db.insert("Orderplace",null,contentValues);
        db.close();
    }
    public ArrayList getCartData(String username, String othertype) { //returnam produsele cumparate alaturi de preturile aferente
        ArrayList<String> helpingArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase(); //DACA EXISTA LINIA RESPECTIVA IN TABEL
        String helpingString[] = new String[2];
        helpingString[0] = username;
        helpingString[1] = othertype;
        Cursor cursor = db.rawQuery("select * from Cart where username=? and othertype=?", helpingString);
        if (cursor.moveToFirst()) {
            do {
                String product = cursor.getString(1); //sunt indexate de la 1
                String price = cursor.getString(2);
                helpingArray.add(product + "$" + price);
            } while (cursor.moveToNext());
        }
        db.close();
        return helpingArray;
    }

    public ArrayList getAllOrderData(String username){
        ArrayList<String> helpingArray=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String usernameStringArray[]=new String[1];
        usernameStringArray[0]=username;
        Cursor cursor=db.rawQuery("select * from Orderplace where username=?",usernameStringArray);//trebuie dat sub forma de array
        if(cursor.moveToFirst()){
            do{
                helpingArray.add(cursor.getString(1)+"$"+
                        cursor.getString(2)+"$"+
                        cursor.getString(3)+"$"+
                        cursor.getString(4)+"$"+
                        cursor.getString(5)+"$"+
                        cursor.getString(6)+"$"+
                        cursor.getString(7)+"$");
            }while(cursor.moveToNext());
        }
        db.close();
        return helpingArray;
    }
}
