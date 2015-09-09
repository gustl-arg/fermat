package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatNotifications;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentMati;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentsObjects;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.ListComponent;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.preference_settings.ReferenceWalletPreferenceSettings;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 02/06/2015.
 */
public class BalanceFragment extends FermatWalletFragment {

    String walletPublicKey = "25428311-deb3-4064-93b2-69093e859871";

    private final String CRYPTO_WALLET_PARAM = "cryptoWalletParam";

    /**
     *  Screen members
     */

    private View rootView;
    private SwipeRefreshLayout swipeLayout;
    private TextView txtViewTypeBalance;
    private TextView txtViewBalance;

    /**
     *  Platform members
     */

    long balanceAvailable;
    long bookBalance;

    /**
     * DealsWithWalletModuleCryptoWallet Interface member variables.
     */

    private CryptoWalletManager cryptoWalletManager;
    private CryptoWallet cryptoWallet;

    /**
     *  Resources
     */
    WalletResourcesProviderManager walletResourcesProviderManager;

    /**
     * TypeFace to apply in all fragment
     */
    Typeface tf;

    /**
     * Wallet Session
     */

    private ReferenceWalletSession referenceWalletSession;

    ReferenceWalletPreferenceSettings referenceWalletPreferenceSettings;

    /**
     * list of last 5 transactions
     */
    List<CryptoWalletTransaction> lstCryptoWalletTransactions;


    ListView list;
    CustomAdapter adapter;
    public  ArrayList<ListComponent> CustomListViewValuesArr = new ArrayList<ListComponent>();


    /**
     *  Create a new instance of BalanceFragment and set referenceWalletSession and platforms plugin inside
     * @param position
     * @param walletSession   An object that contains all session data
     * @return BalanceFragment with Session and platform plugins inside
     */

    public static BalanceFragment newInstance(int position,ReferenceWalletSession walletSession,WalletResourcesProviderManager walletResourcesProviderManager) {
        BalanceFragment balanceFragment = new BalanceFragment();
        balanceFragment.setReferenceWalletSession(walletSession);
        balanceFragment.setWalletResourcesProviderManager(walletResourcesProviderManager);
        return balanceFragment;
    }

    /**
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setRetainInstance(true);


        /**
         *
         */
        try {

            tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

            balanceAvailable = 0;
            bookBalance = 0;
            cryptoWalletManager = referenceWalletSession.getCryptoWalletManager();


            /**
             * Get cryptoWalet that manage balance in wallet
             */
            cryptoWallet = cryptoWalletManager.getCryptoWallet();


            /**
             * Get AvailableBalance
             */
            balanceAvailable = cryptoWallet.getAvailableBalance(walletPublicKey);

            /**
             * Get BookBalance
             */
            bookBalance = cryptoWallet.getBookBalance(walletPublicKey);
        }
         catch (CantGetCryptoWalletException e) {
             referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
         }
        catch (CantGetBalanceException e){
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {

            InputStream inputStream = getActivity().getApplication().getAssets().open("xml/wallets_bitcoin_fragment_balance.xml");

//            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
//            xmlPullParserFactory.setNamespaceAware(true);
//
//            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();



            View contentView;

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream,null);

            //xpp.setInput(new FileReader("/path/to/layout.xml"));

            //LayoutInflater inflater1 =
              //      (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // contentView = inflater.inflate(xpp, null);

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        //products = new ArrayList();
                        break;
                    case XmlPullParser.START_TAG:
                        name = xpp.getName();
                        System.out.println("Comienza tag: "+name);
//                        if (name == "product"){
//                            currentProduct = new Product();
//                        } else if (currentProduct != null){
//                            if (name == "productname"){
//                                currentProduct.name = parser.nextText();
//                            } else if (name == "productcolor"){
//                                currentProduct.color = parser.nextText();
//                            } else if (name == "productquantity"){
//                                currentProduct.quantity= parser.nextText();
//                            }
//                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = xpp.getName();
                        System.out.println("termina tag: "+name);
//                        if (name.equalsIgnoreCase("product") && currentProduct != null){
//                            products.add(currentProduct);
//                        }
                }
                eventType = xpp.next();
            }

            //printProducts(products);





            rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_balance, container, false);


            // Loading a setting textView Balance type
            txtViewTypeBalance = (TextView) rootView.findViewById(R.id.txtViewTypeBalance);
            txtViewTypeBalance.setTypeface(tf);
            txtViewTypeBalance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeBalanceType();
                }
            });


            // Loading a setting textView Balance amount
            txtViewBalance = ((TextView) rootView.findViewById(R.id.txtViewBalance));
            txtViewBalance.setTypeface(tf);
            txtViewBalance.setText(formatBalanceString(balanceAvailable, ShowMoneyType.BITCOIN.getCode()));

            ViewCompat.setElevation(txtViewBalance, 30);

            txtViewBalance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    changeBalance();
                    //refreshBalance();
                }
            });


            // Loading a setting SwipeRefreshLayout
            swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshBalanceContent();
                }
            });


            List<CustomComponentsObjects> lstData = new ArrayList<CustomComponentsObjects>();

            //when we have transactions
        /*
        for (CryptoWalletTransaction cryptoWalletTransaction:cryptoWalletTransactions){
            lstData.add(new ListComponent(cryptoWalletTransaction.getInvolvedActorName(), cryptoWalletTransaction.getBitcoinWalletTransaction().getMemo(),"person1"));
        }
        */

            WalletSettings walletSettings = (WalletSettings)referenceWalletSession.getWalletSettings();
            ReferenceWalletPreferenceSettings referenceWalletPreferenceSettings = new ReferenceWalletPreferenceSettings();
            //this.referenceWalletPreferenceSettings = walletSettings.getPreferenceSettings(referenceWalletPreferenceSettings);
            this.referenceWalletPreferenceSettings = new ReferenceWalletPreferenceSettings();
            lstCryptoWalletTransactions = cryptoWallet.getTransactions(referenceWalletPreferenceSettings.getTransactionsToShow(), 0, walletPublicKey);


            // testing purpose
