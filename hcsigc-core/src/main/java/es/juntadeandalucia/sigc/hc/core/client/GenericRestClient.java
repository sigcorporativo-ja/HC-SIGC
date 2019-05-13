package es.juntadeandalucia.sigc.hc.core.client;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public abstract class GenericRestClient<T> {

   private final T restApi;
   
   public GenericRestClient(String url, Class<T> apiClass) {
      Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
      
      restApi = retrofit.create(apiClass);
   }
   
   protected static <K> K execute(Call<K> retrofitResponse) throws IOException {
      Response<K> response = retrofitResponse.execute();
      return response.body();
   }
   
   public T getApi() {
      return restApi;
   }
}
