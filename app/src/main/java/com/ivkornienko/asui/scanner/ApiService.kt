package com.ivkornienko.asui.scanner

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("test/{test_string}/")
    suspend fun test_connection(
        @Path(QUERY_TEST_STRING) testString: String
    ): TestResponse

    @GET("info_barcode/{barcode}/")
    suspend fun get_infoByBarcode(
        @Path(QUERY_BARCODE) barcode: String
    ): ProductInfoResponse

    companion object {
        private const val QUERY_TEST_STRING = "test_string"
        private const val QUERY_BARCODE = "barcode"
    }
}