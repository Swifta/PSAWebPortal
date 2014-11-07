
/**
 * FinancialsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.swifta.subsidiary.mats.operation.financial.v1_0;

    /**
     *  FinancialsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class FinancialsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public FinancialsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public FinancialsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getaccounttransactionhistoryrequest method
            * override this method for handling normal response from getaccounttransactionhistoryrequest operation
            */
           public void receiveResultgetaccounttransactionhistoryrequest(
                    com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.GetaccounttransactionhistoryrequestResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getaccounttransactionhistoryrequest operation
           */
            public void receiveErrorgetaccounttransactionhistoryrequest(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cashinrequest method
            * override this method for handling normal response from cashinrequest operation
            */
           public void receiveResultcashinrequest(
                    com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.CashinrequestResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cashinrequest operation
           */
            public void receiveErrorcashinrequest(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for adjustaccount method
            * override this method for handling normal response from adjustaccount operation
            */
           public void receiveResultadjustaccount(
                    com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.AdjustaccountResponse0 result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from adjustaccount operation
           */
            public void receiveErroradjustaccount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for floattransfer method
            * override this method for handling normal response from floattransfer operation
            */
           public void receiveResultfloattransfer(
                    com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.FloattransferResponse1 result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from floattransfer operation
           */
            public void receiveErrorfloattransfer(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cashoutrequest method
            * override this method for handling normal response from cashoutrequest operation
            */
           public void receiveResultcashoutrequest(
                    com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.CashoutrequestResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cashoutrequest operation
           */
            public void receiveErrorcashoutrequest(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for gettransactionhistoryrequest method
            * override this method for handling normal response from gettransactionhistoryrequest operation
            */
           public void receiveResultgettransactionhistoryrequest(
                    com.swifta.subsidiary.mats.operation.financial.v1_0.FinancialsStub.GettransactionhistoryrequestResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from gettransactionhistoryrequest operation
           */
            public void receiveErrorgettransactionhistoryrequest(java.lang.Exception e) {
            }
                


    }
    