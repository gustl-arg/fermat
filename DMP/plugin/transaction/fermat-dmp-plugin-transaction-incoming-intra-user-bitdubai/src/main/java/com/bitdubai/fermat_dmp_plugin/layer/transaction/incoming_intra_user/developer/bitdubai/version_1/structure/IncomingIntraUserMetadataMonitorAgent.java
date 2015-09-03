package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.FermatCryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIntraUserCryptoMonitorAgentException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserCryptoSourceAdministrator;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserMetadataSourceAdministrator;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingIntraUserMetadataMonitorAgent {

    private ErrorManager                            errorManager;
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
    private IncomingIntraUserRegistry               registry;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private MonitorAgent monitorAgent;


    public IncomingIntraUserMetadataMonitorAgent(final ErrorManager errorManager, final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager, final IncomingIntraUserRegistry registry){
        this.errorManager                            = errorManager;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
        this.registry                                = registry;
    }

    public void start() throws CantStartIntraUserCryptoMonitorAgentException {
        this.monitorAgent = new MonitorAgent(cryptoTransmissionNetworkServiceManager,errorManager,registry);
        try {
            this.monitorAgent.initialize();
            this.agentThread = new Thread(this.monitorAgent);
            this.agentThread.start();
        } catch (Exception exception) {
            throw new CantStartIntraUserCryptoMonitorAgentException(CantStartIntraUserCryptoMonitorAgentException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "You should inspect the cause");
        }
    }

    public void stop(){
        this.monitorAgent.stop();
    }

    public boolean isRunning(){
        return this.monitorAgent.isRunning();
    }


    private static class MonitorAgent implements Runnable {

        private AtomicBoolean running = new AtomicBoolean(false);

        private ErrorManager                            errorManager;
        private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
        private IncomingIntraUserRegistry               registry;


        public MonitorAgent(CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager, ErrorManager errorManager , IncomingIntraUserRegistry incomingIntraUserRegistry){
            this.errorManager                            = errorManager;
            this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
            this.registry                                = incomingIntraUserRegistry;
        }

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }


        /*
         * MonitorAgent member variables
         */
        private IncomingIntraUserMetadataSourceAdministrator sourceAdministrator;


        private static final int SLEEP_TIME = 5000;

        /**
         * MonitorAgent methods.
         */
        private void initialize () {
            this.sourceAdministrator = new IncomingIntraUserMetadataSourceAdministrator(this.cryptoTransmissionNetworkServiceManager);
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {
            /**
             * Infinite loop.
             */
            running.set(true);

            while (running.get()) {
                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    cleanResources();
                    return;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            cleanResources();
        }

        private void doTheMainTask() {
            IncomingIntraUserRegistry.EventWrapper eventWrapper = null;
            try {
                eventWrapper     = this.registry.getNextCryptoPendingEvent();
                while(eventWrapper != null) {
                    processEvent(eventWrapper);
                    eventWrapper = this.registry.getNextCryptoPendingEvent();
                }
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void processEvent(IncomingIntraUserRegistry.EventWrapper eventWrapper) {
            // We have here new pending transactions, we will check the source and ask for the right
            // TransactionSender

            TransactionProtocolManager<FermatCryptoTransaction> source = null;
            try {
                source = this.sourceAdministrator.getSourceAdministrator(EventSource.getByCode(eventWrapper.eventSource));
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }

            // Now we ask for the pending transactions
            try {
                List<Transaction<FermatCryptoTransaction>> transactionList = source.getPendingTransactions(Specialist.EXTRA_USER_SPECIALIST);
                System.out.println("TTF - INTRA USER MONITOR: " + transactionList.size() + " TRAMSACTION(s) DETECTED");
                // Now we save the list in the registry
                this.registry.acknowledgeFermatCryptoTransactions(transactionList);
                System.out.println("TTF - INTRA USER MONITOR: " + transactionList.size() + " TRAMSACTION(s) ACKNOWLEDGED");
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }

            // Now we take all the transactions in state (ACKNOWLEDGE,TO_BE_NOTIFIED)
            // Remember that this list can be more extensive than the one we saved, this is
            // because the system could have shut down in this step of the protocol making old
            // transactions to be stored but not precessed.
            List<Transaction<FermatCryptoTransaction>> acknowledgedTransactions = null;
            try {
                acknowledgedTransactions = this.registry.getAcknowledgedFermatCryptoTransactions();
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }


            // An finally, for each transaction we confirm it and then register responsibility.
            for(Transaction<FermatCryptoTransaction> transaction : acknowledgedTransactions){
                try {
                    source.confirmReception(transaction.getTransactionID());
                    System.out.println("TTF - INTRA USER MONITOR: TRANSACTION RESPONSIBILITY ACQUIRED");
                    registry.acquireFermatCryptoTransactionResponsibility(transaction);
                } catch (CantConfirmTransactionException | IncomingIntraUserCantAcquireResponsibilityException exception) {
                    // TODO: Consultar si esto hace lo que pienso, si falla no registra en base de datos
                    //       la transacción
                    // We will inform the exception and try again in the next round
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
            // After finishing all the steps we mark the event as seen.
            try {
                registry.disableEvent(eventWrapper.eventId);
                System.out.println("TTF - INTRA USER MONITOR: EVENT DISABLED");
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void cleanResources() {
            /**
             * Disconnect from database and explicitly set all references to null.
             */
        }
    }
}
