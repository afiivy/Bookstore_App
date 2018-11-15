package com.example.android.bookstoreapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.bookstoreapp.Data.BookContract.BookEntry;
import com.example.android.bookstoreapp.Data.BookDbHelper;

import static com.example.android.bookstoreapp.Data.BookDbHelper.LOG_TAG;

public class CatalogActivity extends AppCompatActivity {
    private BookDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_catalog );

        //Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( CatalogActivity.this, EditorActivity.class );
                startActivity ( intent );
            }
        } );
        mDbHelper = new BookDbHelper ( this );


    }

    @Override
    protected void onStart() {
        super.onStart ();
        insertBook ();
        displayDatabaseInfo ();
    }

    @SuppressLint("SetTextI18n")
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase ();
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_PRODUCTNAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPPLIERNAME,
                BookEntry.COLUMN_BOOK_SUPPLIERPHONENUMBER};

        Cursor cursor = db.query (
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null );

        TextView displayView = (TextView) findViewById ( R.id.text_view_book );


        try {

            displayView.setText ( "The books table contains " + cursor.getCount () + " books.\n\n" );
            displayView.append ( BookEntry._ID + "-" +
                    BookEntry.COLUMN_BOOK_PRODUCTNAME + " - " +
                    BookEntry.COLUMN_BOOK_PRICE + " - " +
                    BookEntry.COLUMN_BOOK_QUANTITY + " - " +
                    BookEntry.COLUMN_BOOK_SUPPLIERNAME + " - " +
                    BookEntry.COLUMN_BOOK_SUPPLIERPHONENUMBER + "\n" );


            int idColumnIndex = cursor.getColumnIndex ( BookEntry._ID );
            int productNameColumnIndex = cursor.getColumnIndex ( BookEntry.COLUMN_BOOK_PRODUCTNAME );
            int priceColumnIndex = cursor.getColumnIndex ( BookEntry.COLUMN_BOOK_PRICE );
            int quantityColumnIndex = cursor.getColumnIndex ( BookEntry.COLUMN_BOOK_QUANTITY );
            int supplierNameColumnIndex = cursor.getColumnIndex ( BookEntry.COLUMN_BOOK_SUPPLIERNAME );
            int supplierPhoneNumberColumnIndex = cursor.getColumnIndex ( BookEntry.COLUMN_BOOK_SUPPLIERPHONENUMBER );


            while (cursor.moveToNext ()) {

                int currentID = cursor.getInt ( idColumnIndex );
                String currentProductName = cursor.getString ( productNameColumnIndex );
                int currentPrice = cursor.getInt ( priceColumnIndex );
                int currentQuantity = cursor.getInt ( quantityColumnIndex );
                String currentSupplierName = cursor.getString ( supplierNameColumnIndex );
                int currentSupplierPhoneNumber = cursor.getInt ( supplierPhoneNumberColumnIndex );


                displayView.append ( ("\n" + currentID + " - " +
                        currentProductName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhoneNumber) );
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.

            cursor.close ();
        }

    }

    private void insertBook() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase ();

        ContentValues values = new ContentValues ();
        values.put ( BookEntry.COLUMN_BOOK_PRODUCTNAME, "Things Fall Apart" );
        values.put ( BookEntry.COLUMN_BOOK_PRICE, "15" );
        values.put ( BookEntry.COLUMN_BOOK_QUANTITY, BookEntry.QUANTITY_ONE );
        values.put ( BookEntry.COLUMN_BOOK_SUPPLIERNAME, "ReadBooks" );
        values.put ( BookEntry.COLUMN_BOOK_SUPPLIERPHONENUMBER, "1234567" );
        long newRowId = db.insert ( BookEntry.TABLE_NAME, null, values );

        if (newRowId != -1) {
            Log.d(LOG_TAG, "Data inserted successfully with row ID " + newRowId );
        } else {
            Log.d(LOG_TAG, "Insert unsuccessful");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater ().inflate ( R.menu.menu_catalog, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId ()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Respond to a click
                insertBook ();
                displayDatabaseInfo ();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected ( item );
    }

}



