package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.ArtistIdentityManagerModule;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;

import java.util.List;
import java.util.UUID;

/**
 * Created by alexander on 3/15/16.
 */
public class ModuleArtistIdentityManager implements ArtistIdentityManagerModule {
    private final ArtistIdentityManager artistIdentityManager;
    private final ErrorManager errorManager;

    public ModuleArtistIdentityManager(ErrorManager errorManager,
                                       ArtistIdentityManager artistIdentityManager) {
        this.errorManager = errorManager;
        this.artistIdentityManager = artistIdentityManager;
    }

    @Override
    public List<Artist> listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        return artistIdentityManager.listIdentitiesFromCurrentDeviceUser();
    }

    @Override
    public Artist createArtistIdentity(String alias, byte[] imageBytes, UUID externalIdentityID) throws CantCreateArtistIdentityException, ArtistIdentityAlreadyExistsException {
        return artistIdentityManager.createArtistIdentity(alias,imageBytes,externalIdentityID);
    }

    @Override
    public void updateArtistIdentity(String alias, String publicKey, byte[] profileImage,UUID externalIdentityID) throws CantUpdateArtistIdentityException {
        artistIdentityManager.updateArtistIdentity(alias, publicKey, profileImage,externalIdentityID);
    }

    @Override
    public Artist getArtistIdentity(String publicKey) throws CantGetArtistIdentityException, IdentityNotFoundException {
        return artistIdentityManager.getArtistIdentity(publicKey);
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
        artistIdentityManager.publishIdentity(publicKey);
    }

    @Override
    public void hideIdentity(String publicKey) throws CantHideIdentityException, IdentityNotFoundException {
        artistIdentityManager.hideIdentity(publicKey);
    }

    @Override
    public SettingsManager getSettingsManager() {
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
