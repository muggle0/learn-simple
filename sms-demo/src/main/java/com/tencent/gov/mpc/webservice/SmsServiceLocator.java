/**
 * SmsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tencent.gov.mpc.webservice;

public class SmsServiceLocator extends org.apache.axis.client.Service implements SmsService {

    public SmsServiceLocator() {
    }


    public SmsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SmsServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Sms
    private String Sms_address = "http://10.21.233.179/services/Sms";

    public String getSmsAddress() {
        return Sms_address;
    }

    // The WSDD service name defaults to the port name.
    private String SmsWSDDServiceName = "Sms";

    public String getSmsWSDDServiceName() {
        return SmsWSDDServiceName;
    }

    public void setSmsWSDDServiceName(String name) {
        SmsWSDDServiceName = name;
    }

    public Sms_PortType getSms() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Sms_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSms(endpoint);
    }

    public Sms_PortType getSms(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SmsSoapBindingStub _stub = new SmsSoapBindingStub(portAddress, this);
            _stub.setPortName(getSmsWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSmsEndpointAddress(String address) {
        Sms_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (Sms_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                SmsSoapBindingStub _stub = new SmsSoapBindingStub(new java.net.URL(Sms_address), this);
                _stub.setPortName(getSmsWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("Sms".equals(inputPortName)) {
            return getSms();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.21.242.252:8089/services/Sms", "SmsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.21.242.252:8089/services/Sms", "Sms"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("Sms".equals(portName)) {
            setSmsEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
