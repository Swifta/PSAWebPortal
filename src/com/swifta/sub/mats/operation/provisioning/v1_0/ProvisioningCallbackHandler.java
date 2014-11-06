
/**
 * ProvisioningCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.swifta.sub.mats.operation.provisioning.v1_0;

    /**
     *  ProvisioningCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ProvisioningCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ProvisioningCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ProvisioningCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for specifybalancingtypetoaccountprofile method
            * override this method for handling normal response from specifybalancingtypetoaccountprofile operation
            */
           public void receiveResultspecifybalancingtypetoaccountprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SpecifybalancingtypetoaccountprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from specifybalancingtypetoaccountprofile operation
           */
            public void receiveErrorspecifybalancingtypetoaccountprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addtransferCountmessagetouserprofile method
            * override this method for handling normal response from addtransferCountmessagetouserprofile operation
            */
           public void receiveResultaddtransferCountmessagetouserprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddtransferCountmessagetouserprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addtransferCountmessagetouserprofile operation
           */
            public void receiveErroraddtransferCountmessagetouserprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addtransferCounttouserprofile method
            * override this method for handling normal response from addtransferCounttouserprofile operation
            */
           public void receiveResultaddtransferCounttouserprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddtransferCounttouserprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addtransferCounttouserprofile operation
           */
            public void receiveErroraddtransferCounttouserprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for approveresetpassword method
            * override this method for handling normal response from approveresetpassword operation
            */
           public void receiveResultapproveresetpassword(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ApproveresetpasswordResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from approveresetpassword operation
           */
            public void receiveErrorapproveresetpassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for suspendaccountholder method
            * override this method for handling normal response from suspendaccountholder operation
            */
           public void receiveResultsuspendaccountholder(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.SuspendaccountholderResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from suspendaccountholder operation
           */
            public void receiveErrorsuspendaccountholder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addpermissionstoaccountprofile method
            * override this method for handling normal response from addpermissionstoaccountprofile operation
            */
           public void receiveResultaddpermissionstoaccountprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddpermissionstoaccountprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addpermissionstoaccountprofile operation
           */
            public void receiveErroraddpermissionstoaccountprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addtransferCounttoaccountprofile method
            * override this method for handling normal response from addtransferCounttoaccountprofile operation
            */
           public void receiveResultaddtransferCounttoaccountprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddtransferCounttoaccountprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addtransferCounttoaccountprofile operation
           */
            public void receiveErroraddtransferCounttoaccountprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addthresholdtoaccountprofile method
            * override this method for handling normal response from addthresholdtoaccountprofile operation
            */
           public void receiveResultaddthresholdtoaccountprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddthresholdtoaccountprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addthresholdtoaccountprofile operation
           */
            public void receiveErroraddthresholdtoaccountprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addbackofusers method
            * override this method for handling normal response from addbackofusers operation
            */
           public void receiveResultaddbackofusers(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddbackofusersResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addbackofusers operation
           */
            public void receiveErroraddbackofusers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for resetpassword method
            * override this method for handling normal response from resetpassword operation
            */
           public void receiveResultresetpassword(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ResetpasswordResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from resetpassword operation
           */
            public void receiveErrorresetpassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addloginCountuserprofile method
            * override this method for handling normal response from addloginCountuserprofile operation
            */
           public void receiveResultaddloginCountuserprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddloginCountuserprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addloginCountuserprofile operation
           */
            public void receiveErroraddloginCountuserprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for changepassword method
            * override this method for handling normal response from changepassword operation
            */
           public void receiveResultchangepassword(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ChangepasswordResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from changepassword operation
           */
            public void receiveErrorchangepassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for activation method
            * override this method for handling normal response from activation operation
            */
           public void receiveResultactivation(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ActivationResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from activation operation
           */
            public void receiveErroractivation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addprofile method
            * override this method for handling normal response from addprofile operation
            */
           public void receiveResultaddprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addprofile operation
           */
            public void receiveErroraddprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addthreadholdmessagetouserprofile method
            * override this method for handling normal response from addthreadholdmessagetouserprofile operation
            */
           public void receiveResultaddthreadholdmessagetouserprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddthreadholdmessagetouserprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addthreadholdmessagetouserprofile operation
           */
            public void receiveErroraddthreadholdmessagetouserprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for registration method
            * override this method for handling normal response from registration operation
            */
           public void receiveResultregistration(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.RegistrationResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from registration operation
           */
            public void receiveErrorregistration(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for viewbackofusers method
            * override this method for handling normal response from viewbackofusers operation
            */
           public void receiveResultviewbackofusers(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.ViewbackofusersResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from viewbackofusers operation
           */
            public void receiveErrorviewbackofusers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addservicetouserprofile method
            * override this method for handling normal response from addservicetouserprofile operation
            */
           public void receiveResultaddservicetouserprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddservicetouserprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addservicetouserprofile operation
           */
            public void receiveErroraddservicetouserprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addtransferCountmessagetoaccountprofile method
            * override this method for handling normal response from addtransferCountmessagetoaccountprofile operation
            */
           public void receiveResultaddtransferCountmessagetoaccountprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddtransferCountmessagetoaccountprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addtransferCountmessagetoaccountprofile operation
           */
            public void receiveErroraddtransferCountmessagetoaccountprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addthreadholdmessagetoaccountprofile method
            * override this method for handling normal response from addthreadholdmessagetoaccountprofile operation
            */
           public void receiveResultaddthreadholdmessagetoaccountprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddthreadholdmessagetoaccountprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addthreadholdmessagetoaccountprofile operation
           */
            public void receiveErroraddthreadholdmessagetoaccountprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for editbackofusers method
            * override this method for handling normal response from editbackofusers operation
            */
           public void receiveResulteditbackofusers(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.EditbackofusersResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from editbackofusers operation
           */
            public void receiveErroreditbackofusers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addpermissionstouserprofile method
            * override this method for handling normal response from addpermissionstouserprofile operation
            */
           public void receiveResultaddpermissionstouserprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddpermissionstouserprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addpermissionstouserprofile operation
           */
            public void receiveErroraddpermissionstouserprofile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addglobalsetting method
            * override this method for handling normal response from addglobalsetting operation
            */
           public void receiveResultaddglobalsetting(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddglobalsettingResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addglobalsetting operation
           */
            public void receiveErroraddglobalsetting(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deletebackofusers method
            * override this method for handling normal response from deletebackofusers operation
            */
           public void receiveResultdeletebackofusers(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.DeletebackofusersResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deletebackofusers operation
           */
            public void receiveErrordeletebackofusers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addaccount method
            * override this method for handling normal response from addaccount operation
            */
           public void receiveResultaddaccount(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddaccountResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addaccount operation
           */
            public void receiveErroraddaccount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addthresholdtouserprofile method
            * override this method for handling normal response from addthresholdtouserprofile operation
            */
           public void receiveResultaddthresholdtouserprofile(
                    com.swifta.sub.mats.operation.provisioning.v1_0.ProvisioningStub.AddthresholdtouserprofileResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addthresholdtouserprofile operation
           */
            public void receiveErroraddthresholdtouserprofile(java.lang.Exception e) {
            }
                


    }
    