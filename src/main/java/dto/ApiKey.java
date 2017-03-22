package dto;

import com.google.api.client.util.Key;

/**
 * Created by bstempi on 9/15/14.
 * 
 * From https://github.com/bstempi/vindinium-client
 */
public class ApiKey {

    @Key
    private final String key;

    public ApiKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
