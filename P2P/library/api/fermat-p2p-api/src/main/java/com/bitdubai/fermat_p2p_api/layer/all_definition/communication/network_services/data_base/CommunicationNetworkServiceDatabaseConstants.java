/*
 * @#TemplateNetworkServiceDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.template.developer.bitdubai.version_1.structure.CommunicationNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CommunicationNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME                                   = "network_service_database";


    /**
     * incoming messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME                     = "incoming_messages" ;

    public static final String INCOMING_MESSAGES_ID_COLUMN_NAME                 = "id"                ;
    public static final String INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME          = "sender_id"         ;
    public static final String INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME        = "receiver_id"       ;
<<<<<<< HEAD
    public static final String INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME       = "text_content"      ;
=======
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    public static final String INCOMING_MESSAGES_TYPE_COLUMN_NAME               = "type"              ;
    public static final String INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String INCOMING_MESSAGES_STATUS_COLUMN_NAME             = "status"            ;
<<<<<<< HEAD
=======
    public static final String INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME       = "text_content"      ;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86

    public static final String INCOMING_MESSAGES_FIRST_KEY_COLUMN               = "id"                ;

    /**
     * outgoing messages database table definition.
     */
    public static final String OUTGOING_MESSAGES_TABLE_NAME                     = "outgoing_messages" ;

    public static final String OUTGOING_MESSAGES_ID_COLUMN_NAME                 = "id"                ;
    public static final String OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME          = "sender_id"         ;
<<<<<<< HEAD
    public static final String OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME        = "receiver_id"       ;
    public static final String OUTGOING_MESSAGES_SENDER_TYPE_COLUMN_NAME        = "sender_type"       ;
    public static final String OUTGOING_MESSAGES_RECEIVER_TYPE_COLUMN_NAME      = "receiver_type"     ;
    public static final String OUTGOING_MESSAGES_SENDER_NS_TYPE_COLUMN_NAME     = "sender_ns_type"    ;
    public static final String OUTGOING_MESSAGES_RECEIVER_NS_TYPE_COLUMN_NAME   = "receiver_ns_type"  ;
    public static final String OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME       = "text_content"      ;
=======
    public static final String OUTGOING_MESSAGES_SENDER_TYPE_COLUMN_NAME        = "sender_type"       ;
    public static final String OUTGOING_MESSAGES_SENDER_NS_TYPE_COLUMN_NAME     = "sender_ns_type"    ;
    public static final String OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME        = "receiver_id"       ;
    public static final String OUTGOING_MESSAGES_RECEIVER_TYPE_COLUMN_NAME      = "receiver_type"     ;
    public static final String OUTGOING_MESSAGES_RECEIVER_NS_TYPE_COLUMN_NAME   = "receiver_ns_type"  ;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    public static final String OUTGOING_MESSAGES_TYPE_COLUMN_NAME               = "type"              ;
    public static final String OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME         = "fail_count"        ;
    public static final String OUTGOING_MESSAGES_STATUS_COLUMN_NAME             = "status"            ;
<<<<<<< HEAD
=======
    public static final String OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME       = "text_content"      ;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86

    public static final String OUTGOING_MESSAGES_FIRST_KEY_COLUMN               = "id"                ;

}
