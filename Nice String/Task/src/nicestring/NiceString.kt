package nicestring

fun String.isNice(): Boolean {
    val isFirstCondition = listOf("ba", "be", "bu").none { this.contains(it) }
    val isSecondCondition = count { "aeiou".contains(it) }  >= 3
    val isThirdCondition = this.zipWithNext().any { it.first == it.second }

    val result = listOf(isFirstCondition, isSecondCondition, isThirdCondition).count { it } >= 2
    return result
}

