package com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.enums.ExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRegisterActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ActorArtistNetworkServiceManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistActor;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.database.ArtistIdentityDao;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantInitializeArtistIdentityDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class IdentityArtistManagerImpl implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    /**
     * IdentityAssetIssuerManagerImpl member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface mmeber variables
     */
    LogManager logManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithDeviceUsers Interface member variables.
     */
    private DeviceUserManager deviceUserManager;

    private ActorArtistNetworkServiceManager actorArtistNetworkServiceManager;

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public IdentityArtistManagerImpl(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, DeviceUserManager deviceUserManager, ActorArtistNetworkServiceManager actorArtistNetworkServiceManager){
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
        this.actorArtistNetworkServiceManager = actorArtistNetworkServiceManager;
    }

    private ArtistIdentityDao getArtistIdentityDao() throws CantInitializeArtistIdentityDatabaseException {
        return new ArtistIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
    }

    public List<Artist> getIdentityArtistFromCurrentDeviceUser() throws CantListArtistIdentitiesException {

        try {

            List<Artist> artists;


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            artists = getArtistIdentityDao().getIdentityArtistsFromCurrentDeviceUser(loggedUser);


            return artists;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListArtistIdentitiesException("CAN'T GET ASSET NEW ARTIST IDENTITIES", e, "Error get logged user device", "");
        } catch (Exception e) {
            throw new CantListArtistIdentitiesException("CAN'T GET ASSET NEW ARTIST IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public Artist getIdentitArtist() throws CantGetArtistIdentityException {
        Artist artist = null;
        try {
            artist = getArtistIdentityDao().getIdentityArtist();
        } catch (CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return artist;
    }
    public Artist getIdentitArtist(String publicKey) throws CantGetArtistIdentityException {
        Artist artist = null;
        try {
            artist = getArtistIdentityDao().getIdentityArtist(publicKey);
        } catch (CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return artist;
    }
    public Artist createNewIdentityArtist(String alias, byte[] profileImage) throws CantCreateArtistIdentityException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getArtistIdentityDao().createNewUser(alias, publicKey, privateKey, loggedUser, profileImage);

            return new ArtistIdentityImp(alias, publicKey, profileImage, pluginFileSystem, pluginId);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public Artist createNewIdentityArtist(String alias, byte[] profileImage,
                                                       String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform,
                                                       ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantCreateArtistIdentityException {
        try {
            DeviceUser deviceUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getArtistIdentityDao().createNewUser(alias,publicKey,privateKey,deviceUser,profileImage,externalUserName,externalAccessToken,externalPlatform,exposureLevel,artistAcceptConnectionsType);


            return new ArtistIdentityImp(alias,publicKey,profileImage,externalUserName,externalAccessToken,externalPlatform,exposureLevel,artistAcceptConnectionsType, pluginFileSystem, pluginId);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public void updateIdentityArtist(String alias,String publicKey, byte[] profileImage,
                                     String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform,
                                     ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantUpdateArtistIdentityException {
        try {
            getArtistIdentityDao().updateIdentityArtistUser(publicKey, alias, profileImage, externalUserName,
                    externalAccessToken, externalPlatform, exposureLevel, artistAcceptConnectionsType);

        } catch (CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
    }
//
//    public boolean hasRedeemPointIdentity() throws CantListAssetRedeemPointException {
//        try {
//
//            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
//            if (getArtistIdentityDao().getIdentityAssetRedeemPointsFromCurrentDeviceUser(loggedUser).size() > 0)
//                return true;
//            else
//                return false;
//        } catch (CantGetLoggedInDeviceUserException e) {
//            throw new CantListAssetRedeemPointException("CAN'T GET IF NEW ARTIST IDENTITIES  EXISTS", e, "Error get logged user device", "");
//        } catch (CantListArtistIdentitiesException e) {
//            throw new CantListAssetRedeemPointException("CAN'T GET IF NEW ARTIST IDENTITIES EXISTS", e, "", "");
//        } catch (Exception e) {
//            throw new CantListAssetRedeemPointException("CAN'T GET ASSET NEW ARTIST IDENTITY EXISTS", FermatException.wrapException(e), "", "");
//        }
//    }


    public void registerIdentitiesANS(String publicKey) throws CantRegisterActorArtistNetworkServiceException {
        try {
            Artist artist = getArtistIdentityDao().getIdentityArtist(publicKey);
            actorArtistNetworkServiceManager.registerActorArtist((ArtistActor)artist);
        } catch (CantRegisterActorArtistNetworkServiceException | CantGetArtistIdentityException | CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
    }

}
