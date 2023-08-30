package org.carl.common.utils.tools.security.jwt.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.carl.common.utils.tools.security.jwt.enums.AuthRole;

/**
 * Written by Mr. Carl
 *
 * @description: TODO 鉴权信息
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AuthInfo {

    /**
     * ID
     */
    private Long id;
    /**
     * 登录角色
     */
    private AuthRole role;

    /**
     * 公开用户
     */
    public static final AuthInfo PUBLIC_INFO = new AuthInfo(0L, AuthRole.PUBLIC);

}
