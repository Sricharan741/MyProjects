package com.marketprices;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

   @GET("{relativeURL}")
   Single<List<VegetableItemPojo>> getPrices(@Path(value="relativeURL",encoded = true) String url);
}
