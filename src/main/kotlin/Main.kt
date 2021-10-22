import kotlin.math.roundToInt

fun main(){
    val cardType = "Visa" //Тип карты Visa, VK Pay, Masterkard, Maestro
    val amountOfPreviousTransfersM = 3000 // Сумма предыдущих переводов в рублях по картам и счету в этом месяце
    val transferNow = 150 // Сумма перевода сейчас в рублях


    val comimssion = calculationCommission(cardType, amountOfPreviousTransfersM,transferNow)


    val limit = calculatingTheLimit(cardType, amountOfPreviousTransfersM,transferNow) // Привышен лимит или нет
    val resultComission = (comimssion * 100).roundToInt()
    val result = (transferNow + comimssion) // Сумма перевода
    if (limit === "лимит не привышен") {
        println("На перевод $limit")
        println("Сумма перевода с учетом комиссии: $result")
        println("Комиссия на перевод составляет: $resultComission копеек")
    } else {
        println("На перевод $limit")
        println("Комиссия на перевод составляет: $resultComission копеек")
    }

}

fun calculationCommission(cardType: String, amountOfPreviousTransfersM: Int, transferNow: Int): Double{
    val minCommission = 35.0 // Минимальная комиссия 35 руб
    val comimssion: Double
    when (cardType){
        "Masterkard", "Maestro" -> comimssion = if ((amountOfPreviousTransfersM > 300)&&(amountOfPreviousTransfersM < 75000)) 0.0 else ((transferNow * 0.006) + 20)
        "Visa", "Мир" -> comimssion = if ((transferNow * 0.0075) <= minCommission) minCommission else (transferNow * 0.0075)
        else -> comimssion = 0.0
    }
    return comimssion
}

fun calculatingTheLimit(cardType: String, amountOfPreviousTransfersM: Int, transferNow: Int): String {
    val limitPerDay = 150000 //Лимит перевода по карте в день
    val limitPerMonth = 600000 // Лимит перевода в месяц

    val limitPerDayVk = 15000 //Лимит перевода по карте в день
    val limitPerMonthVk = 40000 // Лимит перевода в месяц
    val limit: String

    when(cardType){
        "VK Pay" -> limit = if ((amountOfPreviousTransfersM <= limitPerMonthVk)&&(transferNow <= limitPerDayVk)) "лимит не привышен" else "лимит привышен"
        else -> limit = if ((amountOfPreviousTransfersM <= limitPerMonth)&&(transferNow <= limitPerDay)) "лимит не привышен" else "лимит привышен"
    }
    return limit
}