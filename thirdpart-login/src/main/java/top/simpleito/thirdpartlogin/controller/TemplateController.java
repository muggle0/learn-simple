package top.simpleito.thirdpartlogin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RestController
public class TemplateController {
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @GetMapping("user")
    public String user(Model model, @RegisteredOAuth2AuthorizedClient("gitee") OAuth2AuthorizedClient authorizedClient,
                       @AuthenticationPrincipal OAuth2User oauth2User) throws JsonProcessingException {
        model.addAttribute("authorizedClient", OBJECT_MAPPER.writeValueAsString(authorizedClient));
        model.addAttribute("userName", oauth2User.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        model.addAttribute("userAttributes", oauth2User.getAttributes());
        return "user";
    }

    /**
     * （在我们模型中，回调地址为前端页面。在demo中直接用回调地址，模拟前端请求）
     * 此处依赖 OAuth2AuthorizationCodeGrantFilter 的逻辑，使用默认基于SESSION实现的 clientRegistrationRepository 去获取 access_token
     * <p>
     * 通过 access_token 获取用户数据，检查系统该三方用户是否已注册本系统用户
     * 若有，则直接登录(返回token)；
     * 若没有，则告诉前端需要补全信息（将三方用户数据临时存入SESSION中）
     */
    @GetMapping("/login/oauth2/code/gitee")
    public String loginWithOauth2(String code)  {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var client = clientRegistrationRepository.findByRegistrationId("gitee");
        var authorizedClient = oAuth2AuthorizedClientRepository.loadAuthorizedClient("gitee",
                SecurityContextHolder.getContext().getAuthentication(),
                request);
        String uri = client.getProviderDetails().getUserInfoEndpoint().getUri();
        String tokenValue = authorizedClient.getAccessToken().getTokenValue();
        return "";
    }

}
