package com.bitdubai.fermat_wpd_api.all_definition;

import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Matias Furszyfer on 2015.07.23..
 */

public class WalletNavigationStructure implements FermatWallet,Serializable{

    /**
     * WalletNavigationStructure identifiers
     */

    private String publicKey;

    /**
     * Wallet identifiers
     */
    private String walletCategory;
    private String walletType;


    /**
     * Screens in a WalletNavigationStructure
     */

    private Map<Activities, Activity> activities = new HashMap<Activities, Activity>();

    /**
     * Main screen of the wallet
     */

    private List<Activities> startActivities = new ArrayList<>();

    /**
     * Activity used to block something
     */
    private Activities blockActivity;

    /**
     * Last active screen
     */

    private Activities lastActivity;

    private int size;


    private DeveloperIdentity developer;
    private int actualStart = 0;

    /**
     * WalletNavigationStructure constructor
     */
    public WalletNavigationStructure() {
    }

    /**
     *
     * @param activities
     */
    public WalletNavigationStructure(Map<Activities, Activity> activities) {
        this.activities = activities;
    }


    @Override
    public FermatApps getFermatApp() {
        return null;
    }

    @Override
    public FermatAppType getFermatAppType() {
        return FermatAppType.WALLET;
    }

    /**
     * WalletNavigationStructure interface implementation.
     */

    @Override
    public String getPublicKey() {
        return publicKey;
    }


    /**
     * RuntimeWallet interface implementation.
     */
    @Override
    public Activity getActivity(Activities activities) {
        this.lastActivity=activities;
        return this.activities.get(activities);
    }

    /**
     *  Get main screen
     *
     * @return Activity
     */
    @Override
    public Activity getStartActivity() {
        return activities.get(startActivities.get(actualStart));
    }

    /**
     *  Set the main screen
     *
     * @param activity
     */
    @Override
    public void addPosibleStartActivity(Activities activity) {
        this.startActivities.add(activity);
    }

    /**
     *  Get the last Screen used
     * @return Activity
     */
    @Override
    public Activity getLastActivity() {
        if (lastActivity == null) {
            return activities.get(startActivities.get(actualStart));
        }
        return activities.get(lastActivity);
    }

    @Override
    public void changeActualStartActivity(int option)throws IllegalArgumentException{
        if(option>activities.size() || option<0) throw new IllegalArgumentException();
        this.actualStart = option;
    }

    @Override
    public void setPublicKey(String publicKey) {
        this.publicKey=publicKey;
    }

    @Override
    public void clear() {
        for(Activity activity : activities.values()){
            SideMenu sideMenu = activity.getSideMenu();
            if (sideMenu!=null){
                sideMenu.clearSelected();
            }

        }
    }

    /**
     *  Screens in a WalletNavigationStructure
     *
     * @return Map<Activities, Activity>
     */

    public Map<Activities, Activity> getActivities() {
        return activities;
    }

    public void setActivities(Map<Activities, Activity> activities) {
        this.activities = activities;
    }

    /**
     *  Add Screen to WalletNavigationStructure
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.put(activity.getType(), activity);
    }

    public String getWalletCategory() {
        return walletCategory;
    }

    public void setWalletCategory(String walletCategory) {
        this.walletCategory = walletCategory;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DeveloperIdentity getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperIdentity developer) {
        this.developer = developer;
    }
}
