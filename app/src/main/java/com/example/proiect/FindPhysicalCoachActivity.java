package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class FindPhysicalCoachActivity extends AppCompatActivity {

    private String[][] physicalCoachesDetails={
            {"Mr Johnson","","","","Click for more details"},
            {"Mr Watkins","","","","Click for more details"},
            {"Mr Peterson","","","","Click for more details"},
            {"Mr Bassoon","","","","Click for more details"},
            {"Mr Steffensen","","","","Click for more details"}
    };

    private int[] physicalCoachesImages={
           R.drawable.mr_johnson,
           R.drawable.mr_watkins,
            R.drawable.mr_peterson,
            R.drawable.mr_bassoon,
            R.drawable.mr_steffensen,
    };

    HashMap<String,String> item;
    ArrayList arrayList;
    SimpleAdapter simpleAdapter;

    ListView listView;
    Button backButton, findPhysicalCoachSendNotificationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_physical_coach);

        //register the broadcast receiver.aceasta bucata de cod a fost pusa aici deoarece, initial, testam ca functioneaza cu un broadcast sender costum, la apasarea pe un text view
        IntentFilter intentFilter=new IntentFilter("android.intent.action.BATTERY_LOW");
        MyBroadcastReceiver objReceiver=new MyBroadcastReceiver();
        registerReceiver(objReceiver,intentFilter);

        listView=findViewById(R.id.listViewFindPhysicalCoach);
        backButton=findViewById(R.id.FindPhyisicalCoachBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindPhysicalCoachActivity.this,HomeActivity.class));
            }
        });
        arrayList=new ArrayList();
        for(int i=0;i<physicalCoachesDetails.length;i++)
        {
            item=new HashMap<String,String>();
            item.put("line1",physicalCoachesDetails[i][0]);
            item.put("line2",physicalCoachesDetails[i][1]);
            item.put("line3",physicalCoachesDetails[i][2]);
            item.put("line4",physicalCoachesDetails[i][3]);
            arrayList.add(item);
        }
        String[] fromArray=new String[]{"line1","line2","line3","line4"};
        int[] toArray=new int[]{R.id.line_1,R.id.line_2,R.id.line_3,R.id.line_4};
        simpleAdapter=new SimpleAdapter(this,
                arrayList,
                R.layout.multi_lines,
                fromArray,
                toArray);

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(FindPhysicalCoachActivity.this,FindPhysicalCoachDetailsActivity.class);
                intent.putExtra("text1",physicalCoachesDetails[i][0]);
                intent.putExtra("text2",physicalCoachesImages[i]);
                startActivity(intent);
            }
        });
    }

    //sending broadcast:
    public void onBroadcastSendBtnClicked(View v)
    {
        Intent intent=new Intent();
        intent.setAction("com.Victor.myBroadcastMessage");
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }
}