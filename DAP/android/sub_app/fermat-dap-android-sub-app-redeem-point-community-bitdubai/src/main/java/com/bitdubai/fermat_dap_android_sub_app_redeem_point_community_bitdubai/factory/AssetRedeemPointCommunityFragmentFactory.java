package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.fragments.RedeemPointCommunityConnectionOtherProfileFragment;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.fragments.RedeemPointCommunityConnectionsListFragment;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.fragments.RedeemPointCommunityHomeFragment;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.fragments.RedeemPointCommunityNotificationsFragment;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.fragments.RedeemPointCommunitySettingsNotificationsFragment;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.sessions.AssetRedeemPointCommunitySubAppSession;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * CommunityAssetRedeemPointFragmentFactory
 */
public class AssetRedeemPointCommunityFragmentFactory extends FermatFragmentFactory<AssetRedeemPointCommunitySubAppSession, SubAppResourcesProviderManager, FragmentFactoryEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(FragmentFactoryEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN:
                return RedeemPointCommunityHomeFragment.newInstance();
            case DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT:
                return RedeemPointCommunityConnectionOtherProfileFragment.newInstance();
            case DAP_ASSET_REDEEM_POINT_COMMUNITY_CONNECTION_LIST_FRAGMENT:
                return RedeemPointCommunityConnectionsListFragment.newInstance();
            case DAP_ASSET_REDEEM_POINT_COMMUNITY_NOTIFICATION_FRAGMENT:
                return RedeemPointCommunityNotificationsFragment.newInstance();
            case DAP_ASSET_REDEEM_POINT_COMMUNITY_SETTINGS_FRAGMENT:
                return RedeemPointCommunitySettingsNotificationsFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-sub-app-asset-redeem-point", "fragment not found");
        }
    }

    @Override
    public FragmentFactoryEnumType getFermatFragmentEnumType(String key) {
        return FragmentFactoryEnumType.getValue(key);
    }
}
