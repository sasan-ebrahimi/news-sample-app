package com.s_ebrahimi.newssample.util

import java.io.IOException
import java.io.InputStream
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory

import javax.net.ssl.*

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * This class generates trusted http client using a .cert file ( certificate file of website )
 * So that in devices with low api level no error will happen to communicate with server
 * It is used to prevent CertPathValidatorException error
 */
object OkHttpClientGenerator {

    /**
     * Generates a trusted http client
     * @param inputStream : inputStram of .cert file
     * @return if certificate is valid returns trustable http client, otherwise returns a default client
     */
    fun generateClient(inputStream: InputStream): OkHttpClient? {
        var client = generateTrustedClient(inputStream)
        if (client == null)
            client = OkHttpClient.Builder()
                .connectTimeout(Constants.NETWORK_TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(Constants.NETWORK_TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(Constants.NETWORK_TIMEOUT_WRITE, TimeUnit.SECONDS)
                .build()
        return client
    }

    private fun generateTrustedClient(inputStream: InputStream): OkHttpClient? {
        var okHttpClient: OkHttpClient? = null
        var trustManagerFactory: TrustManagerFactory? = null
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            var cert = inputStream // Place your 'my_cert.crt' file in `res/raw`
            val cf = CertificateFactory.getInstance("X.509")
            val ca = cf.generateCertificate(cert)
            cert.close()
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
            trustManagerFactory!!.init(keyStore)
            val trustManagers = trustManagerFactory.trustManagers
            /*if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
            }*/
            val trustManager = trustManagers[0] as X509TrustManager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            val sslSocketFactory = sslContext.socketFactory
            okHttpClient = OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager)
                .connectTimeout(Constants.NETWORK_TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(Constants.NETWORK_TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(Constants.NETWORK_TIMEOUT_WRITE, TimeUnit.SECONDS)
                .build()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return okHttpClient
    }
}
