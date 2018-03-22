/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int extra = 0;

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean haswhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        EditText name = (EditText) findViewById(R.id.name_view);
        String nameOnCoffee =  name.getText().toString();

        int price  = calculatePrice(quantity);

        if(haswhippedCream)
            extra = extra + 1;
        if(hasChocolate)
            extra = extra + 2;

        price = price + extra * quantity ;


        String priceMessage = createOrderSummary(price, haswhippedCream,hasChocolate,nameOnCoffee);

        //displayMessage(priceMessage);



        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, "anubhavanandsingh@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for "+nameOnCoffee);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }


    /**
     *
     * @param price is the price calculated
     * @return summary message for the order
     */

    private String createOrderSummary(int price ,boolean hasWhippedCream, boolean hasChocolate, String nameOnCoffee ){
        String summary = getString(R.string.order_summary_name,nameOnCoffee);
        summary = summary +"\nAdd Whipped Cream? "+ hasWhippedCream +
                    "\nAdd Chocolate? "+ hasChocolate +
                    "\nQuantity : "+ quantity +
                    "\nTotal : "+ price +"\n"+getString(R.string.thank_you);
        return summary;
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity) {
     return quantity * 5;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
    * This method increments the quantity value
    * */

    public void increment(View view){

        quantity = quantity + 1;
        if(quantity > 100){
            quantity = 100;
            display(quantity);
            Toast.makeText(this,"Too much orders", Toast.LENGTH_SHORT).show();
            return;

        }

        display(quantity);
    }
    /**
     * This method decrements the quantity value
     * */
    public void decrement(View view){

        quantity = quantity - 1;

        if(quantity < 1){
            quantity = 1;
            display(quantity);
            Toast.makeText(this,"Too few orders", Toast.LENGTH_SHORT).show();
        }

        display(quantity);
    }






}