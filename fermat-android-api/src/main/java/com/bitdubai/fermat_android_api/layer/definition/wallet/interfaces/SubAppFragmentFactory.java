package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * This interface provides the fragments and objects needed by the wallet runtime
 *
 * @author Matias Furszyfer
 */

public interface SubAppFragmentFactory<S extends FermatSession>{

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code the reference used to identify the fragment
     * @return the fragment referenced
     */

    android.app.Fragment getFragment(String code, S subAppsSession, SubAppResourcesProviderManager subAppResourcesProviderManager) throws FragmentNotFoundException;

}
