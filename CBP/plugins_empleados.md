(A = Aprobado, ? = por determinar, I = implentado Android)

### Notas:
- Los contratos no se cancelan (por ahora) y para evitar que se mantenga siempre abierto, se ha de colocar un estado EXPIRADO para indicar que el contrato paso las fechas para su ejecucion y asi filtrarlo de la lista de contratos abiertos
- layer CER con inteligencia, al estipo de P2P (plugins de index ya no irian, porque al parecer ya no se usan)
- Settings de locaciones y cuentas bancarias se guardaran en Plugins de `Customer Broker Sale Negotiation` y `Customer Broker Purchase Negotiation` ya que ellos tienen la responsabildiad de llevar la informacion de las negociaciones.
- Setting de wallets asociadas como Stock se guarda en Plugin `Crypto Broker Wallet` para que el tenga toda la informacion que necesita para hacer sus debitos y creditos.
- El Stock de una mercaderia puede estar descompuesto en varias wallets
- Setting de Wallets asociadas como Ganancia se guardan en Plugin `Matching Engine`, ya que el tiene la responsabilidad de obtener las ganancias
- con respecto a los datos de la negociacion se llego a lo siguiente:
  - las fechas en las clausulas van a ser tipo long
  - `Negotiation Expiration Date` va ser un campo tipo long de `NegotiationInformation` y no una clausula
  - `Memo` va a ser un campo tipo String (texto libre) de `NegotiationInformation` y no una clausula
  - `Cancel Reason` va a ser un campo tipo String (texto libre) de `NegotiationInformation` y no una clausula
- Procurar atomicidad en metodos que guardan settings en los diferentes plugins 
- Es necesario actualizar `NegotiationBankAccount` para cambiar `UUID getBankAccountId();`por `String getWalletPublicKey();`
- Agregar en la interface `FiatIndex` creando por Fraklin un metodo `Currency getCurrency()` para devolver la otra moneda que se usa para obtener la cotizacion, dado que actualmente solo existe `Currency getMerchandise()`
- Despues de analizar la situacion con respecto a obtener la data complementaria que se debe mostrar en la lista de brokers y en la broker community llegamos a la siguientes concluciones:
  - Basicamente el Plugin `Crypto Broker Actor` va a ser el encargado de persistir la informacion complementaria en Base de Datos, esta es:
    - Informacion basica del actor: 
      - Foto, Alias, PublicKey
    - Mercancias:
      - Se usa para asignar valor a la Clausula `CUSTOMER_CURRENCY`
      - `List<Currency>`
      - Se obtiene de la Broker Wallet. El tiene la lista de wallets asociadas y en concecuencia las mercancias
    - Cotizacion
      - Se usa para asignar valor a la Clausula `EXCHANGE_RATE`
      - Sale de Broker Wallet, por el metodo FiatIndex getMarketRate(...)
      - `List<FiatIndex>`
      - Este FiatIndex va a tener la mercancia, la moneda de pago y la cotoizacion propiamente dicha
    - Monedas de Pago
      - Se usa para asignar valor a la Clausula `BROKER_CURRENCY`
      - `List<Currency>`
      - son las mimas mercancias
    - Formas de Pago que acepta el broker
      - Se usa para asignar valor a la Clausula `CUSTOMER_PAYMENT_METHOD`
      - `Map<Currency, List<Platform>>`
      - Se obtiene de la lista de wallets asociadas como ganancia en MatchingEarning y se puede inferir esa informacion 
    - Formas de Entrega que maneja el broker
      - Se usa para asignar valor a la Clausula `BROKER_PAYMENT_METHOD`
      - `Map<Currency, List<Platform>>`
      - Se obtiene de la Broker Wallet, ya que el tiene las wallet asociadas, y se puede inferir esa informacion
  
  - Crear un agente en el `Plugin Crypto Broker Actor` que actualice o agregue periodicamente esta informacion llamando al `Crypto Broker Actor Network Service` para que devuelva la informacion complementaria de los brokers conectados que tiene registrados
  - Haria falta un metodo en el Plugin `Crypto Broker Actor` que devuelva la informacion registrada para poder ser consumida por otros plugins, como por ejemplo los Modules
  - El Plugin `Crypto Broker Actor` tambien a de guardar su propia informacion complementaria ademas de la de los actores conectados para que pueda ser consumida por el `Crypto Broker Actor Network Service` cuando lo requiera

### Crypto Broker Reference Wallet

#### Home Open Negotiations Tab [I]
Metodos:
- `Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset)` 
- `Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset)`

