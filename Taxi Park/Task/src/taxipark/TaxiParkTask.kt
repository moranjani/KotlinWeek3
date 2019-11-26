package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filter { driver -> trips.none { it.driver == driver } }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
       allPassengers.filter { passenger -> (trips.count { it.passengers.contains(passenger) } >= minTrips) }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers.filter { p -> trips.count { it.driver == driver && it.passengers.contains(p)} > 1 }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.filter { p ->
            val tripsOfPassenger = trips.filter { p in it.passengers }
            val partitionByDiscount = tripsOfPassenger.partition { (it.discount?:0.0) > 0 }
            partitionByDiscount.first.size > partitionByDiscount.second.size
        }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val maxGroupKey = trips.groupBy { it.duration / 10 }.maxBy { it.value.size }?.key
    if (maxGroupKey == null) return null
    val rangeStart = maxGroupKey*10
    return rangeStart..rangeStart+9
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false
    val driverToProfit = trips.groupBy { it.driver }.mapValues { it.value.sumByDouble { it.cost } }
    val allDriversProfit = allDrivers.associateWith { driverToProfit[it] ?: 0.0 }
    val profitsSorted = allDriversProfit.values.sortedDescending()
    val mostProfit = (profitsSorted.sum()) * 0.8
    val topDriversProfit = profitsSorted.subList(0, profitsSorted.size/5 ).sum()
    return topDriversProfit >= mostProfit
}