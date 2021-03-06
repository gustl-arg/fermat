package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Jose Briceño josebricenor@gmail.com on 18/02/16.
 */
public class CantLoadDAPMessageException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an exception while loading this DAPMessage.";

    public CantLoadDAPMessageException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantLoadDAPMessageException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
