package com.example.android.bookstoreapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bookstoreapp.Data.BookContract.BookEntry;
import com.example.android.bookstoreapp.Data.BookDbHelper;

public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the book name
     */
    private EditText mProductNameEditText;

    /**
     * EditText field to enter the book price
     */
    private EditText mPriceEditText;

    /**
     * EditText field to enter the book quantity
     */
    private Spinner mQuantitySpinner;

    /**
     * EditText field to enter the book supplier
     */
    private EditText mSupplierNameEditText;

    private EditText mSupplierPhoneNumberEditText;


    private int mQuantity = BookEntry.QUANTITY_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_editor );

        // Find all relevant views that we will need to read user input from
        mProductNameEditText = (EditText) findViewById ( R.id.edit_product_name );
        mPriceEditText = (EditText) findViewById ( R.id.edit_price );
        mQuantitySpinner = (Spinner) findViewById ( R.id.spinner_quantity );
        mSupplierNameEditText = (EditText) findViewById ( R.id.edit_suppliers_name );
        mSupplierPhoneNumberEditText = (EditText) findViewById ( R.id.edit_phonenumber );

        setupSpinner ();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the quantity of books.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter quantitySpinnerAdapter = ArrayAdapter.createFromResource ( this,
                R.array.array_quantity_options, android.R.layout.simple_spinner_item );

        // Specify dropdown layout style - simple list view with 1 item per line
        quantitySpinnerAdapter.setDropDownViewResource ( android.R.layout.simple_dropdown_item_1line );

        // Apply the adapter to the spinner
        mQuantitySpinner.setAdapter ( quantitySpinnerAdapter );

        // Set the integer mSelected to the constant values
        mQuantitySpinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition ( position );
                if (!TextUtils.isEmpty ( selection )) {
                    if (selection.equals ( getString ( R.string.quantity_one ) )) {
                        mQuantity = BookEntry.QUANTITY_ONE; // One
                    } else if (selection.equals ( getString ( R.string.quantity_two ) )) {
                        mQuantity = BookEntry.QUANTITY_TWO; // Two
                    } else if (selection.equals ( getString ( R.string.quantity_three ) )) {
                        mQuantity = BookEntry.QUANTITY_THREE; // Three
                    } else if (selection.equals ( getString ( R.string.quantity_four ) )) {
                        mQuantity = BookEntry.QUANTITY_FOUR; // Four
                    } else if (selection.equals ( getString ( R.string.quantity_five ) )) {
                        mQuantity = BookEntry.QUANTITY_FIVE; // Five
                    } else {
                        mQuantity = BookEntry.QUANTITY_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mQuantity = BookEntry.QUANTITY_UNKNOWN;
            }
        } );
    }

    private void insertBook() {
        String productnameString = mProductNameEditText.getText ().toString ().trim ();
        String priceString = mPriceEditText.getText ().toString ().trim ();
        int price = Integer.parseInt ( priceString );
        String suppliernameString = mSupplierNameEditText.getText ().toString ().trim ();
        String supplierPhonenumberString = mSupplierPhoneNumberEditText.getText ().toString ().trim ();
        int supplierPhonenumber = Integer.parseInt ( supplierPhonenumberString );


        BookDbHelper mDbHelper = new BookDbHelper ( this );

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase ();


        ContentValues values = new ContentValues ();
        values.put ( BookEntry.COLUMN_BOOK_PRODUCTNAME, productnameString );
        values.put ( BookEntry.COLUMN_BOOK_PRICE, priceString );
        values.put ( BookEntry.COLUMN_BOOK_QUANTITY, mQuantity );
        values.put ( BookEntry.COLUMN_BOOK_SUPPLIERNAME, suppliernameString );
        values.put ( BookEntry.COLUMN_BOOK_SUPPLIERPHONENUMBER, supplierPhonenumberString );

        long newRowId = db.insert ( BookEntry.TABLE_NAME, null, values );
        //Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

        if (newRowId == -1) {

            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText ( this, "Error with saving book", Toast.LENGTH_SHORT ).show ();

        } else {

            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText ( this, "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT ).show ();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater ().inflate ( R.menu.menu_editor, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId ()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertBook ();
                //Exit activity
                finish ();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask ( this );
                return true;
        }
        return super.onOptionsItemSelected ( item );
    }
}

