package cn.yj.admin.frame.shiro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author 永健
 * @since 2021-04-15 11:50
 */
@ConfigurationProperties(prefix = "shiro")
public class FilterChainConfig{

    private Map<String, String> filterChain = new LinkedHashMap<>();
    private LinkedList<String> excludeUrl = new LinkedList<>();

    public Map<String, String> getFilterChain() {
        Map<String, String> chain = new LinkedHashMap<>();
        excludeUrl.forEach(url -> chain.put(url, "anon"));
        chain.putAll(this.filterChain);
        return chain;
    }

    public LinkedList<String> getExcludeUrl() {
        return excludeUrl;
    }

    public FilterChainConfig setExcludeUrl(LinkedList<String> excludeUrl) {
        this.excludeUrl = excludeUrl;
        return this;
    }


    public FilterChainConfig setFilterChain(LinkedHashMap<String, String> chain) {
        Map<String, String> map = new LinkedHashMap<>(chain);
        chain.clear();
        map.forEach((key, val) -> {
            if (key.startsWith("[") && key.endsWith("]")) {
                key = key.substring(1, key.length() - 1).replaceAll(" ", "");
            }
            chain.put(key, val);
        });
        this.filterChain = chain;
        return this;
    }
}
