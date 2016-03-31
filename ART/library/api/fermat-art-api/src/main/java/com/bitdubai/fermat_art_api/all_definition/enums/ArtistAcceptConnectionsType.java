package com.bitdubai.fermat_art_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public enum ArtistAcceptConnectionsType implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    AUTOMATIC("AUT"),
    MANUAL("MAN"),
    NO_CONNECTIONS("NOC");


    String code;
    ArtistAcceptConnectionsType(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static ArtistAcceptConnectionsType getByCode(String code) throws InvalidParameterException {
        for (ArtistAcceptConnectionsType value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the ArtistAcceptConnectionsType enum.");
    }

    @Override
    public String toString() {
        return "ArtistAcceptConnectionsType{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }
}

