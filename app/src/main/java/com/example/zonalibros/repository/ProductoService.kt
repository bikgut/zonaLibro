package com.example.zonalibros.repository



import com.example.zonalibros.models.ProductoAgregar
import com.example.zonalibros.models.ProductoModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoService {
    companion object{
        val instance =
            Retrofit.Builder().baseUrl("http://192.168.1.105:8080/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build().create(ProductoService::class.java)

    }

    @GET("productos")
    suspend fun listarProductos():List<ProductoModel>

    @GET("productos/{id}")
    suspend fun obtenerPorId(@Path("id") id:Int): ProductoModel

    @POST("productos")
    suspend fun agregarProducto(@Body libro : ProductoAgregar) : ProductoModel

    @PUT("productos")
    suspend fun actualizarProducto(@Body producto : ProductoModel)

    @DELETE("productos/{id}")
    suspend fun eliminarProducto(@Path("id") id:Int)
}