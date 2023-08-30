package org.carl.common.utils.tools.security.jwt.enums;

/**
 * Written by Mr. Carl
 * @description: TODO 鉴权角色
 * @version: 1.0
 */
public enum AuthRole {

    /**
     * 公开
     */
    PUBLIC,

    /**
     * 管理员
     */
    ADMIN,

    /**
     * 用户
     */
    USER;

    /**
     * 管理员角色
     */
    public static final String ADMIN_ROLE = "ADMIN";

    /**
     * 用户角色
     */
    public static final String USER_ROLE = "USER";


}
