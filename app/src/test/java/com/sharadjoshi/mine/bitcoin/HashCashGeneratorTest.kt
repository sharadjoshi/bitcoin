package com.sharadjoshi.mine.bitcoin

import com.sharadjoshi.mine.bitcoin.blockprocessor.HashCashGenerator
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import com.sharadjoshi.mine.bitcoin.data.toHexString
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.security.MessageDigest

@RunWith(JUnit4::class)
class HashCashGeneratorTest {
    private val hashCashGenerator = HashCashGenerator(MessageDigest.getInstance("SHA-256"))

    @Test
    fun testHashCash_returnsCorrectResult_ifAllParametersAreValid() {
        val validBlockHeader = BlockHeader(536870912,
                "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266",
                "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978",
                1515252561,
                "180091c1",
                45291998)

        val byteArray = hashCashGenerator.generateHash(validBlockHeader)
        assertThat(byteArray.toHexString()).isEqualToIgnoringCase("a9e41527e7613422b75f8d58af24c425fb6365dc2bcf20000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifVersionIsInvalid() {
        val invalidBlockHeader = BlockHeader(222222222, // Invalid
                "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266",
                "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978",
                1515252561,
                "180091c1",
                45291998)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a9e41527e7613422b75f8d58af24c425fb6365dc2bcf20000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifPrevHashIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
        "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de099123456",  // Invalid, 123456 at the end
        "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978",
        1515252561,
        "180091c1",
        45291998)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a9e41527e7613422b75f8d58af24c425fb6365dc2bcf20000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifMerkelRootIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266",
                "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f123456",  // Invalid, 123456 at the end
                1515252561,
                "180091c1",
                45291998)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a9e41527e7613422b75f8d58af24c425fb6365dc2bcf20000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifTimestampIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266",
                "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978",
                1234567891,   // Invalid, 123456 at the end
                "180091c1",
                45291998)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a9e41527e7613422b75f8d58af24c425fb6365dc2bcf20000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifTargetIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266",
                "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978",
                1515252561,
                "18009123",   // Invalid, 123 at the end
                45291998)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a9e41527e7613422b75f8d58af24c425fb6365dc2bcf20000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifNonceIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "00000000000000000061abcd4f51d81ddba5498cff67fed44b287de0990b7266",
                "871148c57dad60c0cde483233b099daa3e6492a91c13b337a5413a4c4f842978",
                1515252561,
                "180091c1",
                12345678)   // Invalid, 123456 at the end

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a9e41527e7613422b75f8d58af24c425fb6365dc2bcf20000000000000000000")
    }
}