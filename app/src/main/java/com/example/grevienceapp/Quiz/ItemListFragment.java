package com.example.grevienceapp.Quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.grevienceapp.R;

public class ItemListFragment extends Fragment {
    String[] AndroidOS = new String[] { "Cupcake","Donut","Eclair","Froyo","Gingerbread","Honeycomb","Ice Cream SandWich", "Jelly Bean","KitKat","LolliPop","MarshMallow","Nougat","Oreo","Cupcake","Donut","Eclair","Froyo","Gingerbread","Honeycomb","Ice Cream SandWich", "Jelly Bean","KitKat","LolliPop","MarshMallow","Nougat","Oreo"};
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.listview, container, false);
        lv=(ListView)view.findViewById(R.id.lst);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, AndroidOS);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TextFragment txtfrag = (TextFragment)getFragmentManager().findFragmentById(R.id.fragment2);

                lv.setSelector(android.R.color.holo_blue_dark);
            }
        });
        return view;
    }
}