package com.marketprices;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/*public class NetworkClient {
    //public static Retrofit retrofit=null;
    public static APIService service= null;

    /*
    This public static method will return Retrofit client
    anywhere in the application
    */
   /* public static APIService buildRetrofitClientService() {
        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (service == null) {
            //Defining the Retrofit using Builder


            service = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
                    .addConverterFactory(MoshiConverterFactory.create()) // Converter library used to convert response into POJO
                    .client(new OkHttpClient()).build().create(APIService.class);
            android.util.Log.i("STATUS","Called NetworkClient Service");
            //service = retrofit.create(APIService.class);
        }
        return service;
    }

}*/
public class ServiceGenerator {

    private static final String BASE_URL = "http://vegetables.dextersolutions.tech/";
    //private static final String BASE_URL = "https://gummy-navigators.000webhostapp.com/"; //testing
    //private static final String BASE_URL ="https://vegdext.herokuapp.com/"; //testing
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}