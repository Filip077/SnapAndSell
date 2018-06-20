package com.newproject.snapandsell.RecyclerView;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newproject.snapandsell.Model.Product;
import com.newproject.snapandsell.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Korisnik on 11.05.2018.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    private List<Product> productList;
    private Context context;


    public ProductsAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;

    }


    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView mDiscription, mPrice;
        public ImageView mImage;

        public ProductsViewHolder(View itemView) {
            super(itemView);
            mDiscription = itemView.findViewById(R.id.text_view_description);
            mPrice = itemView.findViewById(R.id.text_view_price);
            mImage = itemView.findViewById(R.id.image_product);
        }
    }


    @Override
    public ProductsAdapter.ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_row, null);
        ProductsViewHolder viewHolder = new ProductsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ProductsViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.mDiscription.setText(product.getmDiscription());

        Picasso.get().load(product.getmImageUrl()).fit().centerCrop().rotate(90).into(holder.mImage);
        holder.mPrice.setText(product.getmPrice() + " $");
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }
}
