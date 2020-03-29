package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    DataBaseManagement DataBase;
    EditText Date, Price, Item;
    Button btnAdd, btnSub;
    TextView history,balance;
    int list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Item = (EditText) findViewById(R.id.Item);
        DataBase = new DataBaseManagement(this);
        balance = (TextView) findViewById(R.id.balance);
        Date = (EditText) findViewById(R.id.Date);
        Price = (EditText) findViewById(R.id.Price);
        btnAdd = (Button) findViewById(R.id.Add);
        btnSub = (Button) findViewById(R.id.Sub);
        history = (TextView) findViewById(R.id.history);

        addHistory();
        list_History();
    }

    public void addHistory(){
        list++;
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(Price.getText().toString());
                        boolean result = DataBase.createHistory(Item.getText().toString(), Date.getText().toString(), price);
                        if (result)
                            Toast.makeText(MainActivity.this, "Create Success!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Create Fail!", Toast.LENGTH_LONG).show();
                        list_History();
                        MainActivity.this.Date.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                    }
                }
        );

        btnSub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = -1 * Double.parseDouble(Price.getText().toString());
                        boolean result = DataBase.createHistory(Item.getText().toString(), Date.getText().toString(), price);
                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        list_History();
                        MainActivity.this.Date.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                    }
                }
        );
    }

    public void list_History(){


        list++;
        Cursor result = DataBase.pullData();
        StringBuffer str = new StringBuffer();
        Double balance = 0.0;

        while(result.moveToNext()){
            String priceString = result.getString(3);
            double price = Double.parseDouble(result.getString(3));
            balance += price;

            if (price < 0) {
                str.append("NO." + list + "Spent $");
                priceString = priceString.substring(1);
            }
            else {
                str.append("NO." + list + ". " + "Added $");
                str.append(priceString + " on " + result.getString(2)
                        + " for " + result.getString(1) + "\n");

        }
        MainActivity.this.balance.setText("Current Balance: $" + Double.toString(balance));
        MainActivity.this.history.setText(str);
    }
}
}