//package com.example.mvvm.service
//
//import android.content.Context
//import android.util.Log
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import androidx.work.workDataOf
//import com.example.mvvm.model.ProductResponse
//import com.google.gson.Gson
//import retrofit2.Response
//import java.net.UnknownHostException
//
//class ProductWorker(val context: Context, private val workerParameters: WorkerParameters) :
//    CoroutineWorker(context,workerParameters){
//
//    override suspend fun doWork(): Result {
//        Log.i("Hi","I am inside DoWork")
////        /*to test number of tries */
////        if (runAttemptCount == 3) {
////            return Result.failure()
////        }
//
//        val apiInstance = RetrofitHelper.retrofitInstance.create(ApiService::class.java)
//        val gson = Gson()
//
//        try {
//            val response: Response<ProductResponse> = apiInstance.getProducts()
//            Log.i("Hi","I  Got the Responde from Retrofit")
//            if (response.isSuccessful) {
//                Log.i("Hi","Response = ${response.body()}")
//                /*convert to json*/
////                val responseBody = response.body().ProductRespose.javaClass
////                val products: List<ProductDTO> = gson.fromJson(responseBody, Array<ProductDTO>::class.java).toList()
//                val jsonProducts = gson.toJson(response.body()?.getProductResponse() ?: null)
//                val outputData = workDataOf("products" to jsonProducts)
//                return Result.success(outputData)
//            } else {
//                return Result.failure(workDataOf("Reason" to "Server Error: ${response.code()}"))
//            }
//            return Result.success()
//            }catch (ex:UnknownHostException)
//            {
//                return Result.failure(workDataOf("Reason" to "No Internet Connection, Please Check your network"))
//            }catch (ex:Exception)
//            {
//                return Result.failure(workDataOf("Reason" to ex.message))
//            }
//    }
//}