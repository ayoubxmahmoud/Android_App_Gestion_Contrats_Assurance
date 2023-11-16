package com.example.assurance
import android.content.ContentValues.TAG
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DashboardAgent : AppCompatActivity() {
    private lateinit var db: DataBaseHelper
    private lateinit var ourPieChart: PieChart
    private lateinit var ourBarChart: BarChart
    private lateinit var ourLineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_agent)
        db = DataBaseHelper()
        ourPieChart = findViewById(R.id.ourPieChart)
        ourLineChart = findViewById(R.id.ourLineChart)
        //saveAnimals()
        retrieveRecordsAndPopulateCharts()
    }

    //method for saving records in database
    /*
    fun saveAnimals() {

        val DataBaseHelper: db = DataBaseHelper()
        val record1 = databaseHandler.addAnimalDetails(AnimalModel(1, "Lion", 470, 7, 87))
        val record2 = databaseHandler.addAnimalDetails(AnimalModel(2, "Impala", 1879, 10, 90))
        val record3 = databaseHandler.addAnimalDetails(AnimalModel(3, "Leopard", 570, 13, 89))
        val record4 = databaseHandler.addAnimalDetails(AnimalModel(4, "Crocodile", 150, 30, 66))
    }

     */

    fun retrieveRecordsAndPopulateCharts() {
        var sizeOfBranches = 0
        db.getFrequencyOfBranches { branchFreq ->
            sizeOfBranches = branchFreq.size
            val branchNames = Array<String>(sizeOfBranches) { "" }
            val branchFrequencies = Array<Int>(sizeOfBranches) { 0 }
            var i = 0
            for ((branch, frequency) in branchFreq) {
                branchNames[i] = branch
                branchFrequencies[i] = frequency
                i++
            }
            populatePieChart(branchFrequencies, branchNames)
        }

        db.getMonthsActivate { monthsActivate ->
            val months = Array<Int>(12){0}
            val frequencies = Array<Int>(12){0}
            var i = 0
            for ((m,f) in monthsActivate){
                months[i] = m
                frequencies[i] = f
                i++
            }
            populateLineChart(frequencies,months)
        }

    }




    private fun populatePieChart(values: Array<Int>, labels: Array<String>) {
        //an array to store the pie slices entry
        val ourPieEntry = ArrayList<PieEntry>()
        var i = 0

        for (entry in values) {
            //converting to float
            var value = values[i].toFloat()
            var label = labels[i]
            //adding each value to the pieentry array
            ourPieEntry.add(PieEntry(value, label))
            i++
        }

        //assigning color to each slices
        val pieShades: ArrayList<Int> = ArrayList()
        pieShades.add(Color.parseColor("#FFBB86FC"))
        pieShades.add(Color.parseColor("#FF03DAC5"))
        pieShades.add(Color.parseColor("#E33435"))
        pieShades.add(Color.parseColor("#00B33C"))

        //add values to the pie dataset and passing them to the constructor
        val ourSet = PieDataSet(ourPieEntry, "")
        val data = PieData(ourSet)

        //setting the slices divider width
        ourSet.sliceSpace = 1f

        //populating the colors and data
        ourSet.colors = pieShades
        ourPieChart.data = data
        //setting color and size of text
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(10f)

        //add an animation when rendering the pie chart
        ourPieChart.animateY(1400, Easing.EaseInOutQuad)
        //disabling center hole
        ourPieChart.isDrawHoleEnabled = false
        //do not show description text
        ourPieChart.description.isEnabled = false
        //legend enabled and its various appearance settings
        ourPieChart.legend.isEnabled = true
        ourPieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        ourPieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        ourPieChart.legend.isWordWrapEnabled = true

        //dont show the text values on slices e.g Antelope, impala etc
        ourPieChart.setDrawEntryLabels(false)
        //refreshing the chart
        ourPieChart.invalidate()

    }


    private fun populateLineChart(monthlyFrequencies: Array<Int>, monthNumbers: Array<Int>) {
        if (monthlyFrequencies.isNullOrEmpty() || monthNumbers.isNullOrEmpty()) {
            Log.e(TAG, "Monthly activations data is null or empty.")
            Toast.makeText(this, "Monthly activations data is null or empty", Toast.LENGTH_SHORT).show()
            return
        }

        // creating list of entry for line dataset
        val entries: MutableList<Entry> = mutableListOf()
        for (i in monthNumbers.indices) {
            entries.add(Entry(monthNumbers[i].toFloat(), monthlyFrequencies[i].toFloat()))
        }

        // creating line dataset
        val lineDataSet = LineDataSet(entries, "Monthly Activations")
        // styling the dataset
        lineDataSet.color = Color.BLUE
        lineDataSet.setCircleColor(Color.BLUE)
        lineDataSet.circleRadius = 5f
        lineDataSet.setDrawValues(false)

        // creating line data
        val lineData = LineData(lineDataSet)
        // setting line data to the chart
        ourLineChart.data = lineData

        // customizing x-axis
        val xAxis = ourLineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))

        xAxis.axisMinimum = 0f

        // customizing y-axis
        val yAxis = ourLineChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 12f

        // disabling right y-axis
        ourLineChart.axisRight.isEnabled = false

        // customizing legend
        val legend = ourLineChart.legend
        legend.isEnabled = false

        // animating chart
        ourLineChart.animateXY(1500, 1500, Easing.EaseInOutQuad)
    }







}