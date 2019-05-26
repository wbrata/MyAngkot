package com.pilihan.hsd.myangkot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

public class PlaceAutoCompleteActivity extends AppCompatActivity {
    TextView tvPickUpFrom,tvDestLocation;
    TextView tvPickUpAddr,tvPickUpLatLng,tvPickUpName;
    TextView tvDestLocAddr,tvDestLocLatLng,tvDestLocName;
    TextView tvJarak;

    public static final int PICK_UP = 0;
    public static final int DEST_LOC = 1;
    private static int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_auto_complete);

        tvPickUpFrom = findViewById(R.id.tvPickupFrom);
        tvPickUpAddr = findViewById(R.id.tvPickupAddr);
        tvPickUpLatLng = findViewById(R.id.tvPickupLatLng);
        tvPickUpName = findViewById(R.id.tvPickupName);

        tvDestLocation = findViewById(R.id.tvDestLocation);
        tvDestLocAddr = findViewById(R.id.tvDestLocAddr);
        tvDestLocLatLng = findViewById(R.id.tvDestLocLatLng);
        tvDestLocName = findViewById(R.id.tvDestLocName);

        tvJarak = findViewById(R.id.viewJarak);

        tvPickUpFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlaceAutoComplete(PICK_UP);
            }
        });

        tvDestLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlaceAutoComplete(DEST_LOC);
            }
        });

    }

    public void showPlaceAutoComplete(int typeLocation){
        REQUEST_CODE = typeLocation;

        AutocompleteFilter filter = new AutocompleteFilter.Builder().setCountry("ID").build();
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(filter)
                    .build(this);
            startActivityForResult(intent,REQUEST_CODE);
        }catch (GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"layanan service tidak tersedia",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            // Tampung Data tempat ke variable
            Place placeData = PlaceAutocomplete.getPlace(this, data);
            if(placeData.isDataValid()){
                Log.d("autocompleteplace data",placeData.toString());

                //dapatkan detail
                String placeAddress = placeData.getAddress().toString();
                LatLng placeLatLng = placeData.getLatLng();
                String placeName = placeData.getName().toString();


                switch (REQUEST_CODE){
                    case PICK_UP:
                        // Set ke widget lokasi asal
                        tvPickUpFrom.setText(placeAddress);
                        tvPickUpAddr.setText("Location Address : " + placeAddress);
                        tvPickUpLatLng.setText("LatLang : " + placeLatLng.toString());
                        tvPickUpName.setText("Place Name : " + placeName);
                        break;
                    case DEST_LOC:
                        // Set ke widget lokasi tujuan
                        tvDestLocation.setText(placeAddress);
                        tvDestLocAddr.setText("Destination Address : " + placeAddress);
                        tvDestLocLatLng.setText("LatLang : " + placeLatLng.toString());
                        tvDestLocName.setText("Place Name : " + placeName);
                        break;
                }
            }else {
                // Data tempat tidak valid
                Toast.makeText(getApplicationContext(), "Invalid Place !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
