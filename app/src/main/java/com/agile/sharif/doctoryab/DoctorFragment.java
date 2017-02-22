package com.agile.sharif.doctoryab;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mina on 2/21/17.
 */

public class DoctorFragment extends Fragment {

    String name ;
    String diploma;
    String expertise;
    String addr ;
    String tel ;
    ArrayList<String> insurance;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        diploma = getArguments().getString("diploma");
        expertise = getArguments().getString("expertise");
        addr = getArguments().getString("addr");
        tel = getArguments().getString("tel");
        insurance = getArguments().getStringArrayList("insurance");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.doctor_detail, container, false);
        TextView docName = (TextView) view.findViewById(R.id.doc_name);
        docName.setText(name);
        TextView docExp = (TextView) view.findViewById(R.id.doc_exp);
        docExp.setText(diploma + " "+expertise);
        TextView docAddr = (TextView) view.findViewById(R.id.doc_addr);
        docAddr.setText(addr);
        TextView docTel = (TextView) view.findViewById(R.id.doc_tel);
        docTel.setText(tel);
        String ins = "";
        for (String i:
             insurance) {
            ins += (i + " , ");
        }
        if(ins.length() >2)
            ins = ins.substring(0,ins.length()-2);
        TextView docIns = (TextView) view.findViewById(R.id.doc_ins);
        docIns.setText(ins);

        return view;
    }

}
