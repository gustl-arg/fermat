package com.bitdubai.sub_app.intra_user_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.fragments.ConnectionNotificationsFragment;
import com.bitdubai.sub_app.intra_user_community.fragments.ConnectionOtherProfileFragment;
import com.bitdubai.sub_app.intra_user_community.fragments.ConnectionsFragment;
import com.bitdubai.sub_app.intra_user_community.fragments.ConnectionsListFragment;
import com.bitdubai.sub_app.intra_user_community.fragments.ConnectionsWorldFragment;
import com.bitdubai.sub_app.intra_user_community.preference_settings.IntraUserPreferenceSettings;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;


/**
 * Created by Matias Furszyfer on 31/8/2015.
 * Modify by Jose Manuel De Sousa Dos Santos on 08/12/2015.
 */

public class IntraUserFragmentFactory extends FermatFragmentFactory<IntraUserSubAppSession, IntraUserPreferenceSettings,SubAppResourcesProviderManager,IntraUserFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(IntraUserFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments) {
            case CWP_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS_FRAGMENT:
                currentFragment = ConnectionsFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_DETAIL_FRAGMENT:
                currentFragment = null;
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT:
                currentFragment = ConnectionNotificationsFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT:
                currentFragment = ConnectionOtherProfileFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT:
                currentFragment = ConnectionsListFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.toString(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public IntraUserFragmentsEnumType getFermatFragmentEnumType(String key) {
        return IntraUserFragmentsEnumType.getValue(key);
    }

}
