package random

import org.json.JSONObject
import java.math.BigDecimal

val prices = mapOf(
    "blockchain" to { blockchainSellPrice() },
    "bitcoinCom" to { bitcoinComAskPrice() }
)

fun main() {
    do {
        val min = prices.map { it.key to it.value.invoke() }.minByOrNull { it.second }!!
        println("${min.first}=${min.second}")
        println("refresh?")
    } while (readLine() != "q")
}

fun blockchainSellPrice(): BigDecimal = (khttp
    .get("https://blockchain.info/ticker")
    .jsonObject
    .get("USD") as JSONObject)
    .get("sell").toString().toBigDecimal()

fun bitcoinComAskPrice(): BigDecimal = (khttp
    .get("https://api.exchange.bitcoin.com/api/2/public/ticker")
    .jsonArray
    .find { (it as JSONObject).get("symbol") == "BTCUSD" } as JSONObject)
    .get("ask").toString().toBigDecimal()