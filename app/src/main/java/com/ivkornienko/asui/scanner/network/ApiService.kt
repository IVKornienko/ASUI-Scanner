package com.ivkornienko.asui.scanner.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("test/{test_string}/")
    suspend fun testConnection(
        @Path(QUERY_TEST_STRING) testString: String
    ): TestResponseDto

    @GET("info_barcode/{barcode}/")
    suspend fun getInfoByBarcode(
        @Path(QUERY_BARCODE) barcode: String
    ): ProductInfoDto

    companion object {
        private const val QUERY_TEST_STRING = "test_string"
        private const val QUERY_BARCODE = "barcode"
    }
}