Plguins y Flujos:
  - **Esperando por Broker** >> `Customer Broker Sale Negotiation`
  - **Esperando por Customer** >> `Customer Broker Sale Negotiation`

#### Home Open Contracts Tab [I]
Metodos:
- `Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset)`
- `Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset)`

Plguins y Flujos:
  - **Esperando por Broker** >> `Customer Broker Sale Contract`
  - **Esperando por Customer** >> `Customer Broker Sale Contract`

#### Open Negotiation Details
Metodos
  - `List<NegotiationStep> getSteps(CustomerBrokerNegotiationInformation negotiationInfo);`
  - `boolean isNothingLeftToConfirm(List<NegotiationStep> steps);`
  - `CustomerBrokerNegotiationInformation sendNegotiationSteps(CustomerBrokerNegotiationInformation data, List<NegotiationStep> steps);`
  - `void modifyNegotiationStepValues(NegotiationStep step, NegotiationStepStatus status, String... newValues)`
  - `List<String> getPaymentMethods(String currencyToSell);`
  - `Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType)`
  - [implementar] `Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency)`
  - [implementar] `FiatIndex getMarketRate(Currency merchandise, Currency fiatCurrency, CurrencyType currencyType)`
    - FiatIdex propio de la wallet
  - [implementar] `CustomerBrokerNegotiationInformation setMemo(String memo, CustomerBrokerNegotiationInformation data);`

Plugins y Flujos
  - **Agregar notas** >> `Customer Broker Sale Negotiation`
    - Se registra en el campo MEMO
  - **Locaciones del broker** >> `Customer Broker Sale Negotiation` 
    - Esto esta como un setting de `Customer Broker Sale Negotiation` 
    - Se envia al customer a traves del plugin `Customer Broker Update Negotiation Transmission` solo la opcion seleccionada por el
    - Se asigna esta lista cuando el Broker confirme que quiere aceptar Cash on Hand y Cash Delivery para recibir el pago
  - **Cuentas bancarias del broker** >> `Customer Broker Sale Negotiation` 
    - Esto esta como un setting de `Customer Broker Sale Negotiation`
    - Se envia al customer a traves del plugin `Customer Broker Update Negotiation Transmission` solo la opcion seleccionada por el
    - Se asigna esta lista cuando el Broker confirme que quiere aceptar Bank Transfer como pago
  - **Locación del customer** >> `Customer Broker Sale Negotiation`
    - La asigna el customer en su wallet cuando confirme que quiere aceptar la mercancia como Cash
    - No va a ser editable por el broker
  - **Cuenta bancaria del customer** >> `Customer Broker Sale Negotiation`
    - La asigna el customer en su wallet cuando confirme que quiere aceptar la mercancia como Bank Transfer
    - No va a ser editable por el broker
  - **Precio de mercado** >> Plugin de super capa `Currency Exchange Rates`
    - Obtengo referencia del plugin Provider para ese par de mercancias desde los settings de `Crypto Broker Wallet` 
    - Obtengo el precio del mercado del plugin Provider 
  - **Precio sugerido** >> `Crypto Broker Wallet`
  - **Datos ingresados** >> `Customer Broker Sale Negotiation`
    - El plugin tiene un metodo que me devuelve las clausulas con los datos ingresados en el orden correcto, una clausula a la vez
  - **Modificar Datos** >> `Customer Broker Sale Negotiation`
    - Me creo un wrapper para tener la data modificada y la actualice el plugin `Customer Broker Sale Negotiation`
  - **Enviar Datos** >> `Customer Broker Update Negotiation Transmition`
    - Se envia el objeto `NegotiationInformation` con la informacion de la negociacion
  - **Cerrar Negociacion** >> `Customer Broker Close Negotiation Transmition`
    - Se envia el objeto `NegotiationInformation` con la informacion de la negociacion
  - **Cancelar Negociacion** >> `Customer Broker Update Negotiation Transmition`
    - Se cambia el estado de la negociacion a CANCELED usando `Customer Broker Sale Negotiation` 
    - se envia la informacion de la negociacion a `Customer Broker Update Negotiation Transmition` para que se encargue de actualizar la info en la wallet del customer
    - Se va a usar un campo CANCELED Reason (Texto libre) para indicar el porque de la cancelacion

#### Close Negotiation Details
Metodos
  - `List<NegotiationStep> getSteps(CustomerBrokerNegotiationInformation negotiationInfo);`
  - `CustomerBrokerNegotiationInformation getNegotiationDetails(string walletPublicKey)`

#### Open Contract Details (terminar de definir metodos del Module)
Metodos
  - [implementar] `CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(final String ContractId)`
    - plugin dueño: `CustomerBrokerContractSaleManager#getCustomerBrokerContractSaleForContractId(final String ContractId)`

