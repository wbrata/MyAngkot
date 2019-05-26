package com.pilihan.hsd.myangkot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.pilihan.hsd.myangkot.network.ApiServices;
import com.pilihan.hsd.myangkot.network.InitLibrary;
import com.pilihan.hsd.myangkot.response.LegsItem;
import com.pilihan.hsd.myangkot.response.ResponseRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String API_KEY = "AIzaSyDytAGIgheNOUOyCa3shVpRi_peW2vk3x4";
    private LatLng pickUpLatLng = new LatLng(-7.983908, 112.621391); // Malang
    private LatLng locationLatLng = new LatLng(-6.175110, 106.865039); // Jakarta
    private TextView tvStartAddress, tvEndAddress, tvDuration, tvDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        getSupportActionBar().setTitle("Direction API Maps");
        widgetInit();
        actionRoute();
    }

    private void widgetInit() {
        tvStartAddress = findViewById(R.id.tvStartAddress);
        tvEndAddress = findViewById(R.id.tvEndAddress);
        tvDuration = findViewById(R.id.tvDuration);
        tvDistance = findViewById(R.id.tvDistance);
    }

    private void actionRoute() {
        String lokasiAwal = pickUpLatLng.latitude + "," + pickUpLatLng.longitude;
        String lokasiAkhir = locationLatLng.latitude + "," + locationLatLng.longitude;

        // Panggil Retrofit
        ApiServices api = InitLibrary.getInstance();
        // Siapkan request
        Call<ResponseRoute> routeRequest = api.request_route(lokasiAwal, lokasiAkhir, API_KEY);
        // kirim request
        routeRequest.enqueue(new Callback<ResponseRoute>() {
            @Override
            public void onResponse(Call<ResponseRoute> call, Response<ResponseRoute> response) {

                if (response.isSuccessful()){
                    // tampung response ke variable
                    ResponseRoute dataDirection = response.body();

                    LegsItem dataLegs = dataDirection.getRoutes().get(0).getLegs().get(0);

                    // Dapatkan garis polyline
                    String polylinePoint = dataDirection.getRoutes().get(0).getOverviewPolyline().getPoints();
                    // Decode
                    List<LatLng> decodePath = PolyUtil.decode(polylinePoint);

                    // Gambar garis ke maps
                    mMap.addPolyline(new PolylineOptions().addAll(decodePath)
                            .width(8f).color(Color.argb(255, 56, 167, 252)))
                            .setGeodesic(true);

                    // Tambah Marker
                    mMap.addMarker(new MarkerOptions().position(pickUpLatLng).title("Lokasi Awal"));
                    mMap.addMarker(new MarkerOptions().position(locationLatLng).title("Lokasi Akhir"));
                    // Dapatkan jarak dan waktu
//                    Distance dataDistance = dataLegs.getDistance();
//                    Duration dataDuration = dataLegs.getDuration();

                    // Set Nilai Ke Widget
                    tvStartAddress.setText("start location : " + dataLegs.getStartAddress().toString());
                    tvEndAddress.setText("end location : " + dataLegs.getEndAddress().toString());

//                    tvDistance.setText("distance : " + dataDistance.getText() + " (" + dataDistance.getValue() + ")");
//                    tvDuration.setText("duration : " + dataDuration.getText() + " (" + dataDuration.getValue() + ")");

                    /** START
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */

                    LatLngBounds.Builder latLongBuilder = new LatLngBounds.Builder();
                    latLongBuilder.include(pickUpLatLng);
                    latLongBuilder.include(locationLatLng);

                    // Bounds Coordinata
                    LatLngBounds bounds = latLongBuilder.build();

                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int paddingMap = (int) (width * 0.2); //jarak dari
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, paddingMap);
                    mMap.animateCamera(cu);

                    /** END
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */

                }
            }

            @Override
            public void onFailure(Call<ResponseRoute> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
    }
}
