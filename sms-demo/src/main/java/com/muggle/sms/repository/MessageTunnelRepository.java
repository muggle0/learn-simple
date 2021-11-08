package com.muggle.sms.repository;


import com.muggle.sms.entity.MessageTunnelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MessageTunnel entity.
 *
 * @author joelzzhang
 */
@SuppressWarnings("unused")
@Repository
public interface MessageTunnelRepository
        extends JpaRepository<MessageTunnelEntity, Long>, JpaSpecificationExecutor<MessageTunnelEntity> {

    @Query("select distinct mt.messageChannel from MessageTunnelEntity  mt where mt.createBy = ?1")
    List<Byte> findDistinctMessageChannelByCreateBy(String createBy);

    /**
     * 根据通道名称和是否删除状态查询已存在的通道
     *
     * @param name    通道名称
     * @param deleted 是否删
     * @return MessageTunnelEntity
     */
    MessageTunnelEntity findDistinctByTunnelNameEqualsAndDeleted(String name, Byte deleted);

    /**
     * 根据消息通道Id查询通道信息
     *
     * @param tunnelId tunnelId
     * @return MessageTunnelEntity
     * @author p_pttang
     */
    MessageTunnelEntity findByTunnelId(Long tunnelId);


    /**
     * 通过短信存在秘密id和sms密钥和短信发送端点和sms sdk应用程序id
     *
     * @param smsSecretId 短信秘密id
     * @param smsSecretKey 短信密钥
     * @param smsSendEndpoint 短信发送端点
     * @param smsSdkAppId 短信sdk应用程序id
     * @return boolean
     */
    boolean existsBySmsSecretIdAndSmsSecretKeyAndSmsSendEndpointAndSmsSdkAppId(
        String smsSecretId, String smsSecretKey, String smsSendEndpoint, String smsSdkAppId);


    /**
     * 存在不通过隧道id和短信秘密id和sms密钥和短信发送端点和sms sdk应用程序id
     *
     * @param tunnelId 隧道id
     * @param smsSecretId 短信秘密id
     * @param smsSecretKey 短信密钥
     * @param smsSendEndpoint 短信发送端点
     * @param smsSdkAppId 短信sdk应用程序id
     * @return boolean
     */
    boolean existsByTunnelIdNotAndSmsSecretIdAndSmsSecretKeyAndSmsSendEndpointAndSmsSdkAppId(Long tunnelId,
                                                                                             String smsSecretId, String smsSecretKey, String smsSendEndpoint, String smsSdkAppId);
}
