package com.bitdubai.android_core.app.common.version_1.provisory;

import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.CantGetUserSubAppException;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.SubAppManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mati on 2015.12.06..
 */
public class SubAppManagerProvisory implements SubAppManager {

    Map<String, InstalledSubApp> installedSubApps;

    public SubAppManagerProvisory() {
        installedSubApps = new HashMap<>();
        loadMap(installedSubApps);

    }

    private void loadMap(Map<String, InstalledSubApp> lstInstalledSubApps) {
        //TODO - CCP Platform
        InstalledSubApp installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY, null, null, "intra_user_community_sub_app", "Wallet Users", "public_key_intra_user_commmunity", "intra_user_community_sub_app", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY, null, null, "intra_user_identity_sub_app", "Wallet Users", "public_key_ccp_intra_user_identity", "intra_user_identity_sub_app", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - DAP Platform
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_ISSUER, null, null, "sub-app-asset-community-issuer", "Asset Community Issuer", "public_key_dap_issuer_community", "sub-app-asset-community-issuer", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_USER, null, null, "sub-app-asset-community-user", "Asset Community User", "public_key_dap_user_community", "sub-app-asset-community-user", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT, null, null, "sub-app-asset-community-redeem-point", "Asset Community Redeem Point", "public_key_dap_redeem_point_community", "sub-app-asset-community-redeem-point", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_FACTORY, null, null, "sub-app-asset-factory", "Asset Factory", "public_key_dap_factory", "sub-app-asset-factory", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_IDENTITY_ISSUER, null, null, "sub-app-asset-identity-issuer", "Asset Identity Issuer", "public_key_dap_asset_issuer_identity", "sub-app-asset-identity-issuer", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_IDENTITY_USER, null, null, "sub-app-asset-identity-user", "Asset Identity User", "public_key_dap_asset_user_identity", "sub-app-asset-identity-user", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_REDEEM_POINT_IDENTITY, null, null, "sub-app-asset-identity-redeem-point", "Asset Redeem Point Identity", "public_key_dap_redeem_point_identity", "sub-app-asset-identity-redeem-point", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - CBP Platform
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CBP_CRYPTO_BROKER_IDENTITY, null, null, "sub_app_crypto_broker_identity", "Brokers", "sub_app_crypto_broker_identity", "sub_app_crypto_broker_identity", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY, null, null, "sub_app_crypto_customer_identity", "Customers", "sub_app_crypto_customer_identity", "sub_app_crypto_customer_identity", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CBP_CRYPTO_BROKER_COMMUNITY, null, null, "sub_app_crypto_broker_community", "Brokers", "public_key_crypto_broker_community", "sub_app_crypto_broker_community", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY, null, null, "sub_app_crypto_customer_community", "Customers", "public_key_crypto_customer_community", "sub_app_crypto_customer_community", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - CHT Platform
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CHT_CHAT, null, null, "chat_sub_app", "Chat", "public_key_cht_chat", "chat_sub_app", new Version(1, 0, 0));
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - Add Others SubApps
    }

    @Override
    public Collection<InstalledSubApp> getUserSubApps() throws CantGetUserSubAppException {
        return installedSubApps.values();
    }

    @Override
    public InstalledSubApp getSubApp(String subAppCode) {
        return installedSubApps.get(subAppCode);
    }


    @Override
    public FermatApp getApp(String publicKey) throws Exception {
        return installedSubApps.get(publicKey);
    }

    @Override
    public SettingsManager<FermatSettings> getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
