package com.example.testplz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Shop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        TextView buttonGoHomeShop = findViewById(R.id.GoHomeShop);
        buttonGoHomeShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this, MainActivity.class);
                startActivity(intent);
            }
        });

        setupPopupButton(R.id.motoGpButton, "Moto GP", "motogp", 35, 25, 10, 5, 0, 5);
        setupPopupButton(R.id.rs6Button, "Audi rs6", "rs6", 155, 85, 25, 18, 5, 4);
        setupPopupButton(R.id.urusButton, "Urus", "urus", 260, 125, 40, 28, 18, 11);
        setupPopupButton(R.id.gtrButton, "Nissan GTR", "gtr", 590, 180, 65, 40, 38, 21);
        setupPopupButton(R.id.gt500Button, "Shelby Gt500", "gt500", 870, 280, 80, 65, 58, 30);
        setupPopupButton(R.id.aventadorButton, "Aventador", "aventador", 1290, 450, 120, 78, 78, 32);
        setupPopupButton(R.id.buggatiButton, "Buggati Chiron", "buggati", 2295, 810, 160, 98, 98, 45);
        setupPopupButton(R.id.supraButton, "Toyota Supra", "supra", 3275, 1250, 250, 120, 128, 78);
    }

    private void setupPopupButton(int buttonId, final String title, final String idVehicle, final int cashMin, final int cashMax, final int oilMin, final int oilMax, final int ferrariMin, final int ferrariMax) {
        TextView popupButton = findViewById(buttonId);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(idVehicle, title, getRandomValue(cashMin, cashMax), getRandomValue(oilMin, oilMax), getRandomValue(ferrariMin, ferrariMax));
            }
        });
    }

    private void showPopup(String idVehicle, String name, int priceCash, int priceOil, int priceFerrari) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Acheter l'item")
                .setMessage("Voulez-vous acheter " + name + " ?\n\nCash: " + priceCash + "\nOil: " + priceOil + "\nFerrari: " + priceFerrari)
                .setPositiveButton("BUY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Shop.this);

                        if(sharedPreferences.getInt("cash", 0) < priceCash || sharedPreferences.getInt("oil", 0) < priceOil || sharedPreferences.getInt("ferrari", 0) < priceFerrari) {
                            Toast.makeText(Shop.this, "You don't have enought stuff to buy :  " + name +  "...", Toast.LENGTH_LONG).show();
                        } else {
                            deleteMoney(priceCash, priceOil, priceFerrari);
                            sharedPreferences.edit().putInt(idVehicle, sharedPreferences.getInt(idVehicle, 0) + 1).apply();
                            Toast.makeText(Shop.this, "You buy " + name +  " !", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteMoney(int priceCash, int priceOil, int priceFerrari) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cash", sharedPreferences.getInt("cash", 0) - priceCash);
        editor.putInt("oil", sharedPreferences.getInt("oil", 0) - priceOil);
        editor.putInt("ferrari", sharedPreferences.getInt("ferrari", 0) - priceFerrari);

        if(sharedPreferences.getInt("cash", 0) <= 0) {editor.putInt("cash", 0);}
        if(sharedPreferences.getInt("oil", 0) <= 0) {editor.putInt("oil", 0);}
        if(sharedPreferences.getInt("ferrari", 0) <= 0) {editor.putInt("ferrari", 0);}
        editor.apply();

    }

    private int getRandomValue(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) + min; // Valeur aléatoire entre 1 et 10 inclus
    }
}
