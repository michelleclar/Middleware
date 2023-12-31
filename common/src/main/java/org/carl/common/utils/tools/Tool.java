package org.carl.common.utils.tools;

import org.carl.common.utils.tools.random.RandomNameAndTel;
import org.carl.common.utils.tools.security.bcrypt.BCryptPasswordEncoder;
import org.carl.common.utils.tools.snowflake.SnowFlakeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class Tool {

    public static long getSnowFlakeId() {
        return SnowFlakeUtils.getInstance().getId();
    }

    public static String getEncode(String str) {

        return BCryptPasswordEncoder.getInstance().encode(str);
    }

    public static String getChineseName() {
        return RandomNameAndTel.getChineseName();
    }

    public static String getTel() {
        return RandomNameAndTel.getTel();
    }

    /**
     * get classloader from thread context if no classloader found in thread
     * context return the classloader which has loaded this class
     *
     * @return classloader
     */
    public static ClassLoader getCurrentClassLoader(Class clazz) {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader == null) {
            classLoader = clazz.getClassLoader();
        }
        return classLoader;
    }

    /**
     * 从classpath 加载配置信息
     *
     * @param configFileName 配置文件名称
     * @return 配置信息
     * @throws IOException
     */
    public static Properties loadFromClasspath(String configFileName, Class clazz) throws IOException {
        ClassLoader classLoader = getCurrentClassLoader(clazz);
        Properties config = new Properties();

        List<URL> properties = new ArrayList<URL>();
        Enumeration<URL> propertyResources = classLoader
                .getResources(configFileName);
        while (propertyResources.hasMoreElements()) {
            properties.add(propertyResources.nextElement());
        }

        for (URL url : properties) {
            InputStream is = null;
            try {
                is = url.openStream();
                config.load(is);
            } finally {
                if (is != null) {
                    is.close();
                    is = null;
                }
            }
        }

        return config;
    }


}
