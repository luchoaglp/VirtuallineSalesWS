package ar.com.virtualline.servicios;

import ar.com.virtualline.MsgException;
import ar.com.virtualline.PinSale;
import ar.com.virtualline.SaleState;
import ar.com.virtualline.VirtualLineConnection;
import ar.com.virtualline.response.DirecTVSale;
import static ar.com.virtualline.servicios.Const.*;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Luciano Giannelli
 */
@WebService(serviceName = "ventas_ws")
public class ventas_ws {

    final VirtualLineConnection connection = new VirtualLineConnection(HOST, PORT, TIME_OUT);

    /**
     * @param remoteId
     * @param pdv
     * @param phoneLine
     * @param password
     * @param areaCode
     * @param amount
     * @param product
     * @return String
     */
    @WebMethod(operationName = "virtualSale")
    public String virtualSale(
            @WebParam(name = "remoteId") String remoteId,
            @WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "amount") int amount,
            @WebParam(name = "product") long product,
            @WebParam(name = "areaCode") String areaCode,
            @WebParam(name = "phoneLine") String phoneLine) {
    
        try {

            String id = Long.valueOf(connection.virtualSale(remoteId,
                    pdv,
                    password,
                    amount,
                    product,
                    areaCode,
                    phoneLine)).toString();
            
            return "[00] - " + id;

        } catch (MsgException e) {
            return e.getMessage();
        }
    }

    /**
     * @param pdv
     * @param saleId
     * @param password
     * @return String
     */
    @WebMethod(operationName = "getSaleState")
    public String getSaleState(
            @WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "saleId") long saleId) {

        try {

            return connection.getSaleState(pdv, password, saleId).getState();

        } catch (MsgException e) {
            return e.getMessage();
        }
    }
    
    /**
     * @param pdv
     * @param password
     * @param remoteId
     * @return String
     */
    @WebMethod(operationName = "getSaleStateByRemoteId")
    public String getSaleStateByRemoteId(
            @WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "remoteId") String remoteId) {

        try {

            return connection.getSaleState(pdv, password, remoteId).getState();

        } catch (MsgException e) {
            return e.getMessage();
        }
    }
    
    /**
     * <br>
     * <pre>
     * {@code
     * public String getSaleProviderCode(long pdv, String password, long saleId) {
     * 
     *      try {
     * 
     *          ...
     *          ...
     *      
     *          return "1111 2222 3333 4444 5555";
     * 
     *      } catch (MsgException e) {
     * 
     *          return e.getMessage(); // [exception code] - exception message
     *      }
     * }
     * }
     * </pre>
     * 
     * @param pdv punto de venta
     * @param saleId
     * @param password
     * @return String
     */
    @WebMethod(operationName = "getSaleProviderCode")
    public String getSaleProviderCode(@WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "saleId") long saleId) {

        try {
            
            return connection.getSaleState(pdv, password, saleId).getExternalId();

        } catch (MsgException e) {
            
            return e.getMessage();
        }
    }

    /**
     * <br>
     * <pre>
     * {@code
     * public String getSaleExternalState(long pdv, String password, int amount, String areaCode, String phoneLine) {
     * 
     *      try {
     * 
     *          ...
     *          ...
     *      
     *          return "[00] - " + state + " - " + idTrx; // Example: [00] - OK - 00000000000123456789; 
     *                                                    // state (OK, RV, PE)
     * 
     *      } catch (MsgException e) {
     * 
     *          return e.getMessage(); // [exception code] - exception message
     *      }
     * }
     * }
     * </pre>
     *
     * @param pdv
     * @param password
     * @param amount
     * @param areaCode
     * @param phoneLine
     * @return String
     */
    @WebMethod(operationName = "getSaleExternalState")
    public String getSaleExternalState(@WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "amount") int amount,
            @WebParam(name = "areaCode") String areaCode,
            @WebParam(name = "phoneLine") String phoneLine) {

        try {
            
            SaleState saleState = connection.getSaleState(pdv, password, amount, areaCode, phoneLine);

            return "[00] - " + saleState.getState() + " - " + saleState.getId();

        } catch (MsgException e) {
            return e.getMessage();
        }
    }

    /**
     * @param remoteId
     * @param pdv
     * @param password
     * @param amount
     * @param product
     * @return String
     */
    @WebMethod(operationName = "pinSale")
    public String pinSale(
            @WebParam(name = "remoteId") String remoteId, 
            @WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "amount") int amount,
            @WebParam(name = "product") long product) {

        try {

            PinSale pinSale = connection.pinSale(remoteId, pdv, password, amount, product);

            return "[00] - " + pinSale.getTxnId() + " - " + pinSale.getPin() + " - " + pinSale.getExtra() + " - " + pinSale.getGloss();

        } catch (MsgException e) {
            return e.getMessage();
        }
    }

    /**
     * @param remoteId
     * @param pdv
     * @param password
     * @param amount
     * @param product
     * @param docType
     * @param doc
     * @return String
     */
    @WebMethod(operationName = "granDTSale")
    public String granDTSale(@WebParam(name = "remoteId") String remoteId,
            @WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "amount") int amount,
            @WebParam(name = "product") long product,
            @WebParam(name = "docType") int docType,
            @WebParam(name = "doc") String doc) {

        try {

            return "[00] - " + connection.granDTSale(remoteId, pdv, password, amount, product, docType, doc);

        } catch (MsgException e) {
            return e.getMessage();
        }
    }

    /**
     * @param remoteId
     * @param pdv
     * @param password
     * @param amount
     * @param product
     * @param code
     * @return String
     */
    @WebMethod(operationName = "directTVSale")
    public String directTVSale(@WebParam(name = "remoteId") String remoteId,
            @WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "amount") int amount,
            @WebParam(name = "product") long product,
            @WebParam(name = "code") String code) {

        try {

            DirecTVSale direcTVSale = connection.directTVSale(remoteId, pdv, password, amount, product, code);

            return "[00] - " + direcTVSale.getId() + " - " + direcTVSale.getGloss() + " - " + direcTVSale.getText();

        } catch (MsgException e) {
            return e.getMessage();
        }
    }

    /**
     * @param pdv
     * @param password
     * @return String
     */
    @WebMethod(operationName = "getBalance")
    public String getBalance(@WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password) {

        try {

            return connection.getBalance(pdv, password).getBalance().toString();

        } catch (MsgException e) {
            return e.getMessage();
        }
    }
    
    /**
     * <p>Invoke this method, if the response code is [00], then invoke edenorSale.</p>
     * <pre>
     * {@code
     * public String edenorPreSale(String remoteId, long pdv, String password, String meterNumber) {
     * 
     *      try {
     * 
     *          ...
     *          ...
     *      
     *          return "[00] - " + balance; // Example: [00] - 0,00
     * 
     *      } catch (MsgException e) {
     * 
     *          return e.getMessage(); // [exception code] - exception message
     *      }
     * }
     * }
     * </pre>
     *
     * @param pdv punto de venta
     * @param password
     * @param product (optional)
     * @param meterNumber número de medidor
     * @return String
     * 
     */
    @WebMethod(operationName = "edenorPreSale")
    public String edenorPreSale(
            @WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "product") long product,
            @WebParam(name = "meterNumber") String meterNumber) {
        
        if(product == 0) product = 128;
        
        try {
            
            return "[00] - " + connection.edenorPreSale(pdv, password, product, meterNumber);
            
        } catch (MsgException e) {
            
            return e.getMessage();
        }
    }
    
    /**
     * <p>Invoke this method, if the response code is [00], then invoke getTicket passing the transaction id returned by this method.</p>
     * <pre>
     * {@code
     * public String edenorSale(String remoteId, long pdv, String password, int amount, String meterNumber) {
     * 
     *      try {
     * 
     *          ...
     *          ...
     *      
     *          return "[00] - " + transaction_id; // Example: [00] - 00000000000146131879
     * 
     *      } catch (MsgException e) {
     * 
     *          return e.getMessage(); // [exception code] - exception message
     *      }
     * }
     * }
     * </pre>
     * 
     * @param remoteId
     * @param pdv punto de venta
     * @param password
     * @param amount
     * @param product (optional)
     * @param meterNumber número de medidor
     * @param externalMerchantId
     * @return String
     */
    @WebMethod(operationName = "edenorSale")
    public String edenorSale(@WebParam(name = "remoteId") String remoteId,
            @WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "amount") int amount,
            @WebParam(name = "product") long product,
            @WebParam(name = "meterNumber") String meterNumber,
            @WebParam(name = "externalMerchantId") Integer externalMerchantId) {
        
        if(product == 0) product = 128;
        
        try {
            
            return "[00] - " + connection.edenorSale(remoteId, pdv, password, amount, product, meterNumber, externalMerchantId);
            
        } catch (MsgException e) {
            return e.getMessage();
        }
    }
    
    /**
     * <p>Invoke this method with the previously obtained id, if the response code is [00], then the ticket is returned.</p>
     * <pre>
     * {@code
     * public String getTicket(long pdv, String password, long id) {
     * 
     *      try {
     * 
     *          ...
     *          ...
     *      
     *          return "[00] - " + transaction_id; // Example: [00] - [EDENOR, Av Libertador 6363 C1428, CUIT30655116202-IVA Insc, Mar 29/08/17 11:25:58, Comprobante 009900218514, Medidor Nro. 07043910400, Cuenta Edenor 7743640864, C.Final CUIT 00000000000, ABELDAÑO DORA MAGDALENA, MERCEDES 4140, Merlo (1723), TARIFA T1RP, VENTA ACTUAL en kWh y $, Hasta 150 kWh 0,066$/kWh, kWh    0,0        $ 0,00, Hasta 325 kWh 0,356$/kWh, kWh    2,0        $ 0,69, Hasta 400 kWh 0,541$/kWh, kWh    0,0        $ 0,00, Hasta 450 kWh 0,830$/kWh, kWh    0,0        $ 0,00, Hasta 500 kWh 1,122$/kWh, kWh    0,0        $ 0,00, Hasta 600 kWh 0,778$/kWh, kWh    0,0        $ 0,00, Mayor 600 kWh 0,636$/kWh, kWh    0,0        $ 0,00, Subtotal A        $ 0,69, IVA 21%           $ 0,15, Ley 7290/67       $ 0,07, Ley 9038          $ 0,04, Cont Munic        $ 0,05, Cont Prov         $ 0,00, Subtotal B        $ 0,31, IVA no aplica c/credito, Subtotal C        $ 0,00, Subtotal D        $ 0,00, Tasa Municip AP   $ 0,00, $ COMPRA ACTUAL     1,00, kWh COMPRA ACTUAL    2,0, kWh Acumulados     163,7, Dias ultima compra     0, Codigo que debe ingresar, 6346 8645 7299 2495 5044, Puntos de Venta habilitados:, AV. ANDRES ROLON Y TOMKINSON-San, DE LUCA 2045 MERLO-Merlo, ENSENADA 975 MARIANO ACOSTA-Merl, AV. BALBIN 9284-Merlo, AV. ECHEVERRY 1515 -Merlo, AV. DE LA UNION 2098 PONTEVEDRA-, JUAN POSSE 2660 MARIANO ACOSTA-M, GARAT 1115 Y CAMPICHUELO MERLO-M, CONSTITUYENTES 902 M. ACOSTA-Mer, MARCELO T. DE ALVEAR 511-Merlo, 25 DE MAYO 5201-Merlo, OTERO 6530 PONTEVEDRA-Merlo, AV. DEL LIBERTADOR 431-Merlo, FRAY MOCHO 141-Merlo, AVENIDA DON BOSCO 2575 HAEDO-Mor, PLAZA OESTE SHOPPING (KIOMAX)-Mo, PANAMERICANA Y DEBENEDETTI (ACA), TERMINALES DE VENTA QIWI--------]
     * 
     *      } catch (MsgException e) {
     * 
     *          return e.getMessage(); // [exception code] - exception message
     *      }
     * }
     * }
     * </pre>
     * @param pdv punto de venta
     * @param password
     * @param id
     * @return String
     */
    @WebMethod(operationName = "getTicket")
    public String getTicket(@WebParam(name = "pdv") long pdv,
            @WebParam(name = "password") String password,
            @WebParam(name = "id") long id) {
        
        try {
            
            return "[00] - " + connection.getTicket(pdv, password, "financial", "id@" + id);
            
        } catch (MsgException e) {
            return e.getMessage();
        }
    }

}
