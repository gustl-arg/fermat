package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.holders.ActorViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.interfaces.AdapterChangeListener;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.models.Actor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

import java.util.List;

/**
 * Created by Nerio on 21/10/15.
 */
public class ActorAdapter extends FermatAdapter<Actor, ActorViewHolder> {

    private AdapterChangeListener<Actor> adapterChangeListener;

    public ActorAdapter(Context context) {
        super(context);
    }

    public ActorAdapter(Context context, List<Actor> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ActorViewHolder createHolder(View itemView, int type) {
        return new ActorViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_dap_redeem_point_actor;
    }

    @Override
    protected void bindHolder(final ActorViewHolder holder, final Actor data, final int position) {
        try {
            if(data.getCryptoAddress() != null)
                holder.name.setText(String.format("%s %s CryptoAddress: YES ", data.getName(), data.getDapConnectionState()));
            else
                holder.name.setText(String.format("%s %s", data.getName(), data.getDapConnectionState()));

            holder.connect.setChecked(data.selected);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataSet.get(position).selected = !dataSet.get(position).selected;
                    notifyItemChanged(position);
                    if (adapterChangeListener != null)
                        adapterChangeListener.onDataSetChanged(dataSet);
                }
            });
            //TODO: chamo esto te va a tirar error si es nula la imagen :p, el leght no lo va a poder sacar
            if (data.getProfileImage() != null && data.getProfileImage().length > 0) {
                holder.thumbnail.setImageDrawable(new BitmapDrawable(context.getResources(),
                        BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setAdapterChangeListener(AdapterChangeListener<Actor> adapterChangeListener) {
        this.adapterChangeListener = adapterChangeListener;
    }
}
