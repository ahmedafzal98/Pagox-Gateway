package com.example.pagox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    TextView amount;
    TextView transactions;
    ImageView imageView;
    Integer[] images = {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six};
    private String[] texts = {"207.347600", "5.4k", "5.6k","0", "0", "6"};
    private String[] texts2 = {"Total Transaction Count", "Total declined Count", "Total Void & Refund","Total Fees(Pagox)", "Total Transaction Approved Amount", "Total Settlement Amount "};



    public ViewPagerAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override

    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout,container,false);
        imageView = view.findViewById(R.id.img);
        imageView.setImageResource(images[position]);
        amount = (TextView) view.findViewById(R.id.txt);
        transactions = (TextView) view.findViewById(R.id.transaction);
        amount.setText(texts[position]);
        transactions.setText(texts2[position]);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
