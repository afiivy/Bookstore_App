package com.example.android.bookstoreapp.Data;

import android.provider.BaseColumns;

public final class BookContract {
    private BookContract() {
    }

    public static final class BookEntry implements BaseColumns {
        public final static String TABLE_NAME = "bookstore";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_BOOK_PRODUCTNAME = "ProductName";
        public final static String COLUMN_BOOK_PRICE = "Price";
        public final static String COLUMN_BOOK_QUANTITY = "Quantity";
        public final static String COLUMN_BOOK_SUPPLIERNAME = "SupplierName";
        public final static String COLUMN_BOOK_SUPPLIERPHONENUMBER = "SupplierPhoneNumber";

        public static final int QUANTITY_UNKNOWN = 0;
        public static final int QUANTITY_ONE = 1;
        public static final int QUANTITY_TWO = 2;
        public static final int QUANTITY_THREE = 3;
        public static final int QUANTITY_FOUR = 4;
        public static final int QUANTITY_FIVE = 5;


    }
}