Puglis y Flujos
  - **Datos ingresados** >> `Customer BrokerSale Contract` usando `Customer Broker Sale Negotiation` para obtener el detalle de la negociacion y terminar de armar la data a mostrar
  - **Referencia a la negociation** >> `Customer Broker Sale Contract`
  - **Enviar Confirmacion de pago** >> `Broker Ack Offline Payment Business Transaction` o `Broker Ack Online Payment Business Transaction` dependiendo de la moneda a vender
    - En el caso de `Broker Ack Online Payment Business Transaction` el module no lo ejecuta directamente
    - Se envia el contractID
  - **Enviar Mercancia** >> `Broker Submit Offline Merchandise Business Transaction` o `Broker Submit Online Merchandise Business Transaction` dependiendo de la moneda a vender
    - Se envia el contractID

#### Close Contract Details [I]
Metodos
  - `Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset)`
    - Le paso el `ContractBasicInformation` seleccionado en la pantalla de Contract History

#### Market Rates [I]
Metodos
  - `Collection<IndexSummary> getCurrentIndexSummaryForStockCurrencies()`
  - [implementar] `List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings(String cbpWalletPublicKey)`
  - [implementar] `Collection<ExchangeRate> getExchangeRateListFromDate(UUID providerId, Currency from, Currency to, long timestamp)`;

Plugins y Flujos
  - **(A) Lista de referencias a los proveedores** >> `Customer Broker Wallet`
  - **(A) Tasa de cambin actual** >> me la da el Plugin de cada proveedor referenciado en `Customer Broker Wallet`
  - **(A) Historico de Tasas de Cambio para un par de mercancias** >> me la da el Plugin de cada proveedor 

#### Stock Merchandise
Metodos
- [implementar] `List<CryptoBrokerStockTransaction> getStockHistory(FermatEmun merchandise, CurrencyType currencyType, int offset, long timestamp, String publicKey)`
  - El historial va a ser diario
- [implementar] `List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings(String publicKey)`
- [implementar] `float getAvailableBalance(FermatEnum merchandise, String publicKey)`

Plugins y Flujos
  - **Lista de mercancias seleccionadas como stock** >> `Crypto Broker Wallet`
  - **Lista de Stock actual de mercancias** >> `Crypto Broker Wallet`
  - **Historico de stock para una mercancia** >> `Crypto Broker Wallet` 
    - Debe darme info de inicio y fin del Stock para cada uno de los dias en ese historico

#### Contract History [I]
Metodos
 - `Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset)`
   - Plugin dueño: `CustomerBrokerContractSaleManager#Collection<CustomerBrokerContractSale> getCustomerBrokerContractSaleForStatus(final ContractStatus status)`

  - **Lista de contratos cerrados y cancelados** >> `Customer Broker Sale Contract`
  - **Metodo para filtrar contratos por estado** >> `Customer Broker Sale Contract`

#### Earnings (terminar de definir metodos del Module y del plugin MatchigEngine)
Metodos

Plugins y Flujos 
  - **Lista de mercancias seleccionadas como ganancia** >> Settings `Matching Engine Middleware` 
  - **Historico de ganancias para una mercancia** >> `Matching Engine Middleware`

#### Wizard Identity [I]
Metodos
  - `boolean associateIdentity(String brokerPublicKey);`
  - `List<CryptoBrokerIdentity> getListOfIdentities();`

Plugins y flujos
  - **Lista de Identidades** >> `Crypto Broker Identity`
  - **Asociar Wallet con Identidad** >> `Crypto Broker Actor`

#### Wizard Stock Merchandises [I]
Metodos
  - `List<InstalledWallet> getInstallWallets()`
  - `List<BankAccountNumber> getAccounts(String walletPublicKey)`
  - `CryptoBrokerWalletSettingSpread newEmptyCryptoBrokerWalletSetting()`
  - `CryptoBrokerWalletAssociatedSetting newEmptyCryptoBrokerWalletAssociatedSetting()`
  - `FiatCurrency getCashCurrency(String walletPublicKey)`
  - `void saveWalletSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread, String publicKeyWalletCryptoBrokerInstall)`
  - `void saveWalletSettingAssociated(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting, String publicKeyWalletCryptoBrokerInstall)`

