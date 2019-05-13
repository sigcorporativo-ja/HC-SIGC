package es.juntadeandalucia.sigc.hc.persistence.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class EncryptationTest {

	public static void main(String[] args) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setProvider(new BouncyCastleProvider());
		encryptor.setAlgorithm("PBEWITHSHA256AND256BITAES-CBC-BC");
		encryptor.setKeyObtentionIterations(1000);
	    encryptor.setPassword("-qWeRmy0SeEret=PaSSwoRd-ToCiFratE*/");
	    
	    String superadminEncrypted = encryptor.encrypt("superadmin");
	    String adminEncrypted = encryptor.encrypt("admin");
	    String gruposEncrypted = encryptor.encrypt("grupos");
	    String usuarioEncrypted = encryptor.encrypt("usuario");
	    String anonimoEncrypted = encryptor.encrypt("anonimo");
	    System.out.println("superadmin encriptado: " + superadminEncrypted);
	    System.out.println("admin encriptado: " + adminEncrypted);
	    System.out.println("grupos encriptado: " + gruposEncrypted);
	    System.out.println("usuario encriptado: " + usuarioEncrypted);
	    System.out.println("anonimo encriptado: " + anonimoEncrypted);

	    StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
	    decryptor.setProvider(new BouncyCastleProvider());
	    decryptor.setAlgorithm("PBEWITHSHA256AND256BITAES-CBC-BC");
	    decryptor.setKeyObtentionIterations(1000);
	    decryptor.setPassword("-qWeRmy0SeEret=PaSSwoRd-ToCiFratE*/");
	    
	    String superadminDecrypted = decryptor.decrypt(superadminEncrypted);
	    System.out.println("superadmin descifrado: " + superadminDecrypted);
	}

}
