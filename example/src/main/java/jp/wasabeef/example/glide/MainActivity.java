package jp.wasabeef.example.glide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.example.glide.MainAdapter.Type;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    List<Type> dataSet = new ArrayList<>();
    dataSet.add(Type.Mask);
    dataSet.add(Type.NinePatchMask);
    dataSet.add(Type.CropTop);
    dataSet.add(Type.CropCenter);
    dataSet.add(Type.CropBottom);
    dataSet.add(Type.CropSquare);
    dataSet.add(Type.CropCircle);
    dataSet.add(Type.ColorFilter);
    dataSet.add(Type.Grayscale);
    dataSet.add(Type.RoundedCorners);
    dataSet.add(Type.Blur);
    dataSet.add(Type.Toon);
    dataSet.add(Type.Sepia);
    dataSet.add(Type.Contrast);
    dataSet.add(Type.Invert);
    dataSet.add(Type.Pixel);
    dataSet.add(Type.Sketch);
    dataSet.add(Type.Swirl);
    dataSet.add(Type.Brightness);
    dataSet.add(Type.Kuawahara);
    dataSet.add(Type.Vignette);

    recyclerView.setAdapter(new MainAdapter(this, dataSet));
  }
}
