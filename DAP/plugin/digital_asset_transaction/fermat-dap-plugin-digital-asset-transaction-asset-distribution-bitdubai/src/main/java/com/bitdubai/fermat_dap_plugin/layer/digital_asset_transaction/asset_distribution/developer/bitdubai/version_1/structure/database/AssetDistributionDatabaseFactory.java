package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public class AssetDistributionDatabaseFactory implements DealsWithPluginDatabaseSystem {
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public AssetDistributionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Asset Distribution table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_TABLE_NAME);

            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_GENESIS_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DIGITAL_ASSET_HASH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_ACTOR_ASSET_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DISTRIBUTION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_CRYPTO_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_PROTOCOL_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_ACTOR_ASSET_USER_BITCOIN_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DISTRIBUTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);

            table.addIndex(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            eventsRecorderTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating "+AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_EVENTS_RECORDED_TABLE_NAME +" table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
            }

            DatabaseTableFactory assetDeliveringTable = databaseFactory.newTableFactory(ownerId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_TABLE_NAME);

            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 225500, Boolean.TRUE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_GENESIS_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.FALSE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.FALSE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_ASSET_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.FALSE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_USER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.FALSE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_START_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_TIMEOUT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_STATE_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_SENT_GENESISTX_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            assetDeliveringTable.addColumn(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_ATTEMPT_NUMBER_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            assetDeliveringTable.addIndex(AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, assetDeliveringTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating "+AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DELIVERING_TABLE_NAME +" table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
            }

        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