//        lstData.add(new ListComponent("Matias Furszyfer", "Buy one glass of water 10btc","person1"));
//        lstData.add(new ListComponent("Juan Lwon", "Sell a house in 200 btc","person12"));
//        lstData.add(new ListComponent("George Gonzalez", "Buy Venezuela in 3 btc","person12"));
//        lstData.add(new ListComponent("Fer Lewn", "Paid 30 btc","person12"));

            for (CryptoWalletTransaction cryptoWalletTransaction : lstCryptoWalletTransactions) {
                if(cryptoWalletTransaction.getBitcoinWalletTransaction().getBalanceType().getCode().equals(referenceWalletSession.getBalanceTypeSelected())){
                    ListComponent listComponent = new ListComponent(cryptoWalletTransaction);
                    lstData.add(listComponent);
                }

            }


            Resources res = getResources();


            CustomComponentMati custonMati = (CustomComponentMati) rootView.findViewById(R.id.custonMati);

            custonMati.setResources(res);

            custonMati.setWalletResources(referenceWalletSession.getWalletResourcesProviderManager());
            custonMati.setWalletSettings(referenceWalletPreferenceSettings);
            custonMati.setDataList(lstData);


            custonMati.setLastTransactionsEvent(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //((FermatNotifications) getActivity()).launchNotification("Mati notification", "Reference wallet", "Vendiste una lata de café por 100 btc");

                }
            });
            custonMati.setSeeAlltransactionsEvent(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            ((FermatScreenSwapper) getActivity()).changeActivity("CWRWBWBV1T");
                }
            });
        } catch (CantGetTransactionsException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }


        return rootView;
    }




    private String getActorNameProvisorio(CryptoWalletTransaction cryptoWalletTransaction) {
        if (cryptoWalletTransaction.getContactId() != null) {
            try {
                CryptoWalletWalletContact walletContactRecord = cryptoWallet.findWalletContactById(cryptoWalletTransaction.getContactId());
                return walletContactRecord.getActorName();
            } catch (Exception e) {
                System.out.println("esta es para vos mati.");
            }
        } else if (cryptoWalletTransaction.getInvolvedActor() != null) {
            return cryptoWalletTransaction.getInvolvedActor().getName();
        }
        return "Unknow";
    }

    /*
        Method to change the balance type
     */
    private void changeBalanceType() {

        ReferenceWalletSession referenceWalletSession =(ReferenceWalletSession) this.referenceWalletSession;
        try
        {
            if(referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txtViewTypeBalance.setText(R.string.book_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            }else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())){
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
                txtViewTypeBalance.setText(R.string.available_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        }catch (Exception e){
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();


        }

    }

    /*
        Method to change the balance amount
     */
    private void changeBalance() {
        try {
            if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())){
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
            }else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())){
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
            }
        }catch (Exception e){
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

    }




    /**
     *  Method to run the swipeRefreshLayout
     */
    private void refreshBalanceContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshBalance();
                //transactionArrayAdapter = new TransactionArrayAdapter(getActivity(),lstTransactions);
                //listViewTransactions.setAdapter(transactionArrayAdapter);
                swipeLayout.setRefreshing(false);
            }
        }, 3000);

    }

    /**
     *  Method to refresh amount of BookBalance and AvailableBalance
     */
    private void refreshBalance() {
        try {

            balanceAvailable = cryptoWallet.getAvailableBalance(walletPublicKey);

            bookBalance = cryptoWallet.getBookBalance(walletPublicKey);


            if(referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())){
                txtViewBalance.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
            }else if(referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())){
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
            }

        }catch (CantGetBalanceException e)
        {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception ex){
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     *  Set Wallet Session object
     * @param referenceWalletSession
     */
    public void setReferenceWalletSession(ReferenceWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }

    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }
}

