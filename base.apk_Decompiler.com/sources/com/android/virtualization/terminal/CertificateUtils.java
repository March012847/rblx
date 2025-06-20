package com.android.virtualization.terminal;

import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

/* compiled from: CertificateUtils.kt */
public final class CertificateUtils {
    public static final CertificateUtils INSTANCE = new CertificateUtils();

    private CertificateUtils() {
    }

    public final KeyStore.PrivateKeyEntry createOrGetKey() {
        try {
            KeyStore instance = KeyStore.getInstance("AndroidKeyStore");
            instance.load((KeyStore.LoadStoreParameter) null);
            if (!instance.containsAlias("ttyd")) {
                Log.d("VmTerminalApp", "there is no keypair, will generate it");
                createKey();
            } else if (!(instance.getCertificate("ttyd") instanceof X509Certificate)) {
                Log.d("VmTerminalApp", "certificate isn't X509Certificate or it is invalid");
                createKey();
            } else {
                try {
                    Certificate certificate = instance.getCertificate("ttyd");
                    certificate.getClass();
                    ((X509Certificate) certificate).checkValidity();
                } catch (CertificateExpiredException e) {
                    Log.d("VmTerminalApp", "certificate is invalid", e);
                    createKey();
                } catch (CertificateNotYetValidException e2) {
                    Log.d("VmTerminalApp", "certificate is invalid", e2);
                    createKey();
                }
            }
            KeyStore.Entry entry = instance.getEntry("ttyd", (KeyStore.ProtectionParameter) null);
            entry.getClass();
            return (KeyStore.PrivateKeyEntry) entry;
        } catch (Exception e3) {
            throw new RuntimeException("cannot generate or get key", e3);
        }
    }

    private final void createKey() {
        KeyPairGenerator instance = KeyPairGenerator.getInstance("EC", "AndroidKeyStore");
        instance.initialize(new KeyGenParameterSpec.Builder("ttyd", 12).setDigests(new String[]{"SHA-256", "SHA-512"}).build());
        instance.generateKeyPair();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0060, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r5, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0064, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void writeCertificateToFile(android.content.Context r5, java.security.cert.Certificate r6) {
        /*
            r4 = this;
            java.lang.String r4 = "cannot write certs"
            r5.getClass()
            r6.getClass()
            java.io.File r0 = new java.io.File
            java.io.File r5 = r5.getFilesDir()
            java.lang.String r1 = "ca.crt"
            r0.<init>(r5, r1)
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x005c, CertificateEncodingException -> 0x005a }
            r5.<init>(r0)     // Catch:{ IOException -> 0x005c, CertificateEncodingException -> 0x005a }
            java.lang.String r0 = "-----BEGIN CERTIFICATE-----\n"
            java.lang.String r1 = "-----END CERTIFICATE-----\n"
            byte[] r6 = r6.getEncoded()     // Catch:{ all -> 0x005e }
            r2 = 0
            java.lang.String r6 = android.util.Base64.encodeToString(r6, r2)     // Catch:{ all -> 0x005e }
            r6.getClass()     // Catch:{ all -> 0x005e }
            kotlin.text.Regex r2 = new kotlin.text.Regex     // Catch:{ all -> 0x005e }
            java.lang.String r3 = "(.{64})"
            r2.<init>((java.lang.String) r3)     // Catch:{ all -> 0x005e }
            java.lang.String r3 = "$1\n"
            java.lang.String r6 = r2.replace(r6, r3)     // Catch:{ all -> 0x005e }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x005e }
            r2.<init>()     // Catch:{ all -> 0x005e }
            r2.append(r0)     // Catch:{ all -> 0x005e }
            r2.append(r6)     // Catch:{ all -> 0x005e }
            r2.append(r1)     // Catch:{ all -> 0x005e }
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x005e }
            java.nio.charset.Charset r0 = kotlin.text.Charsets.UTF_8     // Catch:{ all -> 0x005e }
            byte[] r6 = r6.getBytes(r0)     // Catch:{ all -> 0x005e }
            r6.getClass()     // Catch:{ all -> 0x005e }
            r5.write(r6)     // Catch:{ all -> 0x005e }
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x005e }
            r6 = 0
            kotlin.io.CloseableKt.closeFinally(r5, r6)     // Catch:{ IOException -> 0x005c, CertificateEncodingException -> 0x005a }
            return
        L_0x005a:
            r5 = move-exception
            goto L_0x0065
        L_0x005c:
            r5 = move-exception
            goto L_0x006b
        L_0x005e:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0060 }
        L_0x0060:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r5, r6)     // Catch:{ IOException -> 0x005c, CertificateEncodingException -> 0x005a }
            throw r0     // Catch:{ IOException -> 0x005c, CertificateEncodingException -> 0x005a }
        L_0x0065:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            r6.<init>(r4, r5)
            throw r6
        L_0x006b:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            r6.<init>(r4, r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.virtualization.terminal.CertificateUtils.writeCertificateToFile(android.content.Context, java.security.cert.Certificate):void");
    }
}