Plugins y flujos
  - **Plataformas (tipos de wallet: bank, cash o crypto)** >> Enum `Platforms`
  - **Wallets Disponibles para plataforma seleccionada** >> `WPD Wallet Manager`
  - **Asociar Wallet como stock** >> Settings de `Crypto Broker Wallet` 
    - Se va a registrar en una tabla que representa el setting de wallets asociadas
    - Con las wallets asociadas se conoce que mercancias se van a utilizar
    - Se debe guardar en el setting: 
      - la plataforma
      - el public_key de la wallet que voy a asociar
      - la mercaderia que maneja esa wallet. 
        - En el caso de una wallet bank se debe guardar tambien de que cuenta va a salir su stock 
        - El stock sale de una unica cuenta
  - **Spread** >> Settings de `Crypto Broker Wallet`
    - este es un setting de valor unico y es un numero entre 0 y 1 (es un porcentaje)

#### Wizard Earning Merchandises (terminar de definir metodos del Module y del plugin MatchigEngine) [I]
Metodos
  - `List<InstalledWallet> getInstallWallets()`
  - [propuesto] `CryptoBrokerEarningWalletAssociatedSetting newEmptyCryptoBrokerEarningWalletAssociatedSetting()`
  - [propuesto] `void saveEarmingWalletSettingAssociated(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting, String publicKeyWalletCryptoBrokerInstall)`

Plugin y Flujos
  - **Plataformas (tipos de wallet: bank, cash o crypto)** >> Enum `Platforms`
  - **Asociar Wallet como ganancia** >> Settings `Matching Engine Middleware`
    - En esta primera version solo se va a permitir seleccionar una wallet por plataforma
    - Se debe guardar en el setting: 
      - la plataforma
      - el public_key de la wallet que voy a asociar
      - la mercaderia que maneja esa wallet.

#### Wizard Providers (implementar metodos del module) [I]
Metodos
  - [implementar] `Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(CurrencyPair currencyPair)`
  - [implementar] `CryptoBrokerWalletProviderSetting newEmptyCryptoBrokerWalletProviderSetting()`
  - [implementar] `void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting setting)`

Plugins y flujos
  - **Obtener Pares de Monedas** >> Enums `FiatCurrency` y `CryptoCurrency` que implementan `Currency`
  - **(A) Lista de Proveedores** >> la implementacion de la super capa CER
    - Los puedo mostrar todos sin importar que wallets he asociado. 
    - Deberia existir una interface o plugin que me perimta listarlos y obtener referencias a ellos
  - **(A) Asociar proveedores con la wallet** >> Settings de `Crypto Broker Wallet`
    - va a ser un setting con multiples valores
    - UUID del plugin, nombre descripyivo (esto es temporal, hasta que se confirme que va a ser asi)

#### Wizard Locations [I]
Metodos:
  - `void createNewLocation(String location, String uri)`
    - uri es opcional, puede ser null 

Plugins y Flujos
  - **Agregar Locaciones** >> Settings de `Customer Broker Sale Negotiation`
    - El setting va a ser un String con el texto de la ubicacion
    - URI que devuelve googlemaps para el texto ingresado como locacion. Esto va a ser un campo opcional
    - Es un setting con multiples registros

#### Wizard Bank Accounts (implementar metodos del module. Depende del plugin MatchingEarnings)
Metodos:
  - [implementar] `List<Object> getEarningWallets();`
    - Esto depende del plugin MatchingEarnings
  - `List<BankAccountNumber> getAccounts(String walletPublicKey);`
  - [implementar] `NegotiationBankAccountImpl newEmptyNegotiationBankAccount();`
  - [implementar] `void createNewBankAccount(NegotiationBankAccount bankAccount);`
    - El metodo esta tambien en Customer Broker Sale Negotiation

Plugins y flujos:  
  - **Seleccionar cuentas bancarias** >> Settings de `Customer Broker Sale Negotiation`
    - Depende de haber seleccionado una Bank Wallet como ganancia.
    - La informacion de las cuentas deberia venir de alli.
    - El `Crypto Broker Wallet Module` se encarga de registrar las cuentas seleccionadas por el usuario en el setting de `Customer Broker Sale Negotiation`
    - La info a guardar es un unico String con la informacion de la cuenta bancaria: numero de cuenta, banco, tipo cuenta, nombre del titular
    - Es un setting con multiples registros
  - **Lista de cuentas bancarias** `Bank Money Wallet`
    - le paso la public_key de la wallet

#### Settings Activity

#### Settings Merchandises
Metodos
  - [implementar] `float getAvaliableBalanceCrypto(String cryptoPublicKey);`
  - [implementar] `float getAvaliableBalanceCash(String cashPublicKey);`
  - [implementar] `float getAvaliableBalanceBank(String bankPublicKey, String bankAccount);`
  - [implementar] `List<CryptoBrokerWalletAssociatedSetting> getAssociatedWallets(string cbpWalletPublicKey)`
  - `void createTransactionRestockBank(...)`
  - `void createTransactionDestockBank(...)`
  - `void createTransactionRestockCash(...)`
  - `void createTransactionDestockCash(...)`
  - `void createTransactionRestockCrypto(...)`
  - `void createTransactionDestockCrypto(...)`

