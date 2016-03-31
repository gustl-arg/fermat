package com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources;

import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantGetSubAppNavigationStructureException;

import java.util.UUID;

/**
 * Created by natalia on 2015.07.28..
 */
public interface SubAppNavigationStructure {

    UUID getNavigationStructureId();

    String getSubAppNavigationStructure() throws CantGetSubAppNavigationStructureException;
}
