package com.bitdubai.fermat_art_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_art_core.layer.actor_network_service.ActorNetworkServiceLayer;
import com.bitdubai.fermat_art_core.layer.identity.IdentityLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/03/16.
 */
public class ARTPlatform extends AbstractPlatform {

    public ARTPlatform() {
        super(new PlatformReference(Platforms.ART_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new ActorNetworkServiceLayer());
            registerLayer(new IdentityLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "ART Platform.",
                    "Problem trying to register a layer."
            );
        }

    }
}
