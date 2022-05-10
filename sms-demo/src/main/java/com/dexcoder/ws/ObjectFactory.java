//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.12.23 时间 06:26:36 PM CST 
//


package com.dexcoder.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.dexcoder.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetFlag_QNAME = new QName("http://server.webservice.Bag.admin.com", "getFlag");
    private final static QName _GetFlagResponse_QNAME = new QName("http://server.webservice.Bag.admin.com", "getFlagResponse");
    private final static QName _SendMessage_QNAME = new QName("http://server.webservice.Bag.admin.com", "sendMessage");
    private final static QName _SendMessageResponse_QNAME = new QName("http://server.webservice.Bag.admin.com", "sendMessageResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.dexcoder.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetFlag }
     * 
     */
    public GetFlag createGetFlag() {
        return new GetFlag();
    }

    /**
     * Create an instance of {@link GetFlagResponse }
     * 
     */
    public GetFlagResponse createGetFlagResponse() {
        return new GetFlagResponse();
    }

    /**
     * Create an instance of {@link SendMessage }
     * 
     */
    public SendMessage createSendMessage() {
        return new SendMessage();
    }

    /**
     * Create an instance of {@link SendMessageResponse }
     * 
     */
    public SendMessageResponse createSendMessageResponse() {
        return new SendMessageResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlag }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.Bag.admin.com", name = "getFlag")
    public JAXBElement<GetFlag> createGetFlag(GetFlag value) {
        return new JAXBElement<GetFlag>(_GetFlag_QNAME, GetFlag.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFlagResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.Bag.admin.com", name = "getFlagResponse")
    public JAXBElement<GetFlagResponse> createGetFlagResponse(GetFlagResponse value) {
        return new JAXBElement<GetFlagResponse>(_GetFlagResponse_QNAME, GetFlagResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.Bag.admin.com", name = "sendMessage")
    public JAXBElement<SendMessage> createSendMessage(SendMessage value) {
        return new JAXBElement<SendMessage>(_SendMessage_QNAME, SendMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.Bag.admin.com", name = "sendMessageResponse")
    public JAXBElement<SendMessageResponse> createSendMessageResponse(SendMessageResponse value) {
        return new JAXBElement<SendMessageResponse>(_SendMessageResponse_QNAME, SendMessageResponse.class, null, value);
    }

}
