package com.example.cm.circleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cm.circleview.view1.CircleImageView1;
import com.example.cm.circleview.view1.CircleImageView2;
import com.example.cm.circleview.view1.CircleImageView3;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533911636&di=04ec95ec44a1623d52b20ff08727ac78&imgtype=jpg&er=1&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201312%2F05%2F20131205172342_P3nwv.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleImageView1 circleImageView1 = findViewById(R.id.image_id1);
        circleImageView1.build(URL);

        CircleImageView2 circleImageView2 = findViewById(R.id.image_id2);
        circleImageView2.build(URL);

        CircleImageView3 circleImageView3 =findViewById(R.id.image_id3);
        circleImageView3.build(URL);

        com.example.cm.circleview.view2.CircleImageView1 circleImageView21 = findViewById(R.id.image2_id1);
        circleImageView21.build(URL);

        com.example.cm.circleview.view2.CircleImageView2 circleImageView22 = findViewById(R.id.image2_id2);
        circleImageView22.build(URL);

        com.example.cm.circleview.view2.CircleImageView3 circleImageView23 = findViewById(R.id.image2_id3);
        circleImageView23.build(URL);
    }
}