Pluguins y Flujos:
  - **Lista de Wallets asociadas** >> `Crypto Broker Wallet` (informacion registrada como setting)
  - **Hacer Restock** >> `Crypto Money Restock` o `Bank Money Restock` o `Cash Money Restock` dependiendo de la wallet devuelta
  - **Hacer Destock** >> `Crypto Money Restock` o `Bank Money Restock` o `Cash Money Restock`

#### Settings Locations
Metodos
  - `Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType)`
  - `void createNewLocation(String location, String uri);`
  - `void updateLocation(NegotiationLocations location);`
  - `void deleteLocation(NegotiationLocations location);`

Plugins y Flujos
  - **Lista de Locaciones actuales** >> `Customer Broker Sale Negotiation`
  - **Agregar Locaciones** >> metodo save en `Customer Broker Sale Negotiation`
  - **Modificar Locaciones** >> metodo save en `Customer Broker Sale Negotiation`
  - **Eliminar Locaciones** >> metodo delete en `Customer Broker Sale Negotiation`

#### Settings Bank Accounts
Metodos:
  - [implementar] `List<FiatCurrency> getBankAccountCurrencies()`
    - Plugin dueño: [implementar angel] `CustomerBrokerSaleNegotiationManager#getBankAccountCurrencies()`
  - [implementar] `List<Object> getEarningWallets();`
    - Esto depende del plugin MatchingEarnings
  - `List<BankAccountNumber> getAccounts(String walletPublicKey);`
  - [implementar] `NegotiationBankAccountImpl newEmptyNegotiationBankAccount();`
  - [implementar] `void createNewBankAccount(NegotiationBankAccount bankAccount);`
    - El metodo esta tambien en Customer Broker Sale Negotiation
  - [implementar] `void deleteBankAccount(NegotiationBankAccount bankAccount);`
    - El metodo esta tambien en Customer Broker Sale Negotiation
  
Plugins yFlujos:
  - **Lista de wallets tipo bank que son ganancia** >> `Matching Engine`
    - Esta registrado como un setting de ese plugin
  - **Seleccionar (agregar y eliminar) cuentas bancarias** >> Settings de `Customer Broker Sale Negotiation`
    - Depende de haber seleccionado una Bank Wallet como ganancia.
    - La informacion de las cuentas deberia venir de alli.
    - El `Crypto Broker Wallet Module` se encarga de registrar las cuentas seleccionadas por el usuario en el setting de `Customer Broker Sale Negotiation`
    - La info a guardar es un unico String con la informacion de la cuenta bancaria: numero de cuenta, banco, tipo cuenta, nombre del titular
    - Es un setting con multiples registros
  - **Lista de cuentas bancarias** >> `Bank Money Wallet`
    - le paso la public_key de la wallet



### Crypto Customer Reference Wallet

#### Home Open Negotiations Tab [I]
Metodos:
  - `Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset)` 
  - `Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset)`

Plugins y Flujos
  - **Esperando por Broker** >> `Customer Broker Purchase Negotiation`
  - **Esperando por Customer** >> `Customer Broker Purchase Negotiation`

#### Home Open Contracts Tab [I]
Metodos:
  - `Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset)`
  - `Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset)`

Plugins y Flujos:
  - **Esperando por Broker** >> `Customer Broker Purchase Contract`
  - **Esperando por Customer** >> `Customer Broker Purchase Contract`

#### Open Negotiation Details
Metodos
  - `List<NegotiationStep> getSteps(CustomerBrokerNegotiationInformation negotiationInfo);`
  - `boolean isNothingLeftToConfirm(List<NegotiationStep> steps);`
  - `CustomerBrokerNegotiationInformation sendNegotiationSteps(CustomerBrokerNegotiationInformation data, List<NegotiationStep> steps);`
  - `void modifyNegotiationStepValues(NegotiationStep step, NegotiationStepStatus status, String... newValues)`
  - `List<String> getPaymentMethods(String currencyToSell);`
  - `Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType)`
  - [implementar] `Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency)`
    - esto va a estar en los settings del module
  - [implementar] `FiatIndex getMarketRate(Currency merchandise, Currency fiatCurrency, CurrencyType currencyType)`
    - FiatIdex propio de la wallet
  - [implementar] `CustomerBrokerNegotiationInformation setMemo(String memo, CustomerBrokerNegotiationInformation data);`

