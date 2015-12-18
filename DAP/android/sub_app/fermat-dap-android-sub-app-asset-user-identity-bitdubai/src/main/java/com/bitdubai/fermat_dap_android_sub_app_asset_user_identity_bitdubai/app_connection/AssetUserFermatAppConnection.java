package com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.fragmentFactory.UserIdentityFragmentFactory;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.preference_settings.UserIdentitySubAppSettings;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.session.UserIdentitySubAppSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class AssetUserFermatAppConnection extends AppConnections{

    public AssetUserFermatAppConnection(Activity activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new UserIdentityFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.IDENTITY,
                Plugins.ASSET_USER,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new UserIdentitySubAppSession();
    }

    @Override
    public FermatSettings getSettings() {
        return new UserIdentitySubAppSettings();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return null;
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return null;
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
