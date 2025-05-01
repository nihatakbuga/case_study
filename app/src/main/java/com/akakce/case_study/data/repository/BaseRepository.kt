package com.akakce.case_study.data.repository


import android.content.Context
import com.akakce.case_study.R
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class BaseRepository @Inject constructor(private val context: Context) {

    suspend fun <T> apiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: IOException) {
            Result.failure(Exception(context.getString(R.string.check_internet_connection)))
        } catch (e: HttpException) {
            Result.failure(Exception(context.getString(R.string.server_error, e.code())))
        } catch (e: Exception) {
            Result.failure(Exception(context.getString(R.string.unknown_error)))
        }
    }
}
