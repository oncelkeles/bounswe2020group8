package com.example.carousel.map

import com.example.carousel.pojo.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    //customer login request
    @POST("/customer/login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<ResponseLogin>

    //customer login request
    @POST("/customer/sigIn")
    fun signIn(
        @Query("email") email: String,
        @Query("googleID") googleID: String
    ): Call<ResponseLogin>

    @POST("/customer/logout")
    fun customerLogout(): Call<ResponseHeader>

    @POST("/vendor/logout")
    fun vendorLogout(): Call<ResponseHeader>

    @POST("/customer/signup")
    fun customerSignup(
        @Query("name") name: String,
        @Query("lastName") lastName: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("passwordConfirm") passwordConfirm: String, ): Call<ResponseHeader>

    @POST("/vendor/signup")
    fun vendorSignup(
        @Query("name") name: String,
        @Query("lastName") lastName: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("passwordConfirm") passwordConfirm: String, ): Call<ResponseHeader>

    @GET("/customer/me")
    fun customerMe(): Call<ResponseCustomerMe>

    @PATCH("/customer/me")
    fun customerUpdate(@Body data: DataCustomerMe): Call<ResponseCustomerMe>

    @GET("/vendor/me")
    fun vendorMe(): Call<ResponseVendorMe>

    @POST("/customer/changePassword")
    fun customerChangePassword(
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String,
        @Query("newPasswordRepeat") newPasswordRepeat : String ): Call<ResponseChangePassword>

    @GET("/mainProduct/{id}")
    fun getMainProduct(
        @Path("id") id: String
    ): Call<ResponseMainProduct>

    @GET("/product/{id}")
    fun getProduct(
        @Path("id") id: String
    ): Call<ResponseProduct>

    @GET("/product")
    fun getAllProducts(
    ): Call<ResponseAllProducts>

    @POST("/vendor/changePassword")
    fun vendorChangePassword(
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String,
        @Query("newPasswordRepeat") newPasswordRepeat : String ): Call<ResponseChangePassword>

    @GET("/category")
    fun getCategories(
        @Query("sort") sort: String = "",
        @Query("limit") limit: Int = 1000,
        @Query("page") page: Int = 1,
        @Query("fields") fields: String = "fields=_id,name", ): Call<ResponseGetCategories>

    @GET("/comment/{pid}/all")
    fun getComments(
        @Path("pid") pid: String = ""): Call<ResponseGetComments>

    @POST("/comment/{pid}")
    fun addComment(
        @Path("pid") pid: String = "",
        @Body body: PostComment): Call<PostComment>

    @POST("/customer/shoppingCart/get")
    fun getCart(
        @Query("_id") _id: String): Call<ArrayList<ResponseCart>>

    @POST("/customer/shoppingCart/update")
    fun updateCart(
        @Body data: ResponseCart,
        @Query("_id") _id: String ): Call<ArrayList<DataCustomerMe>>
}