package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Acosta Rodrigo - (acosta_rodrigo@hotmail.com) on 28/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TimeOutNotifierAgentDatabaseConstants {

    /**
     * agents database table definition.
     */
    public static final String AGENTS_TABLE_NAME = "agents";

    public static final String AGENTS_ID_COLUMN_NAME = "id";
    public static final String AGENTS_NAME_COLUMN_NAME = "name";
    public static final String AGENTS_DESCRIPTION_COLUMN_NAME = "description";
    public static final String AGENTS_OWNER_PUBLICKEY_COLUMN_NAME = "owner_publickey";
    public static final String AGENTS_START_TIME_COLUMN_NAME = "start_time";
    public static final String AGENTS_DURATION_COLUMN_NAME = "duration";
    public static final String AGENTS_ELAPSED_COLUMN_NAME = "elapsed";
    public static final String AGENTS_STATE_COLUMN_NAME = "state";
    public static final String AGENTS_PROTOCOL_STATUS_COLUMN_NAME = "protocol_status";
    public static final String AGENTS_LAST_UPDATE_COLUMN_NAME = "last_update";

    public static final String AGENTS_FIRST_KEY_COLUMN = "id";

    /**
     * agent_owner database table definition.
     */
    public static final String AGENT_OWNER_TABLE_NAME = "agent_owner";

    public static final String AGENT_OWNER_OWNER_PUBLICKEY_COLUMN_NAME = "owner_publickey";
    public static final String AGENT_OWNER_NAME_COLUMN_NAME = "name";
    public static final String AGENT_OWNER_TYPE_COLUMN_NAME = "type";

    public static final String AGENT_OWNER_FIRST_KEY_COLUMN = "agent_owner";

}