Plugins y Flujos
  - **Agregar notas** >> `Customer Broker Purchase Negotiation` 
    - Se registra en el campo MEMO
  - **Locacion del broker** >> `Customer Broker Purchase Negotiation` 
    - La asigna el broker en su wallet cuando confirme que quiere aceptar Cash on Hand y Cash Delivery como pago
    - No va a ser editable por el customer
  - **Cuenta bancaria del broker** >> `Customer Broker Purchase Negotiation`
    - La asigna el broker en su wallet cuando confirme que quiere aceptar Bank Transfer como pago
    - No va a ser editable por el customer
  - **Locaciones del customer** >> `Customer Broker Purchase Negotiation` 
    - Esto esta como un setting de `Customer Broker Purchase Negotiation` 
    - Se envia al broker a traves del plugin `Customer Broker Update Negotiation Transmition` solo la opcion seleccionada
    - Se muestra esta opcion si el broker ofrece la mercancia como Cash
  - **Cuenta(s) bancaria(s) del customer** >> `Customer Broker Purchase Negotiation`
    - Esto esta como un setting de `Customer Broker Purchase Negotiation` 
    - Se envia al broker a traves del plugin `Customer Broker Update Negotiation Transmition` solo la opcion seleccionada
    - Se muestra esta opcion si el broker ofrece la mercancia por Bank Transfer
  - **Monedas que acepta el broker como pago** >> `Crypto Customer Wallet Module`
    - Se guarda en un xml como cache en el Module de la Wallet ya que esto viene de la cotizacion que se obtiene de la lista de brokers
  - **Precio de mercado** >> Plugin de super capa `Currency Exchage Rates`
    - Obtengo referencia del plugin Provider para ese par de mercancias desde los settings de `Crypto Customer Wallet Module`
    - Obtengo el precio del mercado del plugin Provider 
  - **Datos ingresados** >> `Customer Broker Purchase Negotiation`
    - El plugin tiene un metodo que me devuelve las clausulas con los datos ingresados en el orden correcto, una clausula a la vez
  - **Modificar Datos** >> `Customer Broker Purchase Negotiation`
    - me creo un wrapper para tener la data modificada y se actualice en `Customer Broker Purchase Negotiation`
  - **Modificar Datos** >> `Customer Broker Purchase Negotiation`
    - Me creo un wrapper para tener la data modificada y la actualice el plugin `Customer Broker Purchase Negotiation`
  - **Enviar Datos** >> `Customer Broker Update Negotiation Transmition`
    - Se envia el objeto `NegotaitonInformation` con la informacion de la negociacion
  - **Cerrar Negociacion** >> `Customer Broker Close Negotiation Transmition`
    - Se envia el objeto `NegotaitonInformation` con la informacion de la negociacion
  - **Cancelar Negociacion** >> `Customer Broker Update Negotiation Transmition`
    - Se cambia el estado de la negociacion a CANCELED usando `Customer Broker Purchase Negotiation` 
    - se envia la informacion de la negociacion a `Customer Broker Update Negotiation Transmition` para que se encargue de actualizar la info en la wallet del broker
    - Se va a usar un campo CANCELED Reason (Texto libre) para indicar el porque de la cancelacion

#### Close Negotiation Details

#### Open Contract Details
  - **Estado del contrato** >> `Customer Broker Purchase Contract`
    - este me devuelve un `ContractStatus` que me indica en que paso del contrato nos encontramos, y asi puedo tildar aquellos pasos que ya se procesaron, o sencillamente mostrar informacion resumida sobre el contrato en caso de que este haya sido completado o cancelado (este cancelado, hasta donde tengo entendido, se refiere a una negociacion que se cancela)
  - **Datos ingresados** >> `Customer Broker Purchase Contract` usando `Customer Broker Purchase Negotiation` para obtener el detalle de la negociacion y terminar de armar la data a mostrar
  - **Referencia a la negociation** >> `Customer Broker Purchase Contract`
  - **Enviar Pago** >> `Customer Online Payment Business Transaction` o `Customer Offline Payment Business Transaction` dependiendo de la moneda de pago
  - **Enviar Confirmacion de mercancia** >> `Customer Ack Offline Merchandise Business Transaction` o `Customer Ack Online Merchandise Transaction` dependiendo del tipo de mercancia
    - En el caso de `Customer Ack Online Merchandise Transaction` el module no lo ejecuta directamente

#### Close Contract Details [I]
Metodos
  - `Collection<ContractBasicInformation> getContractsHistory(ContractStatus status, int max, int offset)`
    - Le paso el `ContractBasicInformation` seleccionado en la pantalla de Contract History

