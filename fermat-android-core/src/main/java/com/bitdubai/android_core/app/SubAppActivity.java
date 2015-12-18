package com.bitdubai.android_core.app;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.connections.ConnectionConstants;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.ActivityType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatAppConnection;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.*;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.*;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bitdubai.fermat.R;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by Matias Furszyfer
 */


public class SubAppActivity extends FermatActivity implements FermatScreenSwapper{

    public static final String INSTALLED_SUB_APP = "sub_app";

    /**
     *  Called when the activity is first created
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activePlatforms = getIntent().getParcelableArrayListExtra(StartActivity.ACTIVE_PLATFORMS);

        developMode = getIntent().getBooleanExtra(DEVELOP_MODE,false);

        setActivityType(ActivityType.ACTIVITY_TYPE_SUB_APP);

        try {

            loadUI(createOrCallSubAppSession());

        } catch (Exception e) {
            //reportUnexpectedUICoreException
            //hacer un enum con areas genericas
            //TODO error manager null
          //  getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.drawer_layout));
        System.gc();
    }

    private void unbindDrawables(View view) {
        if(view!=null) {
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            }
        }
    }



    /**
     * This method replaces the current fragment by the next in navigation
     * @param fragmentType Type Id of fragment to show
     */

    private void loadFragment(SubApps subApp,int idContainer, String fragmentType,FermatFragmentFactory fermatFragmentFactory) throws InvalidParameterException {


        SubAppSessionManager subAppSessionManager = ((ApplicationSession) getApplication()).getSubAppSessionManager();
        FermatSession subAppsSession = subAppSessionManager.getSubAppsSession(getSubAppRuntimeMiddleware().getLastSubApp().getAppPublicKey());


        try {
            getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getFragment(fragmentType);
            //com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory subAppFragmentFactory = SubAppFragmentFactory.getFragmentFactoryBySubAppType(subApp);

            android.app.Fragment fragment = fermatFragmentFactory.getFermatFragment(fermatFragmentFactory.getFermatFragmentEnumType(fragmentType));

//            android.app.Fragment fragment = subAppFragmentFactory.getFragment(
//                    fragmentType,
//                    subAppsSession,
//                    null, //getSubAppSettingsManager().getSettings(xxx),
//                    getSubAppResourcesProviderManager()
//            );
            FragmentTransaction FT = this.getFragmentManager().beginTransaction();
            FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            FT.replace(idContainer, fragment);
            FT.commit();
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu
     * @param menu
     * @return true if all is okey
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        /**
         *  Our future code goes here...
         */
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * @param item
     * @return true if button is clicked
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        try {
            int id = item.getItemId();

            /**
             *  Our future code goes here...
             */
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }

        return false;
    }


    /**
     * This method catch de back bottom event and decide which screen load
     */

    @Override
    public void onBackPressed() {
        // get actual fragment on execute
        String frgBackType = null;
        try {
            SubAppRuntimeManager subAppRuntimeManager = getSubAppRuntimeMiddleware();

            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragment=null;

            Activity activity = null;
            WeakReference<FermatAppConnection> fermatAppConnection = null;
            try{
                SubApp subApp = subAppRuntimeManager.getLastSubApp();
                activity= subApp.getLastActivity();
                fragment  = activity.getLastFragment();
                fermatAppConnection = new WeakReference<FermatAppConnection>(FermatAppConnectionManager.getFermatAppConnection(subApp.getPublicKey(),this,getIntraUserModuleManager().getActiveIntraUserIdentity())) ;

            }catch (NullPointerException nullPointerException){
                fragment=null;
            }

            //get setting fragment to back
            //if not fragment to back I back to desktop

            if (fragment != null)
                frgBackType = fragment.getBack();


            if (frgBackType != null) {

                Activity activities = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity();
                com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment fragmentBack = activities.getFragment(frgBackType); //set back fragment to actual fragment to run
                //TODO: ver como hacer para obtener el id del container
                if(fragmentBack.getType().equals("CSADDTD") || fragmentBack.getType().equals("CSADDTT") || fragmentBack.getType().equals("CSADDTR")  || fragmentBack.getType().equals("CSADDT")){
                    loadFragment(subAppRuntimeManager.getLastSubApp().getType(), R.id.logContainer,frgBackType,fermatAppConnection.get().getFragmentFactory());
                }else {
                    loadFragment(subAppRuntimeManager.getLastSubApp().getType(), R.id.startContainer,frgBackType,fermatAppConnection.get().getFragmentFactory());
                }

            }else if(activity!=null && activity.getBackActivity()!=null){

                //todo: hacer
                changeActivity(activity.getBackActivity().getCode(),activity.getBackAppPublicKey());

            } else {
                // set Desktop current activity
                activity = getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity();
                if (activity.getType() != Activities.CWP_WALLET_MANAGER_MAIN) {
//                    resetThisActivity();
//                    //getSubAppRuntimeMiddleware().getHomeScreen();
//                    getSubAppRuntimeMiddleware().getSubApp(SubApps.CWP_WALLET_MANAGER);
//                    getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.CWP_WALLET_MANAGER_MAIN);
                    //cleanWindows();

                    Intent intent = new Intent(this, DesktopActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent);


//                    loadUI(createOrCallSubAppSession());
                } else {
                    super.onBackPressed();
                }
            }
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ScreenSwapper interface implementation
     */


    /**
     * This Method execute the navigation to an other fragment or activity
     * Get button action of screen
     */

    @Override
    public void changeScreen(String screen,int idContainer,Object[] objects) {
        try {

            SubAppRuntimeManager subAppRuntimeManager= getSubAppRuntimeMiddleware();

            SubApp subApp = subAppRuntimeManager.getLastSubApp();
            loadFragment(subApp.getType(), idContainer, screen,FermatAppConnectionManager.getFermatAppConnection(subApp.getPublicKey(),this,getIntraUserModuleManager().getActiveIntraUserIdentity()).getFragmentFactory());

        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeWalletFragment"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void selectWallet(InstalledWallet installedWallet){
        Intent intent;
        try {

            WalletNavigationStructure walletNavigationStructure= getWalletRuntimeManager().getWallet(installedWallet.getWalletPublicKey());

            intent = new Intent(this, com.bitdubai.android_core.app.WalletActivity.class);
            intent.putExtra(WalletActivity.INSTALLED_WALLET, installedWallet);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void changeActivity(String activityName, String appBackPublicKey, Object... objects) {
        try {
            if(Activities.getValueFromString(activityName).equals(Activities.CWP_WALLET_FACTORY_EDIT_WALLET.getCode())){
                Intent intent;
                try {


                    intent = new Intent(this, EditableWalletActivity.class);
                    intent.putExtra(EditableWalletActivity.WALLET_NAVIGATION_STRUCTURE,(WalletNavigationStructure)objects[0]);
                    intent.putExtra(EditableWalletActivity.INSTALLED_WALLET,(InstalledWallet)objects[1]);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                }catch (Exception e){
                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
                    Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
                }

            }else{
                try {


                    boolean isConnectionWithOtherApp = false;
                    Activity lastActivity = null;
                    Activity nextActivity = null;
                    SubApp subApp = null;
                    try {
                        SubApp subAppNavigationStructure = getSubAppRuntimeMiddleware().getLastSubApp();
                        if(subAppNavigationStructure.getPublicKey().equals(appBackPublicKey)) {
                            lastActivity = subAppNavigationStructure.getLastActivity();
                            nextActivity = subAppNavigationStructure.getActivity(Activities.getValueFromString(activityName));
                        }else{
                            subApp= getSubAppRuntimeMiddleware().getSubAppByPublicKey(appBackPublicKey);
                            if(subApp!=null){
                                isConnectionWithOtherApp = true;
                                subApp.getActivity(Activities.getValueFromString(activityName));
                            }
                        }
                        if(!isConnectionWithOtherApp) {
                            if (!nextActivity.equals(lastActivity)) {
                                resetThisActivity();
                                loadUI(getSubAppSessionManager().getSubAppsSession(subAppNavigationStructure.getAppPublicKey()));
                            }
                        }else{
                            //connectWithSubApp(null,objects,subApp.getPublicKey());
                        }

                    } catch (Exception e) {
                        getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivity"));
                        Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
                    } catch (Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Oooops! recovering from system error. Throwable", Toast.LENGTH_LONG).show();
                        throwable.printStackTrace();
                    }

                    //resetThisActivity();

                    Activity a =  getSubAppRuntimeMiddleware().getLastSubApp().getActivity(Activities.getValueFromString(activityName));

                    loadUI(getSubAppSessionManager().getSubAppsSession(getSubAppRuntimeMiddleware().getLastSubApp().getAppPublicKey()));


                }catch (Exception e){

                    getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivity"));
                    Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
                }
            }
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {
        Intent intent;
        try {
            SubApp subAppNavigationStructure= getSubAppRuntimeMiddleware().getSubAppByPublicKey(installedSubApp.getAppPublicKey());

            intent = new Intent(this, com.bitdubai.android_core.app.SubAppActivity.class);
            intent.putExtra(SubAppActivity.INSTALLED_SUB_APP, installedSubApp);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in selectWallet"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void changeWalletFragment(String walletCategory, String walletType, String walletPublicKey, String fragmentType) {
    }

    @Override
    public void onCallbackViewObserver(FermatCallback fermatCallback) {
    }

    @Override
    public void connectWithOtherApp(Engine emgine, String fermatAppPublicKey,Object[] objectses) {
    }

    @Override
    public Object[] connectBetweenAppsData() {
        Object[] objects = (Object[]) getIntent().getSerializableExtra(ConnectionConstants.SEARCH_NAME);
        return objects;
    }


    /**
     * Method that loads the UI
     */

    protected void loadUI(FermatSession<InstalledSubApp> subAppSession) {
        try {
            Activity activity = getActivityUsedType();
            loadBasicUI(activity);
            hideBottonIcons();
            if (activity.getTabStrip() == null && activity.getFragments().size() > 1) {
                initialisePaging();
            }
            if (activity.getTabStrip() != null) {
                setPagerTabs(getSubAppRuntimeMiddleware().getLastSubApp(), activity.getTabStrip(), subAppSession);
            }
            if (activity.getFragments().size() == 1) {
                setOneFragmentInScreen();
            }
        }catch (NullPointerException e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setOneFragmentInScreen() throws InvalidParameterException {

        SubAppRuntimeManager subAppRuntimeManager= getSubAppRuntimeMiddleware();
        SubApp subApp = subAppRuntimeManager.getLastSubApp();
        String fragment = subAppRuntimeManager.getLastSubApp().getLastActivity().getLastFragment().getType();
        FermatSession subAppsSession = getSubAppSessionManager().getSubAppsSession(subApp.getAppPublicKey());
        FermatAppConnection fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(getSubAppRuntimeMiddleware().getLastSubApp().getAppPublicKey(), this, null);
        com.bitdubai.fermat_android_api.engine.FermatFragmentFactory fermatFragmentFactory = fermatAppConnection.getFragmentFactory();
        try {
            if(fermatFragmentFactory !=null){
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                    tabLayout.setVisibility(View.GONE);

                    ViewPager pagertabs = (ViewPager) findViewById(R.id.pager);
                    pagertabs.setVisibility(View.VISIBLE);
                    adapter = new TabsPagerAdapter(getFragmentManager(),
                            getApplicationContext(),
                            fermatFragmentFactory,
                            fragment,
                            subAppsSession,
                            getSubAppResourcesProviderManager(),
                            getResources());
                    pagertabs.setAdapter(adapter);

                    final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                            .getDisplayMetrics());
                    pagertabs.setPageMargin(pageMargin);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    private FermatSession<InstalledSubApp> createOrCallSubAppSession(){
        FermatSession<InstalledSubApp> subAppSession = null;
        try {
            Bundle bundle = getIntent().getExtras();
            InstalledSubApp installedSubApp=null;
            SubApps subAppType=null;
            String publicKey=null;
            if(bundle!=null){
                if(bundle.containsKey(INSTALLED_SUB_APP)){
                    installedSubApp  = ((InstalledSubApp) bundle.getSerializable(INSTALLED_SUB_APP));
                }else if(bundle.containsKey(ConnectionConstants.SUB_APP_CONNECTION)){
                    subAppType = (SubApps) bundle.getSerializable(ConnectionConstants.SUB_APP_CONNECTION_TYPE);
                    publicKey = bundle.getString(ConnectionConstants.SUB_APP_CONNECTION);
                }
            }
            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection((publicKey==null) ? installedSubApp.getAppPublicKey() : publicKey, this, null);
            ModuleManager moduleManager = getModuleManager(fermatAppConnection.getPluginVersionReference());
            if(installedSubApp!=null){
                if (getSubAppSessionManager().isSubAppOpen(installedSubApp.getAppPublicKey())) {
                    subAppSession = getSubAppSessionManager().getSubAppsSession(installedSubApp.getAppPublicKey());
                } else {
                        subAppSession = getSubAppSessionManager().openSubAppSession(
                                installedSubApp,
                                getErrorManager(),
                                moduleManager,
                                fermatAppConnection
                        );
                }
            }else {
                installedSubApp = getSubAppManager().getSubApp(subAppType.getCode());
                //TODO:deberiamos tener el subAppManager por eso va en null
                subAppSession = getSubAppSessionManager().openSubAppSession(
                        installedSubApp,
                        getErrorManager(),
                        moduleManager,
                        fermatAppConnection
                );
            }

        } catch (NullPointerException nullPointerException){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(nullPointerException));
        } catch (Exception e){
            e.printStackTrace();
            //this happend when is in home screen
        }
        return subAppSession;
    }


    @Override
    protected List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> getNavigationMenu() {
        return getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().getSideMenu().getMenuItems();
    }

    @Override
    protected void onNavigationMenuItemTouchListener(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem data, int position) {
        try {
            String activityCode = data.getLinkToActivity().getCode();
            if(activityCode.equals("develop_mode")){
                developMode = true;
                onBackPressed();
            }else
                changeActivity(activityCode,data.getAppLinkPublicKey());
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in onNavigationMenuItemTouchListener"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void changeActivityBack(String appBackPublicKey,String activityCode){
        try {
            getSubAppRuntimeMiddleware().getLastSubApp().getLastActivity().changeBackActivity(appBackPublicKey,activityCode);
        }catch (InvalidParameterException e) {
            getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, new IllegalArgumentException("Error in changeActivityBack"));
            Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }





}
