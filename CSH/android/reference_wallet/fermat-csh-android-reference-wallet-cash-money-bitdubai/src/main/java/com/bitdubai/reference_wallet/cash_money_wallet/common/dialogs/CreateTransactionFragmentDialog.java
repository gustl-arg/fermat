package com.bitdubai.reference_wallet.cash_money_wallet.common.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.cash_money_wallet.common.CashTransactionParametersImpl;
import com.bitdubai.reference_wallet.cash_money_wallet.common.NumberInputFilter;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/15/2015.
 */

public class CreateTransactionFragmentDialog extends Dialog implements
        View.OnClickListener {

    public Activity activity;
    public Dialog d;

    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private CashMoneyWalletSession cashMoneyWalletSession;
    private Resources resources;
    private TransactionType transactionType;
    BigDecimal optionalAmount;
    String optionalMemo;

    /**
     *  UI components
     */
    FermatTextView dialogTitle;
    LinearLayout dialogTitleLayout;
    EditText amountText;
    AutoCompleteTextView memoText;
    Button applyBtn;
    Button cancelBtn;


    public CreateTransactionFragmentDialog(Activity a, CashMoneyWalletSession cashMoneyWalletSession, Resources resources, TransactionType transactionType, BigDecimal optionalAmount, String optionalMemo) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.cashMoneyWalletSession = cashMoneyWalletSession;
        this.transactionType = transactionType;
        this.resources = resources;

        this.optionalAmount = (optionalAmount == null || optionalAmount == new BigDecimal(0) ? null : optionalAmount);
        this.optionalMemo = (optionalMemo == null || optionalMemo == "" ? null : optionalMemo);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupScreenComponents();

    }

    private void setupScreenComponents(){

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.csh_create_transaction_dialog);


            dialogTitleLayout = (LinearLayout) findViewById(R.id.csh_ctd_title_layout);
            dialogTitle = (FermatTextView) findViewById(R.id.csh_ctd_title);
            //dialogTitleImg = (FermatTextView) findViewById(R.id.csh_ctd_title_img);
            amountText = (EditText) findViewById(R.id.csh_ctd_amount);
            memoText = (AutoCompleteTextView) findViewById(R.id.csh_ctd_memo);
            applyBtn = (Button) findViewById(R.id.csh_ctd_apply_transaction_btn);
            cancelBtn = (Button) findViewById(R.id.csh_ctd_cancel_transaction_btn);

            dialogTitleLayout.setBackgroundColor(getTransactionTitleColor());
            dialogTitle.setText(getTransactionTitleText());
            amountText.setFilters(new InputFilter[]{new NumberInputFilter(9, 2)});

            cancelBtn.setOnClickListener(this);
            applyBtn.setOnClickListener(this);

            if(optionalAmount != null)
                amountText.append(optionalAmount.toPlainString());  //append places cursor at the end!
            if(optionalMemo != null)
                memoText.setText(optionalMemo);


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private String getTransactionTitleText()
    {
        if (transactionType == TransactionType.DEBIT)
            return resources.getString(R.string.csh_withdrawal_transaction_text);
        else
            return resources.getString(R.string.csh_deposit_transaction_text);
    }

    private int getTransactionTitleColor()
    {
        if (transactionType == TransactionType.DEBIT)
            return resources.getColor(R.color.csh_fab_color_normal_w);
        else
            return resources.getColor(R.color.csh_fab_color_normal_d);
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.csh_ctd_cancel_transaction_btn) {
            dismiss();
        }else if( i == R.id.csh_ctd_apply_transaction_btn){
            applyTransaction();
            dismiss();
        }
    }


    private void applyTransaction() {
        try {

            String memo = memoText.getText().toString();
            String amount = amountText.getText().toString();

            if (amount.equals("")) {
                Toast.makeText(activity.getApplicationContext(), "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(new BigDecimal(amount).compareTo(new BigDecimal(0)) == 0)
            {
                Toast.makeText(activity.getApplicationContext(), "Amount cannot be zero", Toast.LENGTH_SHORT).show();
                return;
            }


            if (transactionType == TransactionType.DEBIT) {
                CashTransactionParameters t = new CashTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActorRefWallet", "pkeyPluginRefWallet", new BigDecimal(amount), FiatCurrency.US_DOLLAR, memo, TransactionType.DEBIT);
                //try {
                    cashMoneyWalletSession.getModuleManager().createAsyncCashTransaction(t);
                    //updateWalletBalances(view.getRootView());

//                } catch (CantCreateWithdrawalTransactionException e) {
//                    Toast.makeText(activity.getApplicationContext(), "There's been an error, please try again", Toast.LENGTH_SHORT).show();
//                    return;
//                } catch (CashMoneyWalletInsufficientFundsException e) {
//                    Toast.makeText(activity.getApplicationContext(), "Insufficient funds, please try a lower value", Toast.LENGTH_SHORT).show();
//                    return;
//                }
            }
            else if(transactionType == TransactionType.CREDIT) {
                CashTransactionParameters t = new CashTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActorRefWallet", "pkeyPluginRefWallet", new BigDecimal(amount), FiatCurrency.US_DOLLAR, memo, TransactionType.CREDIT);
                //try {
                    cashMoneyWalletSession.getModuleManager().createAsyncCashTransaction(t);
                    //updateWalletBalances(view.getRootView());

//                } catch (CantCreateDepositTransactionException e) {
//                    Toast.makeText(activity.getApplicationContext(), "There's been an error, please try again", Toast.LENGTH_SHORT).show();
//                    return;
//                }
            }

        } catch (Exception e) {
            cashMoneyWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "There's been an error, please try again. " +  e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

}