package com.agile.sharif.doctoryab;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mina on 2/20/17.
 */

public class DoctorCard extends LinearLayout {

    private Context mContext;
    private String docName ;
    private String docAddr;
    private TextView docNameTextView ;
    private TextView docAddrTextView ;


    public DoctorCard(Context mContext , String docName , String docAddr){
        super(mContext);
        this.docName = docName;
        this.docAddr = docAddr;
        initializeView(mContext);
        System.out.println("in constructor");

    }

    private void initializeView(Context mContext) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.doctor_card_fragment, this);
        System.out.println("in inflator");
        docAddrTextView = (TextView) findViewById(R.id.card_doc_addr);
        docNameTextView = (TextView) findViewById(R.id.card_doc_name);
        docAddrTextView.setText(docAddr);
        docNameTextView.setText(docName);
        docNameTextView.setBackgroundColor(Color.RED);
        docAddrTextView.setBackgroundColor(Color.RED);
        docNameTextView.setTextColor(Color.BLACK);
//        ImageView docImage = (ImageView) findViewById(R.id.card_doc_image);
//        System.out.println(docImage.isShown());
//        docImage.setVisibility(VISIBLE);
//        System.out.println(docImage.isShown());

//        docAddrTextView.setTextColor(Color.BLACK);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        System.out.println("in custom view");
        docAddrTextView.setText(docAddr);
        docNameTextView.setText(docName);
        docNameTextView.setTextColor(Color.BLACK);
        docAddrTextView.setTextColor(Color.BLACK);


    }
}
