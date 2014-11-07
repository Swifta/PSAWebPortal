
/**
 * SpfinancialsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.swifta.subsidiary.mats.serviceprovider.operation.spfinancial.v1_0;

    /**
     *  SpfinancialsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SpfinancialsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SpfinancialsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SpfinancialsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for cashoutrequest method
            * override this method for handling normal response from cashoutrequest operation
            */
           public void receiveResultcashoutrequest(
                    com.swifta.subsidiary.mats.serviceprovider.operation.spfinancial.v1_0.SpfinancialsStub.CashoutrequestResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cashoutrequest operation
           */
            public void receiveErrorcashoutrequest(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cashinrequest method
            * override this method for handling normal response from cashinrequest operation
            */
           public void receiveResultcashinrequest(
                    com.swifta.subsidiary.mats.serviceprovider.operation.spfinancial.v1_0.SpfinancialsStub.CashinrequestResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cashinrequest operation
           */
            public void receiveErrorcashinrequest(java.lang.Exception e) {
            }
                


    }
    