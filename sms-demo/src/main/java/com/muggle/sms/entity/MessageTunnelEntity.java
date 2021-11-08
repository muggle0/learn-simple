package com.muggle.sms.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A MessageTunnel.
 */
@Entity
@Table(name = "mpc_message_tunnel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted = 0")
@SQLDelete(sql = "update mpc_message_tunnel set deleted = 1 where tunnel_id = ?")
public class MessageTunnelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "tunnel_id")
    private Long tunnelId;

    @Column(name = "tunnel_name")
    private String tunnelName;

    @Column(name = "tunnel_status")
    private Byte tunnelStatus;

    @Column(name = "message_channel")
    private Byte messageChannel;

    @Column(name = "sms_secret_id")
    private String smsSecretId;

    @Column(name = "sms_secret_key")
    private String smsSecretKey;

    @Column(name = "sms_send_endpoint")
    private String smsSendEndpoint;

    @Column(name = "sms_sdk_app_id")
    private String smsSdkAppId;

    // 语音secretKey
    @Column(name = "vms_secret_key")
    private String vmsSecretKey;

    /**
     * 语音secretId
     */
    @Column(name = "vms_secret_id")
    private String vmsSecretId;

    /**
     * 公众号appId
     */
    @Column(name = "mp_app_id")
    private String mpAppId;

    /**
     * 公众号secret
     */
    @Column(name = "mp_secret")
    private String mpSecret;

    /**
     * 公众号通道主营行业
     */
    @Column(name = "mp_pri_industry")
    private String mpPriIndustry;

    /**
     * 公众号副营行业
     */
    @Column(name = "mp_second_industry")
    private String mpSecondIndustry;

    /**
     * 小程序appId
     */
    @Column(name = "applet_app_id")
    private String appletAppId;

    /**
     * 小程序secret
     */
    @Column(name = "applet_secret")
    private String appletSecret;

    /**
     * 小程序名称
     */
    @Column(name = "applet_name")
    private String appletName;

    /**
     * 小程序logo文件id
     */
    @Column(name = "applet_logo_file_id")
    private String appletLogoFileId;

    /**
     * 邮件smtp地址
     */
    @Column(name = "mail_smtp")
    private String mailSmtp;

    /**
     * 邮件smtp端口
     */
    @Column(name = "mail_smtp_port")
    private Integer mailSmtpPort;

    /**
     * 邮件发送用户名
     */
    @Column(name = "mail_user_name")
    private String mailUserName;

    /**
     * 邮件发送密码
     */
    @Column(name = "mail_password")
    private String mailPassword;

    @Column(name = "comment")
    private String comment;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    @LastModifiedDate
    private Timestamp updateTime;

    @Column(name = "create_time")
    @CreatedDate
    private Timestamp createTime;

    @Column(name = "deleted")
    private Byte deleted = (byte) 0;

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public Long getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Long tunnelId) {
        this.tunnelId = tunnelId;
    }

    public MessageTunnelEntity tunnelId(Long tunnelId) {
        this.tunnelId = tunnelId;
        return this;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public MessageTunnelEntity tunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
        return this;
    }

    public Byte getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(Byte messageChannel) {
        this.messageChannel = messageChannel;
    }

    public MessageTunnelEntity messageChannel(Byte messageChannel) {
        this.messageChannel = messageChannel;
        return this;
    }

    public Byte getTunnelStatus() {
        return tunnelStatus;
    }

    public void setTunnelStatus(Byte tunnelStatus) {
        this.tunnelStatus = tunnelStatus;
    }

    public MessageTunnelEntity tunnelStatus(Byte tunnelStatus) {
        this.tunnelStatus = tunnelStatus;
        return this;
    }

    public String getSmsSecretId() {
        return smsSecretId;
    }

    public void setSmsSecretId(String secretId) {
        this.smsSecretId = secretId;
    }

    public MessageTunnelEntity secretId(String secretId) {
        this.smsSecretId = secretId;
        return this;
    }

    public String getSmsSecretKey() {
        return smsSecretKey;
    }

    public void setSmsSecretKey(String secretKey) {
        this.smsSecretKey = secretKey;
    }

    public MessageTunnelEntity secretKey(String secretKey) {
        this.smsSecretKey = secretKey;
        return this;
    }

    public String getSmsSendEndpoint() {
        return smsSendEndpoint;
    }

    public void setSmsSendEndpoint(String sendEndpoint) {
        this.smsSendEndpoint = sendEndpoint;
    }

    public MessageTunnelEntity sendEndpoint(String sendEndpoint) {
        this.smsSendEndpoint = sendEndpoint;
        return this;
    }

    public String getSmsSdkAppId() {
        return smsSdkAppId;
    }

    public void setSmsSdkAppId(String smsSdkAppId) {
        this.smsSdkAppId = smsSdkAppId;
    }

    public MessageTunnelEntity smsSdkAppId(String smsSdkAppId) {
        this.smsSdkAppId = smsSdkAppId;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MessageTunnelEntity comment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public MessageTunnelEntity updateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public MessageTunnelEntity createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public MessageTunnelEntity updateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public MessageTunnelEntity createTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getVmsSecretKey() {
        return vmsSecretKey;
    }

    public void setVmsSecretKey(String vmsSecretKey) {
        this.vmsSecretKey = vmsSecretKey;
    }

    public String getVmsSecretId() {
        return vmsSecretId;
    }

    public void setVmsSecretId(String vmsSecretId) {
        this.vmsSecretId = vmsSecretId;
    }

    public String getMpAppId() {
        return mpAppId;
    }

    public void setMpAppId(String mpAppId) {
        this.mpAppId = mpAppId;
    }

    public String getMpSecret() {
        return mpSecret;
    }

    public void setMpSecret(String mpSecret) {
        this.mpSecret = mpSecret;
    }

    public String getMpPriIndustry() {
        return mpPriIndustry;
    }

    public void setMpPriIndustry(String mpPriIndustry) {
        this.mpPriIndustry = mpPriIndustry;
    }

    public String getMpSecondIndustry() {
        return mpSecondIndustry;
    }

    public void setMpSecondIndustry(String mpSecondIndustry) {
        this.mpSecondIndustry = mpSecondIndustry;
    }

    public String getAppletAppId() {
        return appletAppId;
    }

    public void setAppletAppId(String appletAppId) {
        this.appletAppId = appletAppId;
    }

    public String getAppletSecret() {
        return appletSecret;
    }

    public void setAppletSecret(String appletSecret) {
        this.appletSecret = appletSecret;
    }

    public String getMailSmtp() {
        return mailSmtp;
    }

    public void setMailSmtp(String mailSmtp) {
        this.mailSmtp = mailSmtp;
    }

    public Integer getMailSmtpPort() {
        return mailSmtpPort;
    }

    public void setMailSmtpPort(Integer mailSmtpPort) {
        this.mailSmtpPort = mailSmtpPort;
    }

    public String getMailUserName() {
        return mailUserName;
    }

    public void setMailUserName(String mailUserName) {
        this.mailUserName = mailUserName;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getAppletName() {
        return appletName;
    }

    public void setAppletName(String appletName) {
        this.appletName = appletName;
    }

    public String getAppletLogoFileId() {
        return appletLogoFileId;
    }

    public void setAppletLogoFileId(String appletLogoFileId) {
        this.appletLogoFileId = appletLogoFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageTunnelEntity)) {
            return false;
        }
        return tunnelId != null && tunnelId.equals(((MessageTunnelEntity) o).tunnelId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageTunnel{" + "tunnelId=" + getTunnelId() + ", tunnelName='" + getTunnelName() + "'"
                + ", messageChannel=" + getMessageChannel() + ", tunnelStatus=" + getTunnelStatus() + ", secretId='"
                + getSmsSecretId() + "'" + ", secretKey='" + getSmsSecretKey() + "'" + ", sendEndpoint='"
                + getSmsSendEndpoint()
                + "'" + ", smsSdkAppId='" + getSmsSdkAppId() + "'" + ", mpTokenEndpoint='" + "'"
                + ", comment='" + getComment() + "'" + ", updateBy='" + getUpdateBy() + "'" + ", createBy='"
                + getCreateBy() + "'" + ", updateTime='" + getUpdateTime() + "'" + ", createTime='" + getCreateTime()
                + "'" + "}";
    }
}
