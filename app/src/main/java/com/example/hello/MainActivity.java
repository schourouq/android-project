package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    Bitmap ivBitmap;
    Bitmap mutableivBitmap;
    Button btnGrayscale, btnColorize, btnOneColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Bitmap from loaded ImageView
        ivBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coloredimg);
        iv = findViewById(R.id.myIV);


        // Bitmap dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.coloredimg, options);
        int width = options.outWidth;
        int height = options.outHeight;

        // Display dimensions
        TextView tv = findViewById(R.id.txtHello);
        tv.setText("w = " + width + " h = " + height);

        // Permission for bitmap pixels edition
        mutableivBitmap = ivBitmap.copy(Bitmap.Config.ARGB_8888, true);


        // Show a black line in the middle of the image
        blackMiddleLine(mutableivBitmap);

        // Buttons management
        btnGrayscale = findViewById(R.id.btnGrayscale);
        btnColorize = findViewById(R.id.btnColorize);
        btnOneColor = findViewById(R.id.btnOneColor);

        btnGrayscale.setOnClickListener(this);
        btnColorize.setOnClickListener(this);
        btnOneColor.setOnClickListener(this);

        // Display the editable bitmap
        iv.setImageBitmap(mutableivBitmap);


    }

    /**
     * Draws a black line at the center of the image
     * @param bm the bitmap image
     */
    public void blackMiddleLine(Bitmap bm){
        for (int x = 0; x < bm.getWidth(); x++) {
            bm.setPixel(x, bm.getHeight()/2, Color.BLACK);
        }
    }

    /**
     * Converts a colored image to grayscale
     * @param bm the bitmap image
     */
    public void toGray(Bitmap bm) {
        int size = bm.getWidth() * bm.getHeight();
        int[] pixels = new int[size];
        bm.getPixels(pixels, 0,bm.getWidth(), 0 , 0, bm.getWidth(), bm.getHeight());
        for (int i = 0; i < size; i++) {
            int RED = Color.red(pixels[i]);
            int GREEN = Color.green(pixels[i]);
            int BLUE = Color.blue(pixels[i]);

            int toGray = (int) (0.3 * RED + 0.59 * GREEN + 0.11 * BLUE);
            pixels[i] = Color.rgb(toGray, toGray, toGray);

        }
        bm.setPixels(pixels, 0, bm.getWidth() , 0 , 0, bm.getWidth(), bm.getHeight());
    }

    /**
     * Randomly adds a colored tint to the image
     * @param bm the bitmap image
     */
    public void colorize (Bitmap bm) {
        int size = bm.getWidth() * bm.getHeight();
        int[] pixels = new int[size];
        bm.getPixels(pixels, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
        for (int i = 0; i < size; i++) {
            int RED = Color.red(pixels[i]);
            int GREEN = Color.green(pixels[i]);
            int BLUE = Color.blue(pixels[i]);

            float[] hsv = new float[3];

            Color.RGBToHSV(RED, GREEN, BLUE, hsv);
            hsv[0] = 25;

            int newColor = Color.HSVToColor(hsv);
            pixels[i] = newColor;
        }
        bm.setPixels(pixels, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
    }

    /**
     * Keeps only one color from the picture and turns all the others into grayscale
     * @param bm the bitmap image
     * @param hue the chosen color
     */
    public void keepColor (Bitmap bm, float hue) {
        int size = bm.getWidth() * bm.getHeight();
        int[] pixels = new int[size];
        bm.getPixels(pixels, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
        for (int i = 0; i < size; i++) {
            int RED = Color.red(pixels[i]);
            int GREEN = Color.green(pixels[i]);
            int BLUE = Color.blue(pixels[i]);

            float[] hsv = new float[3];

            Color.RGBToHSV(RED, GREEN, BLUE, hsv);

            if (hue - 10 >= hsv[0] || hsv[0] >= hue + 10) {
                int pxColor = (int) (0.3 * RED + 0.59 * GREEN + 0.11 * BLUE);

                pixels[i] = Color.rgb(pxColor, pxColor, pxColor);
            }
            bm.setPixels(pixels, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
        }
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.btnGrayscale:
                toGray(mutableivBitmap);
                break;
            case R.id.btnColorize:
                colorize(mutableivBitmap);
                break;
            case R.id.btnOneColor:
                keepColor(mutableivBitmap, 25);
        }
    }

    /**
     * Unoptimized version of converting a colored image to grayscale
     * @param bm the bitmap image
     */
    /*public void toGray (Bitmap bm) {
        for (int x = 0; x < bm.getWidth(); x++) {
            for (int y = 0; y < bm.getHeight(); y++) {
                int RED = Color.red(bm.getPixel(x,y));
                int GREEN = Color.green(bm.getPixel(x,y));
                int BLUE = Color.blue(bm.getPixel(x,y));

                int pxColor = (int) (0.3 * RED + 0.59 * GREEN + 0.11 * BLUE);

                bm.setPixel(x,y,Color.rgb(pxColor, pxColor, pxColor));

            }
        }
    }*/

    /**
     * ********************************* BASIC ANDROID METHODS *************************************
     */

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("CV", "onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("CV", "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("CV", "onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("CV","onStop");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("CV", "onRestart");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("CV", "onDestroy");
    }

}
