package uk.ac.tees.aad.a0368816_dynamicweatherapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.ac.tees.aad.a0368816_dynamicweatherapp.Models.TomorrowPojo;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.adapter.TomorrowAdapter;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.CurrentCity;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tomorrow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tomorrow extends Fragment {
    public RecyclerView recyclerView;
    public ArrayList<TomorrowPojo> mylist;

    public static TomorrowAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tomorrow() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tomorrow.
     */
    // TODO: Rename and change types and number of parameters
    public static Tomorrow newInstance(String param1, String param2) {
        Tomorrow fragment = new Tomorrow();
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

        View view = inflater.inflate(R.layout.fragment_tomorrow, container, false);


        recyclerView = view.findViewById(R.id.tommorrowRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mylist = CurrentCity.getTommorrowArrayList();

        adapter = new TomorrowAdapter(mylist, getContext());
        recyclerView.setAdapter(adapter);

        return view;
        // Inflate the layout for this fragment

    }
}