#### Broker List [I]
Metodos:

Plugins y flujos:
  - **Obtener lista de brokers con sus mercancias** >> `Crypto Broker Actor`
  - **Obtener lista de cotizaciones para una mercancia dada** >> `Crypto Broker Actor`
  - **Obtener Metodos de pago** >> `Crypto Broker Actor`
  - **Obtener Metodos de entrega** >> `Crypto Broker Actor`
  
#### Start Negotiation
Metodos:

Plugins y Flujos:
  - **Mercancia a comprar**
    - Viene en la informacion de la sesion cuando selecciono un broker de la Broker List
    - Clausula `CUSTOMER_CURRENCY`
    - Revisar las notas de este documento
  - **Indicar cuanta mercancia quiere comprar**
    - Lo ingresa el customer
    - Clausula `CUSTOMER_CURRENCY_QUANTITY`
    - Revisar las notas de este documento
  - **Mostrar Precio de Cotizacion**
    - Viene en la informacion de la sesion cuando selecciono un broker de la Broker List
    - El precio de mercado que se muestra de referencia a de venir de los proveedores que selecciona el Customer
    - Clausula `EXCHANGE_RATE`
    - Revisar las notas de este documento
  - **Mostrar Metodos de Entrega** >> `Crypto Customer Module`
    - Viene en la informacion de la sesion cuando selecciono un broker de la Broker List
    - Clausula `CUSTOMER_PAYMENT_METHOD`
    - Revisar las notas de este documento
  - **Mostrar Moneda de pago** >> `Crypto Customer Module`
    - Viene en la informacion de la sesion cuando selecciono un broker de la Broker List
    - Clausula `BROKER_CURRENCY`
    - Revisar las notas de este documento
  - **Mostrar Metodos de Pago**
    - Viene en la informacion de la sesion cuando selecciono un broker de la Broker List
    - Clausula `CUSTOMER_PAYMENT_METHOD`
    - Revisar las notas de este documento
  - **Crear Nueva negociacion** >> `Crypto Customer New Negotiation Transaction`
    - Se le pasa un objeto `CustomerBrokerPurchaseNegotiation` al metodo `createCustomerBrokerNewPurchaseNegotiationTransaction()`
    - Me deberia devolver un registro `NegotiationInformation` con los datos de esta nueva negociacion

#### Market Rates [I]
  - **Lista de Tasas de Cambio actual por pares de mercancia** >> se devuelven las referencias a los proveedores registrados en el Setting de `Crypto Customer Wallet Module`
    - Obtengo del plugin proveedor el precio del mercado

#### Contract History [I]
  - **Lista de contratos cerrados y cancelados** >> `Customer Broker Purchase Contract`
  - **Metodo para filtrar contratos por estado** >> `Customer Broker Purchase Contract`

#### Wizard Identity [I]
Metodos
  - `boolean associateIdentity(String customerPublicKey);`
  - `List<CryptoCustomerIdentity> getListOfIdentities();`

Plugins y flujos
  - **(A) Lista de Identidades** >> `Crypto Customer Identity`
  - **(A) Asociar Wallet con Identidad** >> `Crypto Customer Actor`

#### Wizard Bitcoin Wallet y Providers [I]
Metodos
  - `List<InstalledWallet> getInstallWallets()`
  - `void saveWalletSettingAssociated(CryptoCustomerWalletAssociatedSetting setting, String customerWalletpublicKey)`
  - [implementar] `Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(CurrencyPair currencyPair)`
  - [implementar] `CryptoCustomerWalletProviderSetting newEmptyCryptoCustomerrWalletProviderSetting()`
  - [implementar] `void saveCryptoCustomerWalletProviderSetting(CryptoCustomerWalletProviderSetting settting)`

Plugins y flujos
  - **Wallets Disponibles para plataforma seleccionada** >> `WPD Wallet Manager`
    - Solo se van a mostrar wallets de tipo Crypto
  - ** Asociar Wallet** >> Settings de `Crypto Customer Wallet Module`
    - Se debe guardar en el setting la plataforma y el public_key de la wallet
  - **Obtener Pares de Monedas** >> Enums `FiatCurrency` y `CryptoCurrency` que implementan `Currency`
  - **Lista de Proveedores** >> la implementacion de la super capa CER
    - Los puedo mostrar todos sin importar que wallets he asociado. 
    - Deberia existir una interface o plugin que me perimta listarlos y obtener referencias a ellos
  - **Asociar proveedores con la wallet** >> Settings de `Crypto Customer Module`
    - va a ser un setting con multiples valores
    - UUID del plugin, nombre descripyivo (esto es temporal, hasta que se confirme que va a ser asi)

