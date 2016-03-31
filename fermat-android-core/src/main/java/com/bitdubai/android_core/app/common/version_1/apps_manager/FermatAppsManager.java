package com.bitdubai.android_core.app.common.version_1.apps_manager;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.recents.RecentApp;
import com.bitdubai.android_core.app.common.version_1.recents.RecentAppComparator;
import com.bitdubai.android_core.app.common.version_1.sessions.FermatSessionManager;
import com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils;
import com.bitdubai.fermat_android_api.engine.FermatRecentApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_module.AppManager;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2016.02.26..
 */
//TODO: esta clase es la cual se encargará de manejar la creación de una aplicación fermat, manejo de sesiones, ubicacion en la stack (para el back button o para ver la lista de apps activas),
    // obtener conexiones, etc
//TODO: falta agregar el tema de cargar el AppsConfig cuando se incia la app por primera vez

public class FermatAppsManager implements com.bitdubai.fermat_android_api.engine.FermatAppsManager {

    private Map<String,RecentApp> recentsAppsStack;
    private FermatSessionManager fermatSessionManager;
    private HashMap<String,FermatAppType> appsInstalledInDevice = new HashMap<>();


    public FermatAppsManager() {
        this.recentsAppsStack = new HashMap<>();
        this.fermatSessionManager = new FermatSessionManager();
    }

    public void init(){
        AppsConfiguration appsConfiguration = new AppsConfiguration(this);
        appsInstalledInDevice = appsConfiguration.readAppsCoreInstalled();
        if(appsInstalledInDevice.isEmpty()){
            appsInstalledInDevice = appsConfiguration.updateAppsCoreInstalled();
        }
    }

    public FermatStructure lastAppStructure() {
        return selectRuntimeManager(findLastElement().getFermatApp().getAppType()).getLastApp();
    }

    @Override
    public FermatSession lastAppSession() {
        return fermatSessionManager.getAppsSession(findLastElement().getPublicKey());
    }

    private RecentApp findLastElement(){
        return (RecentApp) CollectionUtils.find(recentsAppsStack.values(), new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                int pos = ((RecentApp) o).getTaskStackPosition()+1;
                return ((RecentApp) o).getTaskStackPosition()+1 == recentsAppsStack.size();
            }
        });
    }


    @Override
    public List<FermatRecentApp> getRecentsAppsStack() {
        ArrayList list = new ArrayList(recentsAppsStack.values());
        Collections.sort(list,new RecentAppComparator());
        return list;
    }


    @Override
    public boolean isAppOpen(String appPublicKey) {
        return recentsAppsStack.containsKey(appPublicKey);
    }

    @Override
    public FermatSession getAppsSession(String appPublicKey) {
        try {
            if (fermatSessionManager.isSessionOpen(appPublicKey)) {
                orderStackWithThisPkLast(appPublicKey);
                return fermatSessionManager.getAppsSession(appPublicKey);
            } else {
                return openApp(
                        selectAppManager(
                                appsInstalledInDevice.get(appPublicKey)).getApp(appPublicKey),
                        FermatAppConnectionManager.getFermatAppConnection(
                                appPublicKey,ApplicationSession.getInstance().getApplicationContext()
                        )
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private void orderStackWithThisPkLast(String publicKey){
        RecentApp recentApp = recentsAppsStack.get(publicKey);
        recentsAppsStack.remove(publicKey);
        for(RecentApp r : recentsAppsStack.values()){
            r.setTaskStackPosition(r.getTaskStackPosition()-1);
        }
        recentApp.setTaskStackPosition(recentsAppsStack.size());
        recentsAppsStack.put(publicKey,recentApp);
    }

    @Override
    public FermatSession openApp(FermatApp fermatApp, AppConnections fermatAppConnection) {
        if(recentsAppsStack.containsKey(fermatApp.getAppPublicKey())){
//            recentsAppsStack.get(fermatApp.getAppPublicKey()).setTaskStackPosition(recentsAppsStack.size());
            orderStackWithThisPkLast(fermatApp.getAppPublicKey());
        }else{
            recentsAppsStack.put(fermatApp.getAppPublicKey(), new RecentApp(fermatApp.getAppPublicKey(),fermatApp,recentsAppsStack.size()));
        }

        if(fermatSessionManager.isSessionOpen(fermatApp.getAppPublicKey())){
            return fermatSessionManager.getAppsSession(fermatApp.getAppPublicKey());
        }else {
            return fermatSessionManager.openAppSession(fermatApp, FermatSystemUtils.getErrorManager(), FermatSystemUtils.getModuleManager(fermatAppConnection.getPluginVersionReference()), fermatAppConnection);
        }
    }


    /**
     * aca no solo la obtengo si no que la tengo que poner arriba del stack de apps
     * @param publicKey
     * @return
     */
    @Override
    public FermatApp getApp(String publicKey,FermatAppType fermatAppType) throws Exception {
        FermatApp fermatApp = null;
        if(recentsAppsStack.containsKey(publicKey)){
            fermatApp = recentsAppsStack.get(publicKey).getFermatApp();
        }else{
            fermatApp = selectAppManager(fermatAppType).getApp(publicKey);
        }
        return fermatApp;
    }

    @Override
    public FermatApp getApp(String appPublicKey) throws Exception {
        FermatApp fermatApp = null;
        if(recentsAppsStack.containsKey(appPublicKey)){
            fermatApp = recentsAppsStack.get(appPublicKey).getFermatApp();
        }else{
            fermatApp = selectAppManager(appsInstalledInDevice.get(appPublicKey)).getApp(appPublicKey);
        }
        return fermatApp;
    }

    @Override
    public FermatStructure getAppStructure(String appPublicKey, FermatAppType appType) {
        return selectRuntimeManager(appType).getAppByPublicKey(appPublicKey);
    }

    @Override
    public FermatStructure getAppStructure(String appPublicKey) {
        return selectRuntimeManager(appsInstalledInDevice.get(appPublicKey)).getAppByPublicKey(appPublicKey);
    }

    @Override
    public FermatStructure getLastAppStructure() {
        RecentApp recentApp = findLastElement();
        return selectRuntimeManager(recentApp.getFermatApp().getAppType()).getLastApp();
    }

    /**
     * Search app in every app manager in fermat (module)
     *
     * @param fermatAppType
     * @return
     */
    public AppManager selectAppManager(FermatAppType fermatAppType){
        AppManager appManager = null;
        switch (fermatAppType) {
            case WALLET:
                appManager = FermatSystemUtils.getWalletManager();
                break;
            case SUB_APP:
                appManager = FermatSystemUtils.getSubAppManager();
                break;
            case DESKTOP:
                appManager = FermatSystemUtils.getDesktopManager();
                break;
        }
        return appManager;
    }

    /**
     *
     * Search runtime manager in fermat
     *
     * @param fermatAppType
     * @return
     */
    public RuntimeManager selectRuntimeManager(FermatAppType fermatAppType){
        RuntimeManager runtimeManager = null;
        //Este swith debe ser cambiado por una petición al core pasandole el FermatAppType
        switch (fermatAppType) {
            case WALLET:
                runtimeManager = FermatSystemUtils.getWalletRuntimeManager();
                break;
            case SUB_APP:
                runtimeManager = FermatSystemUtils.getSubAppRuntimeMiddleware();
                break;
            case DESKTOP:
                runtimeManager = FermatSystemUtils.getDesktopRuntimeManager();

                break;
            case P2P_APP:
                runtimeManager = FermatSystemUtils.getP2PApssRuntimeManager();
                break;
        }
        return runtimeManager;
    }


}
