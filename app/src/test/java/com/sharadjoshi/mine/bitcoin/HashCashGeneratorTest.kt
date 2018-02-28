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

    // Use bits value - 402690497

    @Test
    fun testHashCash_returnsCorrectResult_ifAllParametersAreValid() {
        val validBlockHeader = BlockHeader(536870912,
                "Slush", // not needed in calculations
                511334,  // not needed in calculations
                "0000000000000000000ce49f9d47fd77557f6374664905d05e60f5e441a9cfcf",
                "1803516ef185ef8ffb6362debc1eedb2c69330d316ea2b9198431d141951cf15",
                "0000000000000000004a8f8aa018900a10da2b66c73c05bdcd5aa1fb08094ea2", // not needed in calculations
                1519828331,
                392009692,
                2809285749)

        val byteArray = hashCashGenerator.generateHash(validBlockHeader)
        assertThat(byteArray.toHexString()).isEqualToIgnoringCase("a24e0908fba15acdbd053cc7662bda100a9018a08a8f4a000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifVersionIsInvalid() {
        val invalidBlockHeader = BlockHeader(222222222, // Invalid
                "Slush", // not needed in calculations
                511334,  // not needed in calculations
                "0000000000000000000ce49f9d47fd77557f6374664905d05e60f5e441a9cfcf",
                "1803516ef185ef8ffb6362debc1eedb2c69330d316ea2b9198431d141951cf15",
                "0000000000000000004a8f8aa018900a10da2b66c73c05bdcd5aa1fb08094ea2", // not needed in calculations
                1519828331,
                392009692,
                2809285749)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a24e0908fba15acdbd053cc7662bda100a9018a08a8f4a000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifPrevHashIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "Slush", // not needed in calculations
                511334,  // not needed in calculations
                "0000000000000000000ce49f9d47fd77557f6374664905d05e60f5e441123456", // 123456 in the end
                "1803516ef185ef8ffb6362debc1eedb2c69330d316ea2b9198431d141951cf15",
                "0000000000000000004a8f8aa018900a10da2b66c73c05bdcd5aa1fb08094ea2", // not needed in calculations
                1519828331,
                392009692,
                2809285749)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a24e0908fba15acdbd053cc7662bda100a9018a08a8f4a000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifMerkelRootIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "Slush", // not needed in calculations
                511334,  // not needed in calculations
                "0000000000000000000ce49f9d47fd77557f6374664905d05e60f5e441a9cfcf",
                "1803516ef185ef8ffb6362debc1eedb2c69330d316ea2b9198431d1419123456", // 123456 in the end
                "0000000000000000004a8f8aa018900a10da2b66c73c05bdcd5aa1fb08094ea2", // not needed in calculations
                1519828331,
                392009692,
                2809285749)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a24e0908fba15acdbd053cc7662bda100a9018a08a8f4a000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifTimestampIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "Slush", // not needed in calculations
                511334,  // not needed in calculations
                "0000000000000000000ce49f9d47fd77557f6374664905d05e60f5e441a9cfcf",
                "1803516ef185ef8ffb6362debc1eedb2c69330d316ea2b9198431d141951cf15",
                "0000000000000000004a8f8aa018900a10da2b66c73c05bdcd5aa1fb08094ea2", // not needed in calculations
                123456789, // 123456
                392009692,
                2809285749)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a24e0908fba15acdbd053cc7662bda100a9018a08a8f4a000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifTargetIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "Slush", // not needed in calculations
                511334,  // not needed in calculations
                "0000000000000000000ce49f9d47fd77557f6374664905d05e60f5e441a9cfcf",
                "1803516ef185ef8ffb6362debc1eedb2c69330d316ea2b9198431d141951cf15",
                "0000000000000000004a8f8aa018900a10da2b66c73c05bdcd5aa1fb08094ea2", // not needed in calculations
                1519828331,
                392123456, // 123456 in the end
                2809285749)

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a24e0908fba15acdbd053cc7662bda100a9018a08a8f4a000000000000000000")
    }

    @Test
    fun testHashCash_returnsIncorrectResult_ifNonceIsInvalid() {
        val invalidBlockHeader = BlockHeader(536870912,
                "Slush", // not needed in calculations
                511334,  // not needed in calculations
                "0000000000000000000ce49f9d47fd77557f6374664905d05e60f5e441a9cfcf",
                "1803516ef185ef8ffb6362debc1eedb2c69330d316ea2b9198431d141951cf15",
                "0000000000000000004a8f8aa018900a10da2b66c73c05bdcd5aa1fb08094ea2", // not needed in calculations
                1519828331,
                392009692,
                2809123456) // 123456 in the end

        val byteArray = hashCashGenerator.generateHash(invalidBlockHeader)
        assertThat(byteArray.toHexString()).isNotEqualToIgnoringCase("a24e0908fba15acdbd053cc7662bda100a9018a08a8f4a000000000000000000")
    }
}