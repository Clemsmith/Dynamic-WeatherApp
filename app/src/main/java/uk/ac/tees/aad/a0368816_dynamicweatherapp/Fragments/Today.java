package uk.ac.tees.aad.a0368816_dynamicweatherapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.ac.tees.aad.a0368816_dynamicweatherapp.Models.todayPojo;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.adapter.TodayAdapter;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.CurrentCity;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Today#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Today extends Fragment {
    public RecyclerView recyclerView;
    public ArrayList<todayPojo> mylist;

    public static TodayAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Today() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Today.
     */
    // TODO: Rename and change types and number of parameters
    public static Today newInstance(String param1, String param2) {
        Today fragment = new Today();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);



        recyclerView = view.findViewById(R.id.todayRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mylist = CurrentCity.getArrayList();

        adapter = new TodayAdapter(mylist,getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }
}