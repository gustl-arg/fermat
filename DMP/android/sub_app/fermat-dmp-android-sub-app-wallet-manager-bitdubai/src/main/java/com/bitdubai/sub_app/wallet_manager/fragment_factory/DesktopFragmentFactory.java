package com.bitdubai.sub_app.wallet_manager.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.sub_app.wallet_manager.fragment.CommunitiesFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.DesktopFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.DesktopP2PApssFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.DesktopSocialApssFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.FermatNetworkSettings;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;


/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class DesktopFragmentFactory extends FermatFragmentFactory<DesktopSession, ResourceProviderManager,DesktopFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(DesktopFragmentsEnumType fragments) throws FragmentNotFoundException {

        AbstractFermatFragment abstractFermatFragment = null;

        switch (fragments){
            case DESKTOP_MAIN:
                abstractFermatFragment = DesktopFragment.newInstance();
                break;
            case SETTINGS:
                abstractFermatFragment = FermatNetworkSettings.newInstance();
                break;
            case DESKTOP_P2P_MAIN:
                abstractFermatFragment = DesktopP2PApssFragment.newInstance();
                break;
            case DESKTOP_SOCIAL_MAIN:
                abstractFermatFragment = DesktopSocialApssFragment.newInstance();
                break;
            case COMMUNITIES_FRAGMENT:
                abstractFermatFragment = CommunitiesFragment.newInstance();
                break;
            default:
                abstractFermatFragment = DesktopFragment.newInstance();
        }


        return abstractFermatFragment;

    }

    @Override
    public DesktopFragmentsEnumType getFermatFragmentEnumType(String key) {
        return DesktopFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }

}
