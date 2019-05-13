package es.juntadeandalucia.sigc.hc.persistence.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate4.encryptor.HibernatePBEEncryptorRegistry;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;


@Startup
@Singleton
public class JasyptConfigurator {

    private StandardPBEStringEncryptor defaultStringEncryptor;
    private PasswordEncryptor passwordEncryptor;

    @PostConstruct
    public void configureJasypt() {
    	String password = "-qWeRmy0SeEret=PaSSwoRd-ToCiFratE*/";

        this.defaultStringEncryptor = new StandardPBEStringEncryptor();
        this.defaultStringEncryptor.setProvider(new BouncyCastleProvider());
        this.defaultStringEncryptor.setAlgorithm("PBEWITHSHA256AND256BITAES-CBC-BC");
        this.defaultStringEncryptor.setKeyObtentionIterations(1000);
        this.defaultStringEncryptor.setPassword(password);

        HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
        registry.registerPBEStringEncryptor("defaultStringEncryptor", defaultStringEncryptor);

        this.passwordEncryptor = new StrongPasswordEncryptor();
    }

    @Produces
    public StandardPBEStringEncryptor getDefaultStringEncryptor() {
        if (null == defaultStringEncryptor) {
            configureJasypt();
        }
        return defaultStringEncryptor;
    }

    @Produces
    public PasswordEncryptor getPasswordEncryptor() {
        if (null == passwordEncryptor) {
            configureJasypt();
        }
        return passwordEncryptor;
    }
}
