package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= findViewById(R.id.list_view_song_id);

        run_time_permission();
    }

    public void run_time_permission(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        display_s0ngs();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }
    public ArrayList<File> find_song(File file)
    {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File single_file: files)
        {
            if (single_file.isDirectory() && !single_file.isHidden())
            {
                arrayList.addAll(find_song(single_file));
            }
            else{
                if (single_file.getName().endsWith(".mp3") || single_file.getName().endsWith(".wav"))
                {
                    arrayList.add(single_file);
                }
            }
        }
        return arrayList;
    }

    void display_s0ngs(){
        final  ArrayList<File> my_songs = find_song(Environment.getExternalStorageDirectory());
        items= new String[my_songs.size()];

        for (int i = 0 ; i< my_songs.size(); i++)
        {
            items[i] = my_songs.get(i).getName().toString().replace(".mp3" , "").replace(".wav", "");


        }
      //  ArrayAdapter<String> my_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
      //  listView.setAdapter(my_adapter);

        custom_Adapter custom_adapter = new custom_Adapter();
        listView.setAdapter(custom_adapter);

    }


    class custom_Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View my_view = getLayoutInflater().inflate(R.layout.list_items, null);
            TextView text_song = my_view.findViewById(R.id.txt_song_id);
            text_song.setSelected(true);
            text_song.setText(items[i]);

            return my_view;
        }
    }
}