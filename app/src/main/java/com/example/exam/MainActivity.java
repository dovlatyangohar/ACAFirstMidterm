package com.example.exam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button addProductButton;
    String productName;
    Double productPrice;
    List<ProductModel> productList;
    TextView totalSumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProductButton = findViewById(R.id.addProductBtn);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });
        totalSumTextView = findViewById(R.id.totalPriceTextView);


        productList = new ArrayList<>();


    }

    static final int REQUEST_CODE = 1;

    private void sendIntent() {
        Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    ProductModel productModel = data.getParcelableExtra("product");
                    if (productModel != null) {
                        productName = productModel.getProductName();
                        productPrice = productModel.getProductPrice();


                        //        showDataFromFile();

                        productList.add(new ProductModel(productName, productPrice));

                        totalSumTextView.setText("Total Price is " + totalSum(productList));
                        totalSumTextView.setVisibility(View.VISIBLE);
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        final ProductAdapter adapter = new ProductAdapter(productList);


                        adapter.setListener(new ProductAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(ProductModel product) {
                                openDeleteDialog(product, adapter);
                            }
                        });


                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(adapter);
                    }
                } else
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void openDeleteDialog(final ProductModel product, final ProductAdapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                productList.remove(product);
                adapter.notifyDataSetChanged();

                totalSumTextView.setText("Total Price is " + totalSum(productList));

                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle("Remove item");
        dialog.setMessage("Are you sure you want to remove this product?");
        dialog.show();

    }

    private Double totalSum(List<ProductModel> list) {
        Double sum = 0.0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getProductPrice();
        }
        return sum;

    }

//    private void showDataFromFile() {
//        File showFile = new File(getFilesDir(),"productData.txt");
//        StringBuilder text = new StringBuilder();
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(showFile));
//            String line;
//            while ((line = br.readLine())!=null){
//                text.append(line);
//                text.append("\n");
//            }
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        text.toString();
//        productName = text.toString().split("[0-9]")[0];
//
//
//    }
}
