package com.jack.rbac_shiro.config;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        //自定义filter加入factoryBean
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("roleOrFilter",new CustomRolesOrAuthorizationFilter());
        factoryBean.setFilters(filterMap);
        //设置需要登录的接口却没有登录给出提示
        factoryBean.setLoginUrl("/pub/need_login");
        //登录成功时候的调用，若为前后端分离，则没有该接口
        factoryBean.setSuccessUrl("/");
        //没有权限，会调用此接口，先验证登录》验证权限
        factoryBean.setUnauthorizedUrl("/pub/not_permit");
        //拦截器设置，必须使用有序map定义
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        //退出登录拦截
        filterChainDefinitionMap.put("logout","logout");
        //匿名用户可以访问接口
        filterChainDefinitionMap.put("/pub/**","anon");
        filterChainDefinitionMap.put("/websocket/**","anon");
        filterChainDefinitionMap.put("/socket/**","anon");
        //登录用户可以访问
        filterChainDefinitionMap.put("/authc/**","authc");
        //管理员角色可以访问
        filterChainDefinitionMap.put("/admin/**","roleOrFilter[admin,root]");
        //修改权限
        filterChainDefinitionMap.put("/video/update","perms[video_update]");

        filterChainDefinitionMap.put("/**","authc");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return  factoryBean;
    }

    @Bean
    public  SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //设置sessionManager,如果是单体应用则无需设置
        securityManager.setSessionManager(sessionsSecurityManager());
        //配置自定义cache缓存
        securityManager.setCacheManager(cacheManager());
        //设置realm,推荐放到最后，可能在某些时候不生效
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    /**
     * 加载自定义realm
     * @return
     */
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    /**
     * 加载密码加密算法
     * @return
     */
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //加密方式设定
        matcher.setHashAlgorithmName("md5");
        //散列次数
        matcher.setHashIterations(2);
        return matcher;
    }

    /**
     * 加载自定义sessionmanager
     * @return
     */
    @Bean
    public CustomSessionManager sessionsSecurityManager(){
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setGlobalSessionTimeout(200000);
        return sessionManager;
    }

    /**
     * redisManager加载
     */
    @Bean
    public RedisManager getRedisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        return redisManager;
    }

    /**
     * 配置具体实现类
     * @return
     */
    public RedisCacheManager cacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(getRedisManager());
        //设置缓存刷新时间，单位秒
        cacheManager.setExpire(20);
        return cacheManager;
    }

    /**
     * session持久化
     * @return
     */
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        return redisSessionDAO;
    }

    /**
     * 管理shiro一些bean的生命周期 即bean初始化 与销毁
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 加入注解的使用，不加入这个AOP注解不生效(shiro的注解 例如 @RequiresGuest)
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 用来扫描上下文寻找所有的Advistor(通知器),
     * 将符合条件的Advisor应用到切入点的Bean中，需要在LifecycleBeanPostProcessor创建后才可以创建
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
