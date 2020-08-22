package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddProductActivity extends AppCompatActivity {
    private EditText productName;
    private EditText productPrice;
    private ProductModel product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productName = findViewById(R.id.productNameEditText);
        productPrice = findViewById(R.id.productPriceEditText);

        Button saveButton = findViewById(R.id.saveBtn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();
                Double price = Double.parseDouble(productPrice.getText().toString());

                product = new ProductModel(name, price);
                returnIntent(product);
            }

        });

    }

    private void returnIntent(ProductModel productModel) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("product", productModel);
        saveDataInFile();
        setResult(AddProductActivity.RESULT_OK, returnIntent);
        finish();
    }

    private void saveDataInFile() {
//        String fileName = "productData.json";
        String fileName = "productData.txt";
        String nameContent = productName.getText().toString()+" ";
        Double priceContent = Double.parseDouble(productPrice.getText().toString());

        FileOutputStream outputStream;
        File file = new File(getFilesDir(), fileName);

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(nameContent.getBytes());
            outputStream.write(priceContent.toString().getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
