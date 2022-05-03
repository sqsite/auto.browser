package nz.sqsite.auto.ui.propertieshandler;


import nz.sqsite.auto.ui.exceptions.PendingException;
import nz.sqsite.auto.ui.exceptions.PropertyHandlerException;

import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;

public class PropertiesFinder {
    private static final String SECRET_KEY_NAME = "secretKeyName";
    private static Properties propertiesController;
    private static String environment;
    private static Properties envProperties;
    private static Properties configProperties;

    //So as not to instantiate the class
    private PropertiesFinder() {
    }

    public static String getEnvironment() {
        if (environment == null) {
            propertiesController = new PropertiesHandler("properties/controller.properties").getProperties();
            environment = setEnvironment();
        }
        return environment;
    }

    public static String getProperty(String key) {
        if (!isNullOrEmpty(System.getenv(key))) {
            return System.getenv(key);
        }

        if (!isNullOrEmpty(System.getProperty(key))) {
            return System.getProperty(key);
        }
        configProperties = getConfigProperties();

        if (!isNullOrEmpty(configProperties.getProperty(key)) && configProperties.getProperty(key).startsWith("ENC(")) {
            throw new PendingException("Decryption work needs to be finished");
//            return Decryptor.decrypt(getSecretKey(), configProperties.getProperty(key));
        } else if (!isNullOrEmpty(configProperties.getProperty(key))) {
            return configProperties.getProperty(key);
        }

        envProperties = getEnvProperties();

        if (!isNullOrEmpty(envProperties.getProperty(key)) && envProperties.getProperty(key).startsWith("ENC(")) {
            throw new PendingException("Decryption work needs to be finished");
//            return Decryptor.decrypt(getSecretKey(), envProperties.getProperty(key));
        }

        return envProperties.getProperty(key);
    }

    private static String getSecretKey() {
        if (isNullOrEmpty(configProperties.getProperty(SECRET_KEY_NAME))) {
            throw new PropertyHandlerException("secretKeyName is null or cannot be found from 'configuration.properties' file.");
        }
        String secretKeyName = configProperties.getProperty(SECRET_KEY_NAME);

        String secretKey = System.getenv(secretKeyName) == null ? System.getProperty(secretKeyName) : System.getenv(secretKeyName);

        if (isNullOrEmpty(secretKey)) {
            throw new PropertyHandlerException(String.format("You do not have a secret value stored in your env variables with key '%s'", configProperties.getProperty(SECRET_KEY_NAME)));
        }

        return secretKey;
    }

    private static Properties getEnvProperties() {
        if (envProperties == null) {
            envProperties = new PropertiesHandler("properties/application-" + getEnvironment() + ".properties").getProperties();
        }
        return envProperties;
    }

    private static Properties getConfigProperties() {
        if (configProperties == null) {
            configProperties = new PropertiesHandler("configuration.properties").getProperties();
        }
        return configProperties;
    }


    private static String setEnvironment() {
        String env = System.getenv("env");
        if (isNullOrEmpty(env)) {
            env = System.getProperty("env");
        }
        if (isNullOrEmpty(env)) {
            env = propertiesController.getProperty("environment");
        }

        return env;
    }

}
