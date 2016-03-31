package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 *Created by Yordin Alayn on 22.01.16.
 * Based in DateTimeViewHolder of Star_negotiation by nelson
 */
public class DateTimeViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private Button buttonDate;
    private Button buttonTime;
    private TextView descriptionTextView;


    public DateTimeViewHolder(View itemView) {
        super(itemView);

        descriptionTextView = (TextView) itemView.findViewById(R.id.ccw_date_time_description_text);

        buttonDate = (Button) itemView.findViewById(R.id.ccw_date_value);
        buttonDate.setOnClickListener(this);
        buttonTime = (Button) itemView.findViewById(R.id.ccw_time_value);
        buttonTime.setOnClickListener(this);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation data, ClauseInformation clause, int position) {
        super.bindData(data, clause, position);

        java.text.DateFormat timeFormat = DateFormat.getTimeFormat(itemView.getContext());
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(itemView.getContext());

        long timeInMillis = Long.valueOf(clause.getValue());
        buttonTime.setText(timeFormat.format(timeInMillis));
        buttonDate.setText(dateFormat.format(timeInMillis));
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
        descriptionTextView.setText(stringResources[0]);
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseClicked((Button) view, clause, clausePosition);
    }

    @Override
    protected int getConfirmButtonRes() {
        return R.id.ccw_confirm_button;
    }

    @Override
    protected int getClauseNumberImageViewRes() {
        return R.id.ccw_clause_number;
    }

    @Override
    protected int getTitleTextViewRes() {
        return R.id.ccw_card_view_title;
    }

    @Override
    public void setStatus(NegotiationStepStatus stepStatus) {
        super.setStatus(stepStatus);

        switch (stepStatus) {
            case ACCEPTED:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                break;
            case CHANGED:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                break;
            case CONFIRM:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                break;
        }
    }
}
