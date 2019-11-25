package nicestring

fun String.isNice(): Boolean {
    val isFirstCondition = !"b[u,a,e]".toRegex().containsMatchIn(this)
    val isSecondCondition = this.count { "aeiou".contains(it) }  >= 3
    val isThirdCondition = this.zipWithNext().any { it.first == it.second }

    val result = listOf(isFirstCondition, isSecondCondition, isThirdCondition).count { it } >= 2
    return result
}

