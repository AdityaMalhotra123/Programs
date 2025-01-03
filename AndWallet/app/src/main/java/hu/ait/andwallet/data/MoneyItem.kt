package hu.ait.andwallet.data

import hu.ait.andwallet.R

data class MoneyItem (
    val title: String,
    val amount: Int,
    val type: MoneyType
)

enum class MoneyType {
    EXPENSE, INCOME;

    fun getIcon(): Int {
        return if (this == INCOME) R.drawable.income else R.drawable.expense
    }
}