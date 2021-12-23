/**
 * Sms_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tencent.gov.mpc.webservice;

public interface Sms_PortType extends java.rmi.Remote {
    public String connMas(String username, String password) throws java.rmi.RemoteException;
    public boolean checkpass(String username, String password) throws java.rmi.RemoteException;
    public String insertDownSms(String username, String password, String batch, String sendbody) throws java.rmi.RemoteException;
    public String rspUpSms(String username, String password, String msgid) throws java.rmi.RemoteException;
    public String getUpSms(String username, String password, String destaddr) throws java.rmi.RemoteException;
    public String getDownSmsResult(String username, String password, String batch, String cnt) throws java.rmi.RemoteException;
    public String getSpecialDownSmsResult(String username, String password, String batch, String msgid) throws java.rmi.RemoteException;
}
