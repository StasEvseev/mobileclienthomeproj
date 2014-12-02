package com.example.stas.homeproj.library;

import android.content.Context;
import android.util.Log;

import com.example.stas.homeproj.Constrants;
import com.example.stas.homeproj.R;
import com.example.stas.homeproj.models.Token;
import com.example.stas.homeproj.models.User;
import com.example.stas.homeproj.resources.ApiRequestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
//import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.ApacheClient;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * @author StasEvseev
 * Класс помощник в работе с РЕСТ
 */
public class RestApiHelper {

    public static <T> T createResource(Class<T> cl, Context context, String token, User user) {
        class NullHostNameVerifier implements HostnameVerifier {
            public boolean verify(String hostname, SSLSession session) {
                Log.i("RestUtilImpl", "Approving certificate for " + hostname);
                return true;
            }
        }

        SSLContext sslContext = null;

        try {
            KeyStore keyStore = readKeyStore(context);
            sslContext = SSLContext.getInstance("SSL");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "keystore_pass".toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(),trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        if (sslContext != null) {
            okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
            okHttpClient.setHostnameVerifier(new NullHostNameVerifier());
        }

        ApiRequestInterceptor requestInterceptor = new ApiRequestInterceptor();
        if (user != null) {
            requestInterceptor.setUser(user);
        } else {
            requestInterceptor.setToken(new Token(token));
        }
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
        restAdapterBuilder.setRequestInterceptor(requestInterceptor);
        RestAdapter restAdapter = restAdapterBuilder
                .setEndpoint(Constrants.URL_BUY_API)
                .setConverter(new GsonConverter(gson))
                .setErrorHandler(new MyErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .build();

        return restAdapter.create(cl);
    }

    static KeyStore readKeyStore(Context context) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream fis = null;
        try {
            fis = context.getResources().openRawResource(R.raw.mystore);
            ks.load(fis, "policeman".toCharArray());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return ks;
    }

//    KeyStore readKeyStore() throws KeyStoreException, IOException {
//        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//
//        // get user password and file input stream
//        char[] password = getPassword();
//
//        java.io.FileInputStream fis = null;
//        try {
//            fis = new java.io.FileInputStream("keyStoreName");
//            ks.load(fis, password);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                fis.close();
//            }
//        }
//        return ks;
//    }
}

class MyHttpClient extends DefaultHttpClient {

    final Context context;

    public MyHttpClient(Context context) {
        this.context = context;
    }

    @Override protected ClientConnectionManager createClientConnectionManager() {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(
                new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", newSslSocketFactory(), 443));
        return new SingleClientConnManager(getParams(), registry);
    }

    private SSLSocketFactory newSslSocketFactory() {
        try {
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream in = context.getResources().openRawResource(R.raw.mystore);
            try {
                trusted.load(in, "ez24get".toCharArray());
            } finally {
                in.close();
            }
            return new SSLSocketFactory(trusted);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}

class UnauthorizedException extends Exception {

    UnauthorizedException(RetrofitError err) {
        Log.d("EXCPTION", "EXCEPTION");
    }
}

class MyErrorHandler implements ErrorHandler {
    @Override public Throwable handleError(RetrofitError cause) {
        Log.d("MYERROR", "HANDLE");
//        Response r = cause.getResponse();

//        Log.d("ERROR", "");

//        if (r != null && r.getStatus() == 401) {
//            return new UnauthorizedException(cause);
//        }
        return cause;
    }
}