#### Wizard Locations [I]
Metodos:
  - `void createNewLocation(String location, String uri)`
    - uri es opcional, puede ser null 
    - Plugin dueño: `CustomerBrokerPurchaseNegotiationManager#createNewLocation(String location, String uri)`

Plugins y Flujos
  - **Agregar Locaciones** >> Settings de `Customer Broker Purchase Negotiation`
    - El setting va a ser un String con el texto de la ubicacion
    - URI que devuelve googlemaps para el texto ingresado como locacion. Esto va a ser un campo opcional
    - Es un setting con multiples registros

#### Wizard Bank Accounts
Metodos
  - [implementar] `NegotiationBankAccountImpl newEmptyNegotiationBankAccount();`
  - [implementar] `void createNewBankAccount(NegotiationBankAccount bankAccount);`
    - El metodo esta tambien en Customer Broker Sale Negotiation
    - Plugin dueño: `CustomerBrokerPurchaseNegotiationManager#createNewBankAccount(NegotiationBankAccount bankAccount)`

Plugins y Flujos
  - **Agregar cuentas bancarias** >>Settings de `Customer Broker Purchase Negotiation`

#### Settings Activity

#### Setting Providers
Metodos
  - [implementar] `List<CryptoCustomerWalletProviderSetting> getAssociatedProviders(String walletPublicKey)`
  - [implementar] `Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(CurrencyPair currencyPair)`
  - [implementar] `CryptoCustomerWalletProviderSetting newEmptyCryptoCustomerrWalletProviderSetting()`
  - [implementar] `void saveCryptoCustomerWalletProviderSetting(CryptoCustomerWalletProviderSetting settting)`
  - [implementar] `void deleteCryptoCustomerWalletProviderSetting(CryptoCustomerWalletProviderSetting settting)`

Plugins y Flujos
  - **Lista de Currencies configuradas** >> Settings de `Crypto Customer Wallet Module`
  - **Lista de Currencies que soporta Fermat** >> Enums `CryptoCurrency` y `FiatCurrency`
  - **Agregar Currency** >> Settings de `Crypto Customer Wallet Module`
  - **Modificar Currency** >> Settings de `Crypto Customer Wallet Module`
  - **Eliminar Currency** >> Settings de `Crypto Customer Wallet Module`

#### Setting Locations
 Metodos
  - `Collection<NegotiationLocations> getAllLocations(NegotiationType negotiationType)`
  - `void createNewLocation(String location, String uri);`
  - `void updateLocation(NegotiationLocations location);`
  - `void deleteLocation(NegotiationLocations location);`

Plugins y Flujos
  - **Lista de Locaciones actuales** >> `Customer Broker Sale Negotiation`
  - **Agregar Locaciones** >> metodo save en `Customer Broker Sale Negotiation`
  - **Modificar Locaciones** >> metodo save en `Customer Broker Sale Negotiation`
  - **Eliminar Locaciones** >> metodo delete en `Customer Broker Sale Negotiation`

#### Setting Bank Accounts
Metodos
  - [implementar] `List<FiatCurrency> getBankAccountCurrencies()`
    - Plugin dueño: [implementar angel] `CustomerBrokerPurchaseNegotiationManager#getBankAccountCurrencies()`
  - `List<BankAccountNumber> getAccounts(String walletPublicKey);`
  - [implementar] `NegotiationBankAccountImpl newEmptyNegotiationBankAccount();`
  - [implementar] `void createNewBankAccountSetting(NegotiationBankAccount bankAccount);`
    - Plugin dueño `CustomerBrokerPurchaseNegotiationManager#createNewBankAccount(NegotiationBankAccount bankAccount);`
  - [implementar] `void deleteBankAccounSettingt(NegotiationBankAccount bankAccount);`
    - Plugin dueño: `CustomerBrokerPurchaseNegotiationManager#createDeleteBankAccount(NegotiationBankAccount bankAccount);`
  - [implementar] `void updateBankAccountSetting(NegotiationBankAccount bankAccount);`
    - Plugin dueño: `CustomerBrokerPurchaseNegotiationManager#updateBankAccount(NegotiationBankAccount bankAccount);`

Plugins y Flujos
  - **Lista de Currencies configuradas** >> Settings de `Customer Broker Purchase Negotiation`
  - **Agregar cuentas bancarias** >>Settings de `Customer Broker Purchase Negotiation`
  - **Modificar cuentas bancarias** >> Settings de `Customer Broker Purchase Negotiation`
  - **Eliminar cuentas bancarias** >> Settings de `Customer Broker Purchase Negotiation`
