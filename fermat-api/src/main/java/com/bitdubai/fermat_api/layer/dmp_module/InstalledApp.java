package com.bitdubai.fermat_api.layer.dmp_module;

import com.bitdubai.fermat_api.AppsStatus;
<<<<<<< HEAD
=======
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mati on 2016.03.09..
 */
public class InstalledApp implements com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp, Serializable{

    private List<InstalledSkin> skinsId;
    private List<InstalledLanguage> languajesId;
    private String name;
    private String publicKey;
    private Version version;
    private int iconResource;
    private int position;
    private int notifications;
    private AppsStatus appsStatus;
<<<<<<< HEAD

    public InstalledApp(String name, String publicKey, Version version, int iconResource, int position, int notifications,AppsStatus appsStatus) {
=======
    private int bannerRes;
    private Platforms platform;

    public InstalledApp(String name, String publicKey, Version version, int iconResource, int position, int notifications,AppsStatus appsStatus,Platforms platform) {
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
        this.name = name;
        this.publicKey = publicKey;
        this.version = version;
        this.iconResource = iconResource;
        this.position = position;
        this.notifications = notifications;
        this.appsStatus = appsStatus;
<<<<<<< HEAD
=======
        this.platform = platform;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    }

    @Override
    public List<InstalledLanguage> getLanguagesId() {
        return languajesId;
    }

    @Override
    public List<InstalledSkin> getSkinsId() {
        return skinsId;
    }

    @Override
    public String getIcon() {
        return String.valueOf(iconResource);
    }

    @Override
    public void setIconResource(int iconRes) {
        this.iconResource = iconRes;
    }

    @Override
    public int getIconResource() {
        return iconResource;
    }

    @Override
<<<<<<< HEAD
=======
    public void setBanner(int res) {
        this.bannerRes = res;
    }

    @Override
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getNotifications() {
        return notifications;
    }

    @Override
    public InterfaceType getType() {
        return InterfaceType.P2P_APP;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBanner() {
        return 0;
    }

    @Override
<<<<<<< HEAD
=======
    public Platforms getPlatform() {
        return platform;
    }

    @Override
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    public String getAppName() {
        return name;
    }

    @Override
    public String getAppPublicKey() {
        return publicKey;
    }

    @Override
    public AppsStatus getAppStatus() {
        return appsStatus;
    }

    @Override
    public FermatAppType getAppType() {
        return FermatAppType.P2P_APP;
    }

    @Override
    public byte[] getAppIcon() {
        return new byte[0];
    }

<<<<<<< HEAD
=======
    public int getBannerRes() {
        return bannerRes;
    }
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